package com.ayesa.api.linting.engine;

import java.util.List;
import java.util.Map;

/**
 * Registry of all supported rulesets with their metadata.
 */
public final class RulesetCatalog {

    private RulesetCatalog() {
    }

    public record RulesetInfo(String id, String name, String description, List<String> tags) {
    }

    public static final Map<String, RulesetInfo> RULESETS = Map.ofEntries(
            entry("openapi-best-practices",
                    "OpenAPI Best Practices",
                    "Validates OpenAPI specification structure, naming conventions, versioning, and REST design patterns",
                    List.of("openapi", "rest", "design")),
            entry("api-documentation-best-practices",
                    "API Documentation Best Practices",
                    "Validates that the API has comprehensive documentation including descriptions, examples, and FAQ",
                    List.of("documentation")),
            entry("authentication-security-best-practices",
                    "Authentication Security Best Practices",
                    "Validates authentication and authorization mechanisms (OAuth2, OpenID Connect, scopes)",
                    List.of("security", "authentication")),
            entry("https-enforcement",
                    "HTTPS Enforcement",
                    "Validates that all server URLs use HTTPS for transport encryption",
                    List.of("security", "https")),
            entry("owasp-api-security-top-10",
                    "OWASP API Security Top 10 2019 Checklist",
                    "Validates API security controls aligned with OWASP API Security Top 10",
                    List.of("security", "owasp")),
            entry("required-examples",
                    "Required Examples",
                    "Validates that request and response schemas include examples for consumer guidance",
                    List.of("documentation", "examples")),
            entry("api-catalog-information-best-practices",
                    "API Catalog Information Best Practices",
                    "Validates corporate governance extensions, domain classification, and lifecycle metadata",
                    List.of("governance", "catalog")),
            entry("anypoint-best-practices",
                    "Anypoint Best Practices",
                    "Validates best practices specific to MuleSoft Anypoint platform APIs",
                    List.of("anypoint", "mulesoft")),
            entry("mule-api-management-best-practices",
                    "Mule API Management Best Practices",
                    "Validates API management patterns including rate limiting, health checks, and resilience",
                    List.of("management", "operations")),
            entry("agent-network-best-practices",
                    "Agent Network Best Practices",
                    "Validates agent network specific patterns and conventions",
                    List.of("agent-network")),
            entry("datagraph-best-practices",
                    "Datagraph Best Practices",
                    "Validates Datagraph specific patterns and conventions",
                    List.of("datagraph")),
            entry("grpc-best-practices",
                    "gRPC Best Practices",
                    "Validates gRPC specific patterns and conventions",
                    List.of("grpc")),
            entry("asyncapi-best-practices",
                    "AsyncAPI Best Practices",
                    "Validates AsyncAPI specification patterns and conventions",
                    List.of("asyncapi", "events")),
            entry("sf-api-topic-action-enablement",
                    "SF API Topic and Action Enablement",
                    "Validates Salesforce API topic and action enablement patterns",
                    List.of("salesforce"))
    );

    private static Map.Entry<String, RulesetInfo> entry(String id, String name, String description, List<String> tags) {
        return Map.entry(id, new RulesetInfo(id, name, description, tags));
    }

    public static RulesetInfo get(String id) {
        return RULESETS.get(id);
    }
}
