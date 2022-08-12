package com.bootcamp.passive.models.documents.documents;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection="accounts")
public class Account {
    @Transient
    public static final String SEQUENCE_NAME = "account_sequence";

    @Id
    private String idAccountNumber;
    private BigDecimal balance;
    private String customer;
    private String type;
    private Date registerDate;
    private Date lastTransaction;
    private int numberTransactions;
    private Boolean active;
}
