package com.siddhesh.pricingengine.repository;

import com.siddhesh.pricingengine.entity.PriceHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PriceHistoryRepository extends JpaRepository<PriceHistory, UUID> {

    // Example: get history by product
    List<PriceHistory> findByProductIdOrderByTimestampDesc(UUID productId);
}