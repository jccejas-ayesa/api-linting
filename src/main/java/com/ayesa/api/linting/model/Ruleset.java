package com.ayesa.api.linting.model;

import java.util.List;

public record Ruleset(
        String id,
        String name,
        String description,
        List<String> tags,
        int ruleCount,
        List<RuleInfo> rules
) {

    public record RuleInfo(
            String ruleId,
            String description
    ) {
    }
}
