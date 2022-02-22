package com.appsmart.demo.model.converter;

import com.appsmart.demo.model.Customer;
import com.appsmart.demo.model.dto.CustomerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.stream.Collectors;

@Component
public class DtoToCustomerConverter implements Converter<CustomerDto, Customer> {
    @Autowired
    private DtoToProductConverter productConverter;

    @Override
    public Customer convert(CustomerDto source) {
        ZonedDateTime now = ZonedDateTime.now();
        return Customer.builder()
                .id(source.getId())
                .title(source.getTitle())
                .createdAt(now)
                .modifiedAt(null)
                .products(source.getProducts()
                        .stream()
                        .map((p) -> productConverter.convert(p))
                        .collect(Collectors.toList()))
                .build();
    }
}
