package springbootvinylecommercebackend.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import springbootvinylecommercebackend.model.Order;

@Mapper
public interface OrderMapper {
	
	List<Order> getAllOrders();
	
	void saveOrder(Order order);
}
