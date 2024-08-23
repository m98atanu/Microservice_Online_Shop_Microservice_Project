package com.microservices.cart.exceptionhandler;

import lombok.NoArgsConstructor;

@SuppressWarnings("serial")
@NoArgsConstructor
public class BadJsonException extends Exception{
	public BadJsonException(String errorMessage) {
		super(errorMessage);
	}
}
