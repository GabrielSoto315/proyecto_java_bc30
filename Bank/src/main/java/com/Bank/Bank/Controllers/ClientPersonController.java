package com.Bank.Bank.Controllers;

import com.Bank.Bank.Model.Documents.ClientCompany;
import com.Bank.Bank.Model.Documents.ClientPerson;
import com.Bank.Bank.Repository.Data.IClientPersonRepository;
import com.Bank.Bank.Services.SequenceGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;

import static com.Bank.Bank.Model.Entities.Client.SEQUENCE_NAME;

@RestController
@RequestMapping("api/ClientPerson/")
public class ClientPersonController {

    @Autowired
    private IClientPersonRepository oClientPersonRep;
    @Autowired
    private SequenceGeneratorService oSequenceService;


    @GetMapping()
    public Flux<ClientPerson> List(){
        return Flux.fromIterable(oClientPersonRep.findAll().toIterable());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Mono<ClientPerson>> FindbyId(@PathVariable("id") String id){
        Mono<ClientPerson> oClientPerson = oClientPersonRep.findById(id);
        return new ResponseEntity<Mono<ClientPerson>>(oClientPerson, oClientPerson != null ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @PostMapping()
    public Mono<ClientPerson> Save(@RequestBody ClientPerson oClient){
        oClient.setId_client(String.format("191%010d", oSequenceService.getSequenceNumber(SEQUENCE_NAME)));
        oClient.setActive(true);
        oClient.setRegister_date(new Date());
        return oClientPersonRep.save(oClient);
    }

    @PutMapping()
    public Mono<ClientPerson> Update(@RequestBody ClientPerson oClient){
        return oClientPersonRep.findById(oClient.getId_client())
                .flatMap(x -> {x.setFirst_name(oClient.getFirst_name() != null ? oClient.getFirst_name() : x.getFirst_name());
                    x.setLast_name(oClient.getLast_name() != null ? oClient.getLast_name() : x.getLast_name());
                    x.setId_card(oClient.getId_card() != null ? oClient.getId_card() : x.getId_card());
                    return oClientPersonRep.save(x);
                });
    }

    @DeleteMapping("/{id}")
    public Mono<ClientPerson> DeletebyId(@PathVariable("id") String id){
        return oClientPersonRep.findById(id).flatMap(x -> {x.setActive(false);
            return oClientPersonRep.save(x);
        });
    }


}
