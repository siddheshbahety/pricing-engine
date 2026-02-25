package com.siddhesh.pricingengine.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PriceResponse {
    private UUID productId;
    private BigDecimal price;
}