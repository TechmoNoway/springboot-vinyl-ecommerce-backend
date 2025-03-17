package springbootvinylecommercebackend.model;

import lombok.*;

@Data
@Builder
public class OrderItem {
    private Long id;
    private String orderId;
    private Long productId;
    private int quantity;
    private Long price;
}
