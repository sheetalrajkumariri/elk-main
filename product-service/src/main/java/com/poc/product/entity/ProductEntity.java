package com.poc.product.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Document(collection = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductEntity {

    @Id
    private String id;
    private String name;
    private String description;
    private String category;
    private BigDecimal price;
    private int stockQuantity;
    private String sku;
    private boolean active;
}
