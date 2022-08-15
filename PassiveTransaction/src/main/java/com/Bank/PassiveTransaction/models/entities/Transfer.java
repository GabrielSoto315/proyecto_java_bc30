package com.Bank.PassiveTransaction.models.entities;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Transfer {

    private String idAccountOrigin;
    private String idAccountDestiny;
    private BigDecimal amount;
}
