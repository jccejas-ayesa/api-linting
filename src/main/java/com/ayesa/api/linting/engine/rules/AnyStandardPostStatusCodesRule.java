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
public class AnyStandardPostStatusCodesRule implements LintingRule {

    @Override
    public String getRuleId() {
        return "standard-post-status-codes";
    }

    @Override
    public String getDescription() {
        return "POST operations should return 200 or 201";
    }

    @Override
    public String getRulesetId() {
        return "anypoint-best-practices";
    }

    @Override
    public List<LintingIssue> apply(OpenAPI openAPI) {
        List<LintingIssue> issues = new ArrayList<>();
        if (openAPI == null || openAPI.getPaths() == null) {
            return issues;
        }

        for (Map.Entry<String, PathItem> pathEntry : openAPI.getPaths().entrySet()) {
            PathItem pathItem = pathEntry.getValue();
            Operation operation = pathItem == null ? null : pathItem.getPost();
            if (operation == null || operation.getResponses() == null) {
                continue;
            }

            boolean hasAllowed = operation.getResponses().containsKey("200") || operation.getResponses().containsKey("201");
            boolean hasOther2xx = operation.getResponses().keySet().stream().anyMatch(code -> code != null && code.startsWith("2") && !"200".equals(code) && !"201".equals(code));
            if (!hasAllowed || hasOther2xx) {
                issues.add(LintingIssue.warning(
                        getRuleId(),
                        pathEntry.getKey() + " [POST]",
                        "POST operation uses non-standard success status codes",
                        "Use 201 for create operations or 200 when POST is not creating a new resource"
                ));
            }
        }

        return issues;
    }
}
