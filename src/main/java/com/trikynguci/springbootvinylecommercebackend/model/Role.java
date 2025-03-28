package com.trikynguci.springbootvinylecommercebackend.model;

import lombok.*;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Role {
    private Long id;
    private String name;
}
