package com.Bank.Product.service;

import com.Bank.Product.models.documents.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IProductService {
    Flux<Product> findAll();
    Mono<Product> findById(String id);
    Mono<Product> save(Product product);
    Mono<Product> update(String id, Product product);
    Mono<Product> delete (String id);
}
