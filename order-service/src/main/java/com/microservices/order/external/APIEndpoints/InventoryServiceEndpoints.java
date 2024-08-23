package com.microservices.order.external.APIEndpoints;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "INVENTORY-SERVICE")
public interface InventoryServiceEndpoints {

	@GetMapping("/api/inventory/fetchByProductId/{productId}")
	public ResponseEntity<InventoryEntity> fetchInventoryByProductId(@PathVariable int productId);
}
