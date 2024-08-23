package com.microservices.cart.exceptionhandler;

import lombok.NoArgsConstructor;

@SuppressWarnings("serial")
@NoArgsConstructor
public class BadCartLineItemException extends Exception{

	public BadCartLineItemException(String message) {
		super(message);
	}
}
