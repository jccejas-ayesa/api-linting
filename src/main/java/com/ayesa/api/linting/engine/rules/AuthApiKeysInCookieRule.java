package com.ayesa.api.linting.engine.rules;

import com.ayesa.api.linting.engine.LintingRule;
import com.ayesa.api.linting.model.LintingIssue;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class AuthApiKeysInCookieRule implements LintingRule {

    @Override
    public String getRuleId() {
        return "api-keys-in-cookie";
    }

    @Override
    public String getDescription() {
        return "API key security schemes must not use cookies as the transport location";
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
            if (scheme != null
                    && scheme.getType() == SecurityScheme.Type.APIKEY
                    && scheme.getIn() == SecurityScheme.In.COOKIE) {
                issues.add(LintingIssue.error(getRuleId(),
                        "/components/securitySchemes/" + entry.getKey(),
                        "API key security scheme '" + entry.getKey() + "' uses cookies",
                        "Move the API key to an Authorization header or replace it with a stronger authentication mechanism"));
            }
        }

        return issues;
    }
}
