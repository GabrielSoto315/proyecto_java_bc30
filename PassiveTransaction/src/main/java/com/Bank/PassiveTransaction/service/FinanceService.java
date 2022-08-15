package com.Bank.PassiveTransaction.service;

import com.Bank.PassiveTransaction.models.documents.Finance;
import com.Bank.PassiveTransaction.models.entities.Result;
import com.Bank.PassiveTransaction.models.entities.Transfer;
import com.Bank.PassiveTransaction.repository.IFinanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class FinanceService implements IFinanceService{

    @Autowired
    private IFinanceRepository financeRepository;
    @Autowired
    private PassiveService passiveService;

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

    @Override
    public Mono<Result> registerAddFunds(Finance oFinance) {
        Result oResult = new Result();

        if (!passiveService.FindAccount(oFinance.getAccountNumber()).blockOptional().isPresent()) {
            oResult.setMessage("CANCELED -- Account not exists!!");
            return Mono.just(oResult);
        }

        return passiveService.FindAccount(oFinance.getAccountNumber()).flatMap(x -> {
            BigDecimal newBalance = x.getBalance().add(oFinance.getAmount());
            oFinance.setDate(new Date());
            oFinance.setType("Add Funds");
            oFinance.setActive(true);
            oFinance.setCustomer(x.getCustomer());
            financeRepository.save(oFinance).subscribe();
            x.setBalance(newBalance);
            x.setLastTransaction(new Date());
            x.setNumberTransactions(x.getNumberTransactions() + 1);
            passiveService.UpdateAccount(x);
            oResult.setMessage("ADD FUNDS COMPLETE!!");
            return Mono.just(oResult);
        });

    }

    @Override
    public Mono<Result> registerRetireFunds(Finance oFinance) {
        Result oResult = new Result();

        if (!passiveService.FindAccount(oFinance.getAccountNumber()).blockOptional().isPresent()) {
            oResult.setMessage("CANCELED -- Account not exists!!");
            return Mono.just(oResult);
        }

        return passiveService.FindAccount(oFinance.getAccountNumber()).flatMap(x -> {
            BigDecimal newBalance = x.getBalance().add(oFinance.getAmount().negate());
            if(newBalance.compareTo(BigDecimal.ZERO) < 0){
                oResult.setMessage("CANCELED!! Not have enough funds");
                return Mono.just(oResult);
            }
            oFinance.setDate(new Date());
            oFinance.setType("Retire Funds");
            oFinance.setActive(true);
            oFinance.setCustomer(x.getCustomer());
            financeRepository.save(oFinance).subscribe();
            x.setBalance(newBalance);
            x.setLastTransaction(new Date());
            x.setNumberTransactions(x.getNumberTransactions() + 1);
            passiveService.UpdateAccount(x);
            oResult.setMessage("RETIRE FUNDS COMPLETE!!");
            return Mono.just(oResult);
        });

    }

    @Override
    public Mono<Result> registerTransferFunds(Transfer oTransfer) {
        Result oResult = new Result();

        if (!passiveService.FindAccount(oTransfer.getIdAccountOrigin()).blockOptional().isPresent() &&
                !passiveService.FindAccount(oTransfer.getIdAccountOrigin()).blockOptional().isPresent()) {
            oResult.setMessage("CANCELED -- Account not exists!!");
            return Mono.just(oResult);
        }

        return passiveService.FindAccount(oTransfer.getIdAccountOrigin()).flatMap(x -> {
            BigDecimal newBalanceOrigin = x.getBalance().add(oTransfer.getAmount().negate());
            if (newBalanceOrigin.compareTo(BigDecimal.ZERO) < 0) {
                oResult.setMessage("CANCELED!! Not have enough funds");
                return Mono.just(oResult);
            }
            Finance oFinanceOrigin = new Finance();
            oFinanceOrigin.setDate(new Date());
            oFinanceOrigin.setType("Transfer Retire Funds");
            oFinanceOrigin.setActive(true);
            oFinanceOrigin.setCustomer(x.getCustomer());
            oFinanceOrigin.setAmount(oTransfer.getAmount());
            oFinanceOrigin.setAccountNumber(oTransfer.getIdAccountOrigin());
            financeRepository.save(oFinanceOrigin).subscribe();
            x.setBalance(newBalanceOrigin);
            x.setLastTransaction(new Date());
            x.setNumberTransactions(x.getNumberTransactions() + 1);
            passiveService.UpdateAccount(x);
            return passiveService.FindAccount(oTransfer.getIdAccountDestiny()).flatMap(y -> {
                Finance oFinanceDestiny = new Finance();
                BigDecimal newBalanceDestiny = y.getBalance().add(oTransfer.getAmount());
                oFinanceDestiny.setDate(new Date());
                oFinanceDestiny.setType("Transfer Add Funds");
                oFinanceDestiny.setActive(true);
                oFinanceDestiny.setCustomer(y.getCustomer());
                oFinanceDestiny.setAccountNumber(oTransfer.getIdAccountDestiny());
                oFinanceDestiny.setAmount(oTransfer.getAmount());
                financeRepository.save(oFinanceDestiny).subscribe();
                y.setBalance(newBalanceDestiny);
                y.setLastTransaction(new Date());
                y.setNumberTransactions(y.getNumberTransactions() + 1);
                passiveService.UpdateAccount(y);
                oResult.setMessage("TRANSFER FUNDS COMPLETE!!");
                return Mono.just(oResult);
            });
        });
    }
}
