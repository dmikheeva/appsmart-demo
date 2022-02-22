package com.appsmart.demo.controller;

import com.appsmart.demo.model.dto.ProductDto;
import com.appsmart.demo.service.impl.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/v1")
public class ProductsController {

    @Autowired
    private ProductService productService;

    @GetMapping("/products/{productId}")
    public ProductDto getProduct(@PathVariable Long productId) {
        return productService.getProduct(productId);
    }

    @PutMapping("/products/{productId}")
    public ProductDto updateProduct(@PathVariable Long productId,
                                    @RequestParam(required = false) String title,
                                    @RequestParam(required = false) String description) {
        return productService.updateProduct(productId, title, description);
    }

    @DeleteMapping("/products/{productId}")
    public void deleteProduct(@PathVariable Long productId) {
        productService.deleteProduct(productId);
    }

    @GetMapping(value = "/customers/{customerId}/products")
    public Page<ProductDto> getProducts(@PathVariable Long customerId,
                                        @RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "10") int size) {
        return productService.getProducts(customerId, PageRequest.of(page, size));
    }

    @PostMapping("/customers/{customerId}/products")
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDto addProduct(@PathVariable Long customerId,
                                 @RequestParam String title,
                                 @RequestParam(required = false) String description,
                                 @RequestParam BigDecimal price) {
        return productService.addProduct(customerId, title, description, price);
    }
}