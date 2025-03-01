package com.microservices.shoppingservice.externalAPI.cartAPI;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "CART-SERVICE")
public interface CartServiceEndpoints {
	@PostMapping("/api/cart/add")
	public ResponseEntity<Integer> addCart(@RequestBody CartEntity cart);
	
	@GetMapping("/api/cart/{cartId}")
	public ResponseEntity<CartEntity> fetchCartByCartId(@PathVariable int cartId);
	
//	@PutMapping("/api/cart/update/quantity/{cartId}")
//	public ResponseEntity<String> updateQuantityOfExistingLineItem(
//			@PathVariable int cartId, @RequestBody UpdatedCartItemQuantityForLineItemId updatedCartItemQuantityForLineItemId 
//			);
	
	@PutMapping("/api/cart/update/{cartId}")
	public ResponseEntity<String> addNewLineItem(@PathVariable int cartId, @RequestBody List<LineItem> items);
	
	@DeleteMapping("/api/cart/delete/{cartId}")
	public ResponseEntity<String> deleteCartByCartId(@PathVariable int cartId);

}
