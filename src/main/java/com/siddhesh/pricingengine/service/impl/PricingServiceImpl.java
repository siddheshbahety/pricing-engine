package com.siddhesh.pricingengine.service.impl;

import com.siddhesh.pricingengine.entity.Product;
import com.siddhesh.pricingengine.repository.ProductRepository;
import com.siddhesh.pricingengine.service.DemandService;
import com.siddhesh.pricingengine.service.InventoryService;
import com.siddhesh.pricingengine.service.PricingService;
import com.siddhesh.pricingengine.service.SurgeService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PricingServiceImpl implements PricingService {

    private final ProductRepository productRepository;
    private final DemandService demandService;
    private final InventoryService inventoryService;
    private final SurgeService surgeService;

    @Override
    @Cacheable(value = "pricingCache", key = "#productId")
    public BigDecimal getPrice(UUID productId) {

        System.out.println("Calculating dynamic price...");

        // 1️⃣ Fetch product from Postgres
        Product product = productRepository.findById(productId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Product not found"));

        // 2️⃣ Base price
        BigDecimal basePrice = product.getBasePrice();

        // 3️⃣ Fetch multipliers
        BigDecimal demandMultiplier = demandService.getDemandMultiplier(productId);
        BigDecimal inventoryMultiplier = inventoryService.getInventoryMultiplier(product);
        BigDecimal surgeMultiplier = surgeService.getTimeBasedMultiplier();

        // 4️⃣ Final price calculation
        BigDecimal finalPrice = basePrice
                .multiply(demandMultiplier)
                .multiply(inventoryMultiplier)
                .multiply(surgeMultiplier);

        // 5️⃣ Proper rounding to 2 decimal places (currency safe)
        return finalPrice.setScale(2, RoundingMode.HALF_UP);
    }
}