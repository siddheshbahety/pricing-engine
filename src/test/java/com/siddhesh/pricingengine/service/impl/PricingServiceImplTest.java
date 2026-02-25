package com.siddhesh.pricingengine.service.impl;

import com.siddhesh.pricingengine.entity.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class PricingServiceImplTest {

    private PricingServiceImpl pricingService;

    @BeforeEach
    void setUp() {
        pricingService = new PricingServiceImpl();
    }

    @Test
    void testGetPriceWithoutCategoryAdjustment() {
        Product product = Product.builder()
                .name("Basic Product")
                .basePrice(new BigDecimal("100"))
                .category("general")
                .inventory(10)
                .build();

        BigDecimal price = pricingService.getPrice(product);

        assertNotNull(price);
        assertEquals(new BigDecimal("100"), price); // assuming no adjustment rules for "general"
    }

    @Test
    void testGetPriceWithCategoryAdjustment() {
        Product product = Product.builder()
                .name("Luxury Product")
                .basePrice(new BigDecimal("200"))
                .category("premium")
                .inventory(5)
                .build();

        BigDecimal price = pricingService.getPrice(product);

        // assuming your PricingServiceImpl adds 10% for premium
        assertEquals(new BigDecimal("220.0"), price);
    }

    @Test
    void testGetPriceCachingSimulation() {
        Product product = Product.builder()
                .name("Cached Product")
                .basePrice(new BigDecimal("50"))
                .category("general")
                .inventory(10)
                .build();

        BigDecimal firstCall = pricingService.getPrice(product);
        BigDecimal secondCall = pricingService.getPrice(product);

        assertEquals(firstCall, secondCall); // ensures same output for repeated calls
    }
}