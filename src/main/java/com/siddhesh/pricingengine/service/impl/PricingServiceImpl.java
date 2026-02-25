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
    public BigDecimal getPrice(Product product) {
        BigDecimal price = product.getBasePrice();

        if ("premium".equalsIgnoreCase(product.getCategory())) {
            price = price.multiply(BigDecimal.valueOf(1.1)); // 10% only
        }

        return price;
    }
}