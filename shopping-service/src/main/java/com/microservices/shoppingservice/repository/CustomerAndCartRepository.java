package com.microservices.shoppingservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.microservices.shoppingservice.model.entity.CustomerAndCartEntity;

@Repository
public interface CustomerAndCartRepository extends JpaRepository<CustomerAndCartEntity, Integer> {

}
