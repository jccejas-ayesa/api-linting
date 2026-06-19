package com.ayesa.api.linting.model;

public record LintingIssue(
        String ruleId,
        Severity severity,
        String path,
        String message,
        String suggestion
) {

    public static LintingIssue error(String ruleId, String path, String message, String suggestion) {
        return new LintingIssue(ruleId, Severity.ERROR, path, message, suggestion);
    }

    public static LintingIssue warning(String ruleId, String path, String message, String suggestion) {
        return new LintingIssue(ruleId, Severity.WARNING, path, message, suggestion);
    }

    public static LintingIssue info(String ruleId, String path, String message, String suggestion) {
        return new LintingIssue(ruleId, Severity.INFO, path, message, suggestion);
    }
}
