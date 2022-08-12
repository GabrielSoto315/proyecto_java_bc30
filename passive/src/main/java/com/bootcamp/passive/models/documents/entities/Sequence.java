package com.bootcamp.passive.models.documents.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "SequenceSavings")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Sequence {
    @Id
    private String  id;
    private int sequence;
}