package com.Bank.BankCredit.Models.Documents;

import lombok.Data;
import lombok.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.Date;

@Document(value = "Credit")
@Data
public class Credit {
    @Transient
    public static final String SEQUENCE_NAME = "credit_sequence";

    @Id
    private String id_credit_number;
    private String type;
    private String category;
    private BigDecimal balance;
    private BigDecimal amount;
    private String client_number;
    private Boolean active;
    private Date register_date;
    private Date last_transaction;
}
