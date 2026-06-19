package com.ayesa.api.linting.engine.rules;

import com.ayesa.api.linting.engine.LintingRule;
import com.ayesa.api.linting.model.LintingIssue;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class GrpcRpcRequestNamesRule extends GrpcRuleSupport implements LintingRule {

    @Override
    public String getRuleId() {
        return "rpc-request-names";
    }

    @Override
    public String getDescription() {
        return "Validates that Get RPC request schemas end with Request";
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
            if (operation.getOperationId() == null || !operation.getOperationId().startsWith("Get")) {
                return;
            }
            String requestSchemaName = findRequestSchemaName(operation);
            if (requestSchemaName == null || !requestSchemaName.endsWith("Request")) {
                issues.add(LintingIssue.error(
                        getRuleId(),
                        path + " [" + method + "]",
                        "Get operation '" + operation.getOperationId() + "' should use a request schema ending with 'Request'",
                        "Reference a named request schema such as 'GetOrderRequest' in the request body"
                ));
            }
        });
        return issues;
    }
}
