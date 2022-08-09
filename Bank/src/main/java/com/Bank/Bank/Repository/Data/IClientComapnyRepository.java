package com.Bank.Bank.Repository.Data;

import com.Bank.Bank.Model.Documents.ClientCompany;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IClientComapnyRepository extends ReactiveMongoRepository<ClientCompany, String> {

}
