package com.poc.product.repository;

import com.poc.product.entity.ProductEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends MongoRepository<ProductEntity, String> {

    List<ProductEntity> findByCategory(String category);

    Optional<ProductEntity> findBySku(String sku);

    List<ProductEntity> findByActiveTrue();
}
