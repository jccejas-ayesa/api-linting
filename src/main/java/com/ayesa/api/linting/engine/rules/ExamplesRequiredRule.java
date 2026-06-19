package com.ayesa.api.linting.engine.rules;

import com.ayesa.api.linting.engine.LintingRule;
import com.ayesa.api.linting.model.LintingIssue;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.parameters.RequestBody;
import io.swagger.v3.oas.models.responses.ApiResponse;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Validates that schemas, parameters, and responses include examples.
 * Covers directive 9.3: Ejemplos de uso obligatorios.
 */
@Component
public class ExamplesRequiredRule implements LintingRule {

    @Override
    public String getRuleId() {
        return "provide-examples-on-payloads";
    }

    @Override
    public String getDescription() {
        return "Request and response payloads must include examples";
    }

    @Override
    public String getRulesetId() {
        return "required-examples";
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
        if (operation == null) {
            return;
        }

        String location = path + " [" + method + "]";

        // Check request body examples
        RequestBody requestBody = operation.getRequestBody();
        if (requestBody != null && requestBody.getContent() != null) {
            checkContentExamples(issues, location + "/requestBody", requestBody.getContent());
        }

        // Check response examples
        if (operation.getResponses() != null) {
            for (Map.Entry<String, ApiResponse> respEntry : operation.getResponses().entrySet()) {
                String code = respEntry.getKey();
                ApiResponse response = respEntry.getValue();
                if (response.getContent() != null) {
                    checkContentExamples(issues, location + "/responses/" + code, response.getContent());
                }
            }
        }
    }

    private void checkContentExamples(List<LintingIssue> issues, String location, Content content) {
        for (Map.Entry<String, MediaType> entry : content.entrySet()) {
            MediaType mediaType = entry.getValue();

            boolean hasExample = mediaType.getExample() != null
                    || (mediaType.getExamples() != null && !mediaType.getExamples().isEmpty());

            boolean hasSchemaExample = mediaType.getSchema() != null
                    && mediaType.getSchema().getExample() != null;

            if (!hasExample && !hasSchemaExample) {
                issues.add(LintingIssue.info(getRuleId(), location,
                        "No examples provided for content type '" + entry.getKey() + "'",
                        "Add example or examples to help consumers understand the expected format"));
            }
        }
    }
}
