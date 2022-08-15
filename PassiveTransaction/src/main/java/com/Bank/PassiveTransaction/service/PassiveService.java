package com.Bank.PassiveTransaction.service;

import com.Bank.PassiveTransaction.models.entities.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;



@Service
public class PassiveService {

    private static final Logger log = LoggerFactory.getLogger(PassiveService.class);

    public Mono<Account> FindAccount(String id){
        String url = "http://localhost:8082/v1/accounts/"+id;
        Mono<Account> oCreditMono = WebClient.create()
                .get()
                .uri(url)
                .retrieve()
                .bodyToMono(Account.class);
        oCreditMono.subscribe(client -> log.info(client.toString()));
        return oCreditMono;
    }
    public Mono<Account> UpdateAccount(Account oAccount){
        String url = "http://localhost:8082/v1/accounts/"+oAccount.getIdAccountNumber();
        Mono<Account> oAccountMono = WebClient.create()
                .put()
                .uri(url)
                .body(Mono.just(oAccount), Account.class)
                .retrieve()
                .bodyToMono(Account.class);
        oAccountMono.subscribe(client -> log.info(client.toString()));
        return oAccountMono;
    }
}
