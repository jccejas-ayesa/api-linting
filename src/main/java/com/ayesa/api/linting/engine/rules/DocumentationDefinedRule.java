package com.ayesa.api.linting.engine.rules;

import com.ayesa.api.linting.engine.LintingRule;
import com.ayesa.api.linting.model.LintingIssue;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Validates that the API has documentation defined.
 * Covers the "documentation-should-be-defined" rule from API Documentation Best Practices.
 */
@Component
public class DocumentationDefinedRule implements LintingRule {

    @Override
    public String getRuleId() {
        return "documentation-should-be-defined";
    }

    @Override
    public String getDescription() {
        return "The API should have documentation. The API asset should have at least a documentation page detailing the API";
    }

    @Override
    public String getRulesetId() {
        return "api-documentation-best-practices";
    }

    @Override
    public List<LintingIssue> apply(OpenAPI openAPI) {
        List<LintingIssue> issues = new ArrayList<>();

        Info info = openAPI.getInfo();

        // Check info.description exists
        if (info == null || info.getDescription() == null || info.getDescription().isBlank()) {
            issues.add(LintingIssue.warning(getRuleId(), "/info/description",
                    "The API should have documentation — info.description is missing",
                    "Add a comprehensive description in the info section explaining the API purpose, scope, and usage"));
        }

        // Check externalDocs
        ExternalDocumentation externalDocs = openAPI.getExternalDocs();
        if (externalDocs == null || externalDocs.getUrl() == null || externalDocs.getUrl().isBlank()) {
            issues.add(LintingIssue.info(getRuleId(), "/externalDocs",
                    "No external documentation link provided",
                    "Add externalDocs with a URL pointing to the full API documentation or wiki page"));
        }

        return issues;
    }
}
