package com.ayesa.api.linting.engine.rules;

import com.ayesa.api.linting.engine.LintingRule;
import com.ayesa.api.linting.model.LintingIssue;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Validates URL depth does not exceed 3 levels.
 * Covers API-BP-COMMON-008: No more than 3 levels in the URL.
 */
@Component
public class UrlDepthLimitRule implements LintingRule {

    private static final int MAX_DEPTH = 3;

    @Override
    public String getRuleId() {
        return "url-depth-limit";
    }

    @Override
    public String getDescription() {
        return "Validates that URL paths do not exceed 3 resource levels (API-BP-COMMON-008)";
    }

    @Override
    public List<LintingIssue> apply(OpenAPI openAPI) {
        List<LintingIssue> issues = new ArrayList<>();

        if (openAPI.getPaths() == null) {
            return issues;
        }

        for (String path : openAPI.getPaths().keySet()) {
            int resourceLevels = countResourceLevels(path);
            if (resourceLevels > MAX_DEPTH) {
                issues.add(LintingIssue.warning(getRuleId(), path,
                        "Path exceeds " + MAX_DEPTH + " resource levels (has " + resourceLevels + ")",
                        "Restructure the URL to reduce nesting. Extract sub-resources as independent endpoints"));
            }
        }

        return issues;
    }

    /**
     * Counts resource levels (non-parameter segments).
     * /notifications/{id}/phones/{phoneId} = 2 resource levels (notifications, phones)
     * but with params = /resource/{id}/sub/{id}/deep = 3 levels
     */
    private int countResourceLevels(String path) {
        String[] segments = path.split("/");
        int levels = 0;
        for (String segment : segments) {
            if (!segment.isEmpty() && !segment.startsWith("{")) {
                levels++;
            }
        }
        // Subtract version prefix if present (e.g., /v1/)
        if (segments.length > 1 && segments[1].matches("v\\d+")) {
            levels--;
        }
        return levels;
    }
}
