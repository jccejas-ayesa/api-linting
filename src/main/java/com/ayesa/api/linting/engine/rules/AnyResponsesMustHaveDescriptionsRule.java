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

@Component
public class AnyResponsesMustHaveDescriptionsRule implements LintingRule {

    @Override
    public String getRuleId() {
        return "responses-must-have-descriptions";
    }

    @Override
    public String getDescription() {
        return "Response objects must define descriptions";
    }

    @Override
    public String getRulesetId() {
        return "anypoint-best-practices";
    }

    @Override
    public List<LintingIssue> apply(OpenAPI openAPI) {
        List<LintingIssue> issues = new ArrayList<>();
        if (openAPI == null) {
            return issues;
        }

        if (openAPI.getPaths() != null) {
            for (Map.Entry<String, PathItem> pathEntry : openAPI.getPaths().entrySet()) {
                PathItem pathItem = pathEntry.getValue();
                if (pathItem == null || pathItem.readOperationsMap() == null) {
                    continue;
                }
                for (Map.Entry<PathItem.HttpMethod, Operation> operationEntry : pathItem.readOperationsMap().entrySet()) {
                    Operation operation = operationEntry.getValue();
                    if (operation == null || operation.getResponses() == null) {
                        continue;
                    }
                    for (Map.Entry<String, ApiResponse> responseEntry : operation.getResponses().entrySet()) {
                        checkResponse(openAPI, issues, pathEntry.getKey() + " [" + operationEntry.getKey() + "]", responseEntry.getKey(), responseEntry.getValue());
                    }
                }
            }
        }

        if (openAPI.getComponents() != null && openAPI.getComponents().getResponses() != null) {
            for (Map.Entry<String, ApiResponse> entry : openAPI.getComponents().getResponses().entrySet()) {
                ApiResponse response = entry.getValue();
                if (response == null || response.getDescription() == null || response.getDescription().isBlank()) {
                    issues.add(LintingIssue.warning(
                            getRuleId(),
                            "/components/responses/" + entry.getKey(),
                            "Response '" + entry.getKey() + "' is missing a description",
                            "Add a description to the reusable response"
                    ));
                }
            }
        }

        return issues;
    }

    private void checkResponse(OpenAPI openAPI, List<LintingIssue> issues, String location, String statusCode, ApiResponse response) {
        ApiResponse resolved = resolveResponse(openAPI, response);
        if (resolved != null && (resolved.getDescription() == null || resolved.getDescription().isBlank())) {
            issues.add(LintingIssue.warning(
                    getRuleId(),
                    location + "/responses/" + statusCode,
                    "Response '" + statusCode + "' is missing a description",
                    "Add a description explaining the response"
            ));
        }
    }

    private ApiResponse resolveResponse(OpenAPI openAPI, ApiResponse response) {
        if (response == null || response.get$ref() == null || openAPI.getComponents() == null || openAPI.getComponents().getResponses() == null) {
            return response;
        }
        String ref = response.get$ref();
        return openAPI.getComponents().getResponses().getOrDefault(ref.substring(ref.lastIndexOf('/') + 1), response);
    }
}
