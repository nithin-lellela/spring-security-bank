package com.spring.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.spring.security.entity.Customer;
import com.spring.security.repository.ICustomerRepository;

@RestController
public class LoginController {

	@Autowired
	ICustomerRepository customerRepository;
	
	@PostMapping("/register")
	public ResponseEntity<String> registerUser(@RequestBody Customer customer){
		
		Customer savedCustomer = null;
		ResponseEntity response = null;
		try {
			savedCustomer = customerRepository.save(customer);
			if(savedCustomer.getId() > 0) {
				response = ResponseEntity
						.status(HttpStatus.CREATED)
						.body("Given user details are registered successfully");
			}
		}catch(Exception ex) {
			response = ResponseEntity
					.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("An exception occured while processing the request, " + ex.getMessage());
					
		}
		return response;
		
	}
	
}
