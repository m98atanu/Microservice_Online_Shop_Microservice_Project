package com.microservices.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.microservices.order.model.LineItem;

@Repository
public interface LineItemRepository extends JpaRepository<LineItem, Integer> {

}
