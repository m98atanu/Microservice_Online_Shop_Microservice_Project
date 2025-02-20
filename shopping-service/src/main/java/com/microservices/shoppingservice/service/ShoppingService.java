package com.microservices.shoppingservice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.microservices.shoppingservice.externalAPI.cartAPI.CartEntity;
import com.microservices.shoppingservice.externalAPI.cartAPI.CartServiceEndpoints;
import com.microservices.shoppingservice.externalAPI.customerAPI.CustomerEntity;
import com.microservices.shoppingservice.externalAPI.customerAPI.CustomerServiceEndpoints;
import com.microservices.shoppingservice.externalAPI.inventoryAPI.InventoryEntity;
import com.microservices.shoppingservice.externalAPI.inventoryAPI.InventoryServiceEndpoints;
import com.microservices.shoppingservice.externalAPI.inventoryAPI.UpdatedInventoryQuantity;
import com.microservices.shoppingservice.externalAPI.orderAPI.OrderEntity;
import com.microservices.shoppingservice.externalAPI.orderAPI.OrderServiceEndpoints;
import com.microservices.shoppingservice.externalAPI.productAPI.ProductEntity;
import com.microservices.shoppingservice.externalAPI.productAPI.ProductServiceEndpoints;
import com.microservices.shoppingservice.kafka.OrderEvent;
import com.microservices.shoppingservice.model.POJO.CustomerAndAllOrderDetails;
import com.microservices.shoppingservice.model.POJO.ProductAndInventory;
import com.microservices.shoppingservice.model.entity.CustomerAndCartEntity;
import com.microservices.shoppingservice.model.entity.CustomerAndOrderEntity;
import com.microservices.shoppingservice.repository.CustomerAndCartRepository;
import com.microservices.shoppingservice.repository.CustomerAndOrderRepository;

@Service
public class ShoppingService {
	@Autowired CustomerAndCartRepository customerAndCartRepo;
	@Autowired CustomerAndOrderRepository customerAndOrderRepo;
	
	@Autowired ProductServiceEndpoints productAPI;
	@Autowired InventoryServiceEndpoints inventoryAPI;
	@Autowired CustomerServiceEndpoints customerAPI;
	@Autowired CartServiceEndpoints cartAPI;
	@Autowired OrderServiceEndpoints orderAPI;
	
	@Autowired
	KafkaTemplate<String, OrderEvent> kafkaOrder;
	
	public String addProductAndInventory(ProductAndInventory productAndInventory) {
		ProductEntity product = new ProductEntity();
		product.setProductName(productAndInventory.getProductName());
		product.setProductDescription(productAndInventory.getPrductDescription());
		product.setProductPrice(productAndInventory.getProductPrice());
		int productId = productAPI.addProduct(product).getBody(); //Product Added
		
		InventoryEntity inventory = new InventoryEntity();
		inventory.setProductId(productId);
		inventory.setQuantity(productAndInventory.getQuantity());
		inventoryAPI.addInventory(inventory);
		
		return "product and inventory updated successfully";
	}

	public String createCustomerWithEmptyCart(CustomerEntity customer) {
		int customerId = customerAPI.addCustomer(customer).getBody();
		int cartId = cartAPI.addCart(new CartEntity()).getBody();
		
		CustomerAndCartEntity customerAndCart = new CustomerAndCartEntity();
		customerAndCart.setCustomerId(customerId);
		customerAndCart.setCartId(cartId);
		customerAndCartRepo.save(customerAndCart);
		
		return "Customer and Cart Mapping done successfully";
	}
	
	public String addToCart(int customerId, 
			List<com.microservices.shoppingservice.externalAPI.cartAPI.LineItem> cartItems) 
					throws Exception{
		
		Optional<CustomerAndCartEntity> customerAndCart = customerAndCartRepo.findById(customerId);
		customerAndCart.ifPresentOrElse(
				cusAndCart->{
					cartItems.forEach(item->{
						//as price will not set to at the time of cart, I am calling product service to add price.
						item.setPrice(productAPI.fetchProductById(item.getProductId()).getBody().getProductPrice());
					});
					cartAPI.addNewLineItem(customerAndCart.get().getCartId(), cartItems);
				}, 
				()->{
					throw new RuntimeException("CustomerAndCartEntity is returning null for the customerId");
				});
		return "Your item added to the cart successfully";
	}
	
	public String placeOrder(int customerId) throws Exception{
		Optional<CustomerAndCartEntity> customerAndCart = customerAndCartRepo.findById(customerId);
		if(customerAndCart.isPresent()) {
			int cartId = customerAndCart.get().getCartId();
			
			//get All lineItems from cart
			List<com.microservices.shoppingservice.externalAPI.cartAPI.LineItem> cartLineItems =
					cartAPI.fetchCartByCartId(cartId).getBody().getLineItems();
			
			System.out.println("debug: CartLineItems: " + cartLineItems); //debug purpose
			
			//Place Order
			
			List<com.microservices.shoppingservice.externalAPI.orderAPI.LineItem> orderLineItems = new ArrayList<>();
			//To fetch order items from cartLineItems
			cartLineItems.forEach(cartItem->{
				com.microservices.shoppingservice.externalAPI.orderAPI.LineItem order = 
						new com.microservices.shoppingservice.externalAPI.orderAPI.LineItem();
				order.setProductId(cartItem.getProductId());
				order.setProductName(cartItem.getProductName());
				order.setPrice(cartItem.getPrice());
				order.setQuantity(cartItem.getQuantity());
				
				orderLineItems.add(order);
			});
			
			OrderEntity orderEntity = new OrderEntity();
			orderEntity.setLineItems(orderLineItems);
//			/*int orderId = */orderAPI.addOrder(orderEntity)/*.getBody()*/; //ORDER PLACED//Kafka communication from order service
			
			//Kafka: code changed
			OrderEvent orderEvent = new OrderEvent();
			orderEvent.setOrderId(orderEntity.getOrderId());
			List<com.microservices.shoppingservice.kafka.LineItem> lineItems = new ArrayList<>();
			orderEntity.getLineItems().forEach(item->{
				com.microservices.shoppingservice.kafka.LineItem itm = new com.microservices.shoppingservice.kafka.LineItem();
				itm.setItemId(item.getItemId());
				itm.setPrice(item.getPrice());
				itm.setProductId(item.getProductId());
				itm.setProductName(item.getProductName());
				itm.setQuantity(item.getQuantity());
				lineItems.add(itm);
			});
			orderEvent.setLineItems(lineItems);
			orderEvent.setCustomerId(customerId);
			kafkaOrder.send("new-order", orderEvent); //will listen by order-service
			
			System.out.println("debug: OrderLineItems: " + orderLineItems); //debug purpose
			
			//deleting cart for which order has placed
			cartAPI.deleteCartByCartId(cartId);
			
			//Inventory updation after successfully order placed
//			orderEntity.getLineItems().forEach(orderedItem->{
//				int oldQuantityOfTheItem = inventoryAPI.fetchInventoryByProductId(orderedItem.getProductId()).getBody().getQuantity();
//				int quantityOrdered = orderedItem.getQuantity();
//				int currentQuantityOfTheItem = oldQuantityOfTheItem - quantityOrdered;
//				UpdatedInventoryQuantity updatedInventoryQuantity = new UpdatedInventoryQuantity();
//				updatedInventoryQuantity.setQuantity(currentQuantityOfTheItem);
//				inventoryAPI.updateInventoryByProductId(orderedItem.getProductId(), updatedInventoryQuantity);
//			});
			
			//mapping customerId with successfully order's orderId
//			CustomerAndOrderEntity customerAndOrder = new CustomerAndOrderEntity();
//			customerAndOrder.setCustomerId(customerId);
//			customerAndOrder.setOrderId(orderId);
//			customerAndOrderRepo.save(customerAndOrder);
			
			return "Order has been placed successfully";
		}
		else throw new Exception("Customer is not present in Customer_Cart database");
	}
	
	public CustomerAndAllOrderDetails customerWithAllTheOrders(int customerId) throws Exception{
		List<CustomerAndOrderEntity> customerAndOrders = customerAndOrderRepo.findByCustomerId(customerId);
		
		//Debug:
		System.out.println(customerAndOrders);
		
		if(customerAndOrders != null) {
			//fetch all the orderIdsList
			List<Integer> orderIdsList =
					customerAndOrders.stream()
					.map(customerAndOrder -> customerAndOrder.getOrderId()).collect(Collectors.toList());
			
			//Debug:
			System.out.println(orderIdsList);
			
			//invoke customer service to get the customer details for the customerId passed in the request
			CustomerEntity customer = customerAPI.searchCustomer(customerId).getBody();
			
			//invoke Order service to get all the order for the customer
			List<OrderEntity> allOrdersOfTheCustomer = new ArrayList<>();
			orderIdsList.forEach(orderId->{
				allOrdersOfTheCustomer.add(orderAPI.searchOrderById(orderId).getBody());
			});
			
			//debug:
			System.out.println(allOrdersOfTheCustomer);
//			System.out.println(allOrdersOfTheCustomer.get(0).getLineItems().toString());
			
			//aggregate both the response and prepare the final response for client
			CustomerAndAllOrderDetails allDetails = new CustomerAndAllOrderDetails();
			allDetails.setCustomerId(customerId);
			allDetails.setCustomerName(customer.getCustomerName());
			allDetails.setCustomerEmail(customer.getCustomerEmail());
			allDetails.setOrders(allOrdersOfTheCustomer);
			
			//return the aggregated response to client
			return allDetails;
		}
		else throw new Exception("Please provide valid customer_id");
	}
	
}
