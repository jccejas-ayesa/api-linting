package com.ayesa.api.linting.engine.rules;

import com.ayesa.api.linting.engine.LintingRule;
import com.ayesa.api.linting.model.LintingIssue;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.headers.Header;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.parameters.RequestBody;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

@Component
public class AnyNoScriptTagsInMarkdownRule implements LintingRule {

    private static final Pattern BLOCKED_PATTERN = Pattern.compile("(?i)<\\s*script\\b");

    @Override
    public String getRuleId() {
        return "no-script-tags-in-markdown";
    }

    @Override
    public String getDescription() {
        return "Descriptions must not contain script tags";
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

        inspectRoot(issues, openAPI);
        if (openAPI.getComponents() != null && openAPI.getComponents().getSchemas() != null) {
            Set<Schema<?>> visited = Collections.newSetFromMap(new IdentityHashMap<>());
            for (Map.Entry<String, Schema> entry : openAPI.getComponents().getSchemas().entrySet()) {
                inspectSchema(issues, openAPI, "/components/schemas/" + entry.getKey(), entry.getValue(), visited);
            }
        }
        return issues;
    }

    private void inspectRoot(List<LintingIssue> issues, OpenAPI openAPI) {
        Info info = openAPI.getInfo();
        if (info != null) {
            inspectDescription(issues, "/info/description", info.getDescription());
        }

        ExternalDocumentation externalDocs = openAPI.getExternalDocs();
        if (externalDocs != null) {
            inspectDescription(issues, "/externalDocs/description", externalDocs.getDescription());
        }

        if (openAPI.getServers() != null) {
            for (int i = 0; i < openAPI.getServers().size(); i++) {
                Server server = openAPI.getServers().get(i);
                inspectDescription(issues, "/servers/" + i + "/description", server == null ? null : server.getDescription());
            }
        }

        if (openAPI.getTags() != null) {
            for (int i = 0; i < openAPI.getTags().size(); i++) {
                Tag tag = openAPI.getTags().get(i);
                inspectDescription(issues, "/tags/" + i + "/description", tag == null ? null : tag.getDescription());
            }
        }

        if (openAPI.getPaths() == null) {
            return;
        }

        for (Map.Entry<String, PathItem> pathEntry : openAPI.getPaths().entrySet()) {
            PathItem pathItem = pathEntry.getValue();
            if (pathItem == null) {
                continue;
            }
            inspectDescription(issues, "/paths/" + pathEntry.getKey() + "/description", pathItem.getDescription());
            if (pathItem.readOperationsMap() == null) {
                continue;
            }
            for (Map.Entry<PathItem.HttpMethod, Operation> operationEntry : pathItem.readOperationsMap().entrySet()) {
                inspectOperation(issues, openAPI, pathEntry.getKey(), operationEntry.getKey(), operationEntry.getValue());
            }
        }
    }

    private void inspectOperation(List<LintingIssue> issues, OpenAPI openAPI, String path, PathItem.HttpMethod method, Operation operation) {
        if (operation == null) {
            return;
        }

        String base = path + " [" + method + "]";
        inspectDescription(issues, base + "/description", operation.getDescription());

        if (operation.getParameters() != null) {
            for (Parameter parameter : operation.getParameters()) {
                Parameter resolved = resolveParameter(openAPI, parameter);
                inspectDescription(issues, base + "/parameters/" + (resolved == null ? "unknown" : resolved.getName()) + "/description",
                        resolved == null ? null : resolved.getDescription());
            }
        }

        RequestBody requestBody = resolveRequestBody(openAPI, operation.getRequestBody());
        if (requestBody != null) {
            inspectDescription(issues, base + "/requestBody/description", requestBody.getDescription());
        }

        if (operation.getResponses() == null) {
            return;
        }

        for (Map.Entry<String, ApiResponse> responseEntry : operation.getResponses().entrySet()) {
            ApiResponse response = resolveResponse(openAPI, responseEntry.getValue());
            if (response == null) {
                continue;
            }
            inspectDescription(issues, base + "/responses/" + responseEntry.getKey() + "/description", response.getDescription());
            if (response.getHeaders() != null) {
                for (Map.Entry<String, Header> headerEntry : response.getHeaders().entrySet()) {
                    Header header = resolveHeader(openAPI, headerEntry.getValue());
                    inspectDescription(issues, base + "/responses/" + responseEntry.getKey() + "/headers/" + headerEntry.getKey() + "/description",
                            header == null ? null : header.getDescription());
                }
            }
        }
    }

    private void inspectSchema(List<LintingIssue> issues, OpenAPI openAPI, String location, Schema<?> schema, Set<Schema<?>> visited) {
        Schema<?> resolved = resolveSchema(openAPI, schema);
        if (resolved == null || !visited.add(resolved)) {
            return;
        }

        inspectDescription(issues, location + "/description", resolved.getDescription());
        for (Map.Entry<String, Schema> propertyEntry : propertiesOf(resolved).entrySet()) {
            inspectSchema(issues, openAPI, location + "/properties/" + propertyEntry.getKey(), propertyEntry.getValue(), visited);
        }
        inspectSchema(issues, openAPI, location + "/items", resolved.getItems(), visited);
        inspectSchemaList(issues, openAPI, location + "/oneOf", resolved.getOneOf(), visited);
        inspectSchemaList(issues, openAPI, location + "/anyOf", resolved.getAnyOf(), visited);
        inspectSchemaList(issues, openAPI, location + "/allOf", resolved.getAllOf(), visited);
        if (resolved.getAdditionalProperties() instanceof Schema<?> additionalSchema) {
            inspectSchema(issues, openAPI, location + "/additionalProperties", additionalSchema, visited);
        }
    }

    private void inspectSchemaList(List<LintingIssue> issues, OpenAPI openAPI, String location, List<Schema> schemas, Set<Schema<?>> visited) {
        if (schemas == null) {
            return;
        }
        for (int i = 0; i < schemas.size(); i++) {
            inspectSchema(issues, openAPI, location + "/" + i, schemas.get(i), visited);
        }
    }

    private void inspectDescription(List<LintingIssue> issues, String location, String description) {
        if (description != null && BLOCKED_PATTERN.matcher(description).find()) {
            issues.add(LintingIssue.error(
                    getRuleId(),
                    location,
                    "Description contains a script tag",
                    "Remove script tags from Markdown and keep documentation as plain text"
            ));
        }
    }

    private Parameter resolveParameter(OpenAPI openAPI, Parameter parameter) {
        if (parameter == null || parameter.get$ref() == null || openAPI.getComponents() == null || openAPI.getComponents().getParameters() == null) {
            return parameter;
        }
        String ref = parameter.get$ref();
        return openAPI.getComponents().getParameters().getOrDefault(ref.substring(ref.lastIndexOf('/') + 1), parameter);
    }

    private RequestBody resolveRequestBody(OpenAPI openAPI, RequestBody requestBody) {
        if (requestBody == null || requestBody.get$ref() == null || openAPI.getComponents() == null || openAPI.getComponents().getRequestBodies() == null) {
            return requestBody;
        }
        String ref = requestBody.get$ref();
        return openAPI.getComponents().getRequestBodies().getOrDefault(ref.substring(ref.lastIndexOf('/') + 1), requestBody);
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

    @SuppressWarnings("unchecked")
    private Map<String, Schema> propertiesOf(Schema<?> schema) {
        return schema.getProperties() == null ? Collections.emptyMap() : (Map<String, Schema>) schema.getProperties();
    }
}
