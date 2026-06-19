package com.ayesa.api.linting.engine.rules;

import com.ayesa.api.linting.engine.LintingRule;
import com.ayesa.api.linting.model.LintingIssue;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.headers.Header;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.responses.ApiResponse;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class AsyncHeadersSchemaTypeObjectRule implements LintingRule {

    @Override
    public String getRuleId() {
        return "asyncapi-headers-schema-type-object";
    }

    @Override
    public String getDescription() {
        return "Validates that response header schemas use object types with properties";
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

            for (Map.Entry<PathItem.HttpMethod, Operation> operationEntry : pathItem.readOperationsMap().entrySet()) {
                Operation operation = operationEntry.getValue();
                if (operation == null || operation.getResponses() == null) {
                    continue;
                }

                String operationLocation = pathEntry.getKey() + " [" + operationEntry.getKey().name() + "]";
                for (Map.Entry<String, ApiResponse> responseEntry : operation.getResponses().entrySet()) {
                    ApiResponse response = responseEntry.getValue();
                    if (response == null || response.getHeaders() == null) {
                        continue;
                    }

                    for (Map.Entry<String, Header> headerEntry : response.getHeaders().entrySet()) {
                        Header header = headerEntry.getValue();
                        Schema<?> schema = resolveSchema(openAPI, header == null ? null : header.getSchema());
                        boolean invalid = schema == null
                                || !"object".equals(schema.getType())
                                || schema.getProperties() == null
                                || ((Map<?, ?>) schema.getProperties()).isEmpty();
                        if (invalid) {
                            issues.add(LintingIssue.warning(
                                    getRuleId(),
                                    operationLocation + "/responses/" + responseEntry.getKey() + "/headers/" + headerEntry.getKey(),
                                    "Response header schema should be an object with properties",
                                    "Define the header schema as type 'object' and add at least one property"
                            ));
                        }
                    }
                }
            }
        }

        return issues;
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

    private Schema<?> resolveSchema(OpenAPI openAPI, Schema<?> schema) {
        if (schema == null) {
            return null;
        }
        if (schema.get$ref() != null && openAPI.getComponents() != null
                && openAPI.getComponents().getSchemas() != null) {
            String ref = schema.get$ref();
            String schemaName = ref.substring(ref.lastIndexOf('/') + 1);
            Schema<?> resolved = openAPI.getComponents().getSchemas().get(schemaName);
            if (resolved != null) {
                return resolved;
            }
        }
        return schema;
    }
}
