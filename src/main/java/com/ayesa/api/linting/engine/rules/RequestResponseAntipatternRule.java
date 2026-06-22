package com.ayesa.api.linting.engine.rules;

import com.ayesa.api.linting.engine.LintingRule;
import com.ayesa.api.linting.model.LintingIssue;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Validates that component schema names do not use Request/Response suffixes.
 */
@Component
public class RequestResponseAntipatternRule implements LintingRule {

    @Override
    public String getRuleId() {
        return "request-response-antipattern";
    }

    @Override
    public String getDescription() {
        return "Validates that component schema names avoid Request and Response naming antipatterns";
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

        for (String schemaName : openAPI.getComponents().getSchemas().keySet()) {
            String lowerCaseName = schemaName.toLowerCase();
            if (!lowerCaseName.contains("request") && !lowerCaseName.contains("response")) {
                continue;
            }

            issues.add(LintingIssue.warning(
                    getRuleId(),
                    "/components/schemas/" + schemaName,
                    "Schema name '" + schemaName + "' contains Request or Response",
                    "Rename the schema to a domain-focused type name"
            ));
        }

        return issues;
    }
}
