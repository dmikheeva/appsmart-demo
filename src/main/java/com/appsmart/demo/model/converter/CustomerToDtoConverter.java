package com.appsmart.demo.model.converter;

import com.appsmart.demo.model.Customer;
import com.appsmart.demo.model.dto.CustomerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class CustomerToDtoConverter implements Converter<Customer, CustomerDto> {
    @Autowired
    private ProductToDtoConverter productConverter;

    @Override
    public CustomerDto convert(Customer source) {
        return CustomerDto.builder()
                .id(source.getId())
                .title(source.getTitle())
                .createdAt(source.getCreatedAt())
                .modifiedAt(source.getModifiedAt())
                .products(source.getProducts()
                        .stream()
                        .map((p) -> productConverter.convert(p))
                        .collect(Collectors.toList()))
                .build();
    }
}
