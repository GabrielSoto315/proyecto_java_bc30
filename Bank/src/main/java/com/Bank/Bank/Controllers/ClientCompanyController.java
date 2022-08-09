package com.Bank.Bank.Controllers;

import com.Bank.Bank.Model.Documents.ClientCompany;
import com.Bank.Bank.Repository.Data.IClientComapnyRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/ClientCompany/")
public class ClientCompanyController {

    @Autowired
    private IClientComapnyRepository oClientCompanyRep;

    private static final Logger log = LoggerFactory.getLogger(ClientCompanyController.class);

    /**
     * Lista todos los resultados
     * @return
     */
    @GetMapping()
    public Flux<ClientCompany> List(){
        return Flux.fromIterable(oClientCompanyRep.findAll().toIterable());
    }

    /**
     * Obtener resultado por id
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<Mono<ClientCompany>> FindbyId(@PathVariable("id") String id){
        Mono<ClientCompany> oClienteEmpresa = oClientCompanyRep.findById(id);
        return new ResponseEntity<Mono<ClientCompany>>(oClienteEmpresa, oClienteEmpresa != null ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    /**
     * Guardar nuevo cliente empresa
     * @param oCliente
     * @return
     */
    @PostMapping()
    public Mono<ClientCompany> Save(@RequestBody ClientCompany oCliente){
        oClientCompanyRep.save(oCliente).subscribe();
        return Mono.just(oCliente);
    }

    /**
     * Actualizar datos de cliente empresa
     * @param oCliente
     * @return
     */
    @PutMapping()
    public Mono<ClientCompany> Update(@RequestBody ClientCompany oCliente){
        return oClientCompanyRep.save(oCliente);
    }

    /**
     * Borrar datos por id
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public void DeletebyId(@PathVariable("id") String id){
        oClientCompanyRep.deleteById(id);
    }
}
