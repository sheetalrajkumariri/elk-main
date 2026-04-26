package com.poc.product.service;

import com.poc.product.dto.ProductRequest;
import com.poc.product.dto.ProductResponse;
import com.poc.product.entity.ProductEntity;
import com.poc.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    @Override
    public ProductResponse createProduct(ProductRequest request) {
        log.info("Start::createProduct() inside ProductServiceImpl with request: {}", request);
        ProductEntity entity = modelMapper.map(request, ProductEntity.class);
        entity = productRepository.save(entity);
        log.info("End::createProduct() inside ProductServiceImpl with id: {}", entity.getId());
        return modelMapper.map(entity, ProductResponse.class);
    }

    @Override
    public ProductResponse findProductById(String productId) {
        log.info("Start::findProductById() inside ProductServiceImpl with id: {}", productId);
        ProductEntity entity = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + productId));
        log.info("End::findProductById() inside ProductServiceImpl with id: {}", productId);
        return modelMapper.map(entity, ProductResponse.class);
    }

    @Override
    public ProductResponse findProductBySku(String sku) {
        log.info("Start::findProductBySku() inside ProductServiceImpl with sku: {}", sku);
        ProductEntity entity = productRepository.findBySku(sku)
                .orElseThrow(() -> new RuntimeException("Product not found with sku: " + sku));
        log.info("End::findProductBySku() inside ProductServiceImpl");
        return modelMapper.map(entity, ProductResponse.class);
    }

    @Override
    public List<ProductResponse> findAllProducts() {
        log.info("Start::findAllProducts() inside ProductServiceImpl");
        List<ProductEntity> entities = productRepository.findAll();
        log.info("End::findAllProducts() inside ProductServiceImpl, count: {}", entities.size());
        return modelMapper.map(entities, new TypeToken<List<ProductResponse>>() {}.getType());
    }

    @Override
    public List<ProductResponse> findProductsByCategory(String category) {
        log.info("Start::findProductsByCategory() inside ProductServiceImpl with category: {}", category);
        List<ProductEntity> entities = productRepository.findByCategory(category);
        log.info("End::findProductsByCategory() inside ProductServiceImpl, count: {}", entities.size());
        return modelMapper.map(entities, new TypeToken<List<ProductResponse>>() {}.getType());
    }

    @Override
    public List<ProductResponse> findActiveProducts() {
        log.info("Start::findActiveProducts() inside ProductServiceImpl");
        List<ProductEntity> entities = productRepository.findByActiveTrue();
        log.info("End::findActiveProducts() inside ProductServiceImpl, count: {}", entities.size());
        return modelMapper.map(entities, new TypeToken<List<ProductResponse>>() {}.getType());
    }

    @Override
    public ProductResponse updateProduct(String productId, ProductRequest request) {
        log.info("Start::updateProduct() inside ProductServiceImpl with id: {}", productId);
        ProductEntity entity = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + productId));
        modelMapper.map(request, entity);
        ProductEntity updated = productRepository.save(entity);
        log.info("End::updateProduct() inside ProductServiceImpl with id: {}", productId);
        return modelMapper.map(updated, ProductResponse.class);
    }

    @Override
    public String deleteProductById(String productId) {
        log.info("Start::deleteProductById() inside ProductServiceImpl with id: {}", productId);
        ProductEntity entity = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + productId));
        productRepository.delete(entity);
        log.info("End::deleteProductById() inside ProductServiceImpl with id: {}", productId);
        return "Product deleted successfully";
    }

    @Override
    public String deleteAllProducts() {
        log.info("Start::deleteAllProducts() inside ProductServiceImpl");
        productRepository.deleteAll();
        log.info("End::deleteAllProducts() inside ProductServiceImpl");
        return "All products deleted successfully";
    }
}
