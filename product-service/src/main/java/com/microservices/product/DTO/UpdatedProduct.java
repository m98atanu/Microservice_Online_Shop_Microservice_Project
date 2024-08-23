package com.microservices.product.DTO;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdatedProduct {
	private String productName;
	private String productDescription;
	private double productPrice;
	
	public Optional<String> getProductName(){
		return Optional.ofNullable(productName);
	}
	public Optional<String> getProductDescription(){
		return Optional.ofNullable(productDescription);
	}
	public Optional<Double> getProductPrice(){
		return Optional.ofNullable(productPrice);
	}
}
