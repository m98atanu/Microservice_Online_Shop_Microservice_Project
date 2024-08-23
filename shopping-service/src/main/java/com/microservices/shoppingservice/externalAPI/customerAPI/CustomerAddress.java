package com.microservices.shoppingservice.externalAPI.customerAPI;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerAddress {
	
	private int addressId;
	private int doorNo;
	private String streetName;
	private String layout;
	private String city;
	private int pincode;
	
}
