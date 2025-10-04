package com.trikynguci.springbootvinylecommercebackend.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import com.trikynguci.springbootvinylecommercebackend.dto.request.OrderRequest;
import com.trikynguci.springbootvinylecommercebackend.mapper.OrderItemMapper;
import com.trikynguci.springbootvinylecommercebackend.mapper.ProductMapper;
import com.trikynguci.springbootvinylecommercebackend.mapper.OrderMapper;
import com.trikynguci.springbootvinylecommercebackend.model.Order;
import com.trikynguci.springbootvinylecommercebackend.model.OrderItem;
import com.trikynguci.springbootvinylecommercebackend.service.OrderService;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderMapper orderMapper;

    private final OrderItemMapper orderItemMapper;

    private final ProductMapper productMapper;

    private final JavaMailSender mailSender;

    @Override
    public List<Order> getAllOrders() {
        return orderMapper.getAllOrders();
    }

    @Override
    @Transactional
    public Order placeOrder(OrderRequest orderRequest) {
        // validate input
        if (orderRequest.getItems() == null || orderRequest.getItems().isEmpty()) {
            throw new com.trikynguci.springbootvinylecommercebackend.exception.BadRequestException("Order must contain at least one item");
        }

        // Try optimistic updates first (version-based) to handle high-concurrency paths.
        // If optimistic retries fail, fall back to pessimistic locking (SELECT FOR UPDATE).
        for (OrderItem item : orderRequest.getItems()) {
            int maxOptimisticAttempts = 3;
            boolean optimisticSucceeded = false;
            for (int attempt = 0; attempt < maxOptimisticAttempts; attempt++) {
                var current = productMapper.getProductById(item.getProductId());
                if (current == null) {
                    throw new com.trikynguci.springbootvinylecommercebackend.exception.NotFoundException("Product not found: " + item.getProductId());
                }
                if (current.getStockQuantity() == null || current.getStockQuantity() < item.getQuantity()) {
                    throw new com.trikynguci.springbootvinylecommercebackend.exception.InsufficientStockException("Not enough stock for product: " + current.getTitle());
                }

                int updated = productMapper.decrementStockQuantityWithVersion(item.getProductId(), item.getQuantity(), current.getVersion());
                if (updated > 0) {
                    optimisticSucceeded = true;
                    break;
                }
                // small backoff before retry
                try {
                    Thread.sleep(50L * (attempt + 1));
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }

            if (!optimisticSucceeded) {
                // fallback to pessimistic locking: SELECT FOR UPDATE then safe decrement
                var product = productMapper.getProductForUpdate(item.getProductId());
                if (product == null) {
                    throw new com.trikynguci.springbootvinylecommercebackend.exception.NotFoundException("Product not found: " + item.getProductId());
                }
                if (product.getStockQuantity() == null || product.getStockQuantity() < item.getQuantity()) {
                    throw new com.trikynguci.springbootvinylecommercebackend.exception.InsufficientStockException("Not enough stock for product: " + product.getTitle());
                }
                int updated = productMapper.decrementStockQuantity(item.getProductId(), item.getQuantity());
                if (updated == 0) {
                    throw new com.trikynguci.springbootvinylecommercebackend.exception.InsufficientStockException("Not enough stock for product (concurrent update): " + item.getProductId());
                }
            }
        }

    String generatedOrderId = "VINYL-" + UUID.randomUUID().toString().replace("-", "").substring(0, 10);

        Order order = Order.builder()
                .id(generatedOrderId)
                .customerId(orderRequest.getCustomerId())
                .totalPrice(orderRequest.getTotalPrice())
                .fullname(orderRequest.getFullname())
                .status("PAID")
                .customerAddress(orderRequest.getCustomerAddress())
                .customerPhone(orderRequest.getCustomerPhone())
                .note(orderRequest.getNote())
                .email(orderRequest.getEmail())
                .build();

    orderMapper.saveOrder(order);

        for (OrderItem item : orderRequest.getItems()) {
            OrderItem orderItem = OrderItem.builder()
                    .orderId(generatedOrderId)
                    .productId(item.getProductId())
                    .quantity(item.getQuantity())
                    .price(item.getPrice())
                    .build();
            orderItemMapper.saveOrderItem(orderItem);

            // decrement stock in DB and check rows affected to detect concurrent stock depletion
            int updated = productMapper.decrementStockQuantity(item.getProductId(), item.getQuantity());
            if (updated == 0) {
                // This means either not enough stock or concurrent update. Fail the whole transaction.
                throw new com.trikynguci.springbootvinylecommercebackend.exception.InsufficientStockException("Not enough stock for product (concurrent update): " + item.getProductId());
            }
        }

        // Load and return the saved order
        return orderMapper.getOrderById(generatedOrderId);
    }

    @Override
    @Async
    public void sendOrderSuccessMail(String userEmail) {
        String from = "nguyentriky0604@gmail";
        int attempts = 0;
        int maxAttempts = 3;
        while (attempts < maxAttempts) {
            attempts++;
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = null;
            try {
                String htmlContent = new String(Files.readAllBytes(Path.of("D:\\STUDYSPACE\\ALLPROJECTS\\springboot-vinyl-ecommerce-backend\\src\\main\\resources\\email-template\\OrderEmail.html")));

                helper = new MimeMessageHelper(message, true);
                helper.setSubject("Thank You For Order Our Product");
                helper.setFrom(from);
                helper.setTo(userEmail);
                helper.setText(htmlContent, true);

                mailSender.send(message);
                log.info("Order confirmation email sent to {} on attempt {}", userEmail, attempts);
                break;
            } catch (MessagingException | IOException ex) {
                log.error("Failed to send order email to {} on attempt {}: {}", userEmail, attempts, ex.getMessage());
                if (attempts >= maxAttempts) {
                    log.error("Giving up sending order email to {} after {} attempts", userEmail, attempts);
                } else {
                    try {
                        Thread.sleep(1000L * attempts); // backoff
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                }
            }
        }
    }

    @Override
    public List<Order> getOrdersByUserId(Long userId) {
        return orderMapper.getOrdersByUserId(userId);
    }

    @Override
    public Order getOrderById(String orderId) {
        return orderMapper.getOrderById(orderId);
    }

}
