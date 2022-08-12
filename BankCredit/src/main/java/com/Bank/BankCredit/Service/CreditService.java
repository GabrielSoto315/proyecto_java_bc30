package com.Bank.BankCredit.Service;

import com.Bank.BankCredit.Models.Documents.Credit;
import com.Bank.BankCredit.Models.Entities.ClientCompany;
import com.Bank.BankCredit.Models.Entities.ClientPerson;
import com.Bank.BankCredit.Models.Entities.Result;
import com.Bank.BankCredit.Repository.ICreditRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.Date;


import static com.Bank.BankCredit.Models.Documents.Credit.SEQUENCE_NAME;

@Service
@RequiredArgsConstructor
public class CreditService {

    @Autowired
    ICreditRepository oCreditRep;
    @Autowired
    SequenceGeneratorService oSequenceSer;
    @Autowired
    ClientService oClientSer;

    public Mono<Result> RegisterCreditAccount(Credit oCredit){
        Result oResult = new Result();
        Boolean isPerson;
        Boolean isCompany;
        //Validate Client
        ClientPerson oClient;
        Mono<ClientPerson> oClientPerson = oClientSer.FindClientPersonId(oCredit.getClient_number());
        Mono<ClientCompany> oClientCompany = oClientSer.FindClientCompanyId(oCredit.getClient_number());

        isPerson = oClientPerson.blockOptional().isPresent() ? true : false;
        isCompany = oClientCompany.blockOptional().isPresent() ? true : false;

        if (!isCompany && !isPerson){
            oResult.setMessage("CANCELED -- Client is not registered!!");
            return Mono.just(oResult);
        }

        if (isPerson &&
                oCreditRep.findAll().toStream()
                        .filter(x -> x.getActive() && x.getType().equals("Credit Account") && x.getClient_number().equals(oCredit.getClient_number()))
                        .findAny()
                        .isPresent())
        {
            oResult.setMessage("CANCELED -- Client have a credit!!");
            return Mono.just(oResult);
        }

        //Register Credit
        oCredit.setId_credit_number(String.format("2525%010d", oSequenceSer.getSequenceNumber(SEQUENCE_NAME)));
        oCredit.setActive(true);
        oCredit.setType("Credit Account");
        oCredit.setBalance(BigDecimal.ZERO);
        oCredit.setCategory(isPerson ? "Personal" : "Enterprise");
        oCredit.setRegister_date(new Date());
        oCredit.setLast_transaction(new Date());
        Mono<Credit> oCreditResult = oCreditRep.save(oCredit);
        oResult.setMessage("REGISTERED ACCOUNT -- new credit number : " + oCreditResult.block().getId_credit_number());
        return Mono.just(oResult);
    }

    public Mono<Result> RegisterCreditCard(Credit oCredit){
        Result oResult = new Result();
        Boolean isPerson;
        Boolean isCompany;
        //Validate Client
        ClientPerson oClient;
        Mono<ClientPerson> oClientPerson = oClientSer.FindClientPersonId(oCredit.getClient_number());
        Mono<ClientCompany> oClientCompany = oClientSer.FindClientCompanyId(oCredit.getClient_number());

        isPerson = oClientPerson.blockOptional().isPresent() ? true : false;
        isCompany = oClientCompany.blockOptional().isPresent() ? true : false;

        if (!isCompany && !isPerson){
            oResult.setMessage("CANCELED -- Client is not registered!!");
            return Mono.just(oResult);
        }

        //Register Credit Card
        oCredit.setId_credit_number(String.format("4545%010d", oSequenceSer.getSequenceNumber(SEQUENCE_NAME)));
        oCredit.setActive(true);
        oCredit.setCategory(isPerson ? "Personal" : "Enterprise");
        oCredit.setType("Credit Card");
        oCredit.setBalance(oCredit.getAmount());
        oCredit.setRegister_date(new Date());
        oCredit.setLast_transaction(new Date());
        Mono<Credit> oCreditResult = oCreditRep.save(oCredit);
        oResult.setMessage("REGISTERED CREDIT CARD -- new credit card number : " + oCreditResult.block().getId_credit_number());
        return Mono.just(oResult);
    }
}

