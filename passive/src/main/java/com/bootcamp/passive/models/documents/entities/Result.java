package com.bootcamp.passive.models.documents.entities;

import lombok.Data;
import org.springframework.data.mongodb.repository.query.StringBasedMongoQuery;

@Data
public class Result {
    private String message;
}
