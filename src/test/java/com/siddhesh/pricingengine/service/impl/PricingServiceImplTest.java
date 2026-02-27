package com.siddhesh.pricingengine.service.impl;

import com.siddhesh.pricingengine.entity.Product;
import com.siddhesh.pricingengine.repository.ProductRepository;
import com.siddhesh.pricingengine.service.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PricingServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private DemandService demandService;

    @Mock
    private InventoryService inventoryService;

    @Mock
    private SurgeService surgeService;

    @InjectMocks
    private PricingServiceImpl pricingService;

    private UUID productId;
    private Product product;

    @BeforeEach
    void setUp() {
        productId = UUID.randomUUID();

        product = Product.builder()
                .id(productId)
                .name("Test Product")
                .category("general")
                .basePrice(new BigDecimal("100.00"))
                .inventory(50)
                .build();
    }

    @Test
    void testGetPriceWithoutAdjustment() {

        when(productRepository.findById(productId))
                .thenReturn(Optional.of(product));

        when(demandService.getDemandMultiplier(productId))
                .thenReturn(BigDecimal.ONE);

        when(inventoryService.getInventoryMultiplier(product))
                .thenReturn(BigDecimal.ONE);

        when(surgeService.getTimeBasedMultiplier())
                .thenReturn(BigDecimal.ONE);

        BigDecimal price = pricingService.getPrice(productId);

        assertEquals(new BigDecimal("100.00"), price);
    }

    @Test
    void testGetPriceWithMultipliers() {

        when(productRepository.findById(productId))
                .thenReturn(Optional.of(product));

        when(demandService.getDemandMultiplier(productId))
                .thenReturn(new BigDecimal("1.10"));   // +10%

        when(inventoryService.getInventoryMultiplier(product))
                .thenReturn(new BigDecimal("1.20"));   // +20%

        when(surgeService.getTimeBasedMultiplier())
                .thenReturn(new BigDecimal("1.05"));   // +5%

        BigDecimal price = pricingService.getPrice(productId);

        // 100 * 1.10 * 1.20 * 1.05 = 138.60
        assertEquals(new BigDecimal("138.60"), price);
    }

    @Test
    void testProductNotFound() {

        when(productRepository.findById(productId))
                .thenReturn(Optional.empty());

        try {
            pricingService.getPrice(productId);
        } catch (RuntimeException ex) {
            assertEquals("Product not found", ex.getMessage());
        }
    }
}