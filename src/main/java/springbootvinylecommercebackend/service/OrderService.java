package springbootvinylecommercebackend.service;

import java.util.List;

import springbootvinylecommercebackend.model.Order;

public interface OrderService {
	
	List<Order> getAllOrders();
	
	void saveOrder(Order order);
	
	void sendOrderSuccessMail(String userEmail);
	
}
