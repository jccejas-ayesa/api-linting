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
public class OWASPStringParametersPatternRule implements LintingRule {

    @Override
    public String getRuleId() {
        return "injection-pattern-on-string-parameters";
    }

    @Override
    public String getDescription() {
        return "Validates that string parameters define a restrictive pattern, enum, or format to reduce injection risk";
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

            checkParameters(openAPI, issues, path, pathItem.getParameters());
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

        checkParameters(openAPI, issues, path + " [" + method + "]", operation.getParameters());
    }

    private void checkParameters(OpenAPI openAPI, List<LintingIssue> issues, String location, List<Parameter> parameters) {
        if (parameters == null) {
            return;
        }

        for (Parameter parameter : parameters) {
            Parameter resolvedParameter = resolveParameter(openAPI, parameter);
            if (resolvedParameter == null) {
                continue;
            }

            Schema<?> schema = resolveSchema(openAPI, resolvedParameter.getSchema());
            if (schema == null || !"string".equals(schema.getType()) || hasRestrictiveConstraint(schema)) {
                continue;
            }

            issues.add(LintingIssue.warning(
                    getRuleId(),
                    location + "/parameters/" + resolvedParameter.getName(),
                    "String parameter '" + resolvedParameter.getName() + "' does not define a restrictive pattern",
                    "Add a whitelist regex pattern, enum, or explicit format to constrain accepted input"
            ));
        }
    }

    private boolean hasRestrictiveConstraint(Schema<?> schema) {
        return schema.getPattern() != null && !schema.getPattern().isBlank()
                || schema.getEnum() != null && !schema.getEnum().isEmpty()
                || schema.getFormat() != null && !schema.getFormat().isBlank();
    }

    private Parameter resolveParameter(OpenAPI openAPI, Parameter parameter) {
        if (parameter == null || parameter.get$ref() == null) {
            return parameter;
        }

        if (openAPI.getComponents() == null || openAPI.getComponents().getParameters() == null) {
            return parameter;
        }

        String ref = parameter.get$ref();
        String parameterName = ref.substring(ref.lastIndexOf('/') + 1);
        return openAPI.getComponents().getParameters().getOrDefault(parameterName, parameter);
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
