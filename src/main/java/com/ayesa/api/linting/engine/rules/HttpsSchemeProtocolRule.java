package com.ayesa.api.linting.engine.rules;

import com.ayesa.api.linting.engine.LintingRule;
import com.ayesa.api.linting.model.LintingIssue;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Validates that only HTTPS scheme/protocol is used.
 * Covers the "use-https-for-scheme-protocol" rule from HTTPS Enforcement ruleset.
 * In OpenAPI 2.0 (Swagger) this checks the "schemes" field;
 * in OpenAPI 3.x it verifies server URLs don't declare HTTP schemes.
 */
@Component
public class HttpsSchemeProtocolRule implements LintingRule {

    private static final Set<String> INSECURE_SCHEMES = Set.of("http", "ws", "ftp");

    @Override
    public String getRuleId() {
        return "use-https-for-scheme-protocol";
    }

    @Override
    public String getDescription() {
        return "Only HTTPS protocol scheme should be used";
    }

    @Override
    public String getRulesetId() {
        return "https-enforcement";
    }

    @Override
    public List<LintingIssue> apply(OpenAPI openAPI) {
        List<LintingIssue> issues = new ArrayList<>();

        // Check server URLs for insecure schemes
        if (openAPI.getServers() != null) {
            for (int i = 0; i < openAPI.getServers().size(); i++) {
                Server server = openAPI.getServers().get(i);
                String url = server.getUrl();
                if (url != null) {
                    String scheme = extractScheme(url);
                    if (scheme != null && INSECURE_SCHEMES.contains(scheme.toLowerCase())) {
                        issues.add(LintingIssue.error(getRuleId(), "/servers/" + i,
                                "Insecure protocol scheme '" + scheme + "' detected in server URL: " + url,
                                "Only use https:// or wss:// protocol schemes"));
                    }
                }
            }
        }

        // Check extensions for OAS 2.0 schemes migrated to 3.x (x-original-swagger-schemes)
        if (openAPI.getExtensions() != null && openAPI.getExtensions().containsKey("x-original-swagger-schemes")) {
            Object schemes = openAPI.getExtensions().get("x-original-swagger-schemes");
            if (schemes instanceof List<?> schemeList) {
                for (Object s : schemeList) {
                    String scheme = s.toString().toLowerCase();
                    if (INSECURE_SCHEMES.contains(scheme)) {
                        issues.add(LintingIssue.error(getRuleId(), "/x-original-swagger-schemes",
                                "Insecure protocol scheme '" + s + "' declared",
                                "Remove insecure schemes and only use HTTPS/WSS"));
                    }
                }
            }
        }

        return issues;
    }

    private String extractScheme(String url) {
        int idx = url.indexOf("://");
        if (idx > 0) {
            return url.substring(0, idx);
        }
        return null;
    }
}
