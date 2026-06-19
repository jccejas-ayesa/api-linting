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
public class GrpcMessageNamesExcludePrepositionsRule extends GrpcRuleSupport implements LintingRule {

    @Override
    public String getRuleId() {
        return "message-names-exclude-prepositions";
    }

    @Override
    public String getDescription() {
        return "Warns when message names include prepositions mid-name";
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
            if (containsMidNamePreposition(entry.getKey())) {
                issues.add(LintingIssue.warning(
                        getRuleId(),
                        schemaLocation(entry.getKey()),
                        "Schema name '" + entry.getKey() + "' contains a preposition in the middle of the name",
                        "Prefer a more concise noun-based schema name without words like 'With', 'Of', or 'For'"
                ));
            }
        }
        return issues;
    }
}
