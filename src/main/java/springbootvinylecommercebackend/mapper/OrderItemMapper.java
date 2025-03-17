package springbootvinylecommercebackend.mapper;

import org.apache.ibatis.annotations.Mapper;
import springbootvinylecommercebackend.model.Order;
import springbootvinylecommercebackend.model.OrderItem;

import java.util.List;

@Mapper
public interface OrderItemMapper {
    List<OrderItem> getOrderItemsById(Long orderId);
    void saveOrderItem(OrderItem orderItem);
}
