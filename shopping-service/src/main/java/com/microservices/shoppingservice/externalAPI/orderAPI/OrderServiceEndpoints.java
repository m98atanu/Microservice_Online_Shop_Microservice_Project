package com.microservices.shoppingservice.externalAPI.orderAPI;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "ORDER-SERVICE")
public interface OrderServiceEndpoints {
	@PostMapping("/api/order/add")
	public ResponseEntity<Integer> addOrder(@RequestBody OrderEntity order);
	
	@GetMapping("/api/order/{orderId}")
	public ResponseEntity<OrderEntity> searchOrderById(@PathVariable int orderId);
	
	@PutMapping("/api/order/update/{orderId}")
	public ResponseEntity<String> updateOrder(@PathVariable int orderId, @RequestBody OrderEntity updatedOrder);
	
	@DeleteMapping("/api/order/delete/{orderId}")
	public ResponseEntity<String> deleteOrderById(@PathVariable int orderId);
}
