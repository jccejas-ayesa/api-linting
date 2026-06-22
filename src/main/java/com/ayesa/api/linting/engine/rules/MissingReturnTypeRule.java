package com.ayesa.api.linting.engine.rules;

import com.ayesa.api.linting.engine.LintingRule;
import com.ayesa.api.linting.model.LintingIssue;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.responses.ApiResponse;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Validates that JSON and YAML GET/POST responses define a schema.
 */
@Component
public class MissingReturnTypeRule implements LintingRule {

    @Override
    public String getRuleId() {
        return "missing-return-type";
    }

    @Override
    public String getDescription() {
        return "Validates that GET and POST JSON or YAML responses define a schema";
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
            if (response == null || response.getContent() == null) {
                continue;
            }

            checkMediaType(issues, path, method, responseCode, "application/json", response.getContent().get("application/json"));
            checkMediaType(issues, path, method, responseCode, "application/yaml", response.getContent().get("application/yaml"));
        }
    }

    private void checkMediaType(List<LintingIssue> issues, String path, String method, String responseCode,
                                String mediaTypeName, MediaType mediaType) {
        if (mediaType == null || mediaType.getSchema() != null) {
            return;
        }

        issues.add(LintingIssue.warning(
                getRuleId(),
                path + " [" + method + "]/responses/" + responseCode + "/content/" + mediaTypeName,
                "Response media type '" + mediaTypeName + "' is missing a schema",
                "Define a schema for the response media type"
        ));
    }
}
