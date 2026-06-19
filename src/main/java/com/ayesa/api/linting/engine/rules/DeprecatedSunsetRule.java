package com.ayesa.api.linting.engine.rules;

import com.ayesa.api.linting.engine.LintingRule;
import com.ayesa.api.linting.model.LintingIssue;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Validates that deprecated operations include sunset date and migration guidance.
 * Covers directive 1.8: Retirada controlada y deprecación obligatoria.
 */
@Component
public class DeprecatedSunsetRule implements LintingRule {

    private static final String SUNSET_EXTENSION = "x-sunset-date";

    @Override
    public String getRuleId() {
        return "deprecated-sunset";
    }

    @Override
    public String getDescription() {
        return "Validates that deprecated operations include x-sunset-date and migration guidance";
    }

    @Override
    public List<LintingIssue> apply(OpenAPI openAPI) {
        List<LintingIssue> issues = new ArrayList<>();

        if (openAPI.getPaths() == null) {
            return issues;
        }

        for (Map.Entry<String, PathItem> pathEntry : openAPI.getPaths().entrySet()) {
            String path = pathEntry.getKey();
            PathItem pathItem = pathEntry.getValue();

            checkOperation(issues, path, "GET", pathItem.getGet());
            checkOperation(issues, path, "POST", pathItem.getPost());
            checkOperation(issues, path, "PUT", pathItem.getPut());
            checkOperation(issues, path, "DELETE", pathItem.getDelete());
            checkOperation(issues, path, "PATCH", pathItem.getPatch());
        }

        return issues;
    }

    private void checkOperation(List<LintingIssue> issues, String path, String method, Operation operation) {
        if (operation == null || !Boolean.TRUE.equals(operation.getDeprecated())) {
            return;
        }

        String location = path + " [" + method + "]";

        // Check for x-sunset-date extension
        boolean hasSunsetDate = operation.getExtensions() != null
                && operation.getExtensions().containsKey(SUNSET_EXTENSION);

        if (!hasSunsetDate) {
            issues.add(LintingIssue.warning(getRuleId(), location,
                    "Deprecated operation has no sunset date",
                    "Add 'x-sunset-date' extension with the planned removal date (e.g., '2025-12-31')"));
        }

        // Check for migration description
        if (operation.getDescription() == null || operation.getDescription().isBlank()) {
            issues.add(LintingIssue.warning(getRuleId(), location,
                    "Deprecated operation has no description with migration guidance",
                    "Add a description explaining alternatives and migration path"));
        }
    }
}
