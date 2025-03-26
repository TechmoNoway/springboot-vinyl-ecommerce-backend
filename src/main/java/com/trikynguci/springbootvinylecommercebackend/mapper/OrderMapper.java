package com.trikynguci.springbootvinylecommercebackend.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.trikynguci.springbootvinylecommercebackend.model.Order;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface OrderMapper {
	
	List<Order> getAllOrders();
	
	void saveOrder(Order order);

	List<Order> getOrdersByUserId(@Param("userId") Long userId);

	Order getOrderById(@Param("id") String id);
}
