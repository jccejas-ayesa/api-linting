package com.ayesa.api.linting;

import com.ayesa.api.linting.engine.LintingEngine;
import com.ayesa.api.linting.model.LintingResult;
import com.ayesa.api.linting.service.LintingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LintingServiceTest {

    @Autowired
    private LintingService lintingService;

    @Autowired
    private LintingEngine lintingEngine;

    @Test
    void contextLoads() {
        assertNotNull(lintingService);
        assertNotNull(lintingEngine);
        assertFalse(lintingEngine.getRules().isEmpty());
    }

    @Test
    void analyzeValidSpec_shouldBeValid() {
        String spec = """
                openapi: "3.0.3"
                info:
                  title: "Test API"
                  description: "A test API"
                  version: "1.0.0"
                  contact:
                    name: "Dev"
                    email: "dev@test.com"
                  license:
                    name: "MIT"
                    url: "https://opensource.org/licenses/MIT"
                  x-domain: "testing"
                  x-business-capability: "test-cap"
                tags:
                  - name: items
                    description: "Item operations"
                  - name: health
                    description: "Health operations"
                servers:
                  - url: https://api.test.com/api/v1
                components:
                  securitySchemes:
                    oauth2:
                      type: oauth2
                      flows:
                        clientCredentials:
                          tokenUrl: https://auth.test.com/token
                          scopes:
                            read:items: "Read items"
                  schemas:
                    Error:
                      type: object
                      description: "Error response object"
                      properties:
                        type:
                          type: string
                          description: "Error type URI"
                        title:
                          type: string
                          description: "Error title"
                        status:
                          type: integer
                          description: "HTTP status code"
                        detail:
                          type: string
                          description: "Error detail message"
                security:
                  - oauth2:
                      - read:items
                paths:
                  /v1/items:
                    get:
                      operationId: list-items
                      tags:
                        - items
                      summary: "List items"
                      description: "Returns all items"
                      parameters:
                        - name: X-Correlation-Id
                          in: header
                          description: "Correlation ID for request tracking"
                          schema:
                            type: string
                      responses:
                        "200":
                          description: "OK"
                          content:
                            application/json:
                              schema:
                                type: array
                                items:
                                  type: object
                              example:
                                - id: 1
                        "204":
                          description: "No content"
                  /health:
                    get:
                      operationId: health-check
                      tags:
                        - health
                      summary: "Health"
                      description: "Health check"
                      responses:
                        "200":
                          description: "OK"
                          content:
                            application/json:
                              schema:
                                type: object
                              example:
                                status: "UP"
                """;

        LintingResult result = lintingService.analyze(spec);

        assertTrue(result.valid());
        assertNotNull(result.analyzedAt());
    }

    @Test
    void analyzeBadPaths_shouldReportNamingIssues() {
        String spec = """
                openapi: "3.0.3"
                info:
                  title: "Test"
                  version: "1.0.0"
                paths:
                  /MyResource:
                    get:
                      operationId: getMyResource
                      summary: "Get resource"
                      responses:
                        "200":
                          description: "OK"
                """;

        LintingResult result = lintingService.analyze(spec);

        boolean hasPathIssue = result.issues().stream()
                .anyMatch(i -> i.ruleId().equals("path-naming-convention"));
        assertTrue(hasPathIssue, "Should detect non-kebab-case path");
    }

    @Test
    void analyzeMissingOperationId_shouldReportError() {
        String spec = """
                openapi: "3.0.3"
                info:
                  title: "Test"
                  version: "1.0.0"
                paths:
                  /test:
                    get:
                      responses:
                        "200":
                          description: "OK"
                """;

        LintingResult result = lintingService.analyze(spec);

        boolean hasMissingOpId = result.issues().stream()
                .anyMatch(i -> i.ruleId().equals("operation-id-required"));
        assertTrue(hasMissingOpId, "Should detect missing operationId");
        assertFalse(result.valid(), "Should be invalid when operationId is missing");
    }

    @Test
    void analyzeGarbage_shouldReturnParseError() {
        LintingResult result = lintingService.analyze("not a valid spec");

        assertFalse(result.valid());
        assertTrue(result.issues().stream().anyMatch(i -> i.ruleId().equals("parser")));
    }

    @Test
    void analyzeOwaspRuleset_shouldIncludeCoveredExistingRules() {
        String spec = """
                openapi: "3.0.3"
                info:
                  title: "Test API"
                servers:
                  - url: http://api.test.com
                paths:
                  /items:
                    get:
                      responses:
                        "200":
                          description: "OK"
                """;

        LintingResult result = lintingService.analyze(spec, List.of("owasp-api-security-top-10"));

        assertTrue(result.issues().stream().anyMatch(i -> i.ruleId().equals("security-schemes-required")));
        assertTrue(result.issues().stream().anyMatch(i -> i.ruleId().equals("https-required")));
        assertTrue(result.issues().stream().anyMatch(i -> i.ruleId().equals("versioning-required")));
        assertTrue(result.issues().stream().anyMatch(i -> i.ruleId().equals("description-required")));
        assertTrue(result.issues().stream().anyMatch(i -> i.ruleId().equals("response-schema-required")));
    }

    @Test
    void analyzeOwaspRuleset_shouldFlagUnrestrictedStringParameters() {
        LintingResult result = lintingService.analyze(baseOwaspSpec("""
                - name: userId
                  in: path
                  required: true
                  description: "User identifier"
                  schema:
                    type: string
                    format: uuid
                - name: filter
                  in: query
                  description: "Filter expression"
                  schema:
                    type: string
                """, true), List.of("owasp-api-security-top-10"));

        assertTrue(result.issues().stream()
                .anyMatch(i -> i.ruleId().equals("injection-pattern-on-string-parameters")));
    }

    @Test
    void analyzeOwaspRuleset_shouldRequire429Responses() {
        LintingResult result = lintingService.analyze(baseOwaspSpec("", false), List.of("owasp-api-security-top-10"));

        assertTrue(result.issues().stream()
                .anyMatch(i -> i.ruleId().equals("lack-of-resources-and-rate-limiting-too-many-requests")));
    }

    @Test
    void analyzeOwaspRuleset_shouldRequireUuidObjectIdentifiers() {
        LintingResult result = lintingService.analyze(baseOwaspSpec("""
                - name: userId
                  in: path
                  required: true
                  description: "User identifier"
                  schema:
                    type: integer
                """, true), List.of("owasp-api-security-top-10"));

        assertTrue(result.issues().stream()
                .anyMatch(i -> i.ruleId().equals("broken-object-level-authorization-use-guids")));
    }

    private String baseOwaspSpec(String additionalParameters, boolean include429) {
        String defaultParameters = """
                - name: userId
                  in: path
                  required: true
                  description: "User identifier"
                  schema:
                    type: string
                    format: uuid
                """;
        String parametersBlock = (additionalParameters.isBlank() ? defaultParameters : additionalParameters)
                .stripTrailing()
                .indent(22)
                .stripTrailing();

        StringBuilder spec = new StringBuilder()
                .append("openapi: \"3.0.3\"\n")
                .append("info:\n")
                .append("  title: \"Secure API\"\n")
                .append("  description: \"OWASP aligned API\"\n")
                .append("  version: \"1.0.0\"\n")
                .append("servers:\n")
                .append("  - url: https://api.test.com\n")
                .append("components:\n")
                .append("  securitySchemes:\n")
                .append("    bearerAuth:\n")
                .append("      type: http\n")
                .append("      scheme: bearer\n")
                .append("security:\n")
                .append("  - bearerAuth: []\n")
                .append("paths:\n")
                .append("  /v1/users/{userId}:\n")
                .append("    get:\n")
                .append("      operationId: get-user\n")
                .append("      summary: \"Get user\"\n")
                .append("      description: \"Returns a user\"\n")
                .append("      parameters:\n")
                .append(parametersBlock)
                .append("\n")
                .append("      responses:\n")
                .append("        \"200\":\n")
                .append("          description: \"OK\"\n")
                .append("          content:\n")
                .append("            application/json:\n")
                .append("              schema:\n")
                .append("                type: object\n");

        if (include429) {
            spec.append("        \"429\":\n")
                    .append("          description: \"Too Many Requests\"\n");
        }

        return spec.toString();
    }
}
