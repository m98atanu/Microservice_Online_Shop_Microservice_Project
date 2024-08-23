package com.microservices.inventory.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservices.inventory.DTO.UpdatedInventoryQuantity;
import com.microservices.inventory.exceptionhandler.InvalidInventoryIdException;
import com.microservices.inventory.exceptionhandler.InvalidProductIdException;
import com.microservices.inventory.external.APIEndpoints.ProductEntity;
import com.microservices.inventory.external.APIEndpoints.ProductServiceEndpoints;
import com.microservices.inventory.model.InventoryEntity;
import com.microservices.inventory.repository.InventoryRepository;

@Service
public class InventoryService {
	@Autowired
	InventoryRepository inventoryRepo;
	
	@Autowired
	ProductServiceEndpoints productServiceEndpoints;
	
	public int addInventory(InventoryEntity inventory) throws InvalidProductIdException{
		Optional<ProductEntity> productInDbWithSameProductId = 
				Optional.of(productServiceEndpoints.fetchProductById(inventory.getProductId()).getBody());
		Optional<InventoryEntity> InventoryInDbWithSameProductName =
				inventoryRepo.findByProductId(inventory.getProductId());
		if(productInDbWithSameProductId.isPresent() && InventoryInDbWithSameProductName.isEmpty()) {
			return inventoryRepo.save(inventory).getInventoryId();
		}
		else throw new InvalidProductIdException("Invalid ProductId");
	}
	
	public InventoryEntity fetchInventoryById(int inventoryId) throws InvalidInventoryIdException{
		Optional<InventoryEntity> inventoryInDb = inventoryRepo.findById(inventoryId);
		if(inventoryInDb.isPresent()) {
			return inventoryInDb.get();
		}
		else throw new InvalidInventoryIdException("Invalid inventoryId");
	}
	
	public InventoryEntity fetchInventoryByProductId(int productId) throws InvalidProductIdException{
		Optional<InventoryEntity> inventoryInDbWithSameProductId = inventoryRepo.findByProductId(productId);
		if(inventoryInDbWithSameProductId.isPresent()) {
			return inventoryInDbWithSameProductId.get();
		}
		else throw new InvalidProductIdException("Invalid productId");
	}
	
	public String updateInventory(int productId, UpdatedInventoryQuantity updatedQuantity) throws InvalidProductIdException{
		inventoryRepo.findByProductId(productId).ifPresentOrElse(
				inventoryToUpdate->{
					updatedQuantity.getQuantity().ifPresent(quantity-> inventoryToUpdate.setQuantity(quantity));
					inventoryRepo.save(inventoryToUpdate);
				},
				()->{
					throw new RuntimeException(new InvalidProductIdException("Invalid productId"));
				});
		return "Inventory updated successfully :)";
	}
	
	public String deleteInventoryById(int inventoryId) throws InvalidInventoryIdException{
		inventoryRepo.findById(inventoryId).ifPresentOrElse(
				inventory-> inventoryRepo.deleteById(inventoryId),
				()-> {
					throw new RuntimeException(new InvalidInventoryIdException("Invalid inventoryId"));
				});
		return "inventory deleted successfully :(";
	}
}

























