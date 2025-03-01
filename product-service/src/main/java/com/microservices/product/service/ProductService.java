package com.microservices.product.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservices.product.DTO.UpdatedProduct;
import com.microservices.product.exceptionhandler.InvalidProductIdException;
import com.microservices.product.exceptionhandler.NonUniqueProductException;
import com.microservices.product.model.ProductEntity;
import com.microservices.product.repository.ProductRepository;

@Service
public class ProductService {
	@Autowired
	ProductRepository productRepo;
	
	public int addProduct(ProductEntity product) throws NonUniqueProductException{
		Optional<ProductEntity> productInDbWithSameName = productRepo.findByProductName(product.getProductName());
		if(product!=null && productInDbWithSameName.isEmpty()) {
			System.out.println(Optional.ofNullable(product).toString());
			return productRepo.save(product).getProductId();
		}
		else throw new NonUniqueProductException("A Product with same name exist in DB. Please try with different name");
	}
	
	public ProductEntity fetchProductById(int productId) throws InvalidProductIdException{
		Optional<ProductEntity> productInDbWithGivenId = productRepo.findById(productId);
		if(productInDbWithGivenId.isPresent()) {
			return productInDbWithGivenId.get();
		}
		else throw new InvalidProductIdException("Invalid productId");
	}
	
	public String updateProduct(int productId, UpdatedProduct updatedProduct)throws InvalidProductIdException {
		Optional<ProductEntity> productInDbWithGivenId = productRepo.findById(productId);
		productInDbWithGivenId.ifPresentOrElse(
				productInDb->{
					updatedProduct.getProductName().ifPresent(productName-> productInDb.setProductName(productName));
					updatedProduct.getProductDescription().ifPresent(description-> productInDb.setProductDescription(description));
					updatedProduct.getProductPrice().ifPresent(price-> productInDb.setProductPrice(price));
					productRepo.save(productInDb);
				}, 
				()->{
					throw new RuntimeException(new InvalidProductIdException("Invalid ProductId"));
				});
		return "Product updated successfully :)";
	}
	
	public String deleteProductById(int productId) throws InvalidProductIdException{
		Optional<ProductEntity> productInDb = productRepo.findById(productId);
		productInDb.ifPresentOrElse(
				product-> productRepo.deleteById(productId),
				()->{
					throw new RuntimeException(new InvalidProductIdException("Invalid ProductId"));
				});
		return "Product deleted successfully :(";
	}

}

