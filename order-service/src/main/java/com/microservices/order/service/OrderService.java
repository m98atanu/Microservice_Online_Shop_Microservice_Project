package com.microservices.order.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservices.order.exceptionhandler.InvalidOrderIdPassedException;
import com.microservices.order.exceptionhandler.QuantityNotAvailableException;
import com.microservices.order.external.APIEndpoints.InventoryServiceEndpoints;
import com.microservices.order.model.LineItem;
import com.microservices.order.model.OrderEntity;
import com.microservices.order.repository.OrderRepository;

@Service
public class OrderService {
	@Autowired OrderRepository orderRepo;
	@Autowired InventoryServiceEndpoints inventoryServiceEndpoints;
	
	private boolean isQuantityAvailableInInventoryDb (List<LineItem> items){
		if(items != null) {
			return items.stream()
					.allMatch(item ->
						item.getQuantity() < inventoryServiceEndpoints.fetchInventoryByProductId(item.getProductId()).getBody().getQuantity()
					);
		}
		else return false;
	}
	
	public Integer addOrder(OrderEntity order) throws QuantityNotAvailableException{
		if(isQuantityAvailableInInventoryDb(order.getLineItems())) {
			return orderRepo.save(order).getOrderId();	
		}
		else throw new QuantityNotAvailableException("Quantity unavailable");
	}
	
	public OrderEntity searchOrderById(int orderId) throws InvalidOrderIdPassedException{
		Optional<OrderEntity> orderInDb = orderRepo.findById(orderId);
		if(orderInDb.isPresent()) {
			return orderInDb.get();
		}
		else throw new InvalidOrderIdPassedException("Invalid OrderId");
	}
	
	/*
	 * update order with full new order details
	 */
	public String updateOrder(int orderId, OrderEntity updatedOrder) throws InvalidOrderIdPassedException{
		Optional<OrderEntity> orderInDb = orderRepo.findById(orderId);
		if(orderInDb.isPresent()) {
			orderInDb.get().setLineItems(updatedOrder.getLineItems());
			return "Order updated successfully";
		}
		else throw new InvalidOrderIdPassedException("invalid OrderId");
	}
	
	public String deleteOrderById(int orderId) throws Exception{
		orderRepo.findById(orderId).ifPresentOrElse(
				order-> orderRepo.deleteById(orderId), 
				()-> {
					throw new RuntimeException(new InvalidOrderIdPassedException("Invalid orderId"));
				});
		return "order deleted successfully";
	}

}
