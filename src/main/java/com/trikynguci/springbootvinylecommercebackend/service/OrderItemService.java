package com.trikynguci.springbootvinylecommercebackend.service;

import com.trikynguci.springbootvinylecommercebackend.model.OrderItem;

import java.util.List;

public interface OrderItemService {

    List<OrderItem> getOrderItemsByOrderId(String orderId);

    void saveOrderItem(OrderItem orderItem);

    List<OrderItem> getAllOrderItems();
}
