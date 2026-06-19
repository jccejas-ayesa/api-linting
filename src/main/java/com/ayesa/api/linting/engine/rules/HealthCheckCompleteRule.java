package com.ayesa.api.linting.engine.rules;

import com.ayesa.api.linting.engine.LintingRule;
import com.ayesa.api.linting.model.LintingIssue;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.responses.ApiResponse;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Validates comprehensive health check endpoints for enterprise-grade APIs.
 * Requires:
 * - /health (or equivalent): overall service health
 * - Should include dependency status information
 * - Should differentiate liveness from readiness
 *
 * This replaces the basic health-endpoints-required rule with a more thorough check.
 * Covers directive 6.1 and enterprise resilience requirements.
 */
@Component
public class HealthCheckCompleteRule implements LintingRule {

    private static final Set<String> LIVENESS_PATHS = Set.of(
            "/health", "/actuator/health", "/live", "/actuator/live", "/liveness"
    );

    private static final Set<String> READINESS_PATHS = Set.of(
            "/ready", "/actuator/ready", "/readiness", "/actuator/readiness"
    );

    private static final Set<String> DEPENDENCY_INDICATORS = Set.of(
            "dependencies", "components", "checks", "services", "details"
    );

    @Override
    public String getRuleId() {
        return "health-check-complete";
    }

    @Override
    public String getDescription() {
        return "Validates enterprise-grade health checks: liveness, readiness, and dependency health reporting";
    }

    @Override
    public List<LintingIssue> apply(OpenAPI openAPI) {
        List<LintingIssue> issues = new ArrayList<>();

        if (openAPI.getPaths() == null || openAPI.getPaths().isEmpty()) {
            issues.add(LintingIssue.error(getRuleId(), "/paths",
                    "No paths defined — health endpoints are required for enterprise APIs",
                    "Add /health (liveness) and /ready (readiness) endpoints"));
            return issues;
        }

        boolean hasLiveness = false;
        boolean hasReadiness = false;
        boolean hasDependencyInfo = false;

        for (Map.Entry<String, PathItem> entry : openAPI.getPaths().entrySet()) {
            String path = entry.getKey();
            String normalizedPath = normalizePath(path);

            if (LIVENESS_PATHS.contains(normalizedPath)) {
                hasLiveness = true;
                hasDependencyInfo = checkDependencyResponse(openAPI, entry.getValue()) || hasDependencyInfo;
            }

            if (READINESS_PATHS.contains(normalizedPath)) {
                hasReadiness = true;
            }

            // Check for combined health+deps endpoint
            if (normalizedPath.endsWith("/health/dependencies")
                    || normalizedPath.endsWith("/health/deps")
                    || normalizedPath.endsWith("/actuator/health/dependencies")) {
                hasDependencyInfo = true;
            }
        }

        if (!hasLiveness) {
            issues.add(LintingIssue.error(getRuleId(), "/paths",
                    "Missing liveness health endpoint (/health or /live)",
                    "Add a GET /health endpoint that returns service status (UP/DOWN)"));
        }

        if (!hasReadiness) {
            issues.add(LintingIssue.warning(getRuleId(), "/paths",
                    "Missing readiness endpoint (/ready or /readiness)",
                    "Add a GET /ready endpoint that verifies the service can handle requests (dependencies available)"));
        }

        if (hasLiveness && !hasDependencyInfo) {
            issues.add(LintingIssue.warning(getRuleId(), "/paths",
                    "Health endpoint does not report dependency status",
                    "Include dependency health in the response schema (database, external services, message brokers). " +
                    "Add a 'dependencies' or 'components' field in the health response, or create /health/dependencies endpoint"));
        }

        // Validate health endpoint response structure
        if (hasLiveness) {
            validateHealthResponse(openAPI, issues);
        }

        return issues;
    }

    @SuppressWarnings("unchecked")
    private boolean checkDependencyResponse(OpenAPI openAPI, PathItem pathItem) {
        Operation getOp = pathItem.getGet();
        if (getOp == null || getOp.getResponses() == null) {
            return false;
        }

        ApiResponse response200 = getOp.getResponses().get("200");
        if (response200 == null || response200.getContent() == null) {
            return false;
        }

        var jsonContent = response200.getContent().get("application/json");
        if (jsonContent == null || jsonContent.getSchema() == null) {
            return false;
        }

        Schema<?> schema = resolveSchema(openAPI, jsonContent.getSchema());
        if (schema != null && schema.getProperties() != null) {
            return schema.getProperties().keySet().stream()
                    .anyMatch(prop -> DEPENDENCY_INDICATORS.contains(((String) prop).toLowerCase()));
        }

        return false;
    }

    private void validateHealthResponse(OpenAPI openAPI, List<LintingIssue> issues) {
        for (Map.Entry<String, PathItem> entry : openAPI.getPaths().entrySet()) {
            String normalizedPath = normalizePath(entry.getKey());
            if (!LIVENESS_PATHS.contains(normalizedPath)) {
                continue;
            }

            Operation getOp = entry.getValue().getGet();
            if (getOp == null) {
                issues.add(LintingIssue.warning(getRuleId(), entry.getKey(),
                        "Health endpoint should be a GET operation",
                        "Health checks must be GET endpoints (no side effects)"));
                continue;
            }

            if (getOp.getResponses() == null) {
                continue;
            }

            // Should have both 200 (healthy) and 503 (unhealthy)
            if (!getOp.getResponses().containsKey("503")) {
                issues.add(LintingIssue.info(getRuleId(), entry.getKey(),
                        "Health endpoint missing 503 response for unhealthy state",
                        "Add 503 Service Unavailable response for when the service is degraded or down"));
            }
        }
    }

    private String normalizePath(String path) {
        // Remove version prefixes
        return path.replaceFirst("^/v\\d+", "");
    }

    private Schema<?> resolveSchema(OpenAPI openAPI, Schema<?> schema) {
        if (schema.get$ref() != null && openAPI.getComponents() != null
                && openAPI.getComponents().getSchemas() != null) {
            String ref = schema.get$ref();
            String schemaName = ref.substring(ref.lastIndexOf('/') + 1);
            return openAPI.getComponents().getSchemas().get(schemaName);
        }
        return schema;
    }
}
