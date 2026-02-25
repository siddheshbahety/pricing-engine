package com.siddhesh.pricingengine.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue
    private UUID id;

    private String name;

    private BigDecimal basePrice;

    private String category;

    private Integer inventory;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}