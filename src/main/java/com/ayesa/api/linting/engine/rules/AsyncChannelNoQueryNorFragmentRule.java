package com.ayesa.api.linting.engine.rules;

import com.ayesa.api.linting.engine.LintingRule;
import com.ayesa.api.linting.model.LintingIssue;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AsyncChannelNoQueryNorFragmentRule implements LintingRule {

    @Override
    public String getRuleId() {
        return "asyncapi-channel-no-query-nor-fragment";
    }

    @Override
    public String getDescription() {
        return "Validates that channels do not include query strings or fragments";
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
            if (path != null && (path.contains("?") || path.contains("#"))) {
                issues.add(LintingIssue.error(
                        getRuleId(),
                        path,
                        "Channel path must not contain a query string or fragment",
                        "Remove any '?' query section or '#' fragment from the channel path"
                ));
            }
        }

        return issues;
    }
}
