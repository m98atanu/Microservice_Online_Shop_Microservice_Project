package com.microservices.shoppingservice.externalAPI.cartAPI;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CartEntity {
	private int cartId;
	private List<LineItem> lineItems;
}
