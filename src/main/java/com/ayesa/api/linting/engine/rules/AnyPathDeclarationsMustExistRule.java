package com.ayesa.api.linting.engine.rules;

import com.ayesa.api.linting.engine.LintingRule;
import com.ayesa.api.linting.model.LintingIssue;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AnyPathDeclarationsMustExistRule implements LintingRule {

    @Override
    public String getRuleId() {
        return "path-declarations-must-exist";
    }

    @Override
    public String getDescription() {
        return "Path keys must be declared and non-blank";
    }

    @Override
    public String getRulesetId() {
        return "anypoint-best-practices";
    }

    @Override
    public List<LintingIssue> apply(OpenAPI openAPI) {
        List<LintingIssue> issues = new ArrayList<>();
        if (openAPI == null || openAPI.getPaths() == null) {
            return issues;
        }

        openAPI.getPaths().forEach((path, pathItem) -> {
            if (path == null || path.isBlank()) {
                issues.add(LintingIssue.error(
                        getRuleId(),
                        "/paths",
                        "A path declaration is empty or blank",
                        "Declare every path with a non-empty key such as /orders or /customers/{id}"
                ));
            }
        });

        return issues;
    }
}
