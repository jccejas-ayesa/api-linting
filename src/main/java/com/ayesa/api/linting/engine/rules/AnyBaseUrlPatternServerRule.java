package com.ayesa.api.linting.engine.rules;

import com.ayesa.api.linting.engine.LintingRule;
import com.ayesa.api.linting.model.LintingIssue;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Component
public class AnyBaseUrlPatternServerRule implements LintingRule {

    private static final Pattern SERVER_PATH_PATTERN = Pattern.compile("^/api/(v\\d+|\\d+\\.\\d+)(?:/.*)?$");

    @Override
    public String getRuleId() {
        return "base-url-pattern-server";
    }

    @Override
    public String getDescription() {
        return "Server URLs must include /api and a version segment";
    }

    @Override
    public String getRulesetId() {
        return "anypoint-best-practices";
    }

    @Override
    public List<LintingIssue> apply(OpenAPI openAPI) {
        List<LintingIssue> issues = new ArrayList<>();
        if (openAPI == null || openAPI.getServers() == null) {
            return issues;
        }

        for (int i = 0; i < openAPI.getServers().size(); i++) {
            Server server = openAPI.getServers().get(i);
            String path = extractPath(server == null ? null : server.getUrl());
            if (path == null || !SERVER_PATH_PATTERN.matcher(path).matches()) {
                issues.add(LintingIssue.error(
                        getRuleId(),
                        "/servers/" + i + "/url",
                        "Server URL does not follow the required /api/version pattern",
                        "Use a server URL such as /api/v1 or /api/1.0"
                ));
            }
        }

        return issues;
    }

    private String extractPath(String url) {
        if (url == null || url.isBlank()) {
            return null;
        }
        try {
            URI uri = URI.create(url);
            if (uri.getScheme() != null) {
                return uri.getPath();
            }
        } catch (IllegalArgumentException ignored) {
            return url.startsWith("/") ? url : null;
        }
        return url.startsWith("/") ? url : null;
    }
}
