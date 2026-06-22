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
public class AnyNo2xxResponseRule implements LintingRule {

    @Override
    public String getRuleId() {
        return "no-2xx-response";
    }

    @Override
    public String getDescription() {
        return "Operations must define at least one 2xx response";
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
            if (pathItem == null || pathItem.readOperationsMap() == null) {
                continue;
            }

            for (Map.Entry<PathItem.HttpMethod, Operation> operationEntry : pathItem.readOperationsMap().entrySet()) {
                Operation operation = operationEntry.getValue();
                boolean has2xx = operation != null
                        && operation.getResponses() != null
                        && operation.getResponses().keySet().stream().anyMatch(code -> code != null && code.startsWith("2"));
                if (!has2xx) {
                    issues.add(LintingIssue.error(
                            getRuleId(),
                            pathEntry.getKey() + " [" + operationEntry.getKey() + "]",
                            "Operation is missing a 2xx response",
                            "Define at least one success response in the 2xx range"
                    ));
                }
            }
        }

        return issues;
    }
}
