package com.ayesa.api.linting.controller;

import com.ayesa.api.linting.engine.LintingEngine;
import com.ayesa.api.linting.engine.LintingRule;
import com.ayesa.api.linting.engine.RulesetCatalog;
import com.ayesa.api.linting.model.LintingRequest;
import com.ayesa.api.linting.model.LintingResult;
import com.ayesa.api.linting.model.Ruleset;
import com.ayesa.api.linting.service.LintingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

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
    @Operation(summary = "Analyze an OpenAPI specification",
            description = "Parses and lints an OpenAPI specification. Optionally filter by rulesets.")
    public ResponseEntity<LintingResult> analyze(
            @Valid @RequestBody LintingRequest request,
            @Parameter(description = "Comma-separated ruleset IDs to apply (empty = all)")
            @RequestParam(required = false) List<String> rulesets) {
        LintingResult result = lintingService.analyze(request.content(), rulesets);
        return ResponseEntity.ok(result);
    }

    @PostMapping(value = "/raw", consumes = {MediaType.TEXT_PLAIN_VALUE, "application/yaml", "application/x-yaml"},
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Analyze a raw OAS document",
            description = "Accepts raw YAML or JSON content directly. Optionally filter by rulesets.")
    public ResponseEntity<LintingResult> analyzeRaw(
            @RequestBody String content,
            @Parameter(description = "Comma-separated ruleset IDs to apply (empty = all)")
            @RequestParam(required = false) List<String> rulesets) {
        LintingResult result = lintingService.analyze(content, rulesets);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/rulesets")
    @Operation(summary = "List available rulesets",
            description = "Returns all rulesets with their rules, descriptions, and tags")
    public ResponseEntity<List<Ruleset>> listRulesets() {
        Map<String, List<LintingRule>> rulesByRuleset = lintingEngine.getRules().stream()
                .flatMap(rule -> rule.getRulesetIds().stream()
                        .filter(Objects::nonNull)
                        .map(rulesetId -> Map.entry(rulesetId, rule)))
                .collect(Collectors.groupingBy(Map.Entry::getKey,
                        Collectors.mapping(Map.Entry::getValue, Collectors.toList())));

        List<Ruleset> rulesets = RulesetCatalog.RULESETS.values().stream()
                .map(info -> {
                    List<LintingRule> rules = rulesByRuleset.getOrDefault(info.id(), List.of());
                    return new Ruleset(
                            info.id(),
                            info.name(),
                            info.description(),
                            info.tags(),
                            rules.size(),
                            rules.stream()
                                    .map(r -> new Ruleset.RuleInfo(r.getRuleId(), r.getDescription()))
                                    .toList()
                    );
                })
                .filter(rs -> rs.ruleCount() > 0)
                .toList();

        return ResponseEntity.ok(rulesets);
    }

}
