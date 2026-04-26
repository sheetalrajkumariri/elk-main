package com.poc.product.service;

import com.poc.product.dto.ProductRequest;
import com.poc.product.dto.ProductResponse;

import java.util.List;

public interface ProductService {

    ProductResponse createProduct(ProductRequest request);

    ProductResponse findProductById(String productId);

    ProductResponse findProductBySku(String sku);

    List<ProductResponse> findAllProducts();

    List<ProductResponse> findProductsByCategory(String category);

    List<ProductResponse> findActiveProducts();

    ProductResponse updateProduct(String productId, ProductRequest request);

    String deleteProductById(String productId);

    String deleteAllProducts();
}
