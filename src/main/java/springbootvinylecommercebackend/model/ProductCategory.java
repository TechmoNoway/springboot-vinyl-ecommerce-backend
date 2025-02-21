package springbootvinylecommercebackend.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class ProductCategory implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long categoryId;
    private String categoryName;
}
