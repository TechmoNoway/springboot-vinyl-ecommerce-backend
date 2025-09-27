package com.trikynguci.springbootvinylecommercebackend.handler;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiError {
    private boolean success;
    private String error;
    private String message;
}
