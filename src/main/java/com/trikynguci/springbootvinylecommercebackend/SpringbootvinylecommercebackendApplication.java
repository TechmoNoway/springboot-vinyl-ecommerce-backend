package com.trikynguci.springbootvinylecommercebackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class SpringbootvinylecommercebackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootvinylecommercebackendApplication.class, args);
	}

}
