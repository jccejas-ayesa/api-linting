package com.ayesa.api.linting.engine.rules;

import com.ayesa.api.linting.engine.LintingRule;
import com.ayesa.api.linting.model.LintingIssue;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Validates presence of corporate governance extensions in the OAS.
 * Covers directives: 1.2 (x-business-capability), 1.4 (x-domain),
 * 1.5 (x-technical-owner), 1.7 (x-lifecycle-status).
 */
@Component
public class GovernanceExtensionsRule implements LintingRule {

    private static final Set<String> REQUIRED_EXTENSIONS = Set.of(
            "x-domain",
            "x-business-capability"
    );

    private static final Set<String> RECOMMENDED_EXTENSIONS = Set.of(
            "x-technical-owner",
            "x-lifecycle-status",
            "x-api-layer"
    );

    private static final Set<String> VALID_LIFECYCLE_VALUES = Set.of(
            "design", "development", "certification", "published", "operational", "deprecated", "retired"
    );

    private static final Set<String> VALID_LAYER_VALUES = Set.of(
            "system-api", "process-api", "experience-api"
    );

    @Override
    public String getRuleId() {
        return "x-extensions-governance";
    }

    @Override
    public String getDescription() {
        return "Validates presence and values of corporate governance extensions (x-domain, x-business-capability, x-api-layer, x-lifecycle-status)";
    }

    @Override
    public String getRulesetId() {
        return "api-catalog-information-best-practices";
    }

    @Override
    public List<LintingIssue> apply(OpenAPI openAPI) {
        List<LintingIssue> issues = new ArrayList<>();

        Info info = openAPI.getInfo();
        Map<String, Object> extensions = info != null ? info.getExtensions() : null;

        // Check required extensions
        for (String ext : REQUIRED_EXTENSIONS) {
            if (extensions == null || !extensions.containsKey(ext)) {
                issues.add(LintingIssue.warning(getRuleId(), "/info",
                        "Missing required governance extension: '" + ext + "'",
                        "Add '" + ext + "' to the info section for corporate governance"));
            }
        }

        // Check recommended extensions
        for (String ext : RECOMMENDED_EXTENSIONS) {
            if (extensions == null || !extensions.containsKey(ext)) {
                issues.add(LintingIssue.info(getRuleId(), "/info",
                        "Missing recommended governance extension: '" + ext + "'",
                        "Consider adding '" + ext + "' for better governance and traceability"));
            }
        }

        if (extensions == null) {
            return issues;
        }

        // Validate x-lifecycle-status values
        Object lifecycleStatus = extensions.get("x-lifecycle-status");
        if (lifecycleStatus != null && !VALID_LIFECYCLE_VALUES.contains(lifecycleStatus.toString().toLowerCase())) {
            issues.add(LintingIssue.warning(getRuleId(), "/info/x-lifecycle-status",
                    "Invalid lifecycle status: '" + lifecycleStatus + "'",
                    "Use one of: " + String.join(", ", VALID_LIFECYCLE_VALUES)));
        }

        // Validate x-api-layer values
        Object apiLayer = extensions.get("x-api-layer");
        if (apiLayer != null && !VALID_LAYER_VALUES.contains(apiLayer.toString().toLowerCase())) {
            issues.add(LintingIssue.warning(getRuleId(), "/info/x-api-layer",
                    "Invalid API layer: '" + apiLayer + "'",
                    "Use one of: " + String.join(", ", VALID_LAYER_VALUES)));
        }

        return issues;
    }
}
