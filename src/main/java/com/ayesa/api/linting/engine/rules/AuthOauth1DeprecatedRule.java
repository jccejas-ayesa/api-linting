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

@Component
public class AuthOauth1DeprecatedRule implements LintingRule {

    @Override
    public String getRuleId() {
        return "oauth1-deprecated";
    }

    @Override
    public String getDescription() {
        return "OAuth 1.0 security schemes are deprecated and must not be used";
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
            String schemeName = entry.getKey();
            SecurityScheme scheme = entry.getValue();
            if (isOauth1Scheme(schemeName, scheme)) {
                issues.add(LintingIssue.error(getRuleId(),
                        "/components/securitySchemes/" + schemeName,
                        "Security scheme '" + schemeName + "' appears to use deprecated OAuth 1.0 authentication",
                        "Replace OAuth 1.0 with OAuth 2.0 or OpenID Connect"));
            }
        }

        return issues;
    }

    private boolean isOauth1Scheme(String schemeName, SecurityScheme scheme) {
        if (containsOauth1Indicator(schemeName)) {
            return true;
        }
        if (scheme == null) {
            return false;
        }
        if (containsOauth1Indicator(scheme.getScheme())
                || containsOauth1Indicator(scheme.getDescription())
                || containsOauth1Indicator(scheme.getBearerFormat())) {
            return true;
        }
        if (scheme.getExtensions() == null) {
            return false;
        }
        for (Object value : scheme.getExtensions().values()) {
            if (containsOauth1Indicator(value != null ? value.toString() : null)) {
                return true;
            }
        }
        return false;
    }

    private boolean containsOauth1Indicator(String value) {
        if (value == null || value.isBlank()) {
            return false;
        }
        String normalized = value.toLowerCase(Locale.ROOT).replace('_', ' ').replace('-', ' ');
        return normalized.contains("oauth1")
                || normalized.contains("oauth 1")
                || normalized.contains("oauth 1.0");
    }
}
