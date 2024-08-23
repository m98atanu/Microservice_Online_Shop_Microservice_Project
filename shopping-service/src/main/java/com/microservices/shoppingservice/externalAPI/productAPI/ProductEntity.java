package com.microservices.shoppingservice.externalAPI.productAPI;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductEntity {
	private int productId;
	private String productName;
	private String productDescription;
	private double productPrice;

}
