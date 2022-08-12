package com.Bank.PassiveTransaction.repository;

import com.Bank.PassiveTransaction.models.documents.Finance;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IFinanceRepository extends ReactiveMongoRepository<Finance, String> {
}
