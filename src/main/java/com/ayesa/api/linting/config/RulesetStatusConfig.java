package com.ayesa.api.linting.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
@EnableConfigurationProperties(RulesetStatusProperties.class)
public class RulesetStatusConfig {

    @Bean("rulesetStatus")
    public Map<String, String> rulesetStatus(RulesetStatusProperties properties) {
        return Map.copyOf(new LinkedHashMap<>(properties.getRulesets()));
    }
}
