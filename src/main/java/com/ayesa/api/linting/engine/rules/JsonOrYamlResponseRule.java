package com.ayesa.api.linting.engine.rules;

import com.ayesa.api.linting.engine.LintingRule;
import com.ayesa.api.linting.model.LintingIssue;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.responses.ApiResponse;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Validates that GET and POST response content uses supported Datagraph media types.
 */
@Component
public class JsonOrYamlResponseRule implements LintingRule {

    private static final Set<String> ALLOWED_MEDIA_TYPES = Set.of(
            "application/json",
            "application/ld+json",
            "application/yaml"
    );

    @Override
    public String getRuleId() {
        return "not-json-or-yaml-response";
    }

    @Override
    public String getDescription() {
        return "Validates that GET and POST response content uses only JSON, JSON-LD, or YAML media types";
    }

    @Override
    public String getRulesetId() {
        return "datagraph-best-practices";
    }

    @Override
    public List<LintingIssue> apply(OpenAPI openAPI) {
        List<LintingIssue> issues = new ArrayList<>();
        if (openAPI == null || openAPI.getPaths() == null) {
            return issues;
        }

        for (Map.Entry<String, PathItem> entry : openAPI.getPaths().entrySet()) {
            String path = entry.getKey();
            PathItem pathItem = entry.getValue();
            if (pathItem == null) {
                continue;
            }

            checkOperation(issues, path, "GET", pathItem.getGet());
            checkOperation(issues, path, "POST", pathItem.getPost());
        }

        return issues;
    }

    private void checkOperation(List<LintingIssue> issues, String path, String method, Operation operation) {
        if (operation == null || operation.getResponses() == null) {
            return;
        }

        for (Map.Entry<String, ApiResponse> entry : operation.getResponses().entrySet()) {
            String responseCode = entry.getKey();
            ApiResponse response = entry.getValue();
            if (response == null || response.getContent() == null || response.getContent().isEmpty()) {
                continue;
            }

            for (String mediaType : response.getContent().keySet()) {
                if (ALLOWED_MEDIA_TYPES.contains(mediaType)) {
                    continue;
                }

                issues.add(LintingIssue.warning(
                        getRuleId(),
                        path + " [" + method + "]/responses/" + responseCode + "/content/" + mediaType,
                        "Response uses unsupported media type '" + mediaType + "'",
                        "Use only application/json, application/ld+json, or application/yaml for GET and POST responses"
                ));
            }
        }
    }
}
