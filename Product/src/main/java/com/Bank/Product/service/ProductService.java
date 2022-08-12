package com.Bank.Product.service;

import com.Bank.Product.models.documents.Product;
import com.Bank.Product.repository.IProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService{

    @Autowired
    private IProductRepository productRepository;

    @Override
    public Flux<Product> findAll(){
        return productRepository.findAll();
    }

    @Override
    public Mono<Product> findById(String id) {
        return productRepository.findById(id).switchIfEmpty(Mono.empty());
    }

    @Override
    public Mono<Product> save(Product product) {
        product.setActive(true);
        return productRepository.save(product);
    }

    @Override
    public Mono<Product> update(String id, Product product) {
        return productRepository.findById(id).flatMap(product1 -> {
            product.setId(id);
            product.setActive(true);
            return productRepository.save(product);
        }).switchIfEmpty(Mono.empty());
    }

    @Override
    public Mono<Product> delete(String id) {
        return productRepository.findById(id).flatMap(product1 -> {
            product1.setActive(false);
            return productRepository.save(product1);
        }).switchIfEmpty(Mono.empty());
    }
}
