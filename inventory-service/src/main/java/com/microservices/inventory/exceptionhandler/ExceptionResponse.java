package com.microservices.inventory.exceptionhandler;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ExceptionResponse {
	
	private String errorMessage;
	private String requestedURI;
	private String requestedMethod;

}
