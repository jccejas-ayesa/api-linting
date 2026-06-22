package com.ayesa.api.linting.engine.rules;

import com.ayesa.api.linting.engine.LintingRule;
import com.ayesa.api.linting.model.LintingIssue;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

@Component
public class AuthNoNegotiateSchemeRule implements LintingRule {

    private static final Set<String> FORBIDDEN_SCHEMES = Set.of("negotiate", "spnego");

    @Override
    public String getRuleId() {
        return "api-negotiates-authentication";
    }

    @Override
    public String getDescription() {
        return "HTTP security schemes must not use Negotiate or SPNEGO authentication";
    }

    @Override
    public String getRulesetId() {
        return "authentication-security-best-practices";
    }

    @Override
    public List<LintingIssue> apply(OpenAPI openAPI) {
        List<LintingIssue> issues = new ArrayList<>();
        if (openAPI.getComponents() == null || openAPI.getComponents().getSecuritySchemes() == null) {
            return issues;
        }

        for (Map.Entry<String, SecurityScheme> entry : openAPI.getComponents().getSecuritySchemes().entrySet()) {
            SecurityScheme scheme = entry.getValue();
            String httpScheme = scheme != null && scheme.getScheme() != null
                    ? scheme.getScheme().toLowerCase(Locale.ROOT)
                    : null;

            if (scheme != null
                    && scheme.getType() == SecurityScheme.Type.HTTP
                    && httpScheme != null
                    && FORBIDDEN_SCHEMES.contains(httpScheme)) {
                issues.add(LintingIssue.error(getRuleId(),
                        "/components/securitySchemes/" + entry.getKey(),
                        "HTTP security scheme '" + entry.getKey() + "' uses forbidden authentication scheme '" + scheme.getScheme() + "'",
                        "Replace Negotiate/SPNEGO with a modern token-based authentication scheme"));
            }
        }

        return issues;
    }
}
