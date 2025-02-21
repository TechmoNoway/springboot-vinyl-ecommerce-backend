package springbootvinylecommercebackend.model;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class Category implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String name;
}
