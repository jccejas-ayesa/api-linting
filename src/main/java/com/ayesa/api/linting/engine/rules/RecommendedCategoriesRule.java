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
 * Validates that recommended categories (Business Unit, Domain, Sub-Domain, API Layer) are present.
 */
@Component
public class RecommendedCategoriesRule implements LintingRule {

    private static final Set<String> RECOMMENDED_CATEGORIES = Set.of(
            "Business Unit", "Domain", "Sub-Domain", "API Layer"
    );

    @Override
    public String getRuleId() {
        return "recommended-categories";
    }

    @Override
    public String getDescription() {
        return "Recommended categories (Business Unit, Domain, Sub-Domain, API Layer) should be present";
    }

    @Override
    public String getRulesetId() {
        return "api-catalog-information-best-practices";
    }

    @Override
    public List<LintingIssue> apply(OpenAPI openAPI) {
        List<LintingIssue> issues = new ArrayList<>();

        List<String> categories = extractCategories(openAPI);

        boolean hasRecommended = categories.stream()
                .anyMatch(cat -> RECOMMENDED_CATEGORIES.stream()
                        .anyMatch(rec -> cat.toLowerCase().contains(rec.toLowerCase())));

        if (!hasRecommended) {
            issues.add(LintingIssue.info(getRuleId(), "/",
                    "None of the recommended categories (Business Unit, Domain, Sub-Domain, API Layer) are present",
                    "Add x-categories with at least one of: Business Unit, Domain, Sub-Domain, API Layer"));
        }

        return issues;
    }

    private List<String> extractCategories(OpenAPI openAPI) {
        List<String> result = new ArrayList<>();

        // Check root extensions
        extractFromExtensions(openAPI.getExtensions(), result);

        // Check info extensions
        if (openAPI.getInfo() != null) {
            extractFromExtensions(openAPI.getInfo().getExtensions(), result);
        }

        return result;
    }

    private void extractFromExtensions(Map<String, Object> extensions, List<String> result) {
        if (extensions == null || !extensions.containsKey("x-categories")) {
            return;
        }
        Object categories = extensions.get("x-categories");
        if (categories instanceof List<?> list) {
            list.stream()
                    .filter(String.class::isInstance)
                    .map(String.class::cast)
                    .forEach(result::add);
        }
    }
}
