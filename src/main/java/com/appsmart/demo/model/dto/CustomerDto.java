package com.appsmart.demo.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.time.ZonedDateTime;
import java.util.List;

@Data
@Builder
public class CustomerDto {
    private final Long id;
    private final String title;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private final ZonedDateTime createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private final ZonedDateTime modifiedAt;
    private final List<ProductDto> products;
}
