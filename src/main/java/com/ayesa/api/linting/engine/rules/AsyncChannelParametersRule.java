package com.ayesa.api.linting.engine.rules;

import com.ayesa.api.linting.engine.LintingRule;
import com.ayesa.api.linting.model.LintingIssue;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.parameters.Parameter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class AsyncChannelParametersRule implements LintingRule {

    private static final Pattern PATH_PARAMETER_PATTERN = Pattern.compile("\\{([^}/]+)}");

    @Override
    public String getRuleId() {
        return "asyncapi-channel-parameters";
    }

    @Override
    public String getDescription() {
        return "Validates that channel path parameters are defined in the path item parameters list";
    }

    @Override
    public String getRulesetId() {
        return "asyncapi-best-practices";
    }

    @Override
    public List<LintingIssue> apply(OpenAPI openAPI) {
        List<LintingIssue> issues = new ArrayList<>();
        if (openAPI.getPaths() == null) {
            return issues;
        }

        for (String path : openAPI.getPaths().keySet()) {
            PathItem pathItem = openAPI.getPaths().get(path);
            if (path == null || pathItem == null) {
                continue;
            }

            Set<String> definedParameters = extractDefinedParameterNames(openAPI, pathItem);
            Matcher matcher = PATH_PARAMETER_PATTERN.matcher(path);
            while (matcher.find()) {
                String parameterName = matcher.group(1);
                if (!definedParameters.contains(parameterName)) {
                    issues.add(LintingIssue.warning(
                            getRuleId(),
                            path,
                            "Channel path parameter '" + parameterName + "' is not defined in the path item parameters list",
                            "Add a path parameter named '" + parameterName + "' to the path item parameters"
                    ));
                }
            }
        }

        return issues;
    }

    private Set<String> extractDefinedParameterNames(OpenAPI openAPI, PathItem pathItem) {
        Set<String> parameterNames = new HashSet<>();
        if (pathItem.getParameters() == null) {
            return parameterNames;
        }

        for (Parameter parameter : pathItem.getParameters()) {
            Parameter resolved = resolveParameter(openAPI, parameter);
            if (resolved != null && resolved.getName() != null && !resolved.getName().isBlank()) {
                parameterNames.add(resolved.getName());
            }
        }

        return parameterNames;
    }

    private Parameter resolveParameter(OpenAPI openAPI, Parameter parameter) {
        if (parameter == null) {
            return null;
        }

        if (parameter.get$ref() != null && openAPI.getComponents() != null
                && openAPI.getComponents().getParameters() != null) {
            String ref = parameter.get$ref();
            String parameterName = ref.substring(ref.lastIndexOf('/') + 1);
            Parameter resolved = openAPI.getComponents().getParameters().get(parameterName);
            if (resolved != null) {
                return resolved;
            }
        }

        return parameter;
    }
}
