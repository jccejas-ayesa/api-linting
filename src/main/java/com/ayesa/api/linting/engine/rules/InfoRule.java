package com.ayesa.api.linting.engine.rules;

import com.ayesa.api.linting.engine.LintingRule;
import com.ayesa.api.linting.model.LintingIssue;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Validates the info section of the OpenAPI spec.
 * Ensures title, description, version, and contact are present.
 */
@Component
public class InfoRule implements LintingRule {

    @Override
    public String getRuleId() {
        return "info-completeness";
    }

    @Override
    public String getDescription() {
        return "Validates that the info section is complete with title, description, version, and contact";
    }

    @Override
    public String getRulesetId() {
        return "openapi-best-practices";
    }

    @Override
    public List<LintingIssue> apply(OpenAPI openAPI) {
        List<LintingIssue> issues = new ArrayList<>();
        Info info = openAPI.getInfo();

        if (info == null) {
            issues.add(LintingIssue.error(getRuleId(), "/info", "Missing info section", "Add an info section with title, description, and version"));
            return issues;
        }

        if (info.getTitle() == null || info.getTitle().isBlank()) {
            issues.add(LintingIssue.error(getRuleId(), "/info/title", "Missing API title", "Add a descriptive title"));
        }

        if (info.getDescription() == null || info.getDescription().isBlank()) {
            issues.add(LintingIssue.warning(getRuleId(), "/info/description", "Missing API description", "Add a description explaining the API purpose"));
        }

        if (info.getVersion() == null || info.getVersion().isBlank()) {
            issues.add(LintingIssue.error(getRuleId(), "/info/version", "Missing API version", "Add a version (e.g., 1.0.0)"));
        }

        Contact contact = info.getContact();
        if (contact == null) {
            issues.add(LintingIssue.warning(getRuleId(), "/info/contact", "Missing contact information", "Add contact details for API support"));
        } else {
            if (contact.getEmail() == null || contact.getEmail().isBlank()) {
                issues.add(LintingIssue.info(getRuleId(), "/info/contact/email", "Missing contact email", "Add a contact email"));
            }
        }

        return issues;
    }
}
