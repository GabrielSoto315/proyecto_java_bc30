package com.Bank.PassiveTransaction.service;

import com.Bank.PassiveTransaction.models.documents.Finance;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IFinanceService {
    Flux<Finance> findAll();
    Mono<Finance> findById(String id);
    Mono<Finance> save(Finance finance);
    Mono<Finance> update(String id, Finance finance);
    Mono<Finance> delete (String id);
}
