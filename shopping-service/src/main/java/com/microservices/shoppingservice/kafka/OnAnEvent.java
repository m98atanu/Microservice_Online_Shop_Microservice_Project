package com.microservices.shoppingservice.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.microservices.order.model.OrderEntity;
import com.microservices.shoppingservice.model.entity.CustomerAndOrderEntity;
import com.microservices.shoppingservice.repository.CustomerAndOrderRepository;

@Component
public class OnAnEvent {
	
	@Autowired CustomerAndOrderRepository customerAndOrderRepo;
	
	//event triggered by payment service
	@KafkaListener(topics= "order-success", groupId="shopping-service-group")
	public void onSuccessFullPayment(OrderEntity order) {
		//mapping customerId with successfully order's orderId
		CustomerAndOrderEntity customerAndOrder = new CustomerAndOrderEntity();
		customerAndOrder.setCustomerId(order.getCustomerId());
		customerAndOrder.setOrderId(order.getOrderId());
		customerAndOrderRepo.save(customerAndOrder);
	}
	
	@KafkaListener(topics= "reverse-shopping-data", groupId="shopping-service-reverse-group")
	public void onFailureTransactions (OrderEntity orderEntity) {
		customerAndOrderRepo.deleteById(orderEntity.getCustomerId());
	}
}
