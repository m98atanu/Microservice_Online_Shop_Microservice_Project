package com.microservices.shoppingservice.kafka;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderEvent {
	private int customerId;
	private int orderId;
	private List<LineItem> lineItems;

}
