package com.siddhesh.pricingengine.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "price_history")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PriceHistory {

    @Id
    @GeneratedValue
    private UUID id;

    private UUID productId;

    private BigDecimal price;

    private Double multiplier;

    private LocalDateTime timestamp;
}