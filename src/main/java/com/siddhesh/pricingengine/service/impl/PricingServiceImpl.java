package com.siddhesh.pricingengine.service.impl;

import com.siddhesh.pricingengine.entity.Product;
import com.siddhesh.pricingengine.service.PricingService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class PricingServiceImpl implements PricingService {

    @Override
    @Cacheable(value = "pricingCache", key = "#product.id")
    public BigDecimal calculatePrice(Product product) {
        BigDecimal multiplier = BigDecimal.ONE;

        // Example: increase 20% for "premium" products
        if (product.getName() != null && product.getName().toLowerCase().contains("premium")) {
            multiplier = new BigDecimal("1.2");
        }

        // Multiply basePrice by multiplier
        return product.getBasePrice().multiply(multiplier);
    }
}