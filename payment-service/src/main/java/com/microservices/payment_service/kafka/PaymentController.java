package com.microservices.payment_service.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.RestController;

import com.microservices.order.model.OrderEntity;

@RestController
public class PaymentController {
	
	@Autowired
	KafkaTemplate<String, OrderEntity> kafkaShoppingService;
	
	@Autowired
	KafkaTemplate<String, OrderEntity> kafkaInventoryService;
	
	//event triggered by Inventory service
	@KafkaListener(topics = "new-payment", groupId = "payment-group")
	public void doPayment(OrderEntity newOrder) {
		boolean isSuccessfullPayment= false;
		if(isSuccessfullPayment) {
			//good
			System.out.println("Payment completed successfully");
			kafkaShoppingService.send("order-success", newOrder); 
			
		}else {
			//compensate
			System.out.println("Payment failed");
			kafkaInventoryService.send("payment-failed", newOrder); //will be listen from Inventory
		}
	}
}
