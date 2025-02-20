package com.microservices.order.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data //Equivalent to @Getter @Setter @RequiredArgsConstructor @ToString @EqualsAndHashCode. 
@NoArgsConstructor
public class LineItem {
	private int itemId;
	private int productId;
	private String productName;
	private int quantity;
	private double price;
}
