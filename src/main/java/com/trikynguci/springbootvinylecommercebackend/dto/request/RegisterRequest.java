package com.trikynguci.springbootvinylecommercebackend.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterRequest {	
	private String email;
}
