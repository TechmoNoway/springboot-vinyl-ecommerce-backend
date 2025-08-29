package com.trikynguci.springbootvinylecommercebackend.dto.request;

import lombok.Data;
import java.sql.Date;

@Data
public class UpdateUserProfileRequest {
	private Long id;
	private String email;
	private String phone;
	private String gender;
	private String fullname;
	private Date birthday;
	private String address;
	private String avatar;
}
