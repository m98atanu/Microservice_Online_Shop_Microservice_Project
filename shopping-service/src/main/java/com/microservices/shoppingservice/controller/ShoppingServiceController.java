package com.microservices.shoppingservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservices.shoppingservice.externalAPI.customerAPI.CustomerEntity;
import com.microservices.shoppingservice.model.POJO.CustomerAndAllOrderDetails;
import com.microservices.shoppingservice.model.POJO.ProductAndInventory;
import com.microservices.shoppingservice.service.ShoppingService;

@RestController
@RequestMapping("/api/shoppingservice")
public class ShoppingServiceController {
	@Autowired ShoppingService shoppingService;
	
	@PostMapping("/products")
	public ResponseEntity<String> addProductAndInventory(@RequestBody ProductAndInventory productAndInventory) {
		return new ResponseEntity<String>(shoppingService.addProductAndInventory(productAndInventory), HttpStatus.CREATED);
	}
	
	@PostMapping("/customer")
	public ResponseEntity<String> createCustomerWithEmptyCart(@RequestBody CustomerEntity customer) {
		return new ResponseEntity<String>(shoppingService.createCustomerWithEmptyCart(customer), HttpStatus.CREATED);
	}
	
	@PutMapping("/customer/{customerId}/cart")
	public ResponseEntity<String> addToCart(@PathVariable int customerId, 
			@RequestBody List<com.microservices.shoppingservice.externalAPI.cartAPI.LineItem> cartItems) 
					throws Exception{
		return new ResponseEntity<String>(shoppingService.addToCart(customerId, cartItems), HttpStatus.ACCEPTED);
	}
	
	@PostMapping("/customer/{customerId}/order")
	public ResponseEntity<String> placeOrder(@PathVariable int customerId) throws Exception{
		return new ResponseEntity<String>(shoppingService.placeOrder(customerId), HttpStatus.CREATED);
	}
	
	@GetMapping("/customer/{customerId}/orders")
	public ResponseEntity<CustomerAndAllOrderDetails> customerWithAllTheOrders(@PathVariable int customerId) throws Exception{
		return new ResponseEntity<CustomerAndAllOrderDetails>(shoppingService.customerWithAllTheOrders(customerId), HttpStatus.OK);
	}
	
	//FALLBACK HAS NOT APPLIED YET. ALSO NECESSARY THINGS NEED TO WRITE IN application.properties FILE FOR THIS, WHICH IS YET TO COMPLETE
}
