package com.trikynguci.springbootvinylecommercebackend.mapper;

import org.apache.ibatis.annotations.Mapper;
import com.trikynguci.springbootvinylecommercebackend.model.OrderItem;

import java.util.List;

@Mapper
public interface OrderItemMapper {
    List<OrderItem> getOrderItemsByOrderId(Long orderId);
    void saveOrderItem(OrderItem orderItem);
}
