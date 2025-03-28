package com.trikynguci.springbootvinylecommercebackend.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class OrderItem {
    private Long id;
    private String orderId;
    private Long productId;
    private int quantity;
    private Long price;
}
