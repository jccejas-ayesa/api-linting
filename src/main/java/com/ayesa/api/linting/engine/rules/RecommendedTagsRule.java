package com.ayesa.api.linting.engine.rules;

import com.ayesa.api.linting.engine.LintingRule;
import com.ayesa.api.linting.model.LintingIssue;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Validates that recommended tags (system, process, experience) are present.
 */
@Component
public class RecommendedTagsRule implements LintingRule {

    private static final Set<String> RECOMMENDED_TAGS = Set.of("system", "process", "experience");

    @Override
    public String getRuleId() {
        return "recommended-tags";
    }

    @Override
    public String getDescription() {
        return "Recommended tags (system, process, experience) should be present";
    }

    @Override
    public String getRulesetId() {
        return "api-catalog-information-best-practices";
    }

    @Override
    public List<LintingIssue> apply(OpenAPI openAPI) {
        List<LintingIssue> issues = new ArrayList<>();

        List<String> tagNames = Collections.emptyList();
        if (openAPI.getTags() != null) {
            tagNames = openAPI.getTags().stream()
                    .map(Tag::getName)
                    .filter(name -> name != null)
                    .map(String::toLowerCase)
                    .toList();
        }

        boolean hasRecommended = tagNames.stream()
                .anyMatch(RECOMMENDED_TAGS::contains);

        if (!hasRecommended) {
            issues.add(LintingIssue.info(getRuleId(), "/tags",
                    "None of the recommended tags (system, process, experience) are present",
                    "Add at least one of: system, process, experience as a tag to classify your API layer"));
        }

        return issues;
    }
}
