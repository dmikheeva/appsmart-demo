package com.appsmart.demo.model.dto;

import com.appsmart.demo.model.Product;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CustomerDto {
    private final Long id;
    private final String title;
    private final String createdAt;
    private final String modifiedAt;
    private final List<ProductDto> products;
}
