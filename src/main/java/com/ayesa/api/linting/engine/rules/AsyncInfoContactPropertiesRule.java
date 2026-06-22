package com.ayesa.api.linting.engine.rules;

import com.ayesa.api.linting.engine.LintingRule;
import com.ayesa.api.linting.model.LintingIssue;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AsyncInfoContactPropertiesRule implements LintingRule {

    @Override
    public String getRuleId() {
        return "asyncapi-info-contact-properties";
    }

    @Override
    public String getDescription() {
        return "Validates that AsyncAPI contact information includes name, email, and url";
    }

    @Override
    public String getRulesetId() {
        return "asyncapi-best-practices";
    }

    @Override
    public List<LintingIssue> apply(OpenAPI openAPI) {
        List<LintingIssue> issues = new ArrayList<>();
        if (!isAsyncApiSpec(openAPI)) {
            return issues;
        }

        Contact contact = openAPI.getInfo() == null ? null : openAPI.getInfo().getContact();
        List<String> missingFields = new ArrayList<>();
        if (contact == null || contact.getName() == null || contact.getName().isBlank()) {
            missingFields.add("name");
        }
        if (contact == null || contact.getEmail() == null || contact.getEmail().isBlank()) {
            missingFields.add("email");
        }
        if (contact == null || contact.getUrl() == null || contact.getUrl().isBlank()) {
            missingFields.add("url");
        }

        if (!missingFields.isEmpty()) {
            issues.add(LintingIssue.warning(
                    getRuleId(),
                    "/info/contact",
                    "Contact is missing required properties: " + String.join(", ", missingFields),
                    "Populate the contact name, email, and url fields"
            ));
        }

        return issues;
    }

    private boolean isAsyncApiSpec(OpenAPI openAPI) {
        if (openAPI.getExtensions() != null && openAPI.getExtensions().containsKey("x-asyncapi")) return true;
        if (openAPI.getInfo() != null) {
            String title = openAPI.getInfo().getTitle();
            String desc = openAPI.getInfo().getDescription();
            String combined = ((title != null ? title : "") + " " + (desc != null ? desc : "")).toLowerCase();
            return combined.contains("asyncapi") || combined.contains("event-driven")
                || combined.contains("kafka") || combined.contains("rabbitmq")
                || combined.contains("amqp") || combined.contains("mqtt");
        }
        return false;
    }
}
