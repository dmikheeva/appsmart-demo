package com.appsmart.demo.service;

import com.appsmart.demo.model.dto.ProductDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;

public interface IProductService {
    Page<ProductDto> getProducts(Long customerId, Pageable pageable);

    ProductDto addProduct(ProductDto productDto);

    void deleteProduct(Long productId);

    ProductDto getProduct(Long productId);

    ProductDto updateProduct(Long productId, String title, String description);
}
