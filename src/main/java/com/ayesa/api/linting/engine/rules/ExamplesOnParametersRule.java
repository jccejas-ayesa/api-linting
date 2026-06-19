package com.ayesa.api.linting.engine.rules;

import com.ayesa.api.linting.engine.LintingRule;
import com.ayesa.api.linting.model.LintingIssue;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.headers.Header;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.responses.ApiResponse;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Validates that parameters and headers include examples.
 * Covers the "provide-examples-on-parameters" rule from Required Examples ruleset.
 */
@Component
public class ExamplesOnParametersRule implements LintingRule {

    @Override
    public String getRuleId() {
        return "provide-examples-on-parameters";
    }

    @Override
    public String getDescription() {
        return "Always include examples in parameters and headers";
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

            // Check path-level parameters
            if (pathItem.getParameters() != null) {
                checkParameters(issues, path, pathItem.getParameters());
            }

            checkOperation(issues, path, "get", pathItem.getGet());
            checkOperation(issues, path, "post", pathItem.getPost());
            checkOperation(issues, path, "put", pathItem.getPut());
            checkOperation(issues, path, "delete", pathItem.getDelete());
            checkOperation(issues, path, "patch", pathItem.getPatch());
        }

        return issues;
    }

    private void checkOperation(List<LintingIssue> issues, String path, String method, Operation operation) {
        if (operation == null) {
            return;
        }

        String location = path + "/" + method;

        // Check operation parameters
        if (operation.getParameters() != null) {
            checkParameters(issues, location, operation.getParameters());
        }

        // Check response headers
        if (operation.getResponses() != null) {
            for (Map.Entry<String, ApiResponse> respEntry : operation.getResponses().entrySet()) {
                ApiResponse response = respEntry.getValue();
                if (response.getHeaders() != null) {
                    checkHeaders(issues, location + "/responses/" + respEntry.getKey(), response.getHeaders());
                }
            }
        }
    }

    private void checkParameters(List<LintingIssue> issues, String location, List<Parameter> parameters) {
        for (Parameter param : parameters) {
            if (param == null) {
                continue;
            }

            boolean hasExample = param.getExample() != null
                    || (param.getExamples() != null && !param.getExamples().isEmpty());

            // Also check schema-level example
            boolean hasSchemaExample = param.getSchema() != null
                    && param.getSchema().getExample() != null;

            if (!hasExample && !hasSchemaExample) {
                issues.add(LintingIssue.info(getRuleId(),
                        location + "/parameters/" + param.getName(),
                        "Parameter '" + param.getName() + "' does not include an example",
                        "Add an example or examples property to the parameter definition"));
            }
        }
    }

    private void checkHeaders(List<LintingIssue> issues, String location, Map<String, Header> headers) {
        for (Map.Entry<String, Header> entry : headers.entrySet()) {
            Header header = entry.getValue();
            if (header == null) {
                continue;
            }

            boolean hasExample = header.getExample() != null
                    || (header.getExamples() != null && !header.getExamples().isEmpty());

            boolean hasSchemaExample = header.getSchema() != null
                    && header.getSchema().getExample() != null;

            if (!hasExample && !hasSchemaExample) {
                issues.add(LintingIssue.info(getRuleId(),
                        location + "/headers/" + entry.getKey(),
                        "Header '" + entry.getKey() + "' does not include an example",
                        "Add an example or examples property to the header definition"));
            }
        }
    }
}
