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
import java.util.Set;

@Component
public class AuthHttpTokenCleartextRule implements LintingRule {

    private static final Set<String> HTTP_TOKEN_SCHEMES = Set.of(
            "digest",
            "hoba",
            "mutual",
            "scram-sha-1",
            "scram-sha-256",
            "vapid"
    );

    @Override
    public String getRuleId() {
        return "http-token-cleartext";
    }

    @Override
    public String getDescription() {
        return "Sensitive HTTP authentication schemes must only be used with HTTPS server URLs";
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

        List<String> httpTokenSchemes = securitySchemes.entrySet().stream()
                .filter(entry -> isRestrictedHttpScheme(entry.getValue()))
                .map(Map.Entry::getKey)
                .toList();

        if (httpTokenSchemes.isEmpty()) {
            return issues;
        }

        if (openAPI.getServers() == null || openAPI.getServers().isEmpty()) {
            for (String schemeName : httpTokenSchemes) {
                issues.add(LintingIssue.error(getRuleId(),
                        "/components/securitySchemes/" + schemeName,
                        "HTTP security scheme '" + schemeName + "' is defined but no HTTPS server URLs are declared",
                        "Define one or more HTTPS server URLs when using HTTP authentication schemes"));
            }
            return issues;
        }

        for (String schemeName : httpTokenSchemes) {
            for (int i = 0; i < openAPI.getServers().size(); i++) {
                Server server = openAPI.getServers().get(i);
                String url = server != null ? server.getUrl() : null;
                if (!isHttpsOrTemplated(url)) {
                    issues.add(LintingIssue.error(getRuleId(),
                            "/servers/" + i,
                            "HTTP security scheme '" + schemeName + "' requires HTTPS, but server URL is '" + url + "'",
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

    private boolean isRestrictedHttpScheme(SecurityScheme scheme) {
        return scheme != null
                && scheme.getType() == SecurityScheme.Type.HTTP
                && scheme.getScheme() != null
                && HTTP_TOKEN_SCHEMES.contains(scheme.getScheme().toLowerCase(Locale.ROOT));
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
