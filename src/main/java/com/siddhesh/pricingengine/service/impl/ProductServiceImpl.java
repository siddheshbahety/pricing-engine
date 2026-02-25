package com.siddhesh.pricingengine.service.impl;

import com.siddhesh.pricingengine.entity.Product;
import com.siddhesh.pricingengine.repository.ProductRepository;
import com.siddhesh.pricingengine.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    @Cacheable(value = "productCache", key = "#id")
    public Product getProductById(UUID id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }
}