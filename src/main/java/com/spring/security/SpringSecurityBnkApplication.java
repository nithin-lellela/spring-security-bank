package com.spring.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
//@ComponentScan("com.spring.security.controller")
public class SpringSecurityBnkApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityBnkApplication.class, args);
	}

}
