package ru.starter.synthetichumancorestarter.config;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;

@ConfigurationProperties(prefix = "wy.starter")
public record StarterProperties(
        @DefaultValue("console")
        String auditMode,
        @DefaultValue("android-audit")
        String kafkaTopic,
        @DefaultValue("100")
        int queueCapacity
) {
    public StarterProperties() {
        this("console", "android-audit", 100);
    }
}