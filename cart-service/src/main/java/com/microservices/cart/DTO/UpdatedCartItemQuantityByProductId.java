package com.microservices.cart.DTO;

import jakarta.annotation.Nonnull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UpdatedCartItemQuantityByProductId {
	@Nonnull
	private int productId;
	@Nonnull
	private int quantity;
}
