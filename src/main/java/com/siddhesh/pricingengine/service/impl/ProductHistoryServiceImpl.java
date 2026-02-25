package com.siddhesh.pricingengine.service.impl;

import com.siddhesh.pricingengine.entity.ProductHistory;
import com.siddhesh.pricingengine.repository.ProductHistoryRepository;
import com.siddhesh.pricingengine.service.ProductHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductHistoryServiceImpl implements ProductHistoryService {

    private final ProductHistoryRepository productHistoryRepository;

    @Override
    @Cacheable(value = "productHistoryCache", key = "#productId")
    public List<ProductHistory> getHistoryByProductId(UUID productId) {
        return productHistoryRepository.findByProductId(productId);
    }

    @Override
    public ProductHistory saveHistory(ProductHistory history) {
        return productHistoryRepository.save(history);
    }
}