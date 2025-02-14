package com.microservices.cart.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class LineItem {
	@Id
	@GeneratedValue
	private int itemId;
	private int productId;
	private String productName;
	private int quantity;
	private double price;

}
