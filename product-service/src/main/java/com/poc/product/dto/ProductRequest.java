package com.poc.product.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductRequest {

    private String name;
    private String description;
    private String category;
    private BigDecimal price;
    private int stockQuantity;
    private String sku;
    private boolean active;
}
