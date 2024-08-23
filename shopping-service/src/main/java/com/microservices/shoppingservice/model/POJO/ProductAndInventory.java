package com.microservices.shoppingservice.model.POJO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductAndInventory {
	private String productName;
	private String prductDescription;
	private double productPrice;
	private int quantity;
}
