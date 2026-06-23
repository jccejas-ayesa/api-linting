package com.ayesa.api.linting.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.Instant;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record HealthStatus(
    String status,
    String serviceName,
    String version,
    Instant timestamp,
    int port,
    long uptime,
    DatabaseStatus database,
    ComponentStatus[] components
) {
    public record DatabaseStatus(
        String status,
        String type,
        String url
    ) {}

    public record ComponentStatus(
        String name,
        String status,
        String description
    ) {}

    public static HealthStatus UP(String version, int port, long uptime) {
        return new HealthStatus(
            "UP",
            "api-linting",
            version,
            Instant.now(),
            port,
            uptime,
            new DatabaseStatus("UP", "H2", "jdbc:h2:mem:testdb"),
            new ComponentStatus[]{
                new ComponentStatus("LintingEngine", "UP", "139 rules loaded"),
                new ComponentStatus("Database", "UP", "H2 in-memory"),
                new ComponentStatus("RulesetToggle", "UP", "3-tier cache enabled")
            }
        );
    }

    public static HealthStatus DOWN(String reason) {
        return new HealthStatus(
            "DOWN",
            "api-linting",
            "1.0.0",
            Instant.now(),
            8080,
            0,
            new DatabaseStatus("DOWN", "H2", "jdbc:h2:mem:testdb"),
            new ComponentStatus[]{
                new ComponentStatus("LintingEngine", "DOWN", reason)
            }
        );
    }
}
