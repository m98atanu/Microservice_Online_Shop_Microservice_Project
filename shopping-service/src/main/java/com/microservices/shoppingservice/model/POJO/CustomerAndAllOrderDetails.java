package com.microservices.shoppingservice.model.POJO;

import java.util.List;

import com.microservices.shoppingservice.externalAPI.orderAPI.OrderEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerAndAllOrderDetails {
	private int customerId;
	private String customerName;
	private String customerEmail;
	private List<OrderEntity> orders;
}
