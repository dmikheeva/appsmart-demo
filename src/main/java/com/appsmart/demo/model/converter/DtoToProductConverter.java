package com.appsmart.demo.model.converter;

import com.appsmart.demo.exception.NoCustomerFoundException;
import com.appsmart.demo.model.Customer;
import com.appsmart.demo.model.Product;
import com.appsmart.demo.model.dto.ProductDto;
import com.appsmart.demo.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;

@Component
public class DtoToProductConverter implements Converter<ProductDto, Product> {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public Product convert(ProductDto source) {
        ZonedDateTime now = ZonedDateTime.now();
        Customer customer = customerRepository
                .findByIdAndIsDeleted(source.getCustomerId(), false)
                .orElseThrow(() -> new NoCustomerFoundException(source.getCustomerId()));
        return Product.builder()
                .id(source.getId())
                .customer(customer)
                .title(source.getTitle())
                .description(source.getDescription())
                .price(source.getPrice())
                .createdAt(now)
                .modifiedAt(now)
                .build();
    }
}
