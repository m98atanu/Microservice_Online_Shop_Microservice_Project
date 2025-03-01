package com.microservices.cart.exceptionhandler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ExceptionControllerAdvice {
	
	@ExceptionHandler(QuantityNotAvailableException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ResponseBody
	ExceptionResponse handleQuantityNotAvailableException(
			QuantityNotAvailableException exception, HttpServletRequest request
			) {
		ExceptionResponse response = new ExceptionResponse();
		response.setRequestedMethod(request.getMethod());
		response.setRequestedURI(request.getRequestURI());
		response.setErrorMessage(exception.getMessage());
		return response;
	}

	@ExceptionHandler(BadJsonException.class)
	@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE)
	@ResponseBody
	ExceptionResponse handleBadJsonException(
			BadJsonException exception, HttpServletRequest request
			) {
		ExceptionResponse response = new ExceptionResponse();
		response.setRequestedMethod(request.getMethod());
		response.setRequestedURI(request.getRequestURI());
		response.setErrorMessage(exception.getMessage());
		return response;
	}
	
	@ExceptionHandler(InvalidCartIdPassedException.class)
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	@ResponseBody
	ExceptionResponse handleInvalidCartIdPassedException(
			InvalidCartIdPassedException exception, HttpServletRequest request
			) {
		ExceptionResponse response = new ExceptionResponse();
		response.setRequestedMethod(request.getMethod());
		response.setRequestedURI(request.getRequestURI());
		response.setErrorMessage(exception.getMessage());
		return response;
	}
	
	@ExceptionHandler(BadCartLineItemException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ResponseBody
	ExceptionResponse handleBadCartLineItemException(
			BadCartLineItemException exception, HttpServletRequest request
			) {
		ExceptionResponse response = new ExceptionResponse();
		response.setRequestedMethod(request.getMethod());
		response.setRequestedURI(request.getRequestURI());
		response.setErrorMessage(exception.getMessage());
		return response;
	}
	
	@ExceptionHandler(Exception.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	ExceptionResponse handleAllOtherUnHandledExceptions(
			Exception exception, HttpServletRequest request
			) {
		ExceptionResponse response = new ExceptionResponse();
		response.setRequestedMethod(request.getMethod());
		response.setRequestedURI(request.getRequestURI());
		response.setErrorMessage(exception.getMessage());
		return response;
	}
	
}
