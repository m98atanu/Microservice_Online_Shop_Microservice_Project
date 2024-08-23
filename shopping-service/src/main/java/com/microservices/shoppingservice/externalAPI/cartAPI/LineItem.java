package com.microservices.shoppingservice.externalAPI.cartAPI;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LineItem {
	private int itemId;
	private int productId;
	private String produntName;
	private int quantity;
	private double price;

}
