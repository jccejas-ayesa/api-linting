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
import java.util.Set;

/**
 * Validates POST operations return 201 (Created) and not 200 for creation.
 * Exception: POST /search endpoints may return 200.
 * Covers API-BP-POST-004.
 */
@Component
public class PostResponseStandardRule implements LintingRule {

    @Override
    public String getRuleId() {
        return "post-response-standard";
    }

    @Override
    public String getDescription() {
        return "Validates that POST creation endpoints return 201 Created (not 200). Exception: POST /search returns 200 (API-BP-POST-004)";
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

            if (pathItem.getPost() == null) {
                continue;
            }

            // Skip search endpoints (POST /xxx/search returns 200)
            if (path.endsWith("/search")) {
                Operation searchOp = pathItem.getPost();
                if (searchOp.getResponses() != null && !searchOp.getResponses().containsKey("200")) {
                    issues.add(LintingIssue.info(getRuleId(), path + " [POST]",
                            "Search endpoint should return 200 OK",
                            "POST search endpoints should use 200, not 201"));
                }
                continue;
            }

            Operation postOp = pathItem.getPost();
            if (postOp.getResponses() == null) {
                continue;
            }

            boolean has201 = postOp.getResponses().containsKey("201");
            boolean has200Only = postOp.getResponses().containsKey("200") && !has201;

            if (has200Only) {
                issues.add(LintingIssue.warning(getRuleId(), path + " [POST]",
                        "POST creation endpoint returns 200 instead of 201 Created",
                        "Use 201 Created for resource creation operations. 200 is only for POST /search"));
            }

            if (!has201 && !postOp.getResponses().containsKey("200") && !postOp.getResponses().containsKey("202")) {
                issues.add(LintingIssue.warning(getRuleId(), path + " [POST]",
                        "POST endpoint has no success response (201, 200, or 202)",
                        "Add a 201 Created response for creation operations"));
            }
        }

        return issues;
    }
}
