package com.trikynguci.springbootvinylecommercebackend.model;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductCategory implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long categoryId;
    private String categoryName;
}
