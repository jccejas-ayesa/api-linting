package com.ayesa.api.linting.engine.rules;

import com.ayesa.api.linting.engine.LintingRule;
import com.ayesa.api.linting.model.LintingIssue;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.PathItem;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * Validates correct HTTP verb usage and no action verbs in URLs.
 * Covers API-BP-COMMON-003 and API-BP-COMMON-004.
 */
@Component
public class HttpVerbsUsageRule implements LintingRule {

    private static final Set<String> FORBIDDEN_VERBS_IN_PATH = Set.of(
            "create", "update", "delete", "remove", "get", "fetch",
            "modify", "add", "edit", "save", "insert", "put", "post"
    );

    private static final Pattern SEARCH_SUFFIX = Pattern.compile(".*/search$");

    @Override
    public String getRuleId() {
        return "http-verbs-usage";
    }

    @Override
    public String getDescription() {
        return "Validates that URLs use nouns (not action verbs) and HTTP methods are used correctly (API-BP-COMMON-003/004)";
    }

    @Override
    public String getRulesetId() {
        return "openapi-best-practices";
    }

    @Override
    public List<LintingIssue> apply(OpenAPI openAPI) {
        List<LintingIssue> issues = new ArrayList<>();

        if (openAPI.getPaths() == null) {
            return issues;
        }

        for (Map.Entry<String, PathItem> entry : openAPI.getPaths().entrySet()) {
            String path = entry.getKey();
            PathItem pathItem = entry.getValue();

            // Check for action verbs in URL
            checkVerbsInPath(issues, path);

            // Validate POST is not used for updates (should use PUT/PATCH)
            // Exception: /xxx/search is allowed
            if (pathItem.getPost() != null && path.contains("{") && !SEARCH_SUFFIX.matcher(path).matches()) {
                issues.add(LintingIssue.warning(getRuleId(), path + " [POST]",
                        "POST should not be used on existing resources (use PUT or PATCH)",
                        "Use PUT for full replacement or PATCH for partial updates on existing resources"));
            }

            // GET should not have a request body
            if (pathItem.getGet() != null && pathItem.getGet().getRequestBody() != null) {
                issues.add(LintingIssue.error(getRuleId(), path + " [GET]",
                        "GET operations must not have a request body",
                        "Move filters to query parameters or use POST /search for complex queries"));
            }
        }

        return issues;
    }

    private void checkVerbsInPath(List<LintingIssue> issues, String path) {
        String[] segments = path.split("/");
        for (String segment : segments) {
            if (segment.isEmpty() || segment.startsWith("{")) {
                continue;
            }
            String lowerSegment = segment.toLowerCase();
            for (String verb : FORBIDDEN_VERBS_IN_PATH) {
                if (lowerSegment.equals(verb) || lowerSegment.startsWith(verb + "-")) {
                    // Allow "search" as exception (API-BP-COMMON-009)
                    if ("search".equals(lowerSegment)) {
                        continue;
                    }
                    issues.add(LintingIssue.warning(getRuleId(), path,
                            "Path contains action verb '" + segment + "' — use nouns for resources",
                            "Replace with a noun representing the resource (e.g., /users instead of /get-users)"));
                    break;
                }
            }
        }
    }
}
