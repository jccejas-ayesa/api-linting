package com.ayesa.api.linting.engine.rules;

import com.ayesa.api.linting.engine.LintingRule;
import com.ayesa.api.linting.model.LintingIssue;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Component
public class AnyResourceUseLowercaseRule implements LintingRule {

    @Override
    public String getRuleId() {
        return "resource-use-lowercase";
    }

    @Override
    public String getDescription() {
        return "Path segments should use lowercase names";
    }

    @Override
    public String getRulesetId() {
        return "anypoint-best-practices";
    }

    @Override
    public List<LintingIssue> apply(OpenAPI openAPI) {
        List<LintingIssue> issues = new ArrayList<>();
        if (openAPI == null || openAPI.getPaths() == null) {
            return issues;
        }

        openAPI.getPaths().forEach((path, pathItem) -> {
            if (path == null) {
                return;
            }
            String[] segments = path.split("/");
            for (String segment : segments) {
                if (segment.isBlank() || isPathParameter(segment)) {
                    continue;
                }
                if (!segment.equals(segment.toLowerCase(Locale.ROOT))) {
                    issues.add(LintingIssue.warning(
                            getRuleId(),
                            path,
                            "Path segment '" + segment + "' is not lowercase",
                            "Use lowercase resource names for path segments, excluding parameter placeholders"
                    ));
                }
            }
        });

        return issues;
    }

    private boolean isPathParameter(String segment) {
        return segment.startsWith("{") && segment.endsWith("}");
    }
}
