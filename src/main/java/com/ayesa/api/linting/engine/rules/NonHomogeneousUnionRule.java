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

/**
 * Validates that oneOf/anyOf unions do not mix scalar and object schemas.
 */
@Component
public class NonHomogeneousUnionRule implements LintingRule {

    @Override
    public String getRuleId() {
        return "non-homogeneous-union";
    }

    @Override
    public String getDescription() {
        return "Validates that oneOf and anyOf unions do not mix scalar and object schemas";
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

        if (hasMixedTypes(schema.getOneOf()) || hasMixedTypes(schema.getAnyOf())) {
            issues.add(LintingIssue.warning(
                    getRuleId(),
                    location,
                    "Union schema mixes scalar and object variants",
                    "Keep oneOf/anyOf variants homogeneous by using either all scalar types or all object types"
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

    private void inspectSchemaList(List<LintingIssue> issues, String location, List<Schema> schemas, Set<Schema<?>> visited) {
        if (schemas == null) {
            return;
        }

        for (int i = 0; i < schemas.size(); i++) {
            inspectSchema(issues, location + "/" + i, schemas.get(i), visited);
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
        String type = schema.getType();
        return "object".equals(type)
                || !propertiesOf(schema).isEmpty()
                || schema.getAdditionalProperties() instanceof Schema<?>
                || Boolean.TRUE.equals(schema.getAdditionalProperties());
    }

    @SuppressWarnings("unchecked")
    private Map<String, Schema> propertiesOf(Schema<?> schema) {
        return schema.getProperties() == null ? Collections.emptyMap() : (Map<String, Schema>) schema.getProperties();
    }
}
