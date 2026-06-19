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

/**
 * Validates that success responses define response schemas.
 * Covers directive 4.4: Validación obligatoria de salida.
 */
@Component
public class ResponseSchemaRequiredRule implements LintingRule {

    @Override
    public String getRuleId() {
        return "response-schema-required";
    }

    @Override
    public String getDescription() {
        return "Validates that success responses (2xx) define a response body schema";
    }

    @Override
    public List<LintingIssue> apply(OpenAPI openAPI) {
        List<LintingIssue> issues = new ArrayList<>();

        if (openAPI.getPaths() == null) {
            return issues;
        }

        for (Map.Entry<String, PathItem> pathEntry : openAPI.getPaths().entrySet()) {
            String path = pathEntry.getKey();
            PathItem pathItem = pathEntry.getValue();

            checkOperation(issues, path, "GET", pathItem.getGet());
            checkOperation(issues, path, "POST", pathItem.getPost());
            checkOperation(issues, path, "PUT", pathItem.getPut());
            checkOperation(issues, path, "PATCH", pathItem.getPatch());
        }

        return issues;
    }

    private void checkOperation(List<LintingIssue> issues, String path, String method, Operation operation) {
        if (operation == null || operation.getResponses() == null) {
            return;
        }

        String location = path + " [" + method + "]";

        for (Map.Entry<String, ApiResponse> respEntry : operation.getResponses().entrySet()) {
            String code = respEntry.getKey();
            if (!code.startsWith("2") || "204".equals(code)) {
                continue;
            }

            ApiResponse response = respEntry.getValue();

            if (response.getContent() == null || response.getContent().isEmpty()) {
                issues.add(LintingIssue.warning(getRuleId(), location + "/responses/" + code,
                        "Success response " + code + " has no content schema defined",
                        "Define a response body schema to ensure contract compliance"));
            }
        }
    }
}
