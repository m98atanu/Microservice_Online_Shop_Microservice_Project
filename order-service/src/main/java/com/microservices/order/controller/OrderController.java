package com.microservices.order.controller;

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

import com.microservices.order.model.OrderEntity;
import com.microservices.order.service.OrderService;

@RestController
@RequestMapping("/api/order")
public class OrderController {
	
	@Autowired OrderService orderService;
	
//	@PostMapping("/add")
//	public ResponseEntity<Integer> addOrder(@RequestBody OrderEntity order) throws Exception{
//		return new ResponseEntity<Integer>(orderService.addOrder(order), HttpStatus.CREATED);
//	}
	
	@GetMapping("/{orderId}")
	public ResponseEntity<OrderEntity> searchOrderById(@PathVariable int orderId) throws Exception{
		return new ResponseEntity<OrderEntity>(orderService.searchOrderById(orderId), HttpStatus.OK);
	}
	
	@PutMapping("/update/{orderId}")
	public ResponseEntity<String> updateOrder(@PathVariable int orderId, @RequestBody OrderEntity updatedOrder) throws Exception{
		return new ResponseEntity<String>(orderService.updateOrder(orderId, updatedOrder), HttpStatus.ACCEPTED);
	}
	
	@DeleteMapping("delete/{orderId}")
	public ResponseEntity<String> deleteOrderById(@PathVariable int orderId) throws Exception{
		return new ResponseEntity<String>(orderService.deleteOrderById(orderId), HttpStatus.MOVED_PERMANENTLY);
	}

}
