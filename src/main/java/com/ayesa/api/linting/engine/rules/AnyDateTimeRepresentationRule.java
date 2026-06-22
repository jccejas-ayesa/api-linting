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
public class AnyDateTimeRepresentationRule implements LintingRule {

    @Override
    public String getRuleId() {
        return "date-time-representation";
    }

    @Override
    public String getDescription() {
        return "createdAt and updatedAt should use date-time representations";
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
            inspectSchema(openAPI, issues, "/components/schemas/" + entry.getKey(), entry.getValue(), visited);
        }
        return issues;
    }

    private void inspectSchema(OpenAPI openAPI, List<LintingIssue> issues, String location, Schema<?> schema, Set<Schema<?>> visited) {
        Schema<?> resolved = resolveSchema(openAPI, schema);
        if (resolved == null || !visited.add(resolved)) {
            return;
        }

        for (Map.Entry<String, Schema> propertyEntry : propertiesOf(resolved).entrySet()) {
            String propertyName = propertyEntry.getKey();
            Schema<?> propertySchema = resolveSchema(openAPI, propertyEntry.getValue());
            if (("createdAt".equals(propertyName) || "updatedAt".equals(propertyName)) && !isDateTimeSchema(propertySchema)) {
                issues.add(LintingIssue.warning(
                        getRuleId(),
                        location + "/properties/" + propertyName,
                        "Property '" + propertyName + "' should use a date-time representation",
                        "Use type:string with format:date-time or format:rfc3339 for timestamp fields"
                ));
            }
            inspectSchema(openAPI, issues, location + "/properties/" + propertyName, propertyEntry.getValue(), visited);
        }

        inspectSchema(openAPI, issues, location + "/items", resolved.getItems(), visited);
        inspectSchemaList(openAPI, issues, location + "/oneOf", resolved.getOneOf(), visited);
        inspectSchemaList(openAPI, issues, location + "/anyOf", resolved.getAnyOf(), visited);
        inspectSchemaList(openAPI, issues, location + "/allOf", resolved.getAllOf(), visited);
        if (resolved.getAdditionalProperties() instanceof Schema<?> additionalSchema) {
            inspectSchema(openAPI, issues, location + "/additionalProperties", additionalSchema, visited);
        }
    }

    private boolean isDateTimeSchema(Schema<?> schema) {
        return schema != null
                && "string".equals(schema.getType())
                && ("date-time".equals(schema.getFormat()) || "rfc3339".equals(schema.getFormat()));
    }

    private void inspectSchemaList(OpenAPI openAPI, List<LintingIssue> issues, String location, List<Schema> schemas, Set<Schema<?>> visited) {
        if (schemas == null) {
            return;
        }
        for (int i = 0; i < schemas.size(); i++) {
            inspectSchema(openAPI, issues, location + "/" + i, schemas.get(i), visited);
        }
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
