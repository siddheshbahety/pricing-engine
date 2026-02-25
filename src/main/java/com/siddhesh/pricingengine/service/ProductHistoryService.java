package com.siddhesh.pricingengine.service;

import com.siddhesh.pricingengine.entity.ProductHistory;
import java.util.List;
import java.util.UUID;

public interface ProductHistoryService {
    List<ProductHistory> getHistoryByProductId(UUID productId);
    ProductHistory saveHistory(ProductHistory history);
}