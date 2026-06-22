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
 * Validates that schema properties do not rely on nullable=true.
 */
@Component
public class NonUnionAntipatternRule implements LintingRule {

    @Override
    public String getRuleId() {
        return "non-union-antipattern";
    }

    @Override
    public String getDescription() {
        return "Validates that schema properties do not use nullable=true";
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
            inspectProperties(issues, "/components/schemas/" + entry.getKey(), entry.getValue(), visited);
        }

        return issues;
    }

    private void inspectProperties(List<LintingIssue> issues, String location, Schema<?> schema, Set<Schema<?>> visited) {
        if (schema == null || !visited.add(schema)) {
            return;
        }

        for (Map.Entry<String, Schema> propertyEntry : propertiesOf(schema).entrySet()) {
            String propertyLocation = location + "/properties/" + propertyEntry.getKey();
            Schema<?> propertySchema = propertyEntry.getValue();
            if (propertySchema == null) {
                continue;
            }

            if (Boolean.TRUE.equals(propertySchema.getNullable())) {
                issues.add(LintingIssue.warning(
                        getRuleId(),
                        propertyLocation,
                        "Property '" + propertyEntry.getKey() + "' uses nullable=true",
                        "Make the property optional instead of marking it nullable"
                ));
            }

            inspectProperties(issues, propertyLocation, propertySchema, visited);
        }

        inspectProperties(issues, location + "/items", schema.getItems(), visited);
        inspectSchemaList(issues, location + "/oneOf", schema.getOneOf(), visited);
        inspectSchemaList(issues, location + "/anyOf", schema.getAnyOf(), visited);
        inspectSchemaList(issues, location + "/allOf", schema.getAllOf(), visited);

        Object additionalProperties = schema.getAdditionalProperties();
        if (additionalProperties instanceof Schema<?> additionalSchema) {
            inspectProperties(issues, location + "/additionalProperties", additionalSchema, visited);
        }
    }

    private void inspectSchemaList(List<LintingIssue> issues, String location, List<Schema> schemas, Set<Schema<?>> visited) {
        if (schemas == null) {
            return;
        }

        for (int i = 0; i < schemas.size(); i++) {
            inspectProperties(issues, location + "/" + i, schemas.get(i), visited);
        }
    }

    @SuppressWarnings("unchecked")
    private Map<String, Schema> propertiesOf(Schema<?> schema) {
        return schema.getProperties() == null ? Collections.emptyMap() : (Map<String, Schema>) schema.getProperties();
    }
}
