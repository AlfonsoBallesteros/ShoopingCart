package com.shopping.cart.repository;

import com.shopping.cart.domain.Product;
import org.javers.spring.annotation.JaversSpringDataAuditable;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Product entity.
 */
@SuppressWarnings("unused")
@Repository
@JaversSpringDataAuditable
public interface ProductRepository extends MongoRepository<Product, String> {
}
