package com.microservices.inventory.exceptionhandler;

import lombok.NoArgsConstructor;

@SuppressWarnings("serial")
@NoArgsConstructor
public class InvalidProductIdException extends Exception{
	public InvalidProductIdException(String errorMessage) {
		super(errorMessage);
	}
}
