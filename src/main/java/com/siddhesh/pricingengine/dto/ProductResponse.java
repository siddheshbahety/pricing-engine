package com.siddhesh.pricingengine.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductResponse {
    private UUID id;
    private String name;
    private BigDecimal basePrice;
    private String category;
    private Integer inventory;
    private BigDecimal currentPrice;
}