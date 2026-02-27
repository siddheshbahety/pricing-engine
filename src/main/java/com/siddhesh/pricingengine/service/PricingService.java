package com.siddhesh.pricingengine.service;

import java.math.BigDecimal;
import java.util.UUID;

public interface PricingService {

    BigDecimal getPrice(UUID productId);

}