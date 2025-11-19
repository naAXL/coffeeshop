package com.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenuItemDTO {
    private Long id;
    private Long categoryId;
    private String imageUrl;
    private BigDecimal price;
    private Map<String, String> names;
    private Map<String, String> descriptions;
}