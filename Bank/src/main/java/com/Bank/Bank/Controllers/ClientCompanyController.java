package com.Bank.Bank.Controllers;

import com.Bank.Bank.Model.Documents.ClientCompany;
import com.Bank.Bank.Repository.Data.IClientComapnyRepository;
import com.Bank.Bank.Services.ClientCompanyService;
import com.Bank.Bank.Services.SequenceGeneratorService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;

import static com.Bank.Bank.Model.Entities.Client.SEQUENCE_NAME;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/ClientCompany/")
public class ClientCompanyController {

    @Autowired
    private IClientComapnyRepository oClientCompanyRep;
    @Autowired
    private SequenceGeneratorService oSequenceService;

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
        Mono<ClientCompany> oClientEmpresa = oClientCompanyRep.findById(id);
        return new ResponseEntity<Mono<ClientCompany>>(oClientEmpresa, oClientEmpresa != null ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    /**
     * Guardar nuevo cliente empresa
     * @param oClient
     * @return
     */
    @PostMapping()
    public Mono<ClientCompany> Save(@RequestBody ClientCompany oClient){
        oClient.setId_client(String.format("191%010d", oSequenceService.getSequenceNumber(SEQUENCE_NAME)));
        oClient.setActive(true);
        oClient.setRegister_date(new Date());
        return oClientCompanyRep.save(oClient);
    }

    /**
     * Actualizar datos de cliente empresa
     * @param oClient
     * @return
     */
    @PutMapping()
    public Mono<ClientCompany> Update(@RequestBody ClientCompany oClient){
        return oClientCompanyRep.findById(oClient.getId_client())
                .flatMap(x -> {x.setName(oClient.getName() != null ? oClient.getName() : x.getName());
                    x.setId_number(oClient.getId_number() != null ? oClient.getId_number() : x.getId_number());
            return oClientCompanyRep.save(x);
        });
    }

    /**
     * Borrar datos por id
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Mono<ClientCompany> DeletebyId(@PathVariable("id") String id){
        return oClientCompanyRep.findById(id).flatMap(x -> {x.setActive(false);
                    return oClientCompanyRep.save(x);
                });
    }
}
