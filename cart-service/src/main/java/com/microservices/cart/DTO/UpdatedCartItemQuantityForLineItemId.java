package com.microservices.cart.DTO;

import jakarta.annotation.Nonnull;
import lombok.Data;

@Data
public class UpdatedCartItemQuantityForLineItemId {
	@Nonnull
	private int itemId;
	@Nonnull
	private int quantity;
}
