package com.Bank.PassiveTransaction.models.entities;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class Account {
    private String idAccountNumber;
    private BigDecimal balance;
    private String customer;
    private String type;
    private Date registerDate;
    private Date lastTransaction;
    private int numberTransactions;
}
