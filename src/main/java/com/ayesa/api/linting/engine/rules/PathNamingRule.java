package com.ayesa.api.linting.engine.rules;

import com.ayesa.api.linting.engine.LintingRule;
import com.ayesa.api.linting.model.LintingIssue;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.PathItem;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Validates path naming conventions.
 * Paths should use kebab-case and avoid trailing slashes.
 */
@Component
public class PathNamingRule implements LintingRule {

    private static final Pattern KEBAB_CASE = Pattern.compile("^(/[a-z0-9][a-z0-9\\-]*(/\\{[a-zA-Z][a-zA-Z0-9]*})?)+$");

    @Override
    public String getRuleId() {
        return "path-naming-convention";
    }

    @Override
    public String getDescription() {
        return "Validates that paths use kebab-case naming convention and avoid trailing slashes";
    }

    @Override
    public String getRulesetId() {
        return "openapi-best-practices";
    }

    @Override
    public List<LintingIssue> apply(OpenAPI openAPI) {
        List<LintingIssue> issues = new ArrayList<>();

        if (openAPI.getPaths() == null) {
            issues.add(LintingIssue.warning(getRuleId(), "/paths", "No paths defined", "Add at least one API path"));
            return issues;
        }

        for (Map.Entry<String, PathItem> entry : openAPI.getPaths().entrySet()) {
            String path = entry.getKey();

            if (path.endsWith("/") && !path.equals("/")) {
                issues.add(LintingIssue.warning(getRuleId(), path, "Path has trailing slash", "Remove the trailing slash from: " + path));
            }

            if (!KEBAB_CASE.matcher(path).matches() && !path.equals("/")) {
                issues.add(LintingIssue.warning(getRuleId(), path,
                        "Path does not follow kebab-case convention",
                        "Use kebab-case for path segments (e.g., /my-resource instead of /myResource)"));
            }
        }

        return issues;
    }
}
