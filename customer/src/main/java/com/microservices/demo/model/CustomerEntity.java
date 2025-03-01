package com.microservices.demo.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerEntity {
	
	@Id
	@GeneratedValue
	private int customerId;
	private String customerName;
	private String customerEmail;
	
	@OneToOne(cascade = CascadeType.ALL)
	private CustomerAddress customerBillingAddress;
	
	@OneToOne(cascade = CascadeType.ALL)
	private CustomerAddress customerShippingAddress;
}
