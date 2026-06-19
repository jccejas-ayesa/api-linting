package com.ayesa.api.linting.engine.rules;

import com.ayesa.api.linting.engine.LintingRule;
import com.ayesa.api.linting.model.LintingIssue;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Validates that the API defines health check endpoints.
 * Covers directive 6.1: Recursos de salud obligatorios.
 */
@Component
public class HealthEndpointsRequiredRule implements LintingRule {

    private static final Set<String> HEALTH_PATHS = Set.of(
            "/health", "/actuator/health",
            "/ready", "/actuator/ready", "/readiness",
            "/live", "/actuator/live", "/liveness"
    );

    @Override
    public String getRuleId() {
        return "health-endpoints-required";
    }

    @Override
    public String getDescription() {
        return "Validates that the API defines health check endpoints (/health, /ready, /live)";
    }

    @Override
    public List<LintingIssue> apply(OpenAPI openAPI) {
        List<LintingIssue> issues = new ArrayList<>();

        if (openAPI.getPaths() == null || openAPI.getPaths().isEmpty()) {
            return issues;
        }

        boolean hasHealthEndpoint = openAPI.getPaths().keySet().stream()
                .anyMatch(path -> HEALTH_PATHS.stream().anyMatch(hp -> path.endsWith(hp)));

        if (!hasHealthEndpoint) {
            issues.add(LintingIssue.warning(getRuleId(), "/paths",
                    "No health check endpoints defined",
                    "Add health endpoints (e.g., /health, /ready, /live) for monitoring and availability checks"));
        }

        return issues;
    }
}
