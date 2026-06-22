package com.ayesa.api.linting.engine.rules;

import com.ayesa.api.linting.engine.LintingRule;
import com.ayesa.api.linting.model.LintingIssue;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.media.Schema;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Validates that component schemas avoid unsupported file and tuple-like shapes.
 */
@Component
public class UnsupportedSchemaShapesRule implements LintingRule {

    @Override
    public String getRuleId() {
        return "unsupported-schema-shapes";
    }

    @Override
    public String getDescription() {
        return "Validates that schemas do not use file types or tuple-like array shapes";
    }

    @Override
    public String getRulesetId() {
        return "datagraph-best-practices";
    }

    @Override
    public List<LintingIssue> apply(OpenAPI openAPI) {
        List<LintingIssue> issues = new ArrayList<>();
        if (openAPI == null || openAPI.getComponents() == null || openAPI.getComponents().getSchemas() == null) {
            return issues;
        }

        Set<Schema<?>> visited = Collections.newSetFromMap(new IdentityHashMap<>());
        for (Map.Entry<String, Schema> entry : openAPI.getComponents().getSchemas().entrySet()) {
            inspectSchema(issues, "/components/schemas/" + entry.getKey(), entry.getValue(), visited);
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
                    "Schema uses unsupported type 'file'",
                    "Model file payloads with supported schema shapes instead of type:file"
            ));
        }

        if ("array".equals(schema.getType()) && (hasPrefixItems(schema) || hasComplexItems(schema.getItems()))) {
            issues.add(LintingIssue.warning(
                    getRuleId(),
                    location,
                    "Schema uses an unsupported tuple-like array shape",
                    "Use homogeneous array items instead of prefixItems or tuple-like array definitions"
            ));
        }

        for (Map.Entry<String, Schema> propertyEntry : propertiesOf(schema).entrySet()) {
            inspectSchema(issues, location + "/properties/" + propertyEntry.getKey(), propertyEntry.getValue(), visited);
        }

        inspectSchema(issues, location + "/items", schema.getItems(), visited);
        inspectSchemaList(issues, location + "/oneOf", schema.getOneOf(), visited);
        inspectSchemaList(issues, location + "/anyOf", schema.getAnyOf(), visited);
        inspectSchemaList(issues, location + "/allOf", schema.getAllOf(), visited);

        Object additionalProperties = schema.getAdditionalProperties();
        if (additionalProperties instanceof Schema<?> additionalSchema) {
            inspectSchema(issues, location + "/additionalProperties", additionalSchema, visited);
        }
    }

    private boolean hasComplexItems(Schema<?> items) {
        return items != null && (
                hasPrefixItems(items)
                        || (items.getOneOf() != null && !items.getOneOf().isEmpty())
                        || (items.getAnyOf() != null && !items.getAnyOf().isEmpty())
                        || (items.getAllOf() != null && !items.getAllOf().isEmpty())
        );
    }

    private boolean hasPrefixItems(Schema<?> schema) {
        try {
            Method method = schema.getClass().getMethod("getPrefixItems");
            Object prefixItems = method.invoke(schema);
            if (prefixItems instanceof List<?>) {
                return !((List<?>) prefixItems).isEmpty();
            }
        } catch (ReflectiveOperationException ignored) {
            return false;
        }
        return false;
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
