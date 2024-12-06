package springbootvinylecommercebackend.mapper;

import org.apache.ibatis.annotations.Mapper;
import springbootvinylecommercebackend.model.OrderItem;

import java.util.List;

@Mapper
public interface OrderItemMapper {
    List<OrderItem> findByOrderId(Long orderId);
    void insertOrderItem(OrderItem orderItem);
    void deleteOrderItemById(Long orderId);
}
