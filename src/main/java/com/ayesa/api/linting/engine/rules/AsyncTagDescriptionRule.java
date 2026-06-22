package com.ayesa.api.linting.engine.rules;

import com.ayesa.api.linting.engine.LintingRule;
import com.ayesa.api.linting.model.LintingIssue;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AsyncTagDescriptionRule implements LintingRule {

    @Override
    public String getRuleId() {
        return "asyncapi-tag-description";
    }

    @Override
    public String getDescription() {
        return "Validates that AsyncAPI tags include descriptions";
    }

    @Override
    public String getRulesetId() {
        return "asyncapi-best-practices";
    }

    @Override
    public List<LintingIssue> apply(OpenAPI openAPI) {
        List<LintingIssue> issues = new ArrayList<>();
        if (!isAsyncApiSpec(openAPI) || openAPI.getTags() == null) {
            return issues;
        }

        for (Tag tag : openAPI.getTags()) {
            if (tag == null || tag.getName() == null || tag.getName().isBlank()) {
                continue;
            }
            if (tag.getDescription() == null || tag.getDescription().isBlank()) {
                issues.add(LintingIssue.warning(
                        getRuleId(),
                        "/tags/" + tag.getName(),
                        "Tag '" + tag.getName() + "' is missing a description",
                        "Add a non-blank description to the tag"
                ));
            }
        }

        return issues;
    }

    private boolean isAsyncApiSpec(OpenAPI openAPI) {
        if (openAPI.getExtensions() != null && openAPI.getExtensions().containsKey("x-asyncapi")) return true;
        if (openAPI.getInfo() != null) {
            String title = openAPI.getInfo().getTitle();
            String desc = openAPI.getInfo().getDescription();
            String combined = ((title != null ? title : "") + " " + (desc != null ? desc : "")).toLowerCase();
            return combined.contains("asyncapi") || combined.contains("event-driven")
                || combined.contains("kafka") || combined.contains("rabbitmq")
                || combined.contains("amqp") || combined.contains("mqtt");
        }
        return false;
    }
}
