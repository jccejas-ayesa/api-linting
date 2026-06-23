package com.ayesa.api.linting.controller;

import com.ayesa.api.linting.engine.LintingEngine;
import com.ayesa.api.linting.engine.LintingRule;
import com.ayesa.api.linting.engine.RulesetCatalog;
import com.ayesa.api.linting.model.Ruleset;
import com.ayesa.api.linting.service.RulesetToggleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/rulesets")
@Tag(name = "Rulesets", description = "API governance rulesets management")
public class RulesetsController {

    private final LintingEngine lintingEngine;
    private final RulesetToggleService rulesetToggleService;

    public RulesetsController(LintingEngine lintingEngine, RulesetToggleService rulesetToggleService) {
        this.lintingEngine = lintingEngine;
        this.rulesetToggleService = rulesetToggleService;
    }

    @GetMapping
    @Operation(summary = "List all rulesets", description = "Returns all available rulesets with their rules")
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

    @GetMapping("/{rulesetId}")
    @Operation(summary = "Get ruleset by ID", description = "Returns a specific ruleset with all its rules")
    public ResponseEntity<Ruleset> getRuleset(@PathVariable String rulesetId) {
        RulesetCatalog.RulesetInfo info = RulesetCatalog.get(rulesetId);
        if (info == null) {
            return ResponseEntity.notFound().build();
        }

        Map<String, List<LintingRule>> rulesByRuleset = lintingEngine.getRules().stream()
                .flatMap(rule -> rule.getRulesetIds().stream()
                        .filter(Objects::nonNull)
                        .map(rs -> Map.entry(rs, rule)))
                .collect(Collectors.groupingBy(Map.Entry::getKey,
                        Collectors.mapping(Map.Entry::getValue, Collectors.toList())));

        List<LintingRule> rules = rulesByRuleset.getOrDefault(rulesetId, List.of());
        Ruleset ruleset = new Ruleset(
                info.id(),
                info.name(),
                info.description(),
                info.tags(),
                rules.size(),
                rules.stream()
                        .map(r -> new Ruleset.RuleInfo(r.getRuleId(), r.getDescription()))
                        .toList()
        );

        return ResponseEntity.ok(ruleset);
    }

    @PostMapping("/{rulesetId}/enable")
    @Operation(summary = "Enable a ruleset", description = "Enables a ruleset for linting evaluation")
    public ResponseEntity<String> enableRuleset(@PathVariable String rulesetId) {
        rulesetToggleService.toggleRuleset(rulesetId, true, "api");
        return ResponseEntity.ok("{\"message\":\"Ruleset " + rulesetId + " enabled\"}");
    }

    @PostMapping("/{rulesetId}/disable")
    @Operation(summary = "Disable a ruleset", description = "Disables a ruleset from linting evaluation")
    public ResponseEntity<String> disableRuleset(@PathVariable String rulesetId) {
        rulesetToggleService.toggleRuleset(rulesetId, false, "api");
        return ResponseEntity.ok("{\"message\":\"Ruleset " + rulesetId + " disabled\"}");
    }

    @PostMapping("/toggle/disable")
    @Operation(summary = "Disable ruleset (legacy)", description = "Legacy endpoint - use POST /{rulesetId}/disable instead")
    public ResponseEntity<String> disableRulesetLegacy(@Valid @RequestBody Map<String, String> body) {
        String rulesetId = body.get("rulesetId");
        if (rulesetId == null || rulesetId.isEmpty()) {
            return ResponseEntity.badRequest().body("{\"error\":\"rulesetId is required\"}");
        }
        rulesetToggleService.toggleRuleset(rulesetId, false, "api");
        return ResponseEntity.ok("{\"message\":\"Ruleset " + rulesetId + " disabled\"}");
    }

    @PostMapping("/toggle/enable")
    @Operation(summary = "Enable ruleset (legacy)", description = "Legacy endpoint - use POST /{rulesetId}/enable instead")
    public ResponseEntity<String> enableRulesetLegacy(@Valid @RequestBody Map<String, String> body) {
        String rulesetId = body.get("rulesetId");
        if (rulesetId == null || rulesetId.isEmpty()) {
            return ResponseEntity.badRequest().body("{\"error\":\"rulesetId is required\"}");
        }
        rulesetToggleService.toggleRuleset(rulesetId, true, "api");
        return ResponseEntity.ok("{\"message\":\"Ruleset " + rulesetId + " enabled\"}");
    }

    @GetMapping("/toggle/status")
    @Operation(summary = "Get toggle status", description = "Returns the enabled/disabled status of a ruleset")
    public ResponseEntity<Map<String, Object>> getToggleStatus(@RequestParam(required = false) String rulesetId) {
        if (rulesetId == null || rulesetId.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "rulesetId is required"));
        }
        var config = rulesetToggleService.getStatus(rulesetId);
        if (config == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(Map.of(
                "rulesetId", config.getRulesetId(),
                "enabled", config.isEnabled(),
                "lastModified", config.getLastModified(),
                "modifiedBy", config.getModifiedBy()
        ));
    }
}
