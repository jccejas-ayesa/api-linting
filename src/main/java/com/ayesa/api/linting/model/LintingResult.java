package com.ayesa.api.linting.model;

import java.time.Instant;
import java.util.List;
import java.util.Map;

public record LintingResult(
        boolean valid,
        int totalIssues,
        Map<Severity, Long> issuesBySeverity,
        List<LintingIssue> issues,
        Instant analyzedAt
) {

    public static LintingResult of(List<LintingIssue> issues) {
        Map<Severity, Long> bySeverity = issues.stream()
                .collect(java.util.stream.Collectors.groupingBy(
                        LintingIssue::severity,
                        java.util.stream.Collectors.counting()
                ));

        boolean valid = bySeverity.getOrDefault(Severity.ERROR, 0L) == 0;

        return new LintingResult(valid, issues.size(), bySeverity, issues, Instant.now());
    }
}
