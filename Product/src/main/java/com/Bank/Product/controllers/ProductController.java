package com.Bank.Product.controllers;

import com.Bank.Product.models.documents.Product;
import com.Bank.Product.service.IProductService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/products")
public class ProductController {
    @Autowired
    private IProductService productService;

    private static final Logger log = LoggerFactory.getLogger(ProductController.class);

    /**
     * Metodo para todos los productos
     * @return
     */
    @GetMapping
    public Flux<Product> getAll(){
        return productService.findAll();
    }

    /**
     * Metodo para obtener el producto por el id
     * @param id
     * @return
     */
    @GetMapping(path= {"{id}"}, produces = {"application/json"})
    public Mono<Product> getById(@PathVariable("id") String id){
        return productService.findById(id);
    }

    /**
     * Metodo para crear un nuevo producto
     * @param product
     * @return
     */
    @PostMapping
    public Mono<Product> create(@RequestBody Product product){
        return productService.save(product);
    }

    /**
     *Metodo para actualizar datos del producto
     * @param id
     * @return
     */
    @PutMapping(path= {"{id}"}, produces = {"application/json"})
    public Mono<Product> update(@PathVariable("id") String id, @RequestBody Product product){
        return productService.update(id, product);
    }

    /**
     * Metodo para eliminar un producto
     * @param id
     */
    @DeleteMapping(path= {"{id}"}, produces = {"application/json"})
    public Mono<Product> deleteById(@PathVariable("id") String id){
        return productService.delete(id);
    }
}
