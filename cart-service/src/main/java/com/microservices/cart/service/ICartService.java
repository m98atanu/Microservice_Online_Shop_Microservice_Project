package com.microservices.cart.service;

import java.util.List;

import com.microservices.cart.DTO.UpdatedCartItemQuantityForLineItemId;
import com.microservices.cart.exceptionhandler.BadCartLineItemException;
import com.microservices.cart.exceptionhandler.BadJsonException;
import com.microservices.cart.exceptionhandler.InvalidCartIdPassedException;
import com.microservices.cart.exceptionhandler.QuantityNotAvailableException;
import com.microservices.cart.model.CartEntity;
import com.microservices.cart.model.LineItem;

public interface ICartService {
	
	int addCart(CartEntity cart) throws QuantityNotAvailableException, BadJsonException;
	
	CartEntity fetchCartByCartId(int cartId) throws InvalidCartIdPassedException;
	
	String updateQuantityOfExistingLineItem(
			int cartId, UpdatedCartItemQuantityForLineItemId updatedCartItemQuantityForLineItemId 
			) throws InvalidCartIdPassedException, QuantityNotAvailableException, BadCartLineItemException;
	
	String updateNewLineItem(int cartId, List<LineItem> items)throws QuantityNotAvailableException, InvalidCartIdPassedException, BadJsonException;
	
	String deleteCartByCartId(int cartId)throws InvalidCartIdPassedException;

}
