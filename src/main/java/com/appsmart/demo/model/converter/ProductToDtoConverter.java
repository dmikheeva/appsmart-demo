package com.appsmart.demo.model.converter;

import com.appsmart.demo.model.Product;
import com.appsmart.demo.model.dto.ProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ProductToDtoConverter implements Converter<Product, ProductDto> {

    @Override
    public ProductDto convert(Product source) {
        return ProductDto.builder()
                .id(source.getId())
                .title(source.getTitle())
                .description(source.getDescription())
                .price(source.getPrice())
                .createdAt(source.getCreatedAt())
                .modifiedAt(source.getModifiedAt())
                .build();
    }
}
