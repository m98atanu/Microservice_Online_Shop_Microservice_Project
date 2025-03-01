package com.microservices.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerAddress {
	
	@Id
	@GeneratedValue
	private int addressId;
	private int doorNo;
	private String streetName;
	private String layout;
	private String city;
	private int pincode;
	
}
