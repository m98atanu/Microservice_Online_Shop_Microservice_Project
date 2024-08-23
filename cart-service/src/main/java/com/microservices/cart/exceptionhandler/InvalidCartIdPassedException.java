package com.microservices.cart.exceptionhandler;

import lombok.NoArgsConstructor;

@SuppressWarnings("serial")
@NoArgsConstructor
public class InvalidCartIdPassedException extends Exception{
	public InvalidCartIdPassedException(String message) {
		super(message);
	}
	
}
