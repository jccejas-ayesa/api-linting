package com.ayesa.api.linting.engine.rules;

import com.ayesa.api.linting.engine.LintingRule;
import com.ayesa.api.linting.model.LintingIssue;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * Validates that path segments use plural nouns for resource collections.
 * Covers API-BP-COMMON-004: Plural nouns for entities.
 */
@Component
public class PluralResourceNamesRule implements LintingRule {

    private static final Pattern VERSION_PREFIX = Pattern.compile("v\\d+");

    // Common singular words that should typically be plural in REST APIs
    private static final Set<String> LIKELY_SINGULAR = Set.of(
            "notification", "customer", "user", "person", "policy", "appointment",
            "product", "order", "payment", "invoice", "account", "transaction",
            "message", "event", "document", "application", "service", "resource",
            "item", "category", "phone", "email", "address", "contact"
    );

    // Exceptions: endpoints that are legitimately singular
    private static final Set<String> EXCEPTIONS = Set.of(
            "health", "ready", "live", "search", "login", "logout", "me", "status", "info"
    );

    @Override
    public String getRuleId() {
        return "plural-resource-names";
    }

    @Override
    public String getDescription() {
        return "Validates that resource path segments use plural nouns (API-BP-COMMON-004)";
    }

    @Override
    public String getRulesetId() {
        return "openapi-best-practices";
    }

    @Override
    public List<LintingIssue> apply(OpenAPI openAPI) {
        List<LintingIssue> issues = new ArrayList<>();

        if (openAPI.getPaths() == null) {
            return issues;
        }

        for (String path : openAPI.getPaths().keySet()) {
            String[] segments = path.split("/");
            for (String segment : segments) {
                if (segment.isEmpty() || segment.startsWith("{") || VERSION_PREFIX.matcher(segment).matches()) {
                    continue;
                }

                String lowerSegment = segment.toLowerCase();

                if (EXCEPTIONS.contains(lowerSegment)) {
                    continue;
                }

                if (LIKELY_SINGULAR.contains(lowerSegment)) {
                    issues.add(LintingIssue.warning(getRuleId(), path,
                            "Path segment '" + segment + "' appears to be singular — use plural nouns for collections",
                            "Use plural form (e.g., '/" + segment + "s' instead of '/" + segment + "')"));
                }
            }
        }

        return issues;
    }
}
