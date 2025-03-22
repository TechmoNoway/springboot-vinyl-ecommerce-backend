package com.trikynguci.springbootvinylecommercebackend.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.trikynguci.springbootvinylecommercebackend.model.Order;

@Mapper
public interface OrderMapper {
	
	List<Order> getAllOrders();
	
	void saveOrder(Order order);
}
