package com.appsmart.demo.service.impl;

import com.appsmart.demo.exception.NoCustomerFoundException;
import com.appsmart.demo.exception.NoProductFoundException;
import com.appsmart.demo.model.Product;
import com.appsmart.demo.model.converter.DtoToProductConverter;
import com.appsmart.demo.model.converter.ProductToDtoConverter;
import com.appsmart.demo.model.dto.ProductDto;
import com.appsmart.demo.repository.CustomerRepository;
import com.appsmart.demo.repository.ProductRepository;
import com.appsmart.demo.service.IProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Service
@Slf4j
public class ProductService implements IProductService {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-dd-yyyy HH:mm:ss");

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ProductToDtoConverter productToDtoConverter;

    @Autowired
    private DtoToProductConverter dtoToProductConverter;


    public Page<ProductDto> getProducts(Long customerId, Pageable pageable) {
        return productRepository
                .findByCustomerIdAndIsDeleted(customerId, false, pageable)
                .map(t -> productToDtoConverter.convert(t));
    }

    public ProductDto addProduct(ProductDto productDto) {
        Product product = dtoToProductConverter.convert(productDto);
        assert product != null;
        return productToDtoConverter.convert(productRepository.save(product));
    }

    public void deleteProduct(Long productId) {
        Product product = productRepository
                .findByIdAndIsDeleted(productId, false)
                .orElseThrow(() ->
                        new NoCustomerFoundException(productId));
        product.setIsDeleted(true);
        productRepository.save(product);
    }

    public ProductDto getProduct(Long productId) {
        return productToDtoConverter.convert(productRepository
                .findByIdAndIsDeleted(productId, false)
                .orElseThrow(() -> new NoProductFoundException(productId)));
    }

    public ProductDto updateProduct(Long productId, String title, String description) {
        Product product = productRepository
                .findByIdAndIsDeleted(productId, false)
                .orElseThrow(() -> new NoProductFoundException(productId));
        if (title != null) {
            product.setTitle(title);
        }
        if (description != null) {
            product.setDescription(description);
        }
        return productToDtoConverter.convert(productRepository.save(product));
    }


}
