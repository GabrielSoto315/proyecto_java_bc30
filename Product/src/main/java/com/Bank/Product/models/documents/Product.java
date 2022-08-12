package com.Bank.Product.models.documents;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection="products")
public class Product {
    @Id
    private String id;
    private String type;
    private Long quantity;
    private double commission;
    private boolean active;
}
