package com.ayesa.api.linting.engine.rules;

import com.ayesa.api.linting.engine.LintingRule;
import com.ayesa.api.linting.model.LintingIssue;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Validates API versioning in info.version and path prefixes.
 * Covers directive 2.11: Gestión obligatoria de versionado.
 */
@Component
public class VersioningRequiredRule implements LintingRule {

    private static final Pattern SEMVER = Pattern.compile("^\\d+\\.\\d+\\.\\d+(-[a-zA-Z0-9.]+)?(\\+[a-zA-Z0-9.]+)?$");
    private static final Pattern PATH_VERSION = Pattern.compile("^/v\\d+/.*");

    @Override
    public String getRuleId() {
        return "versioning-required";
    }

    @Override
    public String getDescription() {
        return "Validates that info.version uses semantic versioning and paths include version prefix";
    }

    @Override
    public List<LintingIssue> apply(OpenAPI openAPI) {
        List<LintingIssue> issues = new ArrayList<>();

        Info info = openAPI.getInfo();
        if (info == null || info.getVersion() == null || info.getVersion().isBlank()) {
            issues.add(LintingIssue.error(getRuleId(), "/info/version",
                    "API version not specified",
                    "Add a version following semantic versioning (e.g., 1.0.0)"));
            return issues;
        }

        if (!SEMVER.matcher(info.getVersion()).matches()) {
            issues.add(LintingIssue.warning(getRuleId(), "/info/version",
                    "Version '" + info.getVersion() + "' does not follow semantic versioning",
                    "Use semantic versioning format: MAJOR.MINOR.PATCH (e.g., 1.0.0)"));
        }

        // Check path version prefixes
        if (openAPI.getPaths() != null && !openAPI.getPaths().isEmpty()) {
            boolean hasVersionedPaths = openAPI.getPaths().keySet().stream()
                    .anyMatch(path -> PATH_VERSION.matcher(path).matches());

            if (!hasVersionedPaths) {
                issues.add(LintingIssue.info(getRuleId(), "/paths",
                        "No versioned path prefixes found (e.g., /v1/...)",
                        "Consider adding version prefixes to paths for URL-based versioning"));
            }
        }

        return issues;
    }
}
