package com.Bank.PassiveTransaction.service;

import com.Bank.PassiveTransaction.models.documents.Finance;
import com.Bank.PassiveTransaction.models.entities.Result;
import com.Bank.PassiveTransaction.models.entities.Transfer;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IFinanceService {
    Flux<Finance> findAll();
    Mono<Finance> findById(String id);
    Mono<Finance> save(Finance finance);
    Mono<Finance> update(String id, Finance finance);
    Mono<Finance> delete (String id);

    Mono<Result> registerAddFunds(Finance oFinance);

    Mono<Result> registerRetireFunds(Finance oFinance);

    Mono<Result> registerTransferFunds(Transfer oTransfer);
}
