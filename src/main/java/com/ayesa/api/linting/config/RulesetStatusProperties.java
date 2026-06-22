package com.ayesa.api.linting.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.LinkedHashMap;
import java.util.Map;

@ConfigurationProperties(prefix = "api-linting")
public class RulesetStatusProperties {

    private Map<String, String> rulesets = new LinkedHashMap<>();

    public Map<String, String> getRulesets() {
        return rulesets;
    }

    public void setRulesets(Map<String, String> rulesets) {
        this.rulesets = rulesets;
    }
}
