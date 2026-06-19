package com.ayesa.api.linting.engine.rules;

import com.ayesa.api.linting.engine.LintingRule;
import com.ayesa.api.linting.model.LintingIssue;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.responses.ApiResponse;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Validates that rate limiting is documented in the API specification.
 * Covers API-BP-COMMON-020: RateLimits.
 */
@Component
public class RateLimitDocumentedRule implements LintingRule {

    private static final Set<String> RATE_LIMIT_HEADERS = Set.of(
            "x-ratelimit-limit", "x-ratelimit-remaining", "x-ratelimit-reset",
            "x-rate-limit-limit", "x-rate-limit-remaining", "x-rate-limit-reset",
            "ratelimit-limit", "ratelimit-remaining", "ratelimit-reset"
    );

    private static final String RATE_LIMIT_EXTENSION = "x-rate-limit";

    @Override
    public String getRuleId() {
        return "rate-limit-documented";
    }

    @Override
    public String getDescription() {
        return "Validates that rate limiting configuration is documented (headers or extension) (API-BP-COMMON-020)";
    }

    @Override
    public List<LintingIssue> apply(OpenAPI openAPI) {
        List<LintingIssue> issues = new ArrayList<>();

        // Check for x-rate-limit extension at API level
        boolean hasGlobalRateLimit = openAPI.getExtensions() != null
                && openAPI.getExtensions().containsKey(RATE_LIMIT_EXTENSION);

        if (openAPI.getInfo() != null && openAPI.getInfo().getExtensions() != null) {
            hasGlobalRateLimit = hasGlobalRateLimit
                    || openAPI.getInfo().getExtensions().containsKey(RATE_LIMIT_EXTENSION);
        }

        // Check for 429 response defined
        boolean has429Response = false;
        boolean hasRateLimitHeaders = false;

        if (openAPI.getPaths() != null) {
            for (Map.Entry<String, PathItem> entry : openAPI.getPaths().entrySet()) {
                PathItem pathItem = entry.getValue();

                has429Response = has429Response || checkFor429(pathItem.getGet());
                has429Response = has429Response || checkFor429(pathItem.getPost());
                has429Response = has429Response || checkFor429(pathItem.getPut());
                has429Response = has429Response || checkFor429(pathItem.getDelete());
                has429Response = has429Response || checkFor429(pathItem.getPatch());

                hasRateLimitHeaders = hasRateLimitHeaders || checkForRateLimitHeaders(pathItem.getGet());
                hasRateLimitHeaders = hasRateLimitHeaders || checkForRateLimitHeaders(pathItem.getPost());
            }
        }

        if (!hasGlobalRateLimit && !has429Response && !hasRateLimitHeaders) {
            issues.add(LintingIssue.warning(getRuleId(), "/",
                    "No rate limiting documentation found in the API specification",
                    "Document rate limits using: (1) 429 response codes, (2) X-RateLimit-* response headers, " +
                    "or (3) x-rate-limit extension. Define burst and hard limits per API-BP-COMMON-020"));
        }

        if (!has429Response && (hasGlobalRateLimit || hasRateLimitHeaders)) {
            issues.add(LintingIssue.info(getRuleId(), "/paths",
                    "Rate limiting is configured but 429 (Too Many Requests) response is not documented",
                    "Add a 429 response to operations so consumers know the behavior when limits are exceeded"));
        }

        return issues;
    }

    private boolean checkFor429(Operation operation) {
        if (operation == null || operation.getResponses() == null) {
            return false;
        }
        return operation.getResponses().containsKey("429");
    }

    private boolean checkForRateLimitHeaders(Operation operation) {
        if (operation == null || operation.getResponses() == null) {
            return false;
        }

        for (ApiResponse response : operation.getResponses().values()) {
            if (response.getHeaders() != null) {
                boolean found = response.getHeaders().keySet().stream()
                        .anyMatch(h -> RATE_LIMIT_HEADERS.contains(h.toLowerCase()));
                if (found) return true;
            }
        }
        return false;
    }
}
