package com.ayesa.api.linting.engine.rules;

import com.ayesa.api.linting.engine.LintingRule;
import com.ayesa.api.linting.model.LintingIssue;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.OAuthFlows;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class AuthInsecureOauth2GrantsRule implements LintingRule {

    @Override
    public String getRuleId() {
        return "insecure-oauth2-grants";
    }

    @Override
    public String getDescription() {
        return "OAuth2 security schemes must not use implicit or password grant flows";
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
            if (scheme == null || scheme.getType() != SecurityScheme.Type.OAUTH2) {
                continue;
            }

            OAuthFlows flows = scheme.getFlows();
            if (flows == null) {
                continue;
            }

            if (flows.getImplicit() != null) {
                issues.add(LintingIssue.error(getRuleId(),
                        "/components/securitySchemes/" + entry.getKey() + "/flows/implicit",
                        "OAuth2 security scheme '" + entry.getKey() + "' uses the implicit grant flow",
                        "Replace the implicit flow with authorizationCode or clientCredentials"));
            }

            if (flows.getPassword() != null) {
                issues.add(LintingIssue.error(getRuleId(),
                        "/components/securitySchemes/" + entry.getKey() + "/flows/password",
                        "OAuth2 security scheme '" + entry.getKey() + "' uses the password grant flow",
                        "Replace the password flow with authorizationCode or clientCredentials"));
            }
        }

        return issues;
    }
}
