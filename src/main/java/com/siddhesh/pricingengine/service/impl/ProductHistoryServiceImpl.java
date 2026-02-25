package com.siddhesh.pricingengine.service.impl;

import com.siddhesh.pricingengine.entity.Product;
import com.siddhesh.pricingengine.entity.ProductHistory;
import com.siddhesh.pricingengine.repository.ProductHistoryRepository;
import com.siddhesh.pricingengine.service.ProductHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductHistoryServiceImpl implements ProductHistoryService {

    private final ProductHistoryRepository historyRepository;

    @Override
    public void saveHistory(Product product, BigDecimal price) {
        ProductHistory history = ProductHistory.builder()
                .productId(product.getId())
                .price(price)
                .changedAt(LocalDateTime.now())
                .build();
        historyRepository.save(history);
    }

    @Override
    public List<BigDecimal> getHistoryByProductId(String productId) {
        UUID id = UUID.fromString(productId);
        return historyRepository.findByProductIdOrderByChangedAtDesc(id)
                .stream()
                .map(ProductHistory::getPrice)
                .collect(Collectors.toList());
    }
}