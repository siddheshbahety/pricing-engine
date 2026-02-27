package com.siddhesh.pricingengine.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DemandService {

    private final StringRedisTemplate redisTemplate;

    public void recordOrder(UUID productId) {

        String key = "product:" + productId + ":orders_last_5_min";

        redisTemplate.opsForValue().increment(key);
        redisTemplate.expire(key, Duration.ofMinutes(5));
    }

    public BigDecimal getDemandMultiplier(UUID productId) {

        String key = "product:" + productId + ":orders_last_5_min";

        String value = redisTemplate.opsForValue().get(key);
        int orders = value == null ? 0 : Integer.parseInt(value);

        if (orders > 100) return BigDecimal.valueOf(1.30);
        if (orders > 50) return BigDecimal.valueOf(1.15);
        if (orders < 5) return BigDecimal.valueOf(0.95);

        return BigDecimal.ONE;
    }
}