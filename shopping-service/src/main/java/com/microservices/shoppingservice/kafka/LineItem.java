package com.microservices.shoppingservice.kafka;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LineItem {
	private int itemId;
	private int productId;
	private String productName;
	private int quantity;
	private double price;
}
