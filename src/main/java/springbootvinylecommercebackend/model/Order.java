package springbootvinylecommercebackend.model;

import java.sql.Date;
import java.time.Instant;
import java.util.List;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Order {
	private Long id;
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
	private Instant createdAt;
}
