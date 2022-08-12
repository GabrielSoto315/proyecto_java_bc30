package com.Bank.Bank.Model.Documents;

import com.Bank.Bank.Model.Entities.Client;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(value = "ClientCompany")
public class ClientCompany extends Client {

    private String id_number;
    private String name;
}
