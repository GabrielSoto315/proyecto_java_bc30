package com.bootcamp.passive.service;

import com.bootcamp.passive.models.documents.documents.Account;
import com.bootcamp.passive.models.documents.entities.Result;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IAccountService {
    Flux<Account> findAll();
    Mono<Account> findById(String id);
    Mono<Account> save(Account account);
    Mono<Account> update(String id, Account account);
    Mono<Account> delete (String id);
    Mono<Result> RegisterAccountSaving(Account acccount, String type);
}
