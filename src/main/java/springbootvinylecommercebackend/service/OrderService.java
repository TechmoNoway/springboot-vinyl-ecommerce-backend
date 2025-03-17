package springbootvinylecommercebackend.service;

import java.util.List;

import springbootvinylecommercebackend.dto.request.OrderRequest;
import springbootvinylecommercebackend.model.Order;

public interface OrderService {
	
	List<Order> getAllOrders();
	
	void saveOrder(OrderRequest order);
	
	void sendOrderSuccessMail(String userEmail);
	
}
