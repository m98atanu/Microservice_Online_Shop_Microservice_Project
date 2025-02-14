package com.microservices.cart.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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

import com.microservices.cart.DTO.UpdatedCartItemQuantityForLineItemId;
import com.microservices.cart.model.CartEntity;
import com.microservices.cart.model.LineItem;
import com.microservices.cart.service.ICartService;

@RestController
@RequestMapping("/api/cart")
public class CartController {
	
	@Qualifier("CartServiceImpl")
	@Autowired ICartService cartService;
	
	@PostMapping("/add")
	public ResponseEntity<Integer> addCart(@RequestBody CartEntity cart) throws Exception{
		return new ResponseEntity<Integer>(cartService.addCart(cart), HttpStatus.CREATED);
	}
	
	@GetMapping("/{cartId}")
	public ResponseEntity<CartEntity> fetchCartByCartId(@PathVariable int cartId) throws Exception{
		return new ResponseEntity<CartEntity>(cartService.fetchCartByCartId(cartId), HttpStatus.OK);
	}
	
	@PutMapping("/update/quantity/{cartId}")
	public ResponseEntity<String> updateQuantityOfExistingLineItem(
			@PathVariable int cartId, @RequestBody UpdatedCartItemQuantityForLineItemId updatedCartItemQuantityForLineItemId 
			) throws Exception{
		return new ResponseEntity<String>(cartService.updateQuantityOfExistingLineItem(cartId, updatedCartItemQuantityForLineItemId), HttpStatus.ACCEPTED);
	}
	
	@PutMapping("/update/{cartId}")
	public ResponseEntity<String> addNewLineItem(@PathVariable int cartId, @RequestBody List<LineItem> items) throws Exception{
		return new ResponseEntity<String>(cartService.updateNewLineItem(cartId, items), HttpStatus.ACCEPTED);
	}
	
	@DeleteMapping("/delete/{cartId}")
	public ResponseEntity<String> deleteCartByCartId(@PathVariable int cartId)throws Exception{
		return new ResponseEntity<String>(cartService.deleteCartByCartId(cartId), HttpStatus.OK);
	}

}
