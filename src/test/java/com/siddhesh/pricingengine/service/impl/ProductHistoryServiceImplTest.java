package com.siddhesh.pricingengine.service.impl;

import com.siddhesh.pricingengine.entity.Product;
import com.siddhesh.pricingengine.entity.ProductHistory;
import com.siddhesh.pricingengine.repository.ProductHistoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductHistoryServiceImplTest {

    private ProductHistoryRepository historyRepository;
    private ProductHistoryServiceImpl historyService;

    @BeforeEach
    void setUp() {
        historyRepository = mock(ProductHistoryRepository.class);
        historyService = new ProductHistoryServiceImpl(historyRepository);
    }

    @Test
    void testSaveHistory() {
        Product product = Product.builder()
                .id(UUID.randomUUID())
                .name("Test Product")
                .basePrice(new BigDecimal("100"))
                .build();
        BigDecimal price = new BigDecimal("110");

        historyService.saveHistory(product, price);

        ArgumentCaptor<ProductHistory> captor = ArgumentCaptor.forClass(ProductHistory.class);
        verify(historyRepository, times(1)).save(captor.capture());

        ProductHistory savedHistory = captor.getValue();
        assertEquals(product.getId(), savedHistory.getProductId());
        assertEquals(price, savedHistory.getPrice());
        assertNotNull(savedHistory.getChangedAt());
    }

    @Test
    void testGetHistoryByProductId() {
        UUID productId = UUID.randomUUID();
        ProductHistory h1 = ProductHistory.builder().productId(productId).price(new BigDecimal("100")).changedAt(LocalDateTime.now().minusDays(1)).build();
        ProductHistory h2 = ProductHistory.builder().productId(productId).price(new BigDecimal("110")).changedAt(LocalDateTime.now()).build();

        when(historyRepository.findByProductIdOrderByChangedAtDesc(productId)).thenReturn(Arrays.asList(h2, h1));

        List<BigDecimal> prices = historyService.getHistoryByProductId(productId.toString());

        assertEquals(2, prices.size());
        assertEquals(new BigDecimal("110"), prices.get(0)); // newest first
        assertEquals(new BigDecimal("100"), prices.get(1)); // oldest last
    }
}