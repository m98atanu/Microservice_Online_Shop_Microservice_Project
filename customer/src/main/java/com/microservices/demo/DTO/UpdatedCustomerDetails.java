package com.microservices.demo.DTO;

import java.util.Optional;

import com.microservices.demo.model.CustomerAddress;

import lombok.Setter;

@Setter
public class UpdatedCustomerDetails {
	private String customerEmail;
	private CustomerAddress customerBillingAddress;
	private CustomerAddress customerShippingAddress;
	public Optional<String> getCustomerEmail() {
		return Optional.ofNullable(customerEmail);
	}
	public Optional<CustomerAddress> getCustomerBillingAddress() {
		return Optional.ofNullable(customerBillingAddress);
	}
	public Optional<CustomerAddress> getCustomerShippingAddress() {
		return Optional.ofNullable(customerShippingAddress);
	}
	
	
}
