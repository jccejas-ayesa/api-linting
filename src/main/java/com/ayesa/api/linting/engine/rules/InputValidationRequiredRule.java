package com.ayesa.api.linting.engine.rules;

import com.ayesa.api.linting.engine.LintingRule;
import com.ayesa.api.linting.model.LintingIssue;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.parameters.RequestBody;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Validates that request schemas define input validation constraints.
 * Covers directive 4.3: Validación obligatoria de entrada.
 */
@Component
public class InputValidationRequiredRule implements LintingRule {

    @Override
    public String getRuleId() {
        return "input-validation-required";
    }

    @Override
    public String getDescription() {
        return "Validates that request parameters and body schemas define validation constraints (required, pattern, min/max, enum)";
    }

    @Override
    public List<LintingIssue> apply(OpenAPI openAPI) {
        List<LintingIssue> issues = new ArrayList<>();

        if (openAPI.getPaths() == null) {
            return issues;
        }

        for (Map.Entry<String, PathItem> pathEntry : openAPI.getPaths().entrySet()) {
            String path = pathEntry.getKey();
            PathItem pathItem = pathEntry.getValue();

            checkOperation(issues, openAPI, path, "POST", pathItem.getPost());
            checkOperation(issues, openAPI, path, "PUT", pathItem.getPut());
            checkOperation(issues, openAPI, path, "PATCH", pathItem.getPatch());
        }

        return issues;
    }

    private void checkOperation(List<LintingIssue> issues, OpenAPI openAPI, String path, String method, Operation operation) {
        if (operation == null) {
            return;
        }

        String location = path + " [" + method + "]";

        // Check request body
        RequestBody requestBody = operation.getRequestBody();
        if (requestBody != null && requestBody.getContent() != null) {
            for (Map.Entry<String, MediaType> contentEntry : requestBody.getContent().entrySet()) {
                Schema<?> schema = contentEntry.getValue().getSchema();
                if (schema != null) {
                    schema = resolveSchema(openAPI, schema);
                    checkSchemaValidation(issues, location + "/requestBody", schema, openAPI);
                }
            }
        }

        // Check parameters
        if (operation.getParameters() != null) {
            for (Parameter param : operation.getParameters()) {
                if (param.getSchema() != null && !hasValidationConstraints(param.getSchema())) {
                    issues.add(LintingIssue.info(getRuleId(), location + "/parameters/" + param.getName(),
                            "Parameter '" + param.getName() + "' has no validation constraints",
                            "Add constraints like pattern, minLength, maxLength, minimum, maximum, or enum"));
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    private void checkSchemaValidation(List<LintingIssue> issues, String location, Schema<?> schema, OpenAPI openAPI) {
        if (schema == null || schema.getProperties() == null) {
            return;
        }

        if (schema.getRequired() == null || schema.getRequired().isEmpty()) {
            issues.add(LintingIssue.warning(getRuleId(), location,
                    "Request body schema has no required properties defined",
                    "Define which properties are required using the 'required' field"));
        }

        for (Map.Entry<String, Schema> propEntry : ((Map<String, Schema>) schema.getProperties()).entrySet()) {
            String propName = propEntry.getKey();
            Schema<?> propSchema = resolveSchema(openAPI, propEntry.getValue());

            if ("string".equals(propSchema.getType()) && !hasValidationConstraints(propSchema)) {
                issues.add(LintingIssue.info(getRuleId(), location + "/properties/" + propName,
                        "String property '" + propName + "' has no validation constraints",
                        "Add pattern, minLength, maxLength, or enum to restrict input"));
            }
        }
    }

    private boolean hasValidationConstraints(Schema<?> schema) {
        return schema.getPattern() != null
                || schema.getMinLength() != null
                || schema.getMaxLength() != null
                || schema.getMinimum() != null
                || schema.getMaximum() != null
                || schema.getEnum() != null
                || schema.getFormat() != null;
    }

    private Schema<?> resolveSchema(OpenAPI openAPI, Schema<?> schema) {
        if (schema.get$ref() != null && openAPI.getComponents() != null
                && openAPI.getComponents().getSchemas() != null) {
            String ref = schema.get$ref();
            String schemaName = ref.substring(ref.lastIndexOf('/') + 1);
            Schema<?> resolved = openAPI.getComponents().getSchemas().get(schemaName);
            return resolved != null ? resolved : schema;
        }
        return schema;
    }
}
