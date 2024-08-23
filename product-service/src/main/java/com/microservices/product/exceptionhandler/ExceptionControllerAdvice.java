package com.microservices.product.exceptionhandler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ExceptionControllerAdvice {

	@ExceptionHandler(InvalidProductIdException.class)
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	@ResponseBody
	ExceptionResponse handleInvalidProductIdException(
			InvalidProductIdException exception, HttpServletRequest request
			) {
		ExceptionResponse response = new ExceptionResponse();
		response.setRequestedMethod(request.getMethod());
		response.setRequestedURI(request.getRequestURI());
		response.setErrorMessage(exception.getMessage());
		return response;
	}
	
	@ExceptionHandler(NonUniqueProductException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ResponseBody
	ExceptionResponse handleNonUniqueProductException(
			NonUniqueProductException exception, HttpServletRequest request
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
