package com.ayesa.api.linting.engine.rules;

import com.ayesa.api.linting.engine.LintingRule;
import com.ayesa.api.linting.model.LintingIssue;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Component
public class AuthApiKeysInHeaderHttpsRule implements LintingRule {

    @Override
    public String getRuleId() {
        return "api-keys-in-header-https";
    }

    @Override
    public String getDescription() {
        return "Header-based API key security schemes must only be used with HTTPS server URLs";
    }

    @Override
    public String getRulesetId() {
        return "authentication-security-best-practices";
    }

    @Override
    public List<LintingIssue> apply(OpenAPI openAPI) {
        List<LintingIssue> issues = new ArrayList<>();
        Map<String, SecurityScheme> securitySchemes = getSecuritySchemes(openAPI);
        if (securitySchemes.isEmpty()) {
            return issues;
        }

        List<String> headerApiKeySchemes = securitySchemes.entrySet().stream()
                .filter(entry -> entry.getValue() != null
                        && entry.getValue().getType() == SecurityScheme.Type.APIKEY
                        && entry.getValue().getIn() == SecurityScheme.In.HEADER)
                .map(Map.Entry::getKey)
                .toList();

        if (headerApiKeySchemes.isEmpty()) {
            return issues;
        }

        if (openAPI.getServers() == null || openAPI.getServers().isEmpty()) {
            for (String schemeName : headerApiKeySchemes) {
                issues.add(LintingIssue.error(getRuleId(),
                        "/components/securitySchemes/" + schemeName,
                        "Header API key security scheme '" + schemeName + "' is defined but no HTTPS server URLs are declared",
                        "Define one or more HTTPS server URLs when using header-based API keys"));
            }
            return issues;
        }

        for (String schemeName : headerApiKeySchemes) {
            for (int i = 0; i < openAPI.getServers().size(); i++) {
                Server server = openAPI.getServers().get(i);
                String url = server != null ? server.getUrl() : null;
                if (!isHttpsOrTemplated(url)) {
                    issues.add(LintingIssue.error(getRuleId(),
                            "/servers/" + i,
                            "Header API key security scheme '" + schemeName + "' requires HTTPS, but server URL is '" + url + "'",
                            "Change the server URL to use https://"));
                }
            }
        }

        return issues;
    }

    private Map<String, SecurityScheme> getSecuritySchemes(OpenAPI openAPI) {
        if (openAPI.getComponents() == null || openAPI.getComponents().getSecuritySchemes() == null) {
            return Map.of();
        }
        return openAPI.getComponents().getSecuritySchemes();
    }

    private boolean isHttpsOrTemplated(String url) {
        if (url == null || url.isBlank()) {
            return false;
        }
        int schemeSeparator = url.indexOf("://");
        if (schemeSeparator < 0) {
            return url.startsWith("{");
        }
        String scheme = url.substring(0, schemeSeparator).toLowerCase(Locale.ROOT);
        return "https".equals(scheme) || scheme.contains("{");
    }
}
