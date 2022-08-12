package com.bootcamp.passive.repository;

import com.bootcamp.passive.models.documents.documents.Account;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAccountRepository extends ReactiveMongoRepository<Account, String> {
}
