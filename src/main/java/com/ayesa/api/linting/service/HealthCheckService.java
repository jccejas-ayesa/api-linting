package com.ayesa.api.linting.service;

import com.ayesa.api.linting.model.HealthStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class HealthCheckService {

    @Value("${server.port:8080}")
    private int port;

    @Value("${spring.application.version:1.0.0}")
    private String version;

    private final long startTime = System.currentTimeMillis();

    public HealthStatus getHealth() {
        long uptime = System.currentTimeMillis() - startTime;
        return HealthStatus.UP(version, port, uptime);
    }

    public HealthStatus checkDependencies() {
        try {
            long uptime = System.currentTimeMillis() - startTime;
            return HealthStatus.UP(version, port, uptime);
        } catch (Exception e) {
            return HealthStatus.DOWN("Dependency check failed: " + e.getMessage());
        }
    }

    public boolean isReady() {
        return true;
    }

    public boolean isAlive() {
        return true;
    }
}
