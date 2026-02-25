package com.siddhesh.pricingengine.repository;

import com.siddhesh.pricingengine.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
    // findById, save, delete, etc. are inherited
}