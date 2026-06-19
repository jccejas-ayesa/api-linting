package com.ayesa.api.linting.engine.rules;

import com.ayesa.api.linting.engine.LintingRule;
import com.ayesa.api.linting.model.LintingIssue;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Validates that operations define appropriate HTTP response codes.
 */
@Component
public class ResponseCodesRule implements LintingRule {

    private static final Set<String> ERROR_CODES = Set.of("400", "401", "403", "404", "500");

    @Override
    public String getRuleId() {
        return "response-codes-validation";
    }

    @Override
    public String getDescription() {
        return "Validates that operations define appropriate success and error response codes";
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
            checkOperation(issues, path, "DELETE", pathItem.getDelete());
            checkOperation(issues, path, "PATCH", pathItem.getPatch());
        }

        return issues;
    }

    private void checkOperation(List<LintingIssue> issues, String path, String method, Operation operation) {
        if (operation == null) {
            return;
        }

        String location = path + " [" + method + "]";
        ApiResponses responses = operation.getResponses();

        if (responses == null || responses.isEmpty()) {
            issues.add(LintingIssue.error(getRuleId(), location,
                    "No responses defined",
                    "Define at least a success response (2xx)"));
            return;
        }

        boolean hasSuccess = responses.keySet().stream().anyMatch(code -> code.startsWith("2"));
        if (!hasSuccess) {
            issues.add(LintingIssue.error(getRuleId(), location,
                    "No success response (2xx) defined",
                    "Add at least one 2xx response code"));
        }

        boolean hasAnyError = responses.keySet().stream().anyMatch(ERROR_CODES::contains);
        if (!hasAnyError) {
            issues.add(LintingIssue.warning(getRuleId(), location,
                    "No error responses defined",
                    "Consider adding error responses (400, 401, 404, 500)"));
        }

        for (Map.Entry<String, ApiResponse> respEntry : responses.entrySet()) {
            ApiResponse response = respEntry.getValue();
            if (response.getDescription() == null || response.getDescription().isBlank()) {
                issues.add(LintingIssue.warning(getRuleId(), location + "/responses/" + respEntry.getKey(),
                        "Response '" + respEntry.getKey() + "' has no description",
                        "Add a description explaining when this response is returned"));
            }
        }
    }
}
