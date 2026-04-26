package com.poc.product.controller;

import com.poc.product.dto.ProductRequest;
import com.poc.product.dto.ProductResponse;
import com.poc.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    @PostMapping("/createProduct")
    public ProductResponse createProduct(@RequestBody ProductRequest request) {
        log.info("Start::createProduct() inside ProductController with request: {}", request);
        return productService.createProduct(request);
    }

    @GetMapping("/findProductById/{productId}")
    public ProductResponse findProductById(@PathVariable String productId) {
        log.info("Start::findProductById() inside ProductController with id: {}", productId);
        return productService.findProductById(productId);
    }

    @GetMapping("/findProductBySku/{sku}")
    public ProductResponse findProductBySku(@PathVariable String sku) {
        log.info("Start::findProductBySku() inside ProductController with sku: {}", sku);
        return productService.findProductBySku(sku);
    }

    @GetMapping("/findAllProducts")
    public List<ProductResponse> findAllProducts() {
        log.info("Start::findAllProducts() inside ProductController");
        return productService.findAllProducts();
    }

    @GetMapping("/findByCategory/{category}")
    public List<ProductResponse> findProductsByCategory(@PathVariable String category) {
        log.info("Start::findProductsByCategory() inside ProductController with category: {}", category);
        return productService.findProductsByCategory(category);
    }

    @GetMapping("/findActiveProducts")
    public List<ProductResponse> findActiveProducts() {
        log.info("Start::findActiveProducts() inside ProductController");
        return productService.findActiveProducts();
    }

    @PutMapping("/updateProduct/{productId}")
    public ProductResponse updateProduct(@PathVariable String productId, @RequestBody ProductRequest request) {
        log.info("Start::updateProduct() inside ProductController with id: {}", productId);
        return productService.updateProduct(productId, request);
    }

    @DeleteMapping("/deleteProductById/{productId}")
    public String deleteProductById(@PathVariable String productId) {
        log.info("Start::deleteProductById() inside ProductController with id: {}", productId);
        return productService.deleteProductById(productId);
    }

    @DeleteMapping("/deleteAllProducts")
    public String deleteAllProducts() {
        log.info("Start::deleteAllProducts() inside ProductController");
        return productService.deleteAllProducts();
    }
}
