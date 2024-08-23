package com.microservices.order.exceptionhandler;

import lombok.NoArgsConstructor;

@SuppressWarnings("serial")
@NoArgsConstructor
public class InvalidOrderIdPassedException extends Exception{
	public InvalidOrderIdPassedException(String message) {
		super(message);
	}
	
}
