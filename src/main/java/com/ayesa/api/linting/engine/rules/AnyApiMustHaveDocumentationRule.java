package com.ayesa.api.linting.engine.rules;

import com.ayesa.api.linting.engine.LintingRule;
import com.ayesa.api.linting.model.LintingIssue;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class AnyApiMustHaveDocumentationRule implements LintingRule {

    @Override
    public String getRuleId() {
        return "api-must-have-documentation";
    }

    @Override
    public String getDescription() {
        return "The API should expose external or extension-based documentation";
    }

    @Override
    public String getRulesetId() {
        return "anypoint-best-practices";
    }

    @Override
    public List<LintingIssue> apply(OpenAPI openAPI) {
        List<LintingIssue> issues = new ArrayList<>();
        if (openAPI == null) {
            return issues;
        }

        boolean hasExternalDocs = openAPI.getExternalDocs() != null
                && openAPI.getExternalDocs().getUrl() != null
                && !openAPI.getExternalDocs().getUrl().isBlank();
        boolean hasExtensionDocs = hasDocumentationExtension(openAPI.getExtensions());
        if (!hasExternalDocs && !hasExtensionDocs) {
            issues.add(LintingIssue.warning(
                    getRuleId(),
                    "/",
                    "API documentation is missing",
                    "Define externalDocs or provide a populated x-documentation extension"
            ));
        }

        return issues;
    }

    private boolean hasDocumentationExtension(Map<String, Object> extensions) {
        if (extensions == null || !extensions.containsKey("x-documentation")) {
            return false;
        }
        Object value = extensions.get("x-documentation");
        if (value == null) {
            return false;
        }
        if (value instanceof String text) {
            return !text.isBlank();
        }
        if (value instanceof Map<?, ?> map) {
            return !map.isEmpty();
        }
        if (value instanceof List<?> list) {
            return !list.isEmpty();
        }
        return true;
    }
}
