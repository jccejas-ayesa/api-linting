package com.ayesa.api.linting.engine.rules;

import com.ayesa.api.linting.engine.LintingRule;
import com.ayesa.api.linting.model.LintingIssue;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.headers.Header;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.responses.ApiResponse;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class AnyMediaTypeHeadersResponseRule implements LintingRule {

    @Override
    public String getRuleId() {
        return "media-type-headers-response";
    }

    @Override
    public String getDescription() {
        return "Response header object schemas should declare properties";
    }

    @Override
    public String getRulesetId() {
        return "anypoint-best-practices";
    }

    @Override
    public List<LintingIssue> apply(OpenAPI openAPI) {
        List<LintingIssue> issues = new ArrayList<>();
        if (openAPI == null) {
            return issues;
        }

        if (openAPI.getPaths() != null) {
            for (Map.Entry<String, PathItem> pathEntry : openAPI.getPaths().entrySet()) {
                PathItem pathItem = pathEntry.getValue();
                if (pathItem == null || pathItem.readOperationsMap() == null) {
                    continue;
                }
                for (Map.Entry<PathItem.HttpMethod, Operation> operationEntry : pathItem.readOperationsMap().entrySet()) {
                    inspectResponses(openAPI, issues, pathEntry.getKey() + " [" + operationEntry.getKey() + "]", operationEntry.getValue());
                }
            }
        }

        if (openAPI.getComponents() != null && openAPI.getComponents().getResponses() != null) {
            for (Map.Entry<String, ApiResponse> entry : openAPI.getComponents().getResponses().entrySet()) {
                inspectResponse(openAPI, issues, "/components/responses/" + entry.getKey(), entry.getValue());
            }
        }

        return issues;
    }

    private void inspectResponses(OpenAPI openAPI, List<LintingIssue> issues, String location, Operation operation) {
        if (operation == null || operation.getResponses() == null) {
            return;
        }
        for (Map.Entry<String, ApiResponse> responseEntry : operation.getResponses().entrySet()) {
            inspectResponse(openAPI, issues, location + "/responses/" + responseEntry.getKey(), responseEntry.getValue());
        }
    }

    private void inspectResponse(OpenAPI openAPI, List<LintingIssue> issues, String location, ApiResponse response) {
        ApiResponse resolvedResponse = resolveResponse(openAPI, response);
        if (resolvedResponse == null || resolvedResponse.getHeaders() == null) {
            return;
        }

        for (Map.Entry<String, Header> headerEntry : resolvedResponse.getHeaders().entrySet()) {
            Header header = resolveHeader(openAPI, headerEntry.getValue());
            if (header == null) {
                continue;
            }

            Schema<?> schema = resolveSchema(openAPI, header.getSchema());
            if (isEmptyObjectSchema(schema)) {
                issues.add(LintingIssue.warning(
                        getRuleId(),
                        location + "/headers/" + headerEntry.getKey(),
                        "Header object schema does not define any properties",
                        "Define the object header properties or use a scalar header schema"
                ));
            }

            if (header.getContent() != null) {
                for (Map.Entry<String, MediaType> mediaTypeEntry : header.getContent().entrySet()) {
                    Schema<?> contentSchema = resolveSchema(openAPI, mediaTypeEntry.getValue() == null ? null : mediaTypeEntry.getValue().getSchema());
                    if (isEmptyObjectSchema(contentSchema)) {
                        issues.add(LintingIssue.warning(
                                getRuleId(),
                                location + "/headers/" + headerEntry.getKey() + "/content/" + mediaTypeEntry.getKey(),
                                "Header content object schema does not define any properties",
                                "Define properties for the header object schema"
                        ));
                    }
                }
            }
        }
    }

    private boolean isEmptyObjectSchema(Schema<?> schema) {
        return schema != null
                && "object".equals(schema.getType())
                && (schema.getProperties() == null || schema.getProperties().isEmpty())
                && schema.getAdditionalProperties() == null;
    }

    private ApiResponse resolveResponse(OpenAPI openAPI, ApiResponse response) {
        if (response == null || response.get$ref() == null || openAPI.getComponents() == null || openAPI.getComponents().getResponses() == null) {
            return response;
        }
        String ref = response.get$ref();
        return openAPI.getComponents().getResponses().getOrDefault(ref.substring(ref.lastIndexOf('/') + 1), response);
    }

    private Header resolveHeader(OpenAPI openAPI, Header header) {
        if (header == null || header.get$ref() == null || openAPI.getComponents() == null || openAPI.getComponents().getHeaders() == null) {
            return header;
        }
        String ref = header.get$ref();
        return openAPI.getComponents().getHeaders().getOrDefault(ref.substring(ref.lastIndexOf('/') + 1), header);
    }

    private Schema<?> resolveSchema(OpenAPI openAPI, Schema<?> schema) {
        if (schema == null || schema.get$ref() == null || openAPI.getComponents() == null || openAPI.getComponents().getSchemas() == null) {
            return schema;
        }
        String ref = schema.get$ref();
        return openAPI.getComponents().getSchemas().getOrDefault(ref.substring(ref.lastIndexOf('/') + 1), schema);
    }
}
