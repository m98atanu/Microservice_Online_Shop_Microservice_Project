package com.microservices.shoppingservice.externalAPI.orderAPI;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderEntity {
	private int orderId;
	private List<LineItem> lineItems;

}
