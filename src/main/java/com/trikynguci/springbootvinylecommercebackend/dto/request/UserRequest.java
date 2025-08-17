package com.trikynguci.springbootvinylecommercebackend.dto.request;

import lombok.Data;
import java.sql.Date;

@Data
public class UserRequest {
    private Long id;
    private String email;
    private String phone;
    private String gender;
    private Date birthday;
    private String fullname;
    private String address;
}
