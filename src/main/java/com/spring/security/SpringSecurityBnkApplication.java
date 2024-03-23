package com.spring.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@SpringBootApplication
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
// prePostEnabled -> @PreAuthorize & @PostAuthorize annotations
// securedEnabled -> @Secured annotation
// jsr250Enabled  -> @RoleAllowed annotation
//@ComponentScan("com.spring.security.controller")
public class SpringSecurityBnkApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityBnkApplication.class, args);
	}

}
