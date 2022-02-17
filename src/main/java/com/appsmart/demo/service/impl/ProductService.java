package com.appsmart.demo.service.impl;

import com.appsmart.demo.exception.NoCustomerFoundException;
import com.appsmart.demo.exception.NoProductFoundException;
import com.appsmart.demo.model.Customer;
import com.appsmart.demo.model.Product;
import com.appsmart.demo.model.dto.ProductDto;
import com.appsmart.demo.repository.CustomerRepository;
import com.appsmart.demo.repository.ProductRepository;
import com.appsmart.demo.service.IProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProductService implements IProductService {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-dd-yyyy HH:mm:ss");

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CustomerRepository customerRepository;


    public List<ProductDto> getProducts(Long customerId, Pageable pageable) {
        return productRepository
                .findByCustomerId(customerId, pageable)
                .getContent()
                .stream().map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public ProductDto addProduct(Long customerId, String title, String description, BigDecimal price) {
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
        return convertToDto(productRepository.save(product));
    }

    public void deleteProduct(Long productId) {
        productRepository.deleteById(productId);
    }

    public ProductDto getProduct(Long productId) {
        return convertToDto(productRepository
                .findById(productId)
                .orElseThrow(() -> new NoProductFoundException(productId)));
    }

    public ProductDto updateProduct(Long productId, String title, String description) {
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
        return convertToDto(productRepository.save(product));
    }

    private ProductDto convertToDto(Product product) {
        return ProductDto.builder()
                .id(product.getId())
                .title(product.getTitle())
                .description(product.getDescription())
                .price(product.getPrice())
                .customerId(product.getCustomerId())
                .createdAt(product.getCreatedAt().format(formatter))
                .modifiedAt(product.getModifiedAt() != null ? product.getModifiedAt().format(formatter) : "")
                .build();
    }


}
