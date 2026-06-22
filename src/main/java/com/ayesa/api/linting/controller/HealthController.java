package com.ayesa.api.linting.controller;

import com.ayesa.api.linting.model.HealthStatus;
import com.ayesa.api.linting.service.HealthCheckService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/health")
@Tag(name = "Health", description = "Service health and readiness checks")
public class HealthController {

    private final HealthCheckService healthCheckService;

    public HealthController(HealthCheckService healthCheckService) {
        this.healthCheckService = healthCheckService;
    }

    @GetMapping("/status")
    @Operation(summary = "Get service status", description = "Returns overall service health status")
    public ResponseEntity<HealthStatus> getStatus() {
        HealthStatus status = healthCheckService.getHealth();
        HttpStatus httpStatus = "UP".equals(status.status()) ? HttpStatus.OK : HttpStatus.SERVICE_UNAVAILABLE;
        return new ResponseEntity<>(status, httpStatus);
    }

    @GetMapping("/ready")
    @Operation(summary = "Check readiness", description = "Returns 200 if service is ready to accept requests")
    public ResponseEntity<String> getReady() {
        if (healthCheckService.isReady()) {
            return ResponseEntity.ok("{\"ready\":true}");
        }
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("{\"ready\":false}");
    }

    @GetMapping("/live")
    @Operation(summary = "Check liveness", description = "Returns 200 if service is alive")
    public ResponseEntity<String> getLive() {
        if (healthCheckService.isAlive()) {
            return ResponseEntity.ok("{\"alive\":true}");
        }
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("{\"alive\":false}");
    }

    @GetMapping("/dependencies")
    @Operation(summary = "Check dependencies", description = "Checks all service dependencies")
    public ResponseEntity<HealthStatus> checkDependencies() {
        HealthStatus status = healthCheckService.checkDependencies();
        HttpStatus httpStatus = "UP".equals(status.status()) ? HttpStatus.OK : HttpStatus.SERVICE_UNAVAILABLE;
        return new ResponseEntity<>(status, httpStatus);
    }
}
