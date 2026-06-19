package com.ayesa.api.linting.engine.rules;

import com.ayesa.api.linting.engine.LintingRule;
import com.ayesa.api.linting.model.LintingIssue;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AsyncChannelNoEmptyParameterRule implements LintingRule {

    @Override
    public String getRuleId() {
        return "asyncapi-channel-no-empty-parameter";
    }

    @Override
    public String getDescription() {
        return "Validates that channels do not contain empty parameter declarations";
    }

    @Override
    public String getRulesetId() {
        return "asyncapi-best-practices";
    }

    @Override
    public List<LintingIssue> apply(OpenAPI openAPI) {
        List<LintingIssue> issues = new ArrayList<>();
        if (openAPI.getPaths() == null) {
            return issues;
        }

        for (String path : openAPI.getPaths().keySet()) {
            if (path != null && path.contains("{}")) {
                issues.add(LintingIssue.error(
                        getRuleId(),
                        path,
                        "Channel path contains an empty parameter declaration",
                        "Replace '{}' with a named parameter such as '{channelId}' or remove it"
                ));
            }
        }

        return issues;
    }
}
