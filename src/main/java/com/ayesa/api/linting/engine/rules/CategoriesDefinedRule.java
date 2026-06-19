package com.ayesa.api.linting.engine.rules;

import com.ayesa.api.linting.engine.LintingRule;
import com.ayesa.api.linting.model.LintingIssue;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Validates that categories are defined for easier classification of the API.
 * In OAS context, categories can be defined via x-categories extension or structured tags.
 */
@Component
public class CategoriesDefinedRule implements LintingRule {

    @Override
    public String getRuleId() {
        return "categories-should-be-defined";
    }

    @Override
    public String getDescription() {
        return "Categories should be defined for easier classification of the API";
    }

    @Override
    public String getRulesetId() {
        return "api-catalog-information-best-practices";
    }

    @Override
    public List<LintingIssue> apply(OpenAPI openAPI) {
        List<LintingIssue> issues = new ArrayList<>();

        boolean hasCategories = false;

        // Check x-categories extension at root level
        if (openAPI.getExtensions() != null && openAPI.getExtensions().containsKey("x-categories")) {
            Object categories = openAPI.getExtensions().get("x-categories");
            if (categories instanceof List<?> list && !list.isEmpty()) {
                hasCategories = true;
            }
        }

        // Check x-categories in info extensions
        if (!hasCategories && openAPI.getInfo() != null && openAPI.getInfo().getExtensions() != null) {
            if (openAPI.getInfo().getExtensions().containsKey("x-categories")) {
                Object categories = openAPI.getInfo().getExtensions().get("x-categories");
                if (categories instanceof List<?> list && !list.isEmpty()) {
                    hasCategories = true;
                }
            }
        }

        if (!hasCategories) {
            issues.add(LintingIssue.warning(getRuleId(), "/",
                    "No categories defined for this API",
                    "Add x-categories extension with at least one category (e.g., x-categories: [\"Payments\", \"Core\"])"));
        }

        return issues;
    }
}
