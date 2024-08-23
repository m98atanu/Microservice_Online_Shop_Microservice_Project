package com.microservices.inventory.model;

import jakarta.annotation.Nonnull;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class InventoryEntity {
	@Id
	@GeneratedValue
	private int inventoryId;
	
	@Nonnull
	private int productId;
	private int quantity;
}
