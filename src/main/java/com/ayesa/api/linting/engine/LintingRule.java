package com.ayesa.api.linting.engine;

import com.ayesa.api.linting.model.LintingIssue;
import io.swagger.v3.oas.models.OpenAPI;

import java.util.List;

/**
 * Contract for all linting rules.
 * Each rule inspects the parsed OpenAPI model and returns any issues found.
 * Rules are grouped into rulesets for organized governance.
 */
public interface LintingRule {

    String getRuleId();

    String getDescription();

    /**
     * Returns the ruleset this rule belongs to.
     */
    String getRulesetId();

    List<LintingIssue> apply(OpenAPI openAPI);
}
