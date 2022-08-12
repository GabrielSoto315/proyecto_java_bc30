package com.Bank.PassiveTransaction.controllers;

import com.Bank.PassiveTransaction.models.documents.Finance;
import com.Bank.PassiveTransaction.service.IFinanceService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/finance")
public class FinanceController {

    @Autowired
    public IFinanceService financeService;

    private static final Logger log = LoggerFactory.getLogger(FinanceController.class);

    /**
     * Metodo para obtener todos los resultados
     * @return
     */
    @GetMapping
    public Flux<Finance> getAll(){
        return financeService.findAll();
    }

    /**
     * Metodo para obtener un resultado especifico
     * @param id
     * @return
     */
    @GetMapping(path= {"{id}"}, produces = {"application/json"})
    public Mono<Finance> getById(@PathVariable("id") String id){
        return financeService.findById(id);
    }

    /**
     * Metodo para crear un movimiento
     * @param finance
     * @return
     */
    @PostMapping
    public Mono<Finance> create(@RequestBody Finance finance){
        return financeService.save(finance);
    }

    /**
     * Metodo para actualizar
     * @param id
     * @param finance
     * @return
     */
    @PutMapping(path= {"{id}"}, produces = {"application/json"})
    public Mono<Finance> update(@PathVariable("id") String id, @RequestBody Finance finance){
        return financeService.update(id, finance);
    }

    /**
     * Metodo para eliminar
     * @param id
     */
    @DeleteMapping(path= {"{id}"}, produces = {"application/json"})
    public Mono<Finance> deleteById(@PathVariable("id") String id){
        return financeService.delete(id);
    }
}
