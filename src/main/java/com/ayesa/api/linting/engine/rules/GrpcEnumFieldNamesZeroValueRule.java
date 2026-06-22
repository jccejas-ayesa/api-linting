package com.ayesa.api.linting.engine.rules;

import com.ayesa.api.linting.engine.LintingRule;
import com.ayesa.api.linting.model.LintingIssue;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.media.Schema;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class GrpcEnumFieldNamesZeroValueRule extends GrpcRuleSupport implements LintingRule {

    @Override
    public String getRuleId() {
        return "enum-field-names-zero-value-end-with";
    }

    @Override
    public String getDescription() {
        return "Validates that the first string enum value ends with UNSPECIFIED or INVALID";
    }

    @Override
    public String getRulesetId() {
        return RULESET_ID;
    }

    @Override
    public List<LintingIssue> apply(OpenAPI openAPI) {
        List<LintingIssue> issues = new ArrayList<>();
        if (!isGrpcSpec(openAPI) || openAPI.getComponents() == null || openAPI.getComponents().getSchemas() == null) {
            return issues;
        }

        for (Map.Entry<String, Schema> entry : openAPI.getComponents().getSchemas().entrySet()) {
            String schemaName = entry.getKey();
            Schema<?> schema = entry.getValue();
            if (!isStringEnum(schema)) {
                continue;
            }

            String firstValue = schema.getEnum().get(0).toString();
            if (!firstValue.endsWith("UNSPECIFIED") && !firstValue.endsWith("INVALID")) {
                issues.add(LintingIssue.error(
                        getRuleId(),
                        schemaLocation(schemaName),
                        "First enum value '" + firstValue + "' should end with UNSPECIFIED or INVALID",
                        "Rename the first enum value to end with '_UNSPECIFIED' or '_INVALID'"
                ));
            }
        }
        return issues;
    }
}
