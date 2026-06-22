package com.ayesa.api.linting.engine.rules;

import com.ayesa.api.linting.engine.LintingRule;
import com.ayesa.api.linting.model.LintingIssue;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class OWASPRateLimitingRule implements LintingRule {

    @Override
    public String getRuleId() {
        return "lack-of-resources-and-rate-limiting-too-many-requests";
    }

    @Override
    public String getDescription() {
        return "Validates that operations document a 429 Too Many Requests response for rate limiting behavior";
    }

    @Override
    public String getRulesetId() {
        return "owasp-api-security-top-10";
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
            checkOperation(issues, path, "PUT", pathItem.getPut());
            checkOperation(issues, path, "PATCH", pathItem.getPatch());
            checkOperation(issues, path, "DELETE", pathItem.getDelete());
        }

        return issues;
    }

    private void checkOperation(List<LintingIssue> issues, String path, String method, Operation operation) {
        if (operation == null) {
            return;
        }

        if (operation.getResponses() != null && operation.getResponses().containsKey("429")) {
            return;
        }

        issues.add(LintingIssue.warning(
                getRuleId(),
                path + " [" + method + "]/responses",
                "Operation does not document a 429 Too Many Requests response",
                "Add a 429 response so consumers understand the API rate limiting behavior"
        ));
    }
}
