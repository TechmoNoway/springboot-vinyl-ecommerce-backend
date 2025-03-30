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
    private String productTitle;
    private String productPosterUrl;
    private int quantity;
    private Long price;
}
