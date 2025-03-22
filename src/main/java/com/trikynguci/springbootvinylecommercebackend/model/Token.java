package com.trikynguci.springbootvinylecommercebackend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Token {
    public Long id;
    public String accessToken;
    public boolean revoked;
    public boolean expired;
    public Long userId;
}