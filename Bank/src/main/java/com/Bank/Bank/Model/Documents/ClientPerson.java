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
@Document(value= "ClientPerson")
public class ClientPerson extends Client {

    private String last_name;
    private String first_name;
    private String id_card;
}
