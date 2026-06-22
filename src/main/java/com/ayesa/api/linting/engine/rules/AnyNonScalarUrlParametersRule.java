package com.ayesa.api.linting.engine.rules;

import com.ayesa.api.linting.engine.LintingRule;
import com.ayesa.api.linting.model.LintingIssue;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.parameters.Parameter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class AnyNonScalarUrlParametersRule implements LintingRule {

    @Override
    public String getRuleId() {
        return "non-scalar-url-parameters";
    }

    @Override
    public String getDescription() {
        return "Path and query parameters must use scalar schemas";
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
            if (resolved == null || resolved.getSchema() == null) {
                continue;
            }
            if (!"path".equals(resolved.getIn()) && !"query".equals(resolved.getIn())) {
                continue;
            }

            Schema<?> schema = resolveSchema(openAPI, resolved.getSchema());
            if (!isScalarType(schema == null ? null : schema.getType())) {
                issues.add(LintingIssue.warning(
                        getRuleId(),
                        location + "/parameters/" + resolved.getName(),
                        "URL parameter '" + resolved.getName() + "' is not scalar",
                        "Use string, integer, number, or boolean schemas for path and query parameters"
                ));
            }
        }
    }

    private boolean isScalarType(String type) {
        return "string".equals(type) || "integer".equals(type) || "number".equals(type) || "boolean".equals(type);
    }

    private Parameter resolveParameter(OpenAPI openAPI, Parameter parameter) {
        if (parameter == null || parameter.get$ref() == null || openAPI.getComponents() == null || openAPI.getComponents().getParameters() == null) {
            return parameter;
        }
        String ref = parameter.get$ref();
        return openAPI.getComponents().getParameters().getOrDefault(ref.substring(ref.lastIndexOf('/') + 1), parameter);
    }

    private Schema<?> resolveSchema(OpenAPI openAPI, Schema<?> schema) {
        if (schema == null || schema.get$ref() == null || openAPI.getComponents() == null || openAPI.getComponents().getSchemas() == null) {
            return schema;
        }
        String ref = schema.get$ref();
        return openAPI.getComponents().getSchemas().getOrDefault(ref.substring(ref.lastIndexOf('/') + 1), schema);
    }
}
