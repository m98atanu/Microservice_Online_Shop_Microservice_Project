package com.microservices.inventory.external.APIEndpoints;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductEntity {
	
	private int productId;
	private String productName;
	private String productDescription;
	private double productPrice;
}
