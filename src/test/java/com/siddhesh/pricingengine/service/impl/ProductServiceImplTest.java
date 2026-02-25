package com.siddhesh.pricingengine.service.impl;

import com.siddhesh.pricingengine.entity.Product;
import com.siddhesh.pricingengine.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceImplTest {

    private ProductRepository productRepository;
    private ProductServiceImpl productService;

    @BeforeEach
    void setUp() {
        // Mock repository
        productRepository = mock(ProductRepository.class);
        productService = new ProductServiceImpl(productRepository);
    }

    @Test
    void testGetProductByIdFound() {
        UUID id = UUID.randomUUID();
        Product product = Product.builder()
                .id(id)
                .name("Test Product")
                .basePrice(new BigDecimal("100"))
                .build();

        // Mock repository behavior
        when(productRepository.findById(id)).thenReturn(Optional.of(product));

        Product result = productService.getProductById(id);

        assertNotNull(result);
        assertEquals("Test Product", result.getName());
        verify(productRepository, times(1)).findById(id);
    }

    @Test
    void testGetProductByIdNotFound() {
        UUID id = UUID.randomUUID();
        when(productRepository.findById(id)).thenReturn(Optional.empty());

        Product result = productService.getProductById(id);

        assertNull(result); // matches your current implementation
        verify(productRepository, times(1)).findById(id);
    }

    @Test
    void testSaveProduct() {
        Product product = Product.builder()
                .name("New Product")
                .basePrice(new BigDecimal("50"))
                .build();

        when(productRepository.save(product)).thenReturn(product);

        Product saved = productService.saveProduct(product);

        assertNotNull(saved);
        assertEquals("New Product", saved.getName());
        verify(productRepository, times(1)).save(product);
    }
}