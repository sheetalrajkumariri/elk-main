package com.poc.order.client;

import com.poc.order.dto.ProductResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "product-service"/*, configuration = FeignConfig.class*/)
public interface ProductServiceClient {

    @GetMapping("/api/products/findProductById/{productId}")
    ProductResponse findProductById(@PathVariable("productId") String productId);
}
