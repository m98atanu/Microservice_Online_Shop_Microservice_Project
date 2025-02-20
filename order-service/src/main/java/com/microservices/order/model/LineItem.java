package com.microservices.order.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "LineItem_for_orderservice")
@Data //Equivalent to @Getter @Setter @RequiredArgsConstructor @ToString @EqualsAndHashCode. 
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
