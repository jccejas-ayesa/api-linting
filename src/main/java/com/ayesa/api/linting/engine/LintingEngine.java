package com.ayesa.api.linting.engine;

import com.ayesa.api.linting.model.LintingIssue;
import io.swagger.v3.oas.models.OpenAPI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class LintingEngine {

    private static final Logger log = LoggerFactory.getLogger(LintingEngine.class);

    private final List<LintingRule> rules;

    public LintingEngine(List<LintingRule> rules) {
        this.rules = rules;
        log.info("Linting engine initialized with {} rules across {} rulesets",
                rules.size(),
                rules.stream().map(LintingRule::getRulesetId).distinct().count());
    }

    public List<LintingIssue> analyze(OpenAPI openAPI) {
        return analyze(openAPI, null);
    }

    public List<LintingIssue> analyze(OpenAPI openAPI, List<String> rulesetIds) {
        List<LintingRule> activeRules = (rulesetIds == null || rulesetIds.isEmpty())
                ? rules
                : rules.stream()
                .filter(r -> rulesetIds.contains(r.getRulesetId()))
                .toList();

        List<LintingIssue> allIssues = new ArrayList<>();

        for (LintingRule rule : activeRules) {
            try {
                List<LintingIssue> issues = rule.apply(openAPI);
                allIssues.addAll(issues);
                log.debug("Rule '{}' found {} issues", rule.getRuleId(), issues.size());
            } catch (Exception e) {
                log.error("Error executing rule '{}': {}", rule.getRuleId(), e.getMessage(), e);
                allIssues.add(LintingIssue.error(
                        rule.getRuleId(),
                        "/",
                        "Rule execution failed: " + e.getMessage(),
                        "Check the specification structure"
                ));
            }
        }

        return allIssues;
    }

    public List<LintingRule> getRules() {
        return List.copyOf(rules);
    }
}
