package com.siddhesh.pricingengine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class PricingEngineApplication {

    public static void main(String[] args) {
        SpringApplication.run(PricingEngineApplication.class, args);
    }
}
