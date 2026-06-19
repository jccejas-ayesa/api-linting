package com.ayesa.api.linting.engine.rules;

import com.ayesa.api.linting.engine.LintingRule;
import com.ayesa.api.linting.model.LintingIssue;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AsyncInfoDescriptionRule implements LintingRule {

    @Override
    public String getRuleId() {
        return "asyncapi-info-description";
    }

    @Override
    public String getDescription() {
        return "Validates that AsyncAPI specifications include an info description";
    }

    @Override
    public String getRulesetId() {
        return "asyncapi-best-practices";
    }

    @Override
    public List<LintingIssue> apply(OpenAPI openAPI) {
        List<LintingIssue> issues = new ArrayList<>();
        if (!isAsyncApiSpec(openAPI)) {
            return issues;
        }

        if (openAPI.getInfo() == null || openAPI.getInfo().getDescription() == null
                || openAPI.getInfo().getDescription().isBlank()) {
            issues.add(LintingIssue.warning(
                    getRuleId(),
                    "/info/description",
                    "Info description is missing",
                    "Add a clear description to the AsyncAPI info section"
            ));
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
