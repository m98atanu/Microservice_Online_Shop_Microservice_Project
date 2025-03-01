package com.microservices.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.microservices.demo.model.CustomerEntity;


@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, Integer> {
	Optional<CustomerEntity> findByCustomerEmail(String customerEmail);
}
