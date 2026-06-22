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
import java.util.regex.Pattern;

@Component
public class OWASPObjectLevelAuthzRule implements LintingRule {

    private static final Pattern ID_PARAMETER_NAME = Pattern.compile("(^id$|.*[-_]?id$)", Pattern.CASE_INSENSITIVE);
    private static final Pattern UUID_PATTERN_HINT = Pattern.compile("(?i)(uuid|guid|\\{8\\}.*\\{4\\}.*\\{4\\}.*\\{4\\}.*\\{12\\})");

    @Override
    public String getRuleId() {
        return "broken-object-level-authorization-use-guids";
    }

    @Override
    public String getDescription() {
        return "Validates that path object identifiers use UUID/GUID-style schemas instead of predictable IDs";
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
            if (resolvedParameter == null
                    || !"path".equals(resolvedParameter.getIn())
                    || resolvedParameter.getName() == null
                    || !ID_PARAMETER_NAME.matcher(resolvedParameter.getName()).matches()) {
                continue;
            }

            Schema<?> schema = resolveSchema(openAPI, resolvedParameter.getSchema());
            if (usesGuidLikeIdentifier(schema)) {
                continue;
            }

            issues.add(LintingIssue.warning(
                    getRuleId(),
                    location + "/parameters/" + resolvedParameter.getName(),
                    "Path identifier '" + resolvedParameter.getName() + "' is not modeled as a UUID/GUID",
                    "Use a string schema with format: uuid or an equivalent UUID regex pattern for externally visible object IDs"
            ));
        }
    }

    private boolean usesGuidLikeIdentifier(Schema<?> schema) {
        if (schema == null || schema.getType() == null) {
            return false;
        }

        if (!"string".equals(schema.getType())) {
            return false;
        }

        if ("uuid".equalsIgnoreCase(schema.getFormat())) {
            return true;
        }

        return schema.getPattern() != null && UUID_PATTERN_HINT.matcher(schema.getPattern()).find();
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
