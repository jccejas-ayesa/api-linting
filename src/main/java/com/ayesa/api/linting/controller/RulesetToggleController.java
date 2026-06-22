package com.ayesa.api.linting.controller;

import com.ayesa.api.linting.model.RulesetConfiguration;
import com.ayesa.api.linting.service.RulesetToggleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/rulesets-toggle")
public class RulesetToggleController {

    private final RulesetToggleService toggleService;

    public RulesetToggleController(RulesetToggleService toggleService) {
        this.toggleService = toggleService;
    }

    @PostMapping("/{id}/enable")
    public ResponseEntity<Map<String, Object>> enable(@PathVariable String id) {
        toggleService.toggleRuleset(id, true, "api");
        return ResponseEntity.ok(Map.of("rulesetId", id, "enabled", true));
    }

    @PostMapping("/{id}/disable")
    public ResponseEntity<Map<String, Object>> disable(@PathVariable String id) {
        toggleService.toggleRuleset(id, false, "api");
        return ResponseEntity.ok(Map.of("rulesetId", id, "enabled", false));
    }

    @GetMapping("/{id}/status")
    public ResponseEntity<?> status(@PathVariable String id) {
        RulesetConfiguration config = toggleService.getStatus(id);
        if (config != null) {
            return ResponseEntity.ok(config);
        }

        return ResponseEntity.ok(Map.of(
                "rulesetId", id,
                "enabled", toggleService.isRulesetEnabled(id)
        ));
    }
}
