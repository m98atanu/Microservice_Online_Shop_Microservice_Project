package com.microservices.shoppingservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.microservices.shoppingservice.model.entity.CustomerAndOrderEntity;

@Repository
public interface CustomerAndOrderRepository extends JpaRepository<CustomerAndOrderEntity, Integer> {
	List<CustomerAndOrderEntity> findByCustomerId(int customerId);
}
