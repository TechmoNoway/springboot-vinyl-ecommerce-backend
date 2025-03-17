package springbootvinylecommercebackend.dto.request;

import lombok.Data;
import springbootvinylecommercebackend.model.OrderItem;

import java.sql.Date;
import java.util.List;

@Data
public class OrderRequest {
    private Long customerId;
    private Long totalPrice;
    private String fullname;
    private String customerAddress;
    private String customerPhone;
    private String note;
    private Date orderDate;
    private String email;
    private List<OrderItem> items;
}
