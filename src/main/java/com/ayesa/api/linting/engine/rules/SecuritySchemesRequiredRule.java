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

/**
 * Validates that security schemes are defined and applied to all operations.
 * Covers directive 3.1: Seguridad obligatoria en APIs.
 */
@Component
public class SecuritySchemesRequiredRule implements LintingRule {

    @Override
    public String getRuleId() {
        return "security-schemes-required";
    }

    @Override
    public String getDescription() {
        return "Validates that security schemes are defined and applied globally or per-operation";
    }

    @Override
    public String getRulesetId() {
        return "authentication-security-best-practices";
    }

    @Override
    public List<String> getRulesetIds() {
        return List.of(getRulesetId(), "owasp-api-security-top-10");
    }

    @Override
    public List<LintingIssue> apply(OpenAPI openAPI) {
        List<LintingIssue> issues = new ArrayList<>();

        if (openAPI.getComponents() == null
                || openAPI.getComponents().getSecuritySchemes() == null
                || openAPI.getComponents().getSecuritySchemes().isEmpty()) {
            issues.add(LintingIssue.error(getRuleId(), "/components/securitySchemes",
                    "No security schemes defined",
                    "Define at least one security scheme (e.g., OAuth2, OpenID Connect)"));
            return issues;
        }

        boolean hasGlobalSecurity = openAPI.getSecurity() != null && !openAPI.getSecurity().isEmpty();

        if (!hasGlobalSecurity && openAPI.getPaths() != null) {
            for (Map.Entry<String, PathItem> pathEntry : openAPI.getPaths().entrySet()) {
                String path = pathEntry.getKey();
                PathItem pathItem = pathEntry.getValue();

                checkOperationSecurity(issues, path, "GET", pathItem.getGet());
                checkOperationSecurity(issues, path, "POST", pathItem.getPost());
                checkOperationSecurity(issues, path, "PUT", pathItem.getPut());
                checkOperationSecurity(issues, path, "DELETE", pathItem.getDelete());
                checkOperationSecurity(issues, path, "PATCH", pathItem.getPatch());
            }
        }

        return issues;
    }

    private void checkOperationSecurity(List<LintingIssue> issues, String path, String method, Operation operation) {
        if (operation == null) {
            return;
        }

        if (operation.getSecurity() == null || operation.getSecurity().isEmpty()) {
            issues.add(LintingIssue.error(getRuleId(), path + " [" + method + "]",
                    "No security applied to operation (and no global security defined)",
                    "Apply a security scheme to this operation or define global security"));
        }
    }
}
