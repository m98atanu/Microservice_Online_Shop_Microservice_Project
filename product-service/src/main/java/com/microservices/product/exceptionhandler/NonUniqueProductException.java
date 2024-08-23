package com.microservices.product.exceptionhandler;

import lombok.NoArgsConstructor;

@SuppressWarnings("serial")
@NoArgsConstructor
public class NonUniqueProductException extends Exception {
	public NonUniqueProductException(String errorMessage) {
		super(errorMessage);
	}
}
