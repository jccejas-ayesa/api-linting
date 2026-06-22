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

/**
 * Validates DELETE operations return 204 (No Content) or 202 (Accepted).
 * DELETE must not return response body.
 * Covers API-BP-DELETE-001 and API-BP-DELETE-002.
 */
@Component
public class DeleteResponseStandardRule implements LintingRule {

    @Override
    public String getRuleId() {
        return "delete-response-standard";
    }

    @Override
    public String getDescription() {
        return "Validates that DELETE operations return 204 (no body) or 202 (async), never 200 with body (API-BP-DELETE-001/002)";
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

            if (pathItem.getDelete() == null) {
                continue;
            }

            Operation deleteOp = pathItem.getDelete();
            if (deleteOp.getResponses() == null) {
                continue;
            }

            boolean has204 = deleteOp.getResponses().containsKey("204");
            boolean has202 = deleteOp.getResponses().containsKey("202");
            boolean has200 = deleteOp.getResponses().containsKey("200");

            if (has200 && !has204 && !has202) {
                issues.add(LintingIssue.warning(getRuleId(), path + " [DELETE]",
                        "DELETE returns 200 instead of 204 No Content",
                        "Use 204 (no body) for successful deletes, or 202 for async deletes"));
            }

            if (!has204 && !has202 && !has200) {
                issues.add(LintingIssue.warning(getRuleId(), path + " [DELETE]",
                        "DELETE has no success response defined",
                        "Add 204 No Content as success response for DELETE operations"));
            }

            // Check DELETE response has no body for 204
            if (has204) {
                var response204 = deleteOp.getResponses().get("204");
                if (response204.getContent() != null && !response204.getContent().isEmpty()) {
                    issues.add(LintingIssue.warning(getRuleId(), path + " [DELETE]",
                            "DELETE 204 response should not have a response body",
                            "Remove the content/body from the 204 response (API-BP-DELETE-001)"));
                }
            }

            // Check for 404 in DELETE
            if (!deleteOp.getResponses().containsKey("404")) {
                issues.add(LintingIssue.info(getRuleId(), path + " [DELETE]",
                        "DELETE has no 404 response for non-existent resources",
                        "Add 404 Not Found response for cases where the resource does not exist"));
            }
        }

        return issues;
    }
}
