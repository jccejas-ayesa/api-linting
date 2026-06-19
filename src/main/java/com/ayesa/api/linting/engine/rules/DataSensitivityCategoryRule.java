package com.ayesa.api.linting.engine.rules;

import com.ayesa.api.linting.engine.LintingRule;
import com.ayesa.api.linting.model.LintingIssue;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Validates that data sensitivity category is declared.
 * Helps identify APIs that expose personal identifiable information (PII).
 * In OAS context, uses x-data-sensitivity extension.
 */
@Component
public class DataSensitivityCategoryRule implements LintingRule {

    private static final Set<String> VALID_SENSITIVITY_VALUES = Set.of(
            "high", "low", "medium", "public", "confidential", "restricted"
    );

    @Override
    public String getRuleId() {
        return "data-sensitivity-category-should-be-declared";
    }

    @Override
    public String getDescription() {
        return "Data sensitivity categories should be present to identify APIs exposing PII";
    }

    @Override
    public String getRulesetId() {
        return "api-catalog-information-best-practices";
    }

    @Override
    public List<LintingIssue> apply(OpenAPI openAPI) {
        List<LintingIssue> issues = new ArrayList<>();

        boolean hasSensitivity = false;

        // Check x-data-sensitivity at root
        if (openAPI.getExtensions() != null) {
            hasSensitivity = checkSensitivityExtension(openAPI.getExtensions());
        }

        // Check x-data-sensitivity in info
        if (!hasSensitivity && openAPI.getInfo() != null && openAPI.getInfo().getExtensions() != null) {
            hasSensitivity = checkSensitivityExtension(openAPI.getInfo().getExtensions());
        }

        // Check in x-categories for "Data Sensitivity:high" or "Data Sensitivity:low" pattern
        if (!hasSensitivity) {
            hasSensitivity = checkCategoriesForSensitivity(openAPI);
        }

        if (!hasSensitivity) {
            issues.add(LintingIssue.warning(getRuleId(), "/",
                    "Data sensitivity category is not declared",
                    "Add x-data-sensitivity extension (e.g., x-data-sensitivity: high) to identify if this API exposes PII"));
        }

        return issues;
    }

    private boolean checkSensitivityExtension(Map<String, Object> extensions) {
        return extensions.containsKey("x-data-sensitivity");
    }

    private boolean checkCategoriesForSensitivity(OpenAPI openAPI) {
        Map<String, Object> extensions = null;
        if (openAPI.getExtensions() != null) {
            extensions = openAPI.getExtensions();
        } else if (openAPI.getInfo() != null && openAPI.getInfo().getExtensions() != null) {
            extensions = openAPI.getInfo().getExtensions();
        }

        if (extensions == null || !extensions.containsKey("x-categories")) {
            return false;
        }

        Object categories = extensions.get("x-categories");
        if (categories instanceof List<?> list) {
            return list.stream()
                    .filter(String.class::isInstance)
                    .map(String.class::cast)
                    .anyMatch(cat -> cat.toLowerCase().contains("data sensitivity"));
        }
        return false;
    }
}
