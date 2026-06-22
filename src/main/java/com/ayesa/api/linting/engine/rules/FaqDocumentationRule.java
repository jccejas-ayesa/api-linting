package com.ayesa.api.linting.engine.rules;

import com.ayesa.api.linting.engine.LintingRule;
import com.ayesa.api.linting.model.LintingIssue;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Validates that the API documentation includes an FAQ section.
 * Covers the "faq" rule from API Documentation Best Practices.
 */
@Component
public class FaqDocumentationRule implements LintingRule {

    private static final Set<String> FAQ_INDICATORS = Set.of(
            "faq", "frequently asked questions", "preguntas frecuentes"
    );

    @Override
    public String getRuleId() {
        return "faq";
    }

    @Override
    public String getDescription() {
        return "The API documentation should include an FAQ page";
    }

    @Override
    public String getRulesetId() {
        return "api-documentation-best-practices";
    }

    @Override
    public List<LintingIssue> apply(OpenAPI openAPI) {
        List<LintingIssue> issues = new ArrayList<>();

        boolean hasFaq = false;

        // Check external docs URL/description for FAQ reference
        ExternalDocumentation externalDocs = openAPI.getExternalDocs();
        if (externalDocs != null) {
            hasFaq = containsFaqReference(externalDocs.getUrl())
                    || containsFaqReference(externalDocs.getDescription());
        }

        // Check tags for FAQ tag
        if (!hasFaq && openAPI.getTags() != null) {
            hasFaq = openAPI.getTags().stream()
                    .anyMatch(tag -> containsFaqReference(tag.getName())
                            || containsFaqReference(tag.getDescription()));
        }

        // Check info extensions for FAQ
        if (!hasFaq && openAPI.getInfo() != null && openAPI.getInfo().getExtensions() != null) {
            hasFaq = openAPI.getInfo().getExtensions().containsKey("x-faq")
                    || openAPI.getInfo().getExtensions().containsKey("x-documentation-faq");
        }

        // Check info description for FAQ mention
        if (!hasFaq && openAPI.getInfo() != null && openAPI.getInfo().getDescription() != null) {
            hasFaq = containsFaqReference(openAPI.getInfo().getDescription());
        }

        if (!hasFaq) {
            issues.add(LintingIssue.warning(getRuleId(), "/",
                    "The API documentation should include an FAQ page",
                    "Add FAQ content via: (1) externalDocs URL pointing to FAQ, (2) a 'faq' tag, " +
                    "or (3) x-faq extension in info section"));
        }

        return issues;
    }

    private boolean containsFaqReference(String text) {
        if (text == null) {
            return false;
        }
        String lower = text.toLowerCase();
        return FAQ_INDICATORS.stream().anyMatch(lower::contains);
    }
}
