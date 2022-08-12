package com.Bank.BankCredit.Controllers;

import com.Bank.BankCredit.Models.Documents.Credit;
import com.Bank.BankCredit.Models.Entities.Result;
import com.Bank.BankCredit.Repository.ICreditRepository;
import com.Bank.BankCredit.Service.CreditService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/Credit/")
public class CreditController {

    @Autowired
    private ICreditRepository oCreditRep;
    @Autowired
    private CreditService oCreditSer;

    /**
     * Lista todos los resultados
     * @return
     */
    @GetMapping()
    public Flux<Credit> GetAll(){
        return Flux.fromIterable(oCreditRep.findAll().toIterable());
    }

    /**
     * Obtener resultado por id
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<Mono<Credit>> FindbyId(@PathVariable("id") String id){
        Mono<Credit> oCredit = oCreditRep.findById(id);
        return new ResponseEntity<Mono<Credit>>(oCredit, oCredit != null ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    /**
     * Actualizar datos de credito
     * @param oCredit
     * @return
     */
    @PutMapping()
    public Mono<Credit> Update(@RequestBody Credit oCredit){
        return oCreditRep.findById(oCredit.getId_credit_number())
                .flatMap(x -> { x.setBalance(oCredit.getBalance());
                                x.setLast_transaction(new Date());
                    return oCreditRep.save(x);
                });
    }

    /**
     * Borrar datos por id
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Mono<Credit> DeletebyId(@PathVariable("id") String id){
        return oCreditRep.findById(id).flatMap(x -> {x.setActive(false);
            return oCreditRep.save(x);
        });
    }

    /**
     * Guardar nueva cuenta de credito
     * @param oCredit
     * @return
     */
   @PostMapping("RegisterCreditAccount/")
    public Mono<Result> SaveCreditAccount(@RequestBody Credit oCredit){
       return oCreditSer.RegisterCreditAccount(oCredit);
    }

    /**
     * Guardar nueva tarjeta de credito
     * @param oCredit
     * @return
     */
    @PostMapping("RegisterCreditCard/")
    public Mono<Result> SaveCreditCard(@RequestBody Credit oCredit){
        return oCreditSer.RegisterCreditCard(oCredit);
    }

}