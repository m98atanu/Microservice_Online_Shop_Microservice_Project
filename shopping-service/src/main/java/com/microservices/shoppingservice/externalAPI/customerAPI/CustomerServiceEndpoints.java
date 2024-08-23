package com.microservices.shoppingservice.externalAPI.customerAPI;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient( name ="CUSTOMER-SERVICE")
public interface CustomerServiceEndpoints {
	@PostMapping("/api/customer/add")
	public ResponseEntity<Integer> addCustomer(@RequestBody CustomerEntity customer);
	
	@GetMapping("/api/customer/fetch/{customerId}")
	public ResponseEntity<CustomerEntity> searchCustomer(@PathVariable int customerId);
	
//	@PutMapping("/api/customer/update/{customerId}")
//	public ResponseEntity<String> updateCustomer(@PathVariable int customerId, @RequestBody UpdatedCustomerDetails updatedDetails);
	
	@DeleteMapping("/api/customer/delete/{customerId}")
	public ResponseEntity<String> deleteCustomer(@PathVariable int customerId);
}
