package com.ayesa.api.linting.engine.rules;

import com.ayesa.api.linting.engine.LintingRule;
import com.ayesa.api.linting.model.LintingIssue;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Validates that all server URLs use HTTPS.
 * Covers directive 3.4: Cifrado obligatorio en tránsito.
 */
@Component
public class HttpsRequiredRule implements LintingRule {

    @Override
    public String getRuleId() {
        return "https-required";
    }

    @Override
    public String getDescription() {
        return "Validates that all server URLs use HTTPS protocol for transport encryption";
    }

    @Override
    public String getRulesetId() {
        return "https-enforcement";
    }

    @Override
    public List<String> getRulesetIds() {
        return List.of(getRulesetId(), "owasp-api-security-top-10");
    }

    @Override
    public List<LintingIssue> apply(OpenAPI openAPI) {
        List<LintingIssue> issues = new ArrayList<>();

        if (openAPI.getServers() == null || openAPI.getServers().isEmpty()) {
            issues.add(LintingIssue.warning(getRuleId(), "/servers",
                    "No servers defined",
                    "Define at least one server with an HTTPS URL"));
            return issues;
        }

        for (int i = 0; i < openAPI.getServers().size(); i++) {
            Server server = openAPI.getServers().get(i);
            String url = server.getUrl();

            if (url != null && !url.startsWith("https://") && !url.startsWith("{")) {
                issues.add(LintingIssue.error(getRuleId(), "/servers/" + i,
                        "Server URL does not use HTTPS: " + url,
                        "Change the server URL to use https:// protocol"));
            }
        }

        return issues;
    }
}
