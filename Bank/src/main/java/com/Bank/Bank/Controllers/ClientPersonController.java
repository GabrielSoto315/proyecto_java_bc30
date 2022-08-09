package com.Bank.Bank.Controllers;

import com.Bank.Bank.Model.Documents.ClientCompany;
import com.Bank.Bank.Model.Documents.ClientPerson;
import com.Bank.Bank.Repository.Data.IClientPersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/ClientPerson/")
public class ClientPersonController {

    @Autowired
    private IClientPersonRepository oClientPersonRep;

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
        oClientPersonRep.save(oClient).subscribe();
        return Mono.just(oClient);
    }

    @PutMapping()
    public Mono<ClientPerson> Update(@RequestBody ClientPerson oClient){
        return oClientPersonRep.save(oClient);
    }

    @DeleteMapping("/{id}")
    public void DeletebyId(@PathVariable("id") String id){
        oClientPersonRep.deleteById(id);
    }
}
