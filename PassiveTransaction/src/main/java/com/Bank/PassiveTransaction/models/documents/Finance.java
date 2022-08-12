package com.Bank.PassiveTransaction.models.documents;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection="finance")
public class Finance {
    @Id
    private String id;
    private Date date;
    private double mount;
    private String accountNumber;
    private String customer;
    private String type;
    private boolean active;
}
