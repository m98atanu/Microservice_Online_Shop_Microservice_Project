package com.microservices.shoppingservice.externalAPI.inventoryAPI;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class InventoryEntity {
	private int inventoryId;
	private int productId;
	private int quantity;
}
