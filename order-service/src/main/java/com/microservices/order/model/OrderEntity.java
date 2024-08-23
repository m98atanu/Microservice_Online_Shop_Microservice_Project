package com.microservices.order.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class OrderEntity {
	@Id
	@GeneratedValue
	private int orderId;
	@OneToMany(cascade = CascadeType.ALL)
	private List<LineItem> lineItems;

}
