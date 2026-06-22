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

/**
 * Validates that path and query parameters use scalar schemas.
 */
@Component
public class NonScalarParametersRule implements LintingRule {

    @Override
    public String getRuleId() {
        return "non-scalar-parameters";
    }

    @Override
    public String getDescription() {
        return "Validates that path and query parameters use scalar types or arrays of scalar types";
    }

    @Override
    public String getRulesetId() {
        return "datagraph-best-practices";
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

            checkParameters(openAPI, issues, path, "PATH", pathItem.getParameters());
            checkOperation(openAPI, issues, path, "GET", pathItem.getGet());
            checkOperation(openAPI, issues, path, "POST", pathItem.getPost());
            checkOperation(openAPI, issues, path, "PUT", pathItem.getPut());
            checkOperation(openAPI, issues, path, "PATCH", pathItem.getPatch());
            checkOperation(openAPI, issues, path, "DELETE", pathItem.getDelete());
        }

        return issues;
    }

    private void checkOperation(OpenAPI openAPI, List<LintingIssue> issues, String path, String method, Operation operation) {
        if (operation == null) {
            return;
        }

        checkParameters(openAPI, issues, path + " [" + method + "]", method, operation.getParameters());
    }

    private void checkParameters(OpenAPI openAPI, List<LintingIssue> issues, String location, String scope, List<Parameter> parameters) {
        if (parameters == null) {
            return;
        }

        for (Parameter parameter : parameters) {
            if (parameter == null || parameter.getSchema() == null) {
                continue;
            }

            String in = parameter.getIn();
            if (!"path".equals(in) && !"query".equals(in)) {
                continue;
            }

            if (isAllowedParameterSchema(openAPI, parameter.getSchema())) {
                continue;
            }

            issues.add(LintingIssue.warning(
                    getRuleId(),
                    location + "/parameters/" + parameter.getName(),
                    scope + " parameter '" + parameter.getName() + "' does not use a scalar schema",
                    "Use a scalar type or an array of scalar types for path and query parameters"
            ));
        }
    }

    private boolean isAllowedParameterSchema(OpenAPI openAPI, Schema<?> schema) {
        Schema<?> resolved = resolveSchema(openAPI, schema);
        if (resolved == null) {
            return true;
        }

        if (isScalarType(resolved.getType())) {
            return true;
        }

        if (!"array".equals(resolved.getType())) {
            return resolved.getProperties() == null && resolved.getAdditionalProperties() == null;
        }

        Schema<?> itemSchema = resolveSchema(openAPI, resolved.getItems());
        return itemSchema != null && isScalarType(itemSchema.getType());
    }

    private boolean isScalarType(String type) {
        return "string".equals(type) || "integer".equals(type) || "number".equals(type) || "boolean".equals(type);
    }

    private Schema<?> resolveSchema(OpenAPI openAPI, Schema<?> schema) {
        if (schema == null || schema.get$ref() == null) {
            return schema;
        }

        if (openAPI.getComponents() == null || openAPI.getComponents().getSchemas() == null) {
            return schema;
        }

        String ref = schema.get$ref();
        String schemaName = ref.substring(ref.lastIndexOf('/') + 1);
        return openAPI.getComponents().getSchemas().getOrDefault(schemaName, schema);
    }
}
