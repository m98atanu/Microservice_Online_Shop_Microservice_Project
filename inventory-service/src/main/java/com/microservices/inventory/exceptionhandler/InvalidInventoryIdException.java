package com.microservices.inventory.exceptionhandler;

import lombok.NoArgsConstructor;

@SuppressWarnings("serial")
@NoArgsConstructor
public class InvalidInventoryIdException extends Exception {
	public InvalidInventoryIdException(String errorMessage) {
		super(errorMessage);
	}
}
