package com.ayesa.api.linting.engine.rules;

import com.ayesa.api.linting.engine.LintingRule;
import com.ayesa.api.linting.model.LintingIssue;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class GrpcRpcNamesUpperCamelCaseRule extends GrpcRuleSupport implements LintingRule {

    @Override
    public String getRuleId() {
        return "rpc-names-upper-camel-case";
    }

    @Override
    public String getDescription() {
        return "Validates that gRPC-like operationIds use UpperCamelCase";
    }

    @Override
    public String getRulesetId() {
        return RULESET_ID;
    }

    @Override
    public List<LintingIssue> apply(OpenAPI openAPI) {
        List<LintingIssue> issues = new ArrayList<>();
        if (!isGrpcSpec(openAPI)) {
            return issues;
        }

        forEachOperation(openAPI, (path, method, operation) -> {
            if (operation.getOperationId() != null && !operation.getOperationId().isBlank()
                    && !UPPER_CAMEL_CASE.matcher(operation.getOperationId()).matches()) {
                issues.add(LintingIssue.error(
                        getRuleId(),
                        path + " [" + method + "]",
                        "operationId '" + operation.getOperationId() + "' is not UpperCamelCase",
                        "Rename the operationId to UpperCamelCase, for example 'GetOrder'"
                ));
            }
        });
        return issues;
    }
}
