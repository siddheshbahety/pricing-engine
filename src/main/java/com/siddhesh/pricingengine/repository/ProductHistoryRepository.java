package com.siddhesh.pricingengine.repository;

import com.siddhesh.pricingengine.entity.ProductHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductHistoryRepository extends JpaRepository<ProductHistory, UUID> {

    List<ProductHistory> findByProductId(UUID productId);
}