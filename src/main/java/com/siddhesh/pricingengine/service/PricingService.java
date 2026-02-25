package com.siddhesh.pricingengine.service;

import com.siddhesh.pricingengine.entity.Product;

import java.math.BigDecimal;

public interface PricingService {
    BigDecimal getPrice(Product product);
}