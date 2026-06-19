package com.ayesa.api.linting.engine.rules;

import com.ayesa.api.linting.engine.LintingRule;
import com.ayesa.api.linting.model.LintingIssue;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Validates that security schemes use OAuth2 or OpenID Connect.
 * Covers directive 3.2: Autenticación obligatoria mediante estándares corporativos.
 */
@Component
public class Oauth2RequiredRule implements LintingRule {

    private static final Set<SecurityScheme.Type> ALLOWED_TYPES = Set.of(
            SecurityScheme.Type.OAUTH2,
            SecurityScheme.Type.OPENIDCONNECT
    );

    @Override
    public String getRuleId() {
        return "oauth2-required";
    }

    @Override
    public String getDescription() {
        return "Validates that security schemes use OAuth2 or OpenID Connect as authentication mechanism";
    }

    @Override
    public List<LintingIssue> apply(OpenAPI openAPI) {
        List<LintingIssue> issues = new ArrayList<>();

        if (openAPI.getComponents() == null
                || openAPI.getComponents().getSecuritySchemes() == null) {
            return issues;
        }

        for (Map.Entry<String, SecurityScheme> entry : openAPI.getComponents().getSecuritySchemes().entrySet()) {
            String schemeName = entry.getKey();
            SecurityScheme scheme = entry.getValue();

            if (scheme.getType() != null && !ALLOWED_TYPES.contains(scheme.getType())) {
                issues.add(LintingIssue.warning(getRuleId(),
                        "/components/securitySchemes/" + schemeName,
                        "Security scheme '" + schemeName + "' uses type '" + scheme.getType()
                                + "' instead of OAuth2 or OpenID Connect",
                        "Use oauth2 or openIdConnect as the security scheme type"));
            }
        }

        return issues;
    }
}
