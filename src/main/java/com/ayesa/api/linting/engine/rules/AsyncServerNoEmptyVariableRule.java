package com.ayesa.api.linting.engine.rules;

import com.ayesa.api.linting.engine.LintingRule;
import com.ayesa.api.linting.model.LintingIssue;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AsyncServerNoEmptyVariableRule implements LintingRule {

    @Override
    public String getRuleId() {
        return "asyncapi-server-no-empty-variable";
    }

    @Override
    public String getDescription() {
        return "Validates that server URLs do not contain empty variable declarations";
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
            if (url != null && url.contains("{}")) {
                issues.add(LintingIssue.error(
                        getRuleId(),
                        "/servers/" + i,
                        "Server URL contains an empty variable declaration",
                        "Replace '{}' with a named server variable such as '{region}' or remove it"
                ));
            }
        }

        return issues;
    }
}
