package springbootvinylecommercebackend.model;

import java.sql.Date;
import java.util.List;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Order {
	private Long id;
	private Long userId;
	private String fullname;
	private String userAddress;
	private String userPhone;
	private String note;
	private Long totalPrice;
	private Date orderDate;
	private String email;
	private List<OrderItem> items;
}
