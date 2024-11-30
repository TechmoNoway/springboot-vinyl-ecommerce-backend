package springbootvinylecommercebackend.model;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
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
}
