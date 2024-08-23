package com.microservices.demo.exceptionhandler;

import lombok.NoArgsConstructor;

@SuppressWarnings("serial")
@NoArgsConstructor
public class InvalidCustomerIdException extends Exception{
	public InvalidCustomerIdException(String errorMessage) {
		super(errorMessage);
	}
}
