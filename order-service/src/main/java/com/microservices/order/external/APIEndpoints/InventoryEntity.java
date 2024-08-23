package com.microservices.order.external.APIEndpoints;

import lombok.Data;

@Data
public class InventoryEntity {
	
	private int inventoryId;
	private int productId;
	private int quantity;
	
	
}
