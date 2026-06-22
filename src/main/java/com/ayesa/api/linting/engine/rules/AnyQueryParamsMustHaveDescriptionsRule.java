package com.ayesa.api.linting.engine.rules;

import com.ayesa.api.linting.engine.LintingRule;
import com.ayesa.api.linting.model.LintingIssue;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.parameters.Parameter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class AnyQueryParamsMustHaveDescriptionsRule implements LintingRule {

    @Override
    public String getRuleId() {
        return "query-params-must-have-descriptions";
    }

    @Override
    public String getDescription() {
        return "Query parameters must define descriptions";
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
            if (pathItem == null) {
                continue;
            }
            checkParameters(openAPI, issues, pathEntry.getKey(), pathItem.getParameters());
            if (pathItem.readOperationsMap() == null) {
                continue;
            }
            for (Map.Entry<PathItem.HttpMethod, Operation> operationEntry : pathItem.readOperationsMap().entrySet()) {
                Operation operation = operationEntry.getValue();
                checkParameters(openAPI, issues, pathEntry.getKey() + " [" + operationEntry.getKey() + "]", operation == null ? null : operation.getParameters());
            }
        }

        return issues;
    }

    private void checkParameters(OpenAPI openAPI, List<LintingIssue> issues, String location, List<Parameter> parameters) {
        if (parameters == null) {
            return;
        }
        for (Parameter parameter : parameters) {
            Parameter resolved = resolveParameter(openAPI, parameter);
            if (resolved == null || !"query".equals(resolved.getIn())) {
                continue;
            }
            if (resolved.getDescription() == null || resolved.getDescription().isBlank()) {
                issues.add(LintingIssue.warning(
                        getRuleId(),
                        location + "/parameters/" + resolved.getName(),
                        "Query parameter '" + resolved.getName() + "' is missing a description",
                        "Add a description for the query parameter"
                ));
            }
        }
    }

    private Parameter resolveParameter(OpenAPI openAPI, Parameter parameter) {
        if (parameter == null || parameter.get$ref() == null || openAPI.getComponents() == null || openAPI.getComponents().getParameters() == null) {
            return parameter;
        }
        String ref = parameter.get$ref();
        return openAPI.getComponents().getParameters().getOrDefault(ref.substring(ref.lastIndexOf('/') + 1), parameter);
    }
}
