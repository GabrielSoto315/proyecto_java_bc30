package com.bootcamp.passive.controllers;

import com.bootcamp.passive.models.documents.documents.Account;
import com.bootcamp.passive.models.documents.entities.Result;
import com.bootcamp.passive.service.IAccountService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/accounts")
public class AccountController {
    @Autowired
    private IAccountService accountService;

    private static final Logger log = LoggerFactory.getLogger(AccountController.class);

    /**
     * Metodo para listar todos los pasivos de ahorro
     * @return
     */
    @GetMapping
    public Flux<Account> getAll(){
        return accountService.findAll();
    }

    /**
     * Metodo para listar un pasivo de ahorro por id
     * @param id
     * @return
     */
    @GetMapping(path= {"{id}"}, produces = {"application/json"})
    public Mono<Account> getById(@PathVariable("id") String id){
        return accountService.findById(id);
    }

    /**
     * Metodo para crear un pasivo de ahorro
     * @param account
     * @return
     */
    @PostMapping
    public Mono<Account> create(@RequestBody Account account){
        return accountService.save(account);
    }

    /**
     * Metodo para actualizar
     * @param id
     * @param account
     * @return
     */
    @PutMapping(path= {"{id}"}, produces = {"application/json"})
    public Mono<Account> update(@PathVariable("id") String id, @RequestBody Account account){
        return accountService.update(id, account);
    }

    /**
     * Metodo para eliminar
     * @param id
     */
    @DeleteMapping(path= {"{id}"}, produces = {"application/json"})
    public Mono<Account> deleteById(@PathVariable("id") String id){
        return accountService.delete(id);
    }

    /**
     * Guardar nueva cuenta de ahorro
     * @param oAccount
     * @return
     */
    @PostMapping("RegisterSavingsAccount/")
    public Mono<Result> RegisterSavingAccount(@RequestBody Account oAccount){
        return accountService.RegisterAccountSaving(oAccount, "Savings");
    }

    /**
     * Guardar nueva cuenta de ahorro
     * @param oAccount
     * @return
     */
    @PostMapping("RegisterCurrentAccount/")
    public Mono<Result> RegisterCurrentAccount(@RequestBody Account oAccount){
        return accountService.RegisterAccountSaving(oAccount, "Current");
    }

    /**
     * Guardar nueva cuenta de ahorro
     * @param oAccount
     * @return
     */
    @PostMapping("RegisterFixedTermsAccount/")
    public Mono<Result> RegisterFixedTermsAccount(@RequestBody Account oAccount){
        return accountService.RegisterAccountSaving(oAccount, "Fixed Terms");
    }

}
