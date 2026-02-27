package com.siddhesh.pricingengine.service;

import com.siddhesh.pricingengine.entity.Product;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class InventoryService {

    public BigDecimal getInventoryMultiplier(Product product) {

        int stock = product.getInventory();

        if (stock < 10) return BigDecimal.valueOf(1.25);
        if (stock > 200) return BigDecimal.valueOf(0.90);

        return BigDecimal.ONE;
    }
}