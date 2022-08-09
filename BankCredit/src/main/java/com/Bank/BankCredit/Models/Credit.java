package com.Bank.BankCredit.Models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
public class Credit {
    @Id
    private String id;
    private String credit_number;
    private String type;
    private Number balance;
    private String client_number;
}
