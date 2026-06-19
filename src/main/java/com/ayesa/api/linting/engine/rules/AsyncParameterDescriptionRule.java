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
public class AsyncParameterDescriptionRule implements LintingRule {

    @Override
    public String getRuleId() {
        return "asyncapi-parameter-description";
    }

    @Override
    public String getDescription() {
        return "Validates that AsyncAPI parameters include descriptions";
    }

    @Override
    public String getRulesetId() {
        return "asyncapi-best-practices";
    }

    @Override
    public List<LintingIssue> apply(OpenAPI openAPI) {
        List<LintingIssue> issues = new ArrayList<>();
        if (!isAsyncApiSpec(openAPI) || openAPI.getPaths() == null) {
            return issues;
        }

        for (Map.Entry<String, PathItem> pathEntry : openAPI.getPaths().entrySet()) {
            PathItem pathItem = pathEntry.getValue();
            if (pathItem == null) {
                continue;
            }

            checkParameters(issues, openAPI, pathEntry.getKey() + "/parameters", pathItem.getParameters());
            for (Map.Entry<PathItem.HttpMethod, Operation> operationEntry : pathItem.readOperationsMap().entrySet()) {
                Operation operation = operationEntry.getValue();
                if (operation != null) {
                    checkParameters(
                            issues,
                            openAPI,
                            pathEntry.getKey() + " [" + operationEntry.getKey().name() + "]/parameters",
                            operation.getParameters()
                    );
                }
            }
        }

        return issues;
    }

    private void checkParameters(List<LintingIssue> issues, OpenAPI openAPI, String location, List<Parameter> parameters) {
        if (parameters == null) {
            return;
        }

        for (Parameter parameter : parameters) {
            Parameter resolved = resolveParameter(openAPI, parameter);
            if (resolved == null || resolved.getName() == null || resolved.getName().isBlank()) {
                continue;
            }
            if (resolved.getDescription() == null || resolved.getDescription().isBlank()) {
                issues.add(LintingIssue.warning(
                        getRuleId(),
                        location + "/" + resolved.getName(),
                        "Parameter '" + resolved.getName() + "' is missing a description",
                        "Add a non-blank description to the parameter"
                ));
            }
        }
    }

    private Parameter resolveParameter(OpenAPI openAPI, Parameter parameter) {
        if (parameter == null) {
            return null;
        }
        if (parameter.get$ref() != null && openAPI.getComponents() != null
                && openAPI.getComponents().getParameters() != null) {
            String ref = parameter.get$ref();
            String parameterName = ref.substring(ref.lastIndexOf('/') + 1);
            Parameter resolved = openAPI.getComponents().getParameters().get(parameterName);
            if (resolved != null) {
                return resolved;
            }
        }
        return parameter;
    }

    private boolean isAsyncApiSpec(OpenAPI openAPI) {
        if (openAPI.getExtensions() != null && openAPI.getExtensions().containsKey("x-asyncapi")) return true;
        if (openAPI.getInfo() != null) {
            String title = openAPI.getInfo().getTitle();
            String desc = openAPI.getInfo().getDescription();
            String combined = ((title != null ? title : "") + " " + (desc != null ? desc : "")).toLowerCase();
            return combined.contains("asyncapi") || combined.contains("event-driven")
                || combined.contains("kafka") || combined.contains("rabbitmq")
                || combined.contains("amqp") || combined.contains("mqtt");
        }
        return false;
    }
}
