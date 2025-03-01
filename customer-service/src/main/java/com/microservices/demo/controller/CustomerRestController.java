package com.microservices.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservices.demo.DTO.UpdatedCustomerDetails;
import com.microservices.demo.exceptionhandler.EmailIdNotUniqueException;
import com.microservices.demo.model.CustomerEntity;
import com.microservices.demo.service.CustomerService;

@RestController
@RequestMapping("/api/customer")
public class CustomerRestController {
	@Autowired
	CustomerService customerService;
	
	@PostMapping("/add")
	public ResponseEntity<Integer> addCustomer(@RequestBody CustomerEntity customer) throws EmailIdNotUniqueException{
		return new ResponseEntity<Integer>(customerService.addCustomer(customer), HttpStatus.CREATED);
	}
	
	@GetMapping("/fetch/{customerId}")
	public ResponseEntity<CustomerEntity> searchCustomer(@PathVariable int customerId) throws Exception{
		return new ResponseEntity<CustomerEntity>(customerService.searchCustomer(customerId), HttpStatus.OK);
	}
	
	@PutMapping("/update/{customerId}")
	public ResponseEntity<String> updateCustomer(@PathVariable int customerId, @RequestBody UpdatedCustomerDetails updatedDetails) throws Exception{
		return new ResponseEntity<String>(customerService.updateCustomer(customerId, updatedDetails), HttpStatus.ACCEPTED);
	}
	
	@DeleteMapping("/delete/{customerId}")
	public ResponseEntity<String> deleteCustomer(@PathVariable int customerId)throws Exception{
		return new ResponseEntity<String>(customerService.deleteCustomer(customerId), HttpStatus.OK);
	}

}
