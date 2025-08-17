package com.trikynguci.springbootvinylecommercebackend.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginRequest {
	private String email;
	String password;
}
