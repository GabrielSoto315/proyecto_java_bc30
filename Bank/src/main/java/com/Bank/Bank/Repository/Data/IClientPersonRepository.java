package com.Bank.Bank.Repository.Data;

import com.Bank.Bank.Model.Documents.ClientPerson;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IClientPersonRepository extends ReactiveMongoRepository<ClientPerson, String> {

}
