package com.ayesa.api.linting.engine;

import com.ayesa.api.linting.model.LintingIssue;
import com.ayesa.api.linting.service.RulesetToggleService;
import io.swagger.v3.oas.models.OpenAPI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Component
public class LintingEngine {

    private static final Logger log = LoggerFactory.getLogger(LintingEngine.class);

    private final List<LintingRule> rules;
    private final RulesetToggleService rulesetToggleService;

    public LintingEngine(List<LintingRule> rules, RulesetToggleService rulesetToggleService) {
        this.rules = rules;
        this.rulesetToggleService = rulesetToggleService;
        log.info("Linting engine initialized with {} rules across {} rulesets",
                rules.size(),
                rules.stream()
                        .flatMap(rule -> rule.getRulesetIds().stream())
                        .filter(Objects::nonNull)
                        .distinct()
                        .count());
    }

    public List<LintingIssue> analyze(OpenAPI openAPI) {
        return analyze(openAPI, null);
    }

    public List<LintingIssue> analyze(OpenAPI openAPI, List<String> rulesetIds) {
        Set<String> requestedRulesets = rulesetIds == null ? Set.of() : Set.copyOf(rulesetIds);
        boolean filterByRequestedRulesets = !requestedRulesets.isEmpty();

        List<LintingRule> activeRules = rules.stream()
                .filter(rule -> {
                    List<String> enabledRulesets = rule.getRulesetIds().stream()
                            .filter(Objects::nonNull)
                            .filter(rulesetToggleService::isRulesetEnabled)
                            .toList();

                    if (enabledRulesets.isEmpty()) {
                        return false;
                    }

                    return !filterByRequestedRulesets
                            || enabledRulesets.stream().anyMatch(requestedRulesets::contains);
                })
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
