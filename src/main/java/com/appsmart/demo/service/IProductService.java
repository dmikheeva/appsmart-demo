package com.appsmart.demo.service;

import com.appsmart.demo.model.dto.ProductDto;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;

public interface IProductService {
    List<ProductDto> getProducts(Long customerId, Pageable pageable);

    ProductDto addProduct(Long customerId, String title, String description, BigDecimal price);

    void deleteProduct(Long productId);

    ProductDto getProduct(Long productId);

    ProductDto updateProduct(Long productId, String title, String description);
}