package com.microservices.inventory.kafka;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.microservices.inventory.DTO.UpdatedInventoryQuantity;
import com.microservices.inventory.exceptionhandler.InvalidProductIdException;
import com.microservices.inventory.service.InventoryService;
import com.microservices.order.model.LineItem;
import com.microservices.order.model.OrderEntity;

@Component
public class InventoryServicEventsController {
	
	@Autowired
	InventoryService inventoryService;
	
	@Autowired
	KafkaTemplate<String, OrderEntity> kafkaPayment;
	
	@Autowired
	KafkaTemplate<String, OrderEntity> kafkaOrder;
	
	@KafkaListener(topics = "new-inventory", groupId = "inventory-group")
	public void checkInventory(OrderEntity newOrder) {
		if(isQuantityAvailableInInventoryDb(newOrder.getLineItems())) {
			//trigger an event for Payment
			System.out.println("No problem in Inventory. Let's go for payment");
			kafkaPayment.send("new-payment", newOrder);
			
			//update inventory
			newOrder.getLineItems().forEach(orderedItem->{
				int oldQuantityOfTheItem=0;
				try {
					oldQuantityOfTheItem = inventoryService.fetchInventoryByProductId(orderedItem.getProductId()).getQuantity();
				} catch (InvalidProductIdException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				int quantityOrdered = orderedItem.getQuantity();
				int currentQuantityOfTheItem = oldQuantityOfTheItem - quantityOrdered;
				UpdatedInventoryQuantity updatedInventoryQuantity = new UpdatedInventoryQuantity();
				updatedInventoryQuantity.setQuantity(currentQuantityOfTheItem);
				try {
					inventoryService.updateInventory(orderedItem.getProductId(), updatedInventoryQuantity);
				} catch (InvalidProductIdException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
		}else {
			//trigger a compensate event
			kafkaOrder.send("reverse-order", newOrder);
		}
	}
	
	@KafkaListener(topics = "payment-failed", groupId = "inventory-reverse-group")
	public void reverseUpdateInventory(OrderEntity newOrder) {
		
		kafkaOrder.send("reverse-order", newOrder);
		
		newOrder.getLineItems().forEach(orderedItem->{
			int oldQuantityOfTheItem=0;
			try {
				oldQuantityOfTheItem = inventoryService.fetchInventoryByProductId(orderedItem.getProductId()).getQuantity();
			} catch (InvalidProductIdException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			int quantityOrdered = orderedItem.getQuantity();
			int currentQuantityOfTheItem = oldQuantityOfTheItem + quantityOrdered;
			UpdatedInventoryQuantity updatedInventoryQuantity = new UpdatedInventoryQuantity();
			updatedInventoryQuantity.setQuantity(currentQuantityOfTheItem);
			try {
				inventoryService.updateInventory(orderedItem.getProductId(), updatedInventoryQuantity);
			} catch (InvalidProductIdException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	}
	
	private boolean isQuantityAvailableInInventoryDb (List<LineItem> items){
		if(items != null) {
			return items.stream()
					.allMatch(item ->
						{
							try {
								return item.getQuantity() < inventoryService.fetchInventoryByProductId(item.getProductId()).getQuantity();
							} catch (InvalidProductIdException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							return false;
						}
					);
		}
		else return false;
	}
	
	
}
