package com.ayesa.api.linting.controller;

import com.ayesa.api.linting.engine.LintingEngine;
import com.ayesa.api.linting.engine.LintingRule;
import com.ayesa.api.linting.model.LintingRequest;
import com.ayesa.api.linting.model.LintingResult;
import com.ayesa.api.linting.service.LintingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/lint")
@Tag(name = "Linting", description = "OpenAPI Specification linting operations")
public class LintingController {

    private final LintingService lintingService;
    private final LintingEngine lintingEngine;

    public LintingController(LintingService lintingService, LintingEngine lintingEngine) {
        this.lintingService = lintingService;
        this.lintingEngine = lintingEngine;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Analyze an OpenAPI specification", description = "Parses and lints an OpenAPI specification, returning a detailed report of issues found")
    public ResponseEntity<LintingResult> analyze(@Valid @RequestBody LintingRequest request) {
        LintingResult result = lintingService.analyze(request.content());
        return ResponseEntity.ok(result);
    }

    @PostMapping(value = "/raw", consumes = {MediaType.TEXT_PLAIN_VALUE, "application/yaml", "application/x-yaml"},
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Analyze a raw OAS document", description = "Accepts raw YAML or JSON content directly and returns the linting report")
    public ResponseEntity<LintingResult> analyzeRaw(@RequestBody String content) {
        LintingResult result = lintingService.analyze(content);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/rules")
    @Operation(summary = "List available linting rules", description = "Returns all configured linting rules with their IDs and descriptions")
    public ResponseEntity<List<Map<String, String>>> listRules() {
        List<Map<String, String>> rules = lintingEngine.getRules().stream()
                .map(rule -> Map.of(
                        "ruleId", rule.getRuleId(),
                        "description", rule.getDescription()
                ))
                .toList();
        return ResponseEntity.ok(rules);
    }
}
