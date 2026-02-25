package com.siddhesh.pricingengine.controller;

import com.siddhesh.pricingengine.dto.ProductRequest;
import com.siddhesh.pricingengine.dto.ProductResponse;
import com.siddhesh.pricingengine.dto.PriceResponse;
import com.siddhesh.pricingengine.entity.Product;
import com.siddhesh.pricingengine.exception.ResourceNotFoundException;
import com.siddhesh.pricingengine.service.ProductService;
import com.siddhesh.pricingengine.service.PricingService;
import com.siddhesh.pricingengine.service.ProductHistoryService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final PricingService pricingService;
    private final ProductHistoryService productHistoryService;

    // Create a new product
    @PostMapping("/products")
    public ProductResponse createProduct(@Valid @RequestBody ProductRequest request) {
        Product product = Product.builder()
                .name(request.getName())
                .basePrice(request.getBasePrice())
                .category(request.getCategory())
                .inventory(request.getInventory())
                .build();

        Product saved = productService.saveProduct(product);
        return ProductResponse.builder()
                .id(saved.getId())
                .name(saved.getName())
                .basePrice(saved.getBasePrice())
                .category(saved.getCategory())
                .inventory(saved.getInventory())
                .currentPrice(saved.getBasePrice())
                .build();
    }

    // Get product by ID with dynamic price
    @GetMapping("/products/{id}")
    public ProductResponse getProduct(@PathVariable UUID id) {
        Product product = productService.getProductById(id);
        if (product == null) throw new ResourceNotFoundException("Product with id " + id + " not found");

        // Calculate dynamic price
        var price = pricingService.getPrice(product);

        // Save history
        productHistoryService.saveHistory(product, price);

        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .basePrice(product.getBasePrice())
                .category(product.getCategory())
                .inventory(product.getInventory())
                .currentPrice(price)
                .build();
    }

    // Get just the dynamic price for a product
    @GetMapping("/products/{id}/price")
    public PriceResponse getPrice(@PathVariable UUID id) {
        Product product = productService.getProductById(id);
        if (product == null) throw new RuntimeException("Product not found");

        var price = pricingService.getPrice(product);
        productHistoryService.saveHistory(product, price);

        return PriceResponse.builder()
                .productId(product.getId())
                .price(price)
                .build();
    }
    @GetMapping("/products/{id}/history")
    public List<BigDecimal> getPriceHistory(@PathVariable String id) {
        return productHistoryService.getHistoryByProductId(id);
    }
}