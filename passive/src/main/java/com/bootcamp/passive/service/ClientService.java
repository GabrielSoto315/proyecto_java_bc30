package com.bootcamp.passive.service;

import com.bootcamp.passive.models.documents.entities.ClientCompany;
import com.bootcamp.passive.models.documents.entities.ClientPerson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class ClientService {

    private static final Logger log = LoggerFactory.getLogger(ClientService.class);

    public Mono<ClientCompany> FindClientCompanyId(String id){
        String url = "http://localhost:8080/api/ClientCompany/"+id;
        Mono<ClientCompany> oClientCompanyMono = WebClient.create()
                .get()
                .uri(url)
                .retrieve()
                .bodyToMono(ClientCompany.class);
        oClientCompanyMono.subscribe(client -> log.info(client.toString()));
        return oClientCompanyMono;
    }

    public Mono<ClientPerson> FindClientPersonId(String id){
        String url = "http://localhost:8080/api/ClientPerson/"+id;
        Mono<ClientPerson> oCLientPersonMono = WebClient.create()
                .get()
                .uri(url)
                .retrieve()
                .bodyToMono(ClientPerson.class);
        oCLientPersonMono.subscribe(client -> log.info(client.toString()));
        return oCLientPersonMono;
    }
}

