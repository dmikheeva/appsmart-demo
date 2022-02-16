package com.appsmart.demo.service;

import com.appsmart.demo.exception.NoCustomerFoundException;
import com.appsmart.demo.exception.NoProductFoundException;
import com.appsmart.demo.model.Customer;
import com.appsmart.demo.model.Product;
import com.appsmart.demo.repository.CustomerRepository;
import com.appsmart.demo.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

@Service
@Slf4j
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CustomerRepository customerRepository;


    public List<Product> getProducts(Long customerId, Pageable pageable) {
        return productRepository
                .findByCustomerId(customerId, pageable)
                .getContent();
    }

    public Product addProduct(Long customerId, String title, String description, BigDecimal price) {
        Customer customer = customerRepository
                .findById(customerId)
                .filter(p -> !p.getIsDeleted())
                .orElseThrow(() -> new NoCustomerFoundException(customerId));
        ZonedDateTime now = ZonedDateTime.now();
        Product product = Product.builder()
                .customerId(customerId)
                .title(title)
                .createdAt(now)
                .price(price)
                .build();
        if (description != null) {
            product.setDescription(description);
        }
        return productRepository.save(product);
    }

    public void deleteProduct(Long productId) {
        productRepository.deleteById(productId);
    }

    public Product getProduct(Long productId) {
        return productRepository
                .findById(productId)
                .orElseThrow(() -> new NoProductFoundException(productId));
    }

    public Product updateProduct(Long productId, String title, String description) {
        Product product = productRepository
                .findById(productId)
                .filter(p -> !p.getIsDeleted())
                .orElseThrow(() -> new NoProductFoundException(productId));
        if (title != null) {
            product.setTitle(title);
        }
        if (description != null) {
            product.setDescription(description);
        }
        return productRepository.save(product);
    }


}
