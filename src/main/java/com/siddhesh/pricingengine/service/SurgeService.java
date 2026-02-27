package com.siddhesh.pricingengine.service;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalTime;

@Service
public class SurgeService {

    public BigDecimal getTimeBasedMultiplier() {

        LocalTime now = LocalTime.now();

        if (now.getHour() >= 18 && now.getHour() <= 22) {
            return BigDecimal.valueOf(1.10); // evening surge
        }

        return BigDecimal.ONE;
    }
}