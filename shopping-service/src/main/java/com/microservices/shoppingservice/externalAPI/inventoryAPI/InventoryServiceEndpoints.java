package com.microservices.shoppingservice.externalAPI.inventoryAPI;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "INVENTORY-SERVICE")
public interface InventoryServiceEndpoints {
	@PostMapping("/api/inventory/add")
	public ResponseEntity<Integer> addInventory(@RequestBody InventoryEntity inventory);

	@GetMapping("/api/inventory/{inventoryId}")
	public ResponseEntity<InventoryEntity> fetchInventoryById(@PathVariable int inventoryId);
	
	@GetMapping("/api/inventory/fetchByProductId/{productId}")
	public ResponseEntity<InventoryEntity> fetchInventoryByProductId(@PathVariable int productId);
	
	@PutMapping("/api/inventory/update/{productId}")
	public ResponseEntity<String> updateInventoryByProductId(@PathVariable int productId, @RequestBody UpdatedInventoryQuantity updatedQuantity);
	
	@DeleteMapping("/api/inventory/delete/{inventoryId}")
	public ResponseEntity<String> deleteInventoryById(@PathVariable int inventoryId);
}
