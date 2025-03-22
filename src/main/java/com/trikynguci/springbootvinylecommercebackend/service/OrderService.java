package com.trikynguci.springbootvinylecommercebackend.service;

import java.util.List;

import com.trikynguci.springbootvinylecommercebackend.dto.request.OrderRequest;
import com.trikynguci.springbootvinylecommercebackend.model.Order;

public interface OrderService {
	
	List<Order> getAllOrders();
	
	void saveOrder(OrderRequest order);
	
	void sendOrderSuccessMail(String userEmail);
	
}
