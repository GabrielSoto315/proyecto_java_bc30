package com.Bank.Product.repository;

import com.Bank.Product.models.documents.Product;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProductRepository extends ReactiveMongoRepository<Product, String> {
}
