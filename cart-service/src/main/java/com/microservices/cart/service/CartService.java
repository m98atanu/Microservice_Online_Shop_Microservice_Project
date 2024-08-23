package com.microservices.cart.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservices.cart.DTO.UpdatedCartItemQuantityForLineItemId;
import com.microservices.cart.exceptionhandler.BadCartLineItemException;
import com.microservices.cart.exceptionhandler.BadJsonException;
import com.microservices.cart.exceptionhandler.InvalidCartIdPassedException;
import com.microservices.cart.exceptionhandler.QuantityNotAvailableException;
import com.microservices.cart.external.APIEndpoints.InventoryServiceEndpoints;
import com.microservices.cart.model.CartEntity;
import com.microservices.cart.model.LineItem;
import com.microservices.cart.repository.CartEntityRepository;
import com.microservices.cart.repository.LineItemRepository;

@Service
public class CartService {
	@Autowired
	CartEntityRepository cartRepo;
	
	@Autowired
	LineItemRepository lineItemRepo;
	
	@Autowired
	InventoryServiceEndpoints inventoryServiceEndpoints;
	
	private boolean isQuantityAvailableInInventoryDb (List<LineItem> items){
		if(items != null) {
			return items.stream()
					.allMatch(item ->
						item.getQuantity() < inventoryServiceEndpoints.fetchInventoryByProductId(item.getProductId()).getBody().getQuantity()
					);
		}
		else return true; //to tell to create empty cart
	}
	
	public int addCart(CartEntity cart) throws QuantityNotAvailableException, BadJsonException{
		if(cart == null) throw new BadJsonException("cart can't be null");
		if(isQuantityAvailableInInventoryDb(cart.getLineItems())) {
			return cartRepo.save(cart).getCartId();
		}
		else throw new QuantityNotAvailableException("quantity not available");
	}
	
	public CartEntity fetchCartByCartId(int cartId) throws InvalidCartIdPassedException{
		Optional<CartEntity> cartInDbWithAskedCartId = cartRepo.findById(cartId);
		if(cartInDbWithAskedCartId.isPresent()) {
			return cartInDbWithAskedCartId.get();
		}
		else throw new InvalidCartIdPassedException("Invalid CartId");
	}
	
	/*
	 * 1. Updating quantity count of an existing lineItem of cart's
	 */
	public String updateQuantityOfExistingLineItem(
			int cartId, UpdatedCartItemQuantityForLineItemId updatedCartItemQuantityForLineItemId 
			) throws InvalidCartIdPassedException, QuantityNotAvailableException, BadCartLineItemException{
		Optional<CartEntity> cartInDbWithAskedId = cartRepo.findById(cartId);
		if(cartInDbWithAskedId.isPresent()) {
			List<LineItem> availableItemsList = cartInDbWithAskedId.get().getLineItems();
			
			Optional<Integer> itemIdToUpdate = Optional.of(availableItemsList.stream()
					.filter(item -> item.getItemId() == updatedCartItemQuantityForLineItemId.getItemId())
					.findFirst().get().getItemId());
					
			if(itemIdToUpdate.isPresent()) {
				LineItem itemToUpdate = lineItemRepo.findById(itemIdToUpdate.get()).get();
				itemToUpdate.setQuantity(updatedCartItemQuantityForLineItemId.getQuantity());
				
				List<LineItem> itemToUpdateList = new ArrayList<LineItem>(); //to pass parameter in isQuantityAvailableInInventoryDb()
				itemToUpdateList.add(itemToUpdate);
				
				if(isQuantityAvailableInInventoryDb(itemToUpdateList)) {
					lineItemRepo.save(itemToUpdate);
					return "Item Updated successfully";
				}
				else throw new QuantityNotAvailableException("Quantity Not Available");
			}
			else throw new BadCartLineItemException("Invalid ItemId passed");
		}
		else throw new InvalidCartIdPassedException("Invalid cartId passed");					
	}
	
	/*
	 * 2. Adding full new lineItem to existing cart
	 */
	public String updateNewLineItem(int cartId, List<LineItem> items) 
			throws QuantityNotAvailableException, InvalidCartIdPassedException, BadJsonException{
		Optional<CartEntity> cartInDbWithAskedId = cartRepo.findById(cartId);
		if(cartInDbWithAskedId.isPresent()) {
			if(items == null) throw new BadJsonException("CartItems can't be null");
			if(isQuantityAvailableInInventoryDb(items)) {
				cartInDbWithAskedId.get().setLineItems(items);
				cartRepo.save(cartInDbWithAskedId.get());
			}
			else throw new QuantityNotAvailableException("quantity not available");
		}
		else throw new InvalidCartIdPassedException("Invalid cartId");
		
		return "Cart Updated successfully";
	}
	
	public String deleteCartByCartId(int cartId)throws InvalidCartIdPassedException{
		if(cartRepo.findById(cartId).isPresent()) {
			cartRepo.deleteById(cartId);
			return "the cart has been deleted successfully :(";
		}
		else throw new InvalidCartIdPassedException("Invalid CartId");
	}
}
























