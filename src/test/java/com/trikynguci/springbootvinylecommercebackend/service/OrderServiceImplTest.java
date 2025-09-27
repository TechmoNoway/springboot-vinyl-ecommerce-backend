package com.trikynguci.springbootvinylecommercebackend.service;

import com.trikynguci.springbootvinylecommercebackend.dto.request.OrderRequest;
import com.trikynguci.springbootvinylecommercebackend.exception.InsufficientStockException;
import com.trikynguci.springbootvinylecommercebackend.mapper.OrderItemMapper;
import com.trikynguci.springbootvinylecommercebackend.mapper.OrderMapper;
import com.trikynguci.springbootvinylecommercebackend.mapper.ProductMapper;
import com.trikynguci.springbootvinylecommercebackend.model.Order;
import com.trikynguci.springbootvinylecommercebackend.model.OrderItem;
import com.trikynguci.springbootvinylecommercebackend.model.Product;
import com.trikynguci.springbootvinylecommercebackend.service.impl.OrderServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceImplTest {

    @Mock
    private OrderMapper orderMapper;

    @Mock
    private OrderItemMapper orderItemMapper;

    @Mock
    private ProductMapper productMapper;

    @Mock
    private JavaMailSender mailSender;

    @InjectMocks
    private OrderServiceImpl orderService;

    private OrderRequest request;

    @BeforeEach
    public void setUp() {
        OrderItem item = OrderItem.builder().productId(1L).quantity(2).price(100L).build();
        request = new OrderRequest();
        request.setCustomerId(1L);
        request.setItems(List.of(item));
        request.setFullname("Test User");
        request.setEmail("test@example.com");
        request.setCustomerAddress("Addr");
        request.setCustomerPhone("0123");
        request.setTotalPrice(200L);
    }

    @Test
    public void testPlaceOrderSuccess() {
        Product p = new Product();
        p.setId(1L);
        p.setTitle("Vinyl A");
    p.setStockQuantity(5L);

        when(productMapper.getProductForUpdate(1L)).thenReturn(p);
        when(productMapper.decrementStockQuantity(1L, 2)).thenReturn(1);
        // orderMapper.getOrderById will be called to return the saved order id, so stub a simple Order
        when(orderMapper.getOrderById(anyString())).thenReturn(Order.builder().id("VINYL-123").build());

        Order order = orderService.placeOrder(request);

        assertNotNull(order);
        assertEquals("VINYL-123", order.getId());
        verify(orderItemMapper, times(1)).saveOrderItem(any());
        verify(productMapper, times(1)).decrementStockQuantity(1L, 2);
    }

    @Test
    public void testPlaceOrderInsufficientStock() {
        Product p = new Product();
        p.setId(1L);
        p.setTitle("Vinyl A");
    p.setStockQuantity(1L); // less than requested

        when(productMapper.getProductForUpdate(1L)).thenReturn(p);

        assertThrows(InsufficientStockException.class, () -> orderService.placeOrder(request));

        verify(orderMapper, never()).saveOrder(any());
        verify(productMapper, never()).decrementStockQuantity(anyLong(), anyInt());
    }
}
