package com.ayesa.api.linting.engine.rules;

import com.ayesa.api.linting.engine.LintingRule;
import com.ayesa.api.linting.model.LintingIssue;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.responses.ApiResponse;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class AsyncPayloadUnsupportedSchemaFormatRule implements LintingRule {

    @Override
    public String getRuleId() {
        return "asyncapi-payload-unsupported-schemaFormat";
    }

    @Override
    public String getDescription() {
        return "Validates that x-schema-format extensions use supported schema formats";
    }

    @Override
    public String getRulesetId() {
        return "asyncapi-best-practices";
    }

    @Override
    public List<LintingIssue> apply(OpenAPI openAPI) {
        List<LintingIssue> issues = new ArrayList<>();
        if (!isAsyncApiSpec(openAPI)) {
            return issues;
        }

        if (openAPI.getComponents() != null && openAPI.getComponents().getSchemas() != null) {
            for (Map.Entry<String, Schema> schemaEntry : openAPI.getComponents().getSchemas().entrySet()) {
                checkSchemaFormat(issues, openAPI, "/components/schemas/" + schemaEntry.getKey(), schemaEntry.getValue());
            }
        }

        if (openAPI.getPaths() == null) {
            return issues;
        }

        for (Map.Entry<String, PathItem> pathEntry : openAPI.getPaths().entrySet()) {
            PathItem pathItem = pathEntry.getValue();
            if (pathItem == null) {
                continue;
            }

            for (Map.Entry<PathItem.HttpMethod, Operation> operationEntry : pathItem.readOperationsMap().entrySet()) {
                Operation operation = operationEntry.getValue();
                if (operation == null) {
                    continue;
                }

                String operationLocation = pathEntry.getKey() + " [" + operationEntry.getKey().name() + "]";
                if (operation.getRequestBody() != null && operation.getRequestBody().getContent() != null) {
                    checkContent(issues, openAPI, operationLocation + "/requestBody/content", operation.getRequestBody().getContent());
                }
                if (operation.getResponses() != null) {
                    for (Map.Entry<String, ApiResponse> responseEntry : operation.getResponses().entrySet()) {
                        ApiResponse response = responseEntry.getValue();
                        if (response != null && response.getContent() != null) {
                            checkContent(
                                    issues,
                                    openAPI,
                                    operationLocation + "/responses/" + responseEntry.getKey() + "/content",
                                    response.getContent()
                            );
                        }
                    }
                }
            }
        }

        return issues;
    }

    private void checkContent(List<LintingIssue> issues, OpenAPI openAPI, String location, Content content) {
        for (Map.Entry<String, MediaType> mediaTypeEntry : content.entrySet()) {
            MediaType mediaType = mediaTypeEntry.getValue();
            if (mediaType == null) {
                continue;
            }
            checkExtensions(issues, location + "/" + mediaTypeEntry.getKey(), mediaType.getExtensions());
            checkSchemaFormat(issues, openAPI, location + "/" + mediaTypeEntry.getKey() + "/schema", mediaType.getSchema());
        }
    }

    private void checkSchemaFormat(List<LintingIssue> issues, OpenAPI openAPI, String location, Schema<?> schema) {
        Schema<?> resolvedSchema = resolveSchema(openAPI, schema);
        if (resolvedSchema != null) {
            checkExtensions(issues, location, resolvedSchema.getExtensions());
        }
    }

    private void checkExtensions(List<LintingIssue> issues, String location, Map<String, Object> extensions) {
        if (extensions == null || !extensions.containsKey("x-schema-format")) {
            return;
        }

        Object schemaFormat = extensions.get("x-schema-format");
        String value = schemaFormat == null ? "" : schemaFormat.toString().trim();
        if (!value.isEmpty() && !isSupportedSchemaFormat(value)) {
            issues.add(LintingIssue.warning(
                    getRuleId(),
                    location + "/x-schema-format",
                    "Unsupported schema format: " + value,
                    "Use a supported schema format such as JSON Schema, AsyncAPI, Avro, Protobuf, Thrift, or OpenAPI"
            ));
        }
    }

    private boolean isSupportedSchemaFormat(String value) {
        String normalized = value.toLowerCase();
        return normalized.equals("json-schema")
                || normalized.equals("avro")
                || normalized.equals("protobuf")
                || normalized.equals("openapi")
                || normalized.startsWith("application/schema+json")
                || normalized.startsWith("application/vnd.aai.asyncapi+json")
                || normalized.startsWith("application/vnd.apache.avro")
                || normalized.startsWith("application/vnd.google.protobuf")
                || normalized.startsWith("application/vnd.apache.thrift")
                || normalized.startsWith("application/vnd.oai.openapi");
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
