package com.microservices.product.model;

import jakarta.annotation.Nonnull;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductEntity {
	@Id
	@GeneratedValue
	private int productId;
	
	@Nonnull
	private String productName;
	
	@Nonnull
	private String productDescription;
	
	@Nonnull
	private double productPrice;

}
