package com.farm.farm_marketplace.repository;

import com.farm.farm_marketplace.model.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends MongoRepository<Order, String> {
    List<Order> findByBuyerId(String buyerId);
    Optional<Order> findById(String id);
}