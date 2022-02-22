package com.appsmart.demo.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Data
@Builder
public class ProductDto {
    private final Long id;
    private final Long customerId;
    private final String title;
    private final String description;
    private final BigDecimal price;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private final ZonedDateTime createdAt;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private final ZonedDateTime modifiedAt;
}
