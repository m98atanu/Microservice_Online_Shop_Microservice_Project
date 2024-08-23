package com.microservices.demo.exceptionhandler;

@SuppressWarnings("serial")
//@NoArgsConstructor
public class EmailIdNotUniqueException extends Exception {
	public EmailIdNotUniqueException() {
		
	}
	public EmailIdNotUniqueException(String errorMessage) {
		super(errorMessage);
	}
}
