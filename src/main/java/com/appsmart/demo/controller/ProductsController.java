package com.appsmart.demo.controller;

import com.appsmart.demo.model.Product;
import com.appsmart.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class ProductsController {

    @Autowired
    private ProductService productService;

    @GetMapping("/products/{productId}")
    public Product getProduct(@PathVariable Long productId) {
        return productService.getProduct(productId);
    }

    @PutMapping("/products/{productId}")
    public Product updateProduct(@PathVariable Long productId,
                                 @RequestParam(required = false) String title,
                                 @RequestParam(required = false) String description) {
        return productService.updateProduct(productId, title, description);
    }

    @DeleteMapping("/products/{productId}")
    public void deleteProduct(@PathVariable Long productId) {
        productService.deleteProduct(productId);
    }

}
