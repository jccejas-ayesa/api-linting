package com.ayesa.api.linting.engine.rules;

import com.ayesa.api.linting.engine.LintingRule;
import com.ayesa.api.linting.model.LintingIssue;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.parameters.RequestBody;
import io.swagger.v3.oas.models.responses.ApiResponse;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class AnyPreferredMediaTypeRepresentationsRule implements LintingRule {

    private static final Set<String> PREFERRED_MEDIA_TYPES = Set.of("application/json", "application/xml");

    @Override
    public String getRuleId() {
        return "preferred-media-type-representations";
    }

    @Override
    public String getDescription() {
        return "Request and response bodies should prefer JSON or XML media types";
    }

    @Override
    public String getRulesetId() {
        return "anypoint-best-practices";
    }

    @Override
    public List<LintingIssue> apply(OpenAPI openAPI) {
        List<LintingIssue> issues = new ArrayList<>();
        if (openAPI == null || openAPI.getPaths() == null) {
            return issues;
        }

        for (Map.Entry<String, PathItem> pathEntry : openAPI.getPaths().entrySet()) {
            PathItem pathItem = pathEntry.getValue();
            if (pathItem == null || pathItem.readOperationsMap() == null) {
                continue;
            }
            for (Map.Entry<PathItem.HttpMethod, Operation> operationEntry : pathItem.readOperationsMap().entrySet()) {
                Operation operation = operationEntry.getValue();
                if (operation == null) {
                    continue;
                }
                String base = pathEntry.getKey() + " [" + operationEntry.getKey() + "]";
                inspectRequestBody(issues, base, operation.getRequestBody());
                inspectResponses(issues, base, operation.getResponses());
            }
        }

        return issues;
    }

    private void inspectRequestBody(List<LintingIssue> issues, String location, RequestBody requestBody) {
        if (requestBody == null || requestBody.getContent() == null) {
            return;
        }
        for (String mediaType : requestBody.getContent().keySet()) {
            if (!PREFERRED_MEDIA_TYPES.contains(mediaType)) {
                issues.add(LintingIssue.warning(
                        getRuleId(),
                        location + "/requestBody/content/" + mediaType,
                        "Media type '" + mediaType + "' is not a preferred representation",
                        "Prefer application/json or application/xml for request payloads"
                ));
            }
        }
    }

    private void inspectResponses(List<LintingIssue> issues, String location, Map<String, ApiResponse> responses) {
        if (responses == null) {
            return;
        }
        for (Map.Entry<String, ApiResponse> responseEntry : responses.entrySet()) {
            ApiResponse response = responseEntry.getValue();
            if (response == null || response.getContent() == null) {
                continue;
            }
            for (String mediaType : response.getContent().keySet()) {
                if (!PREFERRED_MEDIA_TYPES.contains(mediaType)) {
                    issues.add(LintingIssue.warning(
                            getRuleId(),
                            location + "/responses/" + responseEntry.getKey() + "/content/" + mediaType,
                            "Media type '" + mediaType + "' is not a preferred representation",
                            "Prefer application/json or application/xml for response payloads"
                    ));
                }
            }
        }
    }
}
