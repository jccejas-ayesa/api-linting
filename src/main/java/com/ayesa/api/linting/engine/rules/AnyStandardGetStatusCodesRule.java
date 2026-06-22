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
public class AnyStandardGetStatusCodesRule implements LintingRule {

    @Override
    public String getRuleId() {
        return "standard-get-status-codes";
    }

    @Override
    public String getDescription() {
        return "GET operations should use standard 200/304/404/400 response codes";
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
            Operation operation = pathItem == null ? null : pathItem.getGet();
            if (operation == null || operation.getResponses() == null) {
                continue;
            }

            boolean hasAnyRecommended = operation.getResponses().containsKey("200")
                    || operation.getResponses().containsKey("304")
                    || operation.getResponses().containsKey("404")
                    || operation.getResponses().containsKey("400");
            boolean hasUnsupported2xx = operation.getResponses().keySet().stream().anyMatch(code -> code != null && code.startsWith("2") && !"200".equals(code));
            if (!hasAnyRecommended || hasUnsupported2xx) {
                issues.add(LintingIssue.warning(
                        getRuleId(),
                        pathEntry.getKey() + " [GET]",
                        "GET operation does not use standard status codes",
                        "Use 200 for successful GET responses and add 304, 404, or 400 when applicable"
                ));
            }
        }

        return issues;
    }
}
