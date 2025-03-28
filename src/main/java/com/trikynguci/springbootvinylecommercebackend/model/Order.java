package com.trikynguci.springbootvinylecommercebackend.model;

import java.sql.Date;
import java.util.List;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order {
	private String id;
	private Long customerId;
	private Long totalPrice;
	private String status;
	private String fullname;
	private String customerAddress;
	private String customerPhone;
	private String note;
	private Date orderDate;
	private String email;
	private List<OrderItem> items;
}
