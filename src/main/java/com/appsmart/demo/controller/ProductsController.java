package com.appsmart.demo.controller;

import com.appsmart.demo.model.Product;
import com.appsmart.demo.model.dto.ProductDto;
import com.appsmart.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

}
