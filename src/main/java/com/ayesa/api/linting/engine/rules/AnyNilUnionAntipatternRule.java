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
public class AnyNilUnionAntipatternRule implements LintingRule {

    @Override
    public String getRuleId() {
        return "nil-union-antipattern";
    }

    @Override
    public String getDescription() {
        return "Properties should avoid nullable:true in favor of optional fields";
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

        for (Map.Entry<String, Schema> propertyEntry : propertiesOf(schema).entrySet()) {
            Schema<?> propertySchema = propertyEntry.getValue();
            if (propertySchema != null && Boolean.TRUE.equals(propertySchema.getNullable())) {
                issues.add(LintingIssue.warning(
                        getRuleId(),
                        location + "/properties/" + propertyEntry.getKey(),
                        "Property '" + propertyEntry.getKey() + "' is nullable",
                        "Prefer optional fields instead of nullable:true when modeling absent values"
                ));
            }
            inspectSchema(issues, location + "/properties/" + propertyEntry.getKey(), propertySchema, visited);
        }

        inspectSchema(issues, location + "/items", schema.getItems(), visited);
        inspectSchemaList(issues, location + "/oneOf", schema.getOneOf(), visited);
        inspectSchemaList(issues, location + "/anyOf", schema.getAnyOf(), visited);
        inspectSchemaList(issues, location + "/allOf", schema.getAllOf(), visited);
        if (schema.getAdditionalProperties() instanceof Schema<?> additionalSchema) {
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

    @SuppressWarnings("unchecked")
    private Map<String, Schema> propertiesOf(Schema<?> schema) {
        return schema.getProperties() == null ? Collections.emptyMap() : (Map<String, Schema>) schema.getProperties();
    }
}
