package com.Bank.PassiveTransaction.service;

import com.Bank.PassiveTransaction.models.documents.Finance;
import com.Bank.PassiveTransaction.repository.IFinanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class FinanceService implements IFinanceService{

    @Autowired
    private IFinanceRepository financeRepository;

    @Override
    public Flux<Finance> findAll() {
        return financeRepository.findAll();
    }

    @Override
    public Mono<Finance> findById(String id) {
        return financeRepository.findById(id).switchIfEmpty(Mono.empty());
    }

    @Override
    public Mono<Finance> save(Finance finance) {
        finance.setActive(true);
        return financeRepository.save(finance);
    }

    @Override
    public Mono<Finance> update(String id, Finance finance) {
        return financeRepository.findById(id).flatMap(finance1 -> {
            finance.setId(id);
            finance.setActive(true);
            return financeRepository.save(finance);
        }).switchIfEmpty(Mono.empty());
    }

    @Override
    public Mono<Finance> delete(String id) {
        return financeRepository.findById(id).flatMap(finance -> {
            finance.setActive(false);
            return financeRepository.save(finance);
        }).switchIfEmpty(Mono.empty());
    }
}
