package com.siddhesh.pricingengine.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "pricing")
@Data
public class PricingProperties {

    private Model model;
    private Demand demand;
    private Surge surge;
    private Cache cache;

    @Data
    public static class Model {
        private String version;
        private double baseMultiplier;
    }

    @Data
    public static class Demand {
        private double highThreshold;
        private double lowThreshold;
    }

    @Data
    public static class Surge {
        private double maxMultiplier;
        private double minMultiplier;
    }

    @Data
    public static class Cache {
        private int ttlSeconds;
    }
}