package com.siddhesh.pricingengine.service;

import com.siddhesh.pricingengine.entity.Product;

import java.math.BigDecimal;
import java.util.List;

public interface ProductHistoryService {
    void saveHistory(Product product, BigDecimal price);
    List<BigDecimal> getHistoryByProductId(String productId);
}