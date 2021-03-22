package com.shopping.cart.repository;

import com.shopping.cart.domain.Sale;
import org.javers.spring.annotation.JaversSpringDataAuditable;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Sale entity.
 */
@SuppressWarnings("unused")
@Repository
@JaversSpringDataAuditable
public interface SaleRepository extends MongoRepository<Sale, String> {
}
