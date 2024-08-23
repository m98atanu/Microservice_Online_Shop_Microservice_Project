package com.microservices.demo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservices.demo.DTO.UpdatedCustomerDetails;
import com.microservices.demo.model.CustomerEntity;
import com.microservices.demo.repository.CustomerAddressRepository;
import com.microservices.demo.repository.CustomerRepository;

@Service
public class CustomerService {
	
	@Autowired CustomerRepository customerRepo;
	@Autowired CustomerAddressRepository customerAddressRepo;
	
	public int addCustomer (CustomerEntity customer) throws Exception{
		Optional<CustomerEntity> customerInDbWithSameEmail = 
				customerRepo.findByCustomerEmail(customer.getCustomerEmail());
		if(customerInDbWithSameEmail.isEmpty()) {
			return customerRepo.save(customer).getCustomerId();
		}
		else throw new Exception("The email address has used, Please provide unique email id to proceed");
	}
	
	public CustomerEntity searchCustomer(int customerId) throws Exception {
		Optional<CustomerEntity> customer = customerRepo.findById(customerId);
		if(customer.isPresent()) {
			return customer.get();
		}
		else throw new Exception("Invalid Customer Id");
	}
	
	public String updateCustomer(int customerId, UpdatedCustomerDetails updatedDetails) throws Exception{
		Optional<CustomerEntity>customerInDb = customerRepo.findById(customerId);
		customerInDb.ifPresentOrElse(
				customer->{
					CustomerEntity customerToUpdate = customerInDb.get();
					updatedDetails.getCustomerEmail().ifPresent(email-> customerToUpdate.setCustomerEmail(email));
					updatedDetails.getCustomerBillingAddress().ifPresent(adrs-> customerToUpdate.setCustomerBillingAddress(adrs));
					updatedDetails.getCustomerShippingAddress().ifPresent(adrs-> customerToUpdate.setCustomerShippingAddress(adrs));
				},
				()-> {
					throw new RuntimeException("Invalid customerId");
				});
		return "hi "+customerInDb.get().getCustomerName()+"!!! Your data has beeen upaded successfully :)";
	}
	
	public String deleteCustomer(int customerId)throws Exception{
		Optional<CustomerEntity> customerInDb = customerRepo.findById(customerId);
		customerInDb.ifPresentOrElse(
				(customer)-> customerRepo.deleteById(customerId),
				()-> {
					throw new RuntimeException("Invalid customerId");
				}
				);
		return "Hi "+ customerInDb.get().getCustomerName()+" Your data has been deleted succussfully, "
				+ "you are no longer a customer of this ShoppingKart :(";
	}
}


















