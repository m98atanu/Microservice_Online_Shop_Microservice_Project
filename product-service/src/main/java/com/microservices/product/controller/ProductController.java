package com.microservices.product.controller;

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

import com.microservices.product.DTO.UpdatedProduct;
import com.microservices.product.model.ProductEntity;
import com.microservices.product.service.ProductService;

@RestController
@RequestMapping("/api/product")
public class ProductController {
	@Autowired ProductService productService;
	
	@PostMapping("/add")
	public ResponseEntity<Integer> addProduct(@RequestBody ProductEntity product) throws Exception{
		return new ResponseEntity<Integer>(productService.addProduct(product), HttpStatus.CREATED);
	}
	
	@GetMapping("/{productId}")
	public ResponseEntity<ProductEntity> fetchProductById(@PathVariable int productId) throws Exception{
		return new ResponseEntity<ProductEntity>(productService.fetchProductById(productId), HttpStatus.OK);
	}
	
	@PutMapping("/update/{productId}")
	public ResponseEntity<String> updateProduct(@PathVariable int productId, @RequestBody UpdatedProduct updatedProduct)throws Exception {
		return new ResponseEntity<String>(productService.updateProduct(productId, updatedProduct), HttpStatus.ACCEPTED);
	}

	@DeleteMapping("/delete/{productId}")
	public ResponseEntity<String> deleteProductById(@PathVariable int productId) throws Exception{
		return new ResponseEntity<String>(productService.deleteProductById(productId), HttpStatus.MOVED_PERMANENTLY);
	}
}
