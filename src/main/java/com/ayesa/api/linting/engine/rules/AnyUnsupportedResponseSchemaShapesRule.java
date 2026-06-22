package com.ayesa.api.linting.engine.rules;

import com.ayesa.api.linting.engine.LintingRule;
import com.ayesa.api.linting.model.LintingIssue;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.responses.ApiResponse;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class AnyUnsupportedResponseSchemaShapesRule implements LintingRule {

    @Override
    public String getRuleId() {
        return "unsupported-response-schema-shapes";
    }

    @Override
    public String getDescription() {
        return "Response schemas should avoid file types and tuple-like arrays";
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
            if (pathItem == null || pathItem.readOperationsMap() == null) {
                continue;
            }
            for (Map.Entry<PathItem.HttpMethod, Operation> operationEntry : pathItem.readOperationsMap().entrySet()) {
                Operation operation = operationEntry.getValue();
                if (operation == null || operation.getResponses() == null) {
                    continue;
                }
                for (Map.Entry<String, ApiResponse> responseEntry : operation.getResponses().entrySet()) {
                    ApiResponse response = responseEntry.getValue();
                    if (response == null || response.getContent() == null) {
                        continue;
                    }
                    response.getContent().forEach((mediaType, value) -> inspectSchema(
                            issues,
                            pathEntry.getKey() + " [" + operationEntry.getKey() + "]" + "/responses/" + responseEntry.getKey() + "/content/" + mediaType + "/schema",
                            value == null ? null : value.getSchema(),
                            Collections.newSetFromMap(new IdentityHashMap<>())
                    ));
                }
            }
        }

        return issues;
    }

    private void inspectSchema(List<LintingIssue> issues, String location, Schema<?> schema, Set<Schema<?>> visited) {
        if (schema == null || !visited.add(schema)) {
            return;
        }

        if ("file".equals(schema.getType())) {
            issues.add(LintingIssue.warning(
                    getRuleId(),
                    location,
                    "Response schema uses unsupported type 'file'",
                    "Model file responses with a supported schema representation"
            ));
        }

        if ("array".equals(schema.getType()) && (hasPrefixItems(schema) || hasComplexItems(schema.getItems()))) {
            issues.add(LintingIssue.warning(
                    getRuleId(),
                    location,
                    "Response schema uses an unsupported tuple-like array shape",
                    "Use homogeneous array items instead of tuple-like array definitions"
            ));
        }

        for (Map.Entry<String, Schema> propertyEntry : propertiesOf(schema).entrySet()) {
            inspectSchema(issues, location + "/properties/" + propertyEntry.getKey(), propertyEntry.getValue(), visited);
        }
        inspectSchema(issues, location + "/items", schema.getItems(), visited);
        inspectSchemaList(issues, location + "/oneOf", schema.getOneOf(), visited);
        inspectSchemaList(issues, location + "/anyOf", schema.getAnyOf(), visited);
        inspectSchemaList(issues, location + "/allOf", schema.getAllOf(), visited);
        if (schema.getAdditionalProperties() instanceof Schema<?> additionalSchema) {
            inspectSchema(issues, location + "/additionalProperties", additionalSchema, visited);
        }
    }

    private boolean hasComplexItems(Schema<?> items) {
        return items != null && (hasPrefixItems(items)
                || (items.getOneOf() != null && !items.getOneOf().isEmpty())
                || (items.getAnyOf() != null && !items.getAnyOf().isEmpty())
                || (items.getAllOf() != null && !items.getAllOf().isEmpty()));
    }

    private boolean hasPrefixItems(Schema<?> schema) {
        try {
            Method method = schema.getClass().getMethod("getPrefixItems");
            Object prefixItems = method.invoke(schema);
            return prefixItems instanceof List<?> list && !list.isEmpty();
        } catch (ReflectiveOperationException ignored) {
            return false;
        }
    }

    private void inspectSchemaList(List<LintingIssue> issues, String location, List<Schema> schemas, Set<Schema<?>> visited) {
        if (schemas == null) {
            return;
        }
        for (int i = 0; i < schemas.size(); i++) {
            inspectSchema(issues, location + "/" + i, schemas.get(i), visited);
        }
    }

    @SuppressWarnings("unchecked")
    private Map<String, Schema> propertiesOf(Schema<?> schema) {
        return schema.getProperties() == null ? Collections.emptyMap() : (Map<String, Schema>) schema.getProperties();
    }
}
