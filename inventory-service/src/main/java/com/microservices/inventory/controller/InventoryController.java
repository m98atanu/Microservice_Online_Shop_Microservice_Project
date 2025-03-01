package com.microservices.inventory.controller;

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

import com.microservices.inventory.DTO.UpdatedInventoryQuantity;
import com.microservices.inventory.model.InventoryEntity;
import com.microservices.inventory.service.InventoryService;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {
	@Autowired
	InventoryService inventoryService;
	
	@PostMapping("/add")
	public ResponseEntity<Integer> addInventory(@RequestBody InventoryEntity inventory) throws Exception{
		return new ResponseEntity<Integer>(inventoryService.addInventory(inventory), HttpStatus.CREATED);
	}

	@GetMapping("/{inventoryId}")
	public ResponseEntity<InventoryEntity> fetchInventoryById(@PathVariable int inventoryId) throws Exception{
		return new ResponseEntity<InventoryEntity>(inventoryService.fetchInventoryById(inventoryId), HttpStatus.OK);
	}
	
	@GetMapping("/fetchByProductId/{productId}")
	public ResponseEntity<InventoryEntity> fetchInventoryByProductId(@PathVariable int productId) throws Exception{
		return new ResponseEntity<InventoryEntity>(inventoryService.fetchInventoryByProductId(productId), HttpStatus.OK);
	}
	
	@PutMapping("/update/{productId}")
	public ResponseEntity<String> updateInventoryByProductId(@PathVariable int productId, @RequestBody UpdatedInventoryQuantity updatedQuantity) throws Exception{
		return new ResponseEntity<String>(inventoryService.updateInventory(productId, updatedQuantity), HttpStatus.ACCEPTED);
	}
	
	@DeleteMapping("/delete/{inventoryId}")
	public ResponseEntity<String> deleteInventoryById(@PathVariable int inventoryId) throws Exception{
		return new ResponseEntity<String>(inventoryService.deleteInventoryById(inventoryId), HttpStatus.MOVED_PERMANENTLY);
	}
}
