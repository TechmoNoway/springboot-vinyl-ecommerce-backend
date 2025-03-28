package com.trikynguci.springbootvinylecommercebackend.model;

import lombok.*;

import java.io.Serializable;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Category implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String name;
}
