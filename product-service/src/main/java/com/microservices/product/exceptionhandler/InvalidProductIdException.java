package com.microservices.product.exceptionhandler;

import lombok.NoArgsConstructor;

@SuppressWarnings("serial")
@NoArgsConstructor
public class InvalidProductIdException extends Exception{
	public InvalidProductIdException(String errorMessage) {
		super(errorMessage);
	}
}
