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
public class GrpcNoRepeatedPrimitiveFieldsRule extends GrpcRuleSupport implements LintingRule {

    @Override
    public String getRuleId() {
        return "no-repeated-primitive-fields";
    }

    @Override
    public String getDescription() {
        return "Flags array properties that repeat primitive items in gRPC-oriented specifications";
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
            for (Map.Entry<String, Schema> propertyEntry : propertiesOf(entry.getValue()).entrySet()) {
                if (isPrimitiveArray(propertyEntry.getValue())) {
                    issues.add(LintingIssue.error(
                            getRuleId(),
                            propertyLocation(entry.getKey(), propertyEntry.getKey()),
                            "Array property '" + propertyEntry.getKey() + "' uses primitive items",
                            "Consider a wrapper object or message instead of repeating primitive values directly"
                    ));
                }
            }
        }
        return issues;
    }
}
