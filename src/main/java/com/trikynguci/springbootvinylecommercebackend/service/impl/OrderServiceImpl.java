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
import com.trikynguci.springbootvinylecommercebackend.exception.BadRequestException;
import com.trikynguci.springbootvinylecommercebackend.exception.InsufficientStockException;
import com.trikynguci.springbootvinylecommercebackend.exception.NotFoundException;
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
        if (orderRequest.getItems() == null || orderRequest.getItems().isEmpty()) {
            throw new BadRequestException("Order must contain at least one item");
        }

        for (OrderItem item : orderRequest.getItems()) {
            int maxOptimisticAttempts = 3;
            boolean optimisticSucceeded = false;
            for (int attempt = 0; attempt < maxOptimisticAttempts; attempt++) {
                var current = productMapper.getProductById(item.getProductId());
                if (current == null) {
                    throw new NotFoundException("Product not found: " + item.getProductId());
                }
                if (current.getStockQuantity() == null || current.getStockQuantity() < item.getQuantity()) {
                    throw new InsufficientStockException("Not enough stock for product: " + current.getTitle());
                }

                int updated = productMapper.decrementStockQuantityWithVersion(item.getProductId(), item.getQuantity(), current.getVersion());
                if (updated > 0) {
                    optimisticSucceeded = true;
                    break;
                }
                try {
                    Thread.sleep(50L * (attempt + 1));
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }

            if (!optimisticSucceeded) {
                var product = productMapper.getProductForUpdate(item.getProductId());
                if (product == null) {
                    throw new NotFoundException("Product not found: " + item.getProductId());
                }
                if (product.getStockQuantity() == null || product.getStockQuantity() < item.getQuantity()) {
                    throw new InsufficientStockException("Not enough stock for product: " + product.getTitle());
                }
                int updated = productMapper.decrementStockQuantity(item.getProductId(), item.getQuantity());
                if (updated == 0) {
                    throw new InsufficientStockException("Not enough stock for product (concurrent update): " + item.getProductId());
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
        }

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
                        Thread.sleep(1000L * attempts); 
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
