package com.siddhesh.pricingengine.controller;

import com.siddhesh.pricingengine.entity.Product;
import com.siddhesh.pricingengine.repository.ProductRepository;
import com.siddhesh.pricingengine.service.DemandService;
import com.siddhesh.pricingengine.service.PricingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final ProductRepository productRepository;
    private final PricingService pricingService;
    private final DemandService demandService;

    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        return productRepository.save(product);
    }

    @GetMapping("/{id}/price")
    public BigDecimal getPrice(@PathVariable UUID id) {
        log.info("Received request to fetch price for productId={}", id);
        return pricingService.getPrice(id);
    }

    @PostMapping("/{id}/order")
    public String placeOrder(@PathVariable UUID id) {
        demandService.recordOrder(id);
        return "Order recorded";
    }
}