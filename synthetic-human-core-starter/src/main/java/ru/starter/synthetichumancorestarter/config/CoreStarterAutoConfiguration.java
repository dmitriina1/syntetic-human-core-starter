package ru.starter.synthetichumancorestarter.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.starter.synthetichumancorestarter.service.CommandExecutorService;

@Configuration
public class CoreStarterAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public StarterProperties starterProperties() {
        return new StarterProperties(false, "android-audit", 100, "console");
    }
//
//    @Bean
//    @ConditionalOnMissingBean
//    public CommandExecutorService commandExecutorService(StarterProperties properties) {
//        return new CommandExecutorService(properties);
//    }
}
