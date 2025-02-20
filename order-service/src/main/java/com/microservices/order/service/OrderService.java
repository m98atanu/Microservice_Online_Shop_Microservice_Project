package com.microservices.order.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.microservices.order.exceptionhandler.InvalidOrderIdPassedException;
import com.microservices.order.exceptionhandler.QuantityNotAvailableException;
import com.microservices.order.external.APIEndpoints.InventoryServiceEndpoints;
import com.microservices.order.model.LineItem;
import com.microservices.order.model.OrderEntity;
import com.microservices.order.repository.OrderRepository;
import com.microservices.shoppingservice.kafka.OrderEvent;

@Service
public class OrderService {
	@Autowired OrderRepository orderRepo;
	@Autowired InventoryServiceEndpoints inventoryServiceEndpoints;
	
	@Autowired
	KafkaTemplate<String, OrderEntity> kafkaInventory;
	
	@Autowired
	KafkaTemplate<String, OrderEntity> kafkaShoppingService; //to compensate
	
	private boolean isQuantityAvailableInInventoryDb (List<LineItem> items){
		if(items != null) {
			return items.stream()
					.allMatch(item ->
						item.getQuantity() < inventoryServiceEndpoints.fetchInventoryByProductId(item.getProductId()).getBody().getQuantity()
					);
		}
		else return false;
	}
	
	@KafkaListener(topics="new-order", groupId="order-group")
	public int addOrder(OrderEvent orderEvent) throws QuantityNotAvailableException, 
									JsonMappingException, JsonProcessingException{
		
		OrderEntity order = new OrderEntity();
		order.setOrderId(orderEvent.getOrderId());
		List<com.microservices.order.model.LineItem> lineItems = new ArrayList<>();
		orderEvent.getLineItems().forEach(item->{
			com.microservices.order.model.LineItem itm = new com.microservices.order.model.LineItem();
			itm.setItemId(item.getItemId());
			itm.setPrice(item.getPrice());
			itm.setProductId(item.getProductId());
			itm.setProductName(item.getProductName());
			itm.setQuantity(item.getQuantity());
			lineItems.add(itm);
		});
		order.setLineItems(lineItems);
		order.setCustomerId(orderEvent.getCustomerId());
		OrderEntity newOrder = orderRepo.save(order);
		System.out.println("No problem in Order service. Let's go to check quantity from inventory");
		
		kafkaInventory.send("new-inventory", newOrder); //kafka
		
		return newOrder.getOrderId();
	}
	
	@KafkaListener(topics="reverse-order", groupId="order-reverse-group")
	public void reverseOrder(OrderEvent orderEvent) {
		orderRepo.deleteById(orderEvent.getOrderId());//order deleted from database
		System.out.println("Order deleted from the database successfully");
		
		OrderEntity orderEntity = new OrderEntity();
		orderEntity.setCustomerId(orderEvent.getCustomerId());
		orderEntity.setOrderId(orderEvent.getOrderId());
		orderEntity.setLineItems(null);
		kafkaShoppingService.send("reverse-shopping-data", orderEntity);
	}
	
//	public Integer addOrder(OrderEntity order) throws QuantityNotAvailableException{
//		if(isQuantityAvailableInInventoryDb(order.getLineItems())) {
//			return orderRepo.save(order).getOrderId();	
//		}
//		else throw new QuantityNotAvailableException("Quantity unavailable");
//	}
	
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
