package com.ayesa.api.linting.engine.rules;

import com.ayesa.api.linting.engine.LintingRule;
import com.ayesa.api.linting.model.LintingIssue;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.media.Schema;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class AnyHeterogeneousUnionRule implements LintingRule {

    @Override
    public String getRuleId() {
        return "heterogeneous-union";
    }

    @Override
    public String getDescription() {
        return "oneOf/anyOf unions should not mix scalar and object types";
    }

    @Override
    public String getRulesetId() {
        return "anypoint-best-practices";
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

        if (hasMixedTypes(schema.getOneOf()) || hasMixedTypes(schema.getAnyOf())) {
            issues.add(LintingIssue.warning(
                    getRuleId(),
                    location,
                    "Union schema mixes scalar and object variants",
                    "Keep union variants homogeneous by using all scalar or all object schemas"
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

    private boolean hasMixedTypes(List<Schema> variants) {
        if (variants == null || variants.isEmpty()) {
            return false;
        }

        boolean hasScalar = false;
        boolean hasObject = false;
        for (Schema<?> variant : variants) {
            if (variant == null) {
                continue;
            }
            hasScalar |= isScalarSchema(variant);
            hasObject |= isObjectSchema(variant);
            if (hasScalar && hasObject) {
                return true;
            }
        }
        return false;
    }

    private boolean isScalarSchema(Schema<?> schema) {
        String type = schema.getType();
        return "string".equals(type) || "integer".equals(type) || "number".equals(type) || "boolean".equals(type);
    }

    private boolean isObjectSchema(Schema<?> schema) {
        return "object".equals(schema.getType())
                || !propertiesOf(schema).isEmpty()
                || schema.getAdditionalProperties() instanceof Schema<?>
                || Boolean.TRUE.equals(schema.getAdditionalProperties());
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
