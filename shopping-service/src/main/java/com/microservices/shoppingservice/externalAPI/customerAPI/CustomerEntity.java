package com.microservices.shoppingservice.externalAPI.customerAPI;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerEntity {
	
	private int customerId;
	private String customerName;
	private String customerEmail;
	private CustomerAddress customerBillingAddress;
	private CustomerAddress customerShippingAddress;
}
