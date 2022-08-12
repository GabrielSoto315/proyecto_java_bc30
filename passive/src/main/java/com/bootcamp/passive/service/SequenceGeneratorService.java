package com.bootcamp.passive.service;

import com.bootcamp.passive.models.documents.entities.Sequence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Objects;


import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;

@Service
public class SequenceGeneratorService {
    @Autowired
    private ReactiveMongoOperations mongoOperations;


    public int getSequenceNumber(String sequenceName) {
        Query query = new Query(Criteria.where("id").is(sequenceName));
        Update update = new Update().inc("sequence", 1);
        Sequence counter = mongoOperations
                .findAndModify(query, update, options().returnNew(true).upsert(true),
                        Sequence.class).block();
        return !Objects.isNull(counter) ? counter.getSequence() : 1;
    }
}
