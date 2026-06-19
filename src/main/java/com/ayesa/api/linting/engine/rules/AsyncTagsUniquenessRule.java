package com.ayesa.api.linting.engine.rules;

import com.ayesa.api.linting.engine.LintingRule;
import com.ayesa.api.linting.model.LintingIssue;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class AsyncTagsUniquenessRule implements LintingRule {

    @Override
    public String getRuleId() {
        return "asyncapi-tags-uniqueness";
    }

    @Override
    public String getDescription() {
        return "Validates that tag names are unique";
    }

    @Override
    public String getRulesetId() {
        return "asyncapi-best-practices";
    }

    @Override
    public List<LintingIssue> apply(OpenAPI openAPI) {
        List<LintingIssue> issues = new ArrayList<>();
        if (openAPI.getTags() == null) {
            return issues;
        }

        Set<String> seenNames = new HashSet<>();
        for (Tag tag : openAPI.getTags()) {
            if (tag == null || tag.getName() == null || tag.getName().isBlank()) {
                continue;
            }
            if (!seenNames.add(tag.getName())) {
                issues.add(LintingIssue.warning(
                        getRuleId(),
                        "/tags/" + tag.getName(),
                        "Duplicate tag name detected: " + tag.getName(),
                        "Rename or remove duplicate tag definitions so each tag name is unique"
                ));
            }
        }

        return issues;
    }
}
