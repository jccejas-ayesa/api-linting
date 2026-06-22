package com.ayesa.api.linting.engine.rules;

import com.ayesa.api.linting.engine.LintingRule;
import com.ayesa.api.linting.model.LintingIssue;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Validates that OAuth2 security requirements include scopes.
 * Covers directive 3.3: Autorización obligatoria basada en scopes y roles.
 */
@Component
public class ScopesRequiredRule implements LintingRule {

    @Override
    public String getRuleId() {
        return "scopes-required";
    }

    @Override
    public String getDescription() {
        return "Validates that OAuth2 security requirements define explicit scopes for authorization";
    }

    @Override
    public String getRulesetId() {
        return "authentication-security-best-practices";
    }

    @Override
    public List<LintingIssue> apply(OpenAPI openAPI) {
        List<LintingIssue> issues = new ArrayList<>();

        Set<String> oauth2Schemes = getOauth2SchemeNames(openAPI);
        if (oauth2Schemes.isEmpty()) {
            return issues;
        }

        // Check global security
        if (openAPI.getSecurity() != null) {
            checkSecurityRequirements(issues, "/security", openAPI.getSecurity(), oauth2Schemes);
        }

        // Check per-operation security
        if (openAPI.getPaths() != null) {
            for (Map.Entry<String, PathItem> pathEntry : openAPI.getPaths().entrySet()) {
                String path = pathEntry.getKey();
                PathItem pathItem = pathEntry.getValue();

                checkOperationScopes(issues, path, "GET", pathItem.getGet(), oauth2Schemes);
                checkOperationScopes(issues, path, "POST", pathItem.getPost(), oauth2Schemes);
                checkOperationScopes(issues, path, "PUT", pathItem.getPut(), oauth2Schemes);
                checkOperationScopes(issues, path, "DELETE", pathItem.getDelete(), oauth2Schemes);
                checkOperationScopes(issues, path, "PATCH", pathItem.getPatch(), oauth2Schemes);
            }
        }

        return issues;
    }

    private Set<String> getOauth2SchemeNames(OpenAPI openAPI) {
        if (openAPI.getComponents() == null || openAPI.getComponents().getSecuritySchemes() == null) {
            return Set.of();
        }

        return openAPI.getComponents().getSecuritySchemes().entrySet().stream()
                .filter(e -> e.getValue().getType() == SecurityScheme.Type.OAUTH2
                        || e.getValue().getType() == SecurityScheme.Type.OPENIDCONNECT)
                .map(Map.Entry::getKey)
                .collect(java.util.stream.Collectors.toSet());
    }

    private void checkOperationScopes(List<LintingIssue> issues, String path, String method,
                                       Operation operation, Set<String> oauth2Schemes) {
        if (operation == null || operation.getSecurity() == null) {
            return;
        }
        checkSecurityRequirements(issues, path + " [" + method + "]", operation.getSecurity(), oauth2Schemes);
    }

    private void checkSecurityRequirements(List<LintingIssue> issues, String location,
                                            List<SecurityRequirement> requirements, Set<String> oauth2Schemes) {
        for (SecurityRequirement requirement : requirements) {
            for (Map.Entry<String, List<String>> entry : requirement.entrySet()) {
                if (oauth2Schemes.contains(entry.getKey())
                        && (entry.getValue() == null || entry.getValue().isEmpty())) {
                    issues.add(LintingIssue.warning(getRuleId(), location,
                            "OAuth2 security requirement '" + entry.getKey() + "' has no scopes defined",
                            "Define explicit scopes for fine-grained authorization (e.g., 'read:users', 'write:orders')"));
                }
            }
        }
    }
}
