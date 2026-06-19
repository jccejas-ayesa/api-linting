package com.ayesa.api.linting.engine.rules;

import com.ayesa.api.linting.engine.LintingRule;
import com.ayesa.api.linting.model.LintingIssue;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.callbacks.Callback;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Validates that all callback URLs use HTTPS.
 * Covers the "use-https-for-callbacks" rule from HTTPS Enforcement ruleset.
 */
@Component
public class HttpsCallbacksRule implements LintingRule {

    @Override
    public String getRuleId() {
        return "use-https-for-callbacks";
    }

    @Override
    public String getDescription() {
        return "Callback URLs must use HTTPS protocol";
    }

    @Override
    public String getRulesetId() {
        return "https-enforcement";
    }

    @Override
    public List<LintingIssue> apply(OpenAPI openAPI) {
        List<LintingIssue> issues = new ArrayList<>();

        if (openAPI.getPaths() == null) {
            return issues;
        }

        for (Map.Entry<String, PathItem> pathEntry : openAPI.getPaths().entrySet()) {
            String path = pathEntry.getKey();
            PathItem pathItem = pathEntry.getValue();

            checkOperationCallbacks(pathItem.getGet(), path, "get", issues);
            checkOperationCallbacks(pathItem.getPost(), path, "post", issues);
            checkOperationCallbacks(pathItem.getPut(), path, "put", issues);
            checkOperationCallbacks(pathItem.getDelete(), path, "delete", issues);
            checkOperationCallbacks(pathItem.getPatch(), path, "patch", issues);
        }

        return issues;
    }

    private void checkOperationCallbacks(Operation operation, String path, String method,
                                         List<LintingIssue> issues) {
        if (operation == null || operation.getCallbacks() == null) {
            return;
        }

        for (Map.Entry<String, Callback> cbEntry : operation.getCallbacks().entrySet()) {
            String callbackName = cbEntry.getKey();
            Callback callback = cbEntry.getValue();

            if (callback == null) {
                continue;
            }

            for (String expression : callback.keySet()) {
                if (expression != null && !expression.startsWith("https://")
                        && !expression.startsWith("{$")) {
                    // Only flag if it looks like a URL (starts with http:// or similar)
                    if (expression.startsWith("http://") || expression.contains("://")) {
                        issues.add(LintingIssue.error(getRuleId(),
                                path + "/" + method + "/callbacks/" + callbackName,
                                "Callback URL must use HTTPS: " + expression,
                                "Change the callback URL to use https:// protocol"));
                    }
                }
            }
        }
    }
}
