package com.Bank.BankCredit.Controllers;

import com.Bank.BankCredit.Models.Credit;
import com.Bank.BankCredit.Repository.ICreditRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/Credit/")
public class CreditController {

    @Autowired
    private ICreditRepository oCreditRep;

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
     * Guardar nuevo credito
     * @param oCredit
     * @return
     */
    @PostMapping()
    public Mono<Credit> Save(@RequestBody Credit oCredit){
        oCreditRep.save(oCredit).subscribe();
        return Mono.just(oCredit);
    }

    /**
     * Actualizar datos de credito
     * @param oCredit
     * @return
     */
    @PutMapping()
    public Mono<Credit> Update(@RequestBody Credit oCredit){
        return oCreditRep.save(oCredit);
    }

    /**
     * Borrar datos por id
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public void DeletebyId(@PathVariable("id") String id){
        oCreditRep.deleteById(id);
    }
}