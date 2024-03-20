package com.spring.security.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.spring.security.entity.Customer;

@Repository
public interface ICustomerRepository extends CrudRepository<Customer, Long>{

	List<Customer> findByEmail(String email);
	
}
