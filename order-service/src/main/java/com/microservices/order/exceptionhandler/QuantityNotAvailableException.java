package com.microservices.order.exceptionhandler;

@SuppressWarnings("serial")
public class QuantityNotAvailableException extends Exception{
	public QuantityNotAvailableException() {
		
	}
	public QuantityNotAvailableException(String errorMessage) {
		super(errorMessage);
	}
}
