package com.appsmart.demo.model.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Data
@Builder
public class ProductDto {
    private final Long id;
    private final String title;
    private final String description;
    private final BigDecimal price;
    private final Long customerId;
    private final String createdAt;
    private final String modifiedAt;
}
