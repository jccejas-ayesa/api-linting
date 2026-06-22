package com.ayesa.api.linting.engine.rules;

import com.ayesa.api.linting.engine.LintingRule;
import com.ayesa.api.linting.model.LintingIssue;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AsyncChannelNoTrailingSlashRule implements LintingRule {

    @Override
    public String getRuleId() {
        return "asyncapi-channel-no-trailing-slash";
    }

    @Override
    public String getDescription() {
        return "Validates that channels do not end with a trailing slash";
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
            if (path != null && !"/".equals(path) && path.endsWith("/")) {
                issues.add(LintingIssue.error(
                        getRuleId(),
                        path,
                        "Channel path must not end with a trailing slash",
                        "Remove the trailing slash from the channel path"
                ));
            }
        }

        return issues;
    }
}
