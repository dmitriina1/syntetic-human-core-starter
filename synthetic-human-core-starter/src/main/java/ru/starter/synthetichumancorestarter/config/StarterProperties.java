package ru.starter.synthetichumancorestarter.config;


import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "starter.synthetichumancorestarter")
public record StarterProperties(
        boolean auditToKafka,
        String kafkaTopic,
        int queueCapacity,
        String auditMode // "console" or "kafka"
) {}