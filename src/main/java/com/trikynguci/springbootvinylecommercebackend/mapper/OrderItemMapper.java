package com.trikynguci.springbootvinylecommercebackend.mapper;

import org.apache.ibatis.annotations.Mapper;
import com.trikynguci.springbootvinylecommercebackend.model.OrderItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OrderItemMapper {
    List<OrderItem> getOrderItemsByOrderId(@Param("orderId") String orderId);

    void saveOrderItem(OrderItem orderItem);

    List<OrderItem> getAllOrderItems();
}
