package com.ayesa.api.linting.engine.rules;

import com.ayesa.api.linting.engine.LintingRule;
import com.ayesa.api.linting.model.LintingIssue;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AsyncServerNoTrailingSlashRule implements LintingRule {

    @Override
    public String getRuleId() {
        return "asyncapi-server-no-trailing-slash";
    }

    @Override
    public String getDescription() {
        return "Validates that server URLs do not end with a trailing slash";
    }

    @Override
    public String getRulesetId() {
        return "asyncapi-best-practices";
    }

    @Override
    public List<LintingIssue> apply(OpenAPI openAPI) {
        List<LintingIssue> issues = new ArrayList<>();
        if (openAPI.getServers() == null) {
            return issues;
        }

        for (int i = 0; i < openAPI.getServers().size(); i++) {
            Server server = openAPI.getServers().get(i);
            String url = server == null ? null : server.getUrl();
            if (url != null && !url.isBlank() && url.endsWith("/")) {
                issues.add(LintingIssue.error(
                        getRuleId(),
                        "/servers/" + i,
                        "Server URL must not end with a trailing slash",
                        "Remove the trailing slash from the server URL"
                ));
            }
        }

        return issues;
    }
}
