package com.ayesa.api.linting.engine.rules;

import com.ayesa.api.linting.engine.LintingRule;
import com.ayesa.api.linting.model.LintingIssue;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.media.Schema;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Validates naming conventions for schemas and properties.
 * Schemas should use PascalCase, properties should use camelCase.
 * Covers directive 2.8: Convención obligatoria de nombrado.
 */
@Component
public class NamingConventionSchemasRule implements LintingRule {

    private static final Pattern PASCAL_CASE = Pattern.compile("^[A-Z][a-zA-Z0-9]*$");
    private static final Pattern CAMEL_CASE = Pattern.compile("^[a-z][a-zA-Z0-9]*$");

    @Override
    public String getRuleId() {
        return "naming-convention-schemas";
    }

    @Override
    public String getDescription() {
        return "Validates that schema names use PascalCase and properties use camelCase";
    }

    @Override
    public String getRulesetId() {
        return "openapi-best-practices";
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<LintingIssue> apply(OpenAPI openAPI) {
        List<LintingIssue> issues = new ArrayList<>();

        if (openAPI.getComponents() == null || openAPI.getComponents().getSchemas() == null) {
            return issues;
        }

        for (Map.Entry<String, Schema> schemaEntry : openAPI.getComponents().getSchemas().entrySet()) {
            String schemaName = schemaEntry.getKey();
            Schema<?> schema = schemaEntry.getValue();

            // Check schema name is PascalCase
            if (!PASCAL_CASE.matcher(schemaName).matches()) {
                issues.add(LintingIssue.warning(getRuleId(),
                        "/components/schemas/" + schemaName,
                        "Schema name '" + schemaName + "' does not follow PascalCase convention",
                        "Rename to PascalCase (e.g., 'UserProfile' instead of 'user_profile')"));
            }

            // Check property names are camelCase
            if (schema.getProperties() != null) {
                for (String propName : ((Map<String, Schema>) schema.getProperties()).keySet()) {
                    if (!CAMEL_CASE.matcher(propName).matches()) {
                        issues.add(LintingIssue.warning(getRuleId(),
                                "/components/schemas/" + schemaName + "/properties/" + propName,
                                "Property '" + propName + "' does not follow camelCase convention",
                                "Rename to camelCase (e.g., 'firstName' instead of 'first_name')"));
                    }
                }
            }
        }

        return issues;
    }
}
