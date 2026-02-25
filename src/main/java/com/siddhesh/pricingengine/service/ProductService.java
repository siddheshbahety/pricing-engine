package com.siddhesh.pricingengine.service;

import com.siddhesh.pricingengine.entity.Product;

import java.util.UUID;

public interface ProductService {
    Product getProductById(UUID id);
    Product saveProduct(Product product);
}