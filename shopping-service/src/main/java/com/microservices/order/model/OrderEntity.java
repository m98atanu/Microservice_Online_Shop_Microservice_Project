package com.microservices.order.model;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderEntity {
	private int customerId;
	private int orderId;
	private List<LineItem> lineItems;

}
