package com.microservices.cart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.microservices.cart.model.LineItem;

@Repository
public interface LineItemRepository extends JpaRepository<LineItem, Integer>{

}
