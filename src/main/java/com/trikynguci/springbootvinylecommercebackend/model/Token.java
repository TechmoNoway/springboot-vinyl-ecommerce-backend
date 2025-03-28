package com.trikynguci.springbootvinylecommercebackend.model;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Token {
    public Long id;
    public String accessToken;
    public boolean revoked;
    public boolean expired;
    public Long userId;
}