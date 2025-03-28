package com.trikynguci.springbootvinylecommercebackend.service.impl;

import com.trikynguci.springbootvinylecommercebackend.mapper.OrderItemMapper;
import com.trikynguci.springbootvinylecommercebackend.model.OrderItem;
import com.trikynguci.springbootvinylecommercebackend.service.OrderItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderItemServiceImpl  implements OrderItemService {

    private final OrderItemMapper orderItemMapper;

    @Override
    public List<OrderItem> getOrderItemsByOrderId(String orderId) {
        return List.of();
    }

    @Override
    public void saveOrderItem(OrderItem orderItem) {

    }

    @Override
    public List<OrderItem> getAllOrderItems() {
        return orderItemMapper.getAllOrderItems();
    }
}
