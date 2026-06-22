package com.ayesa.api.linting;

import com.ayesa.api.linting.model.LintingResult;
import com.ayesa.api.linting.service.LintingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DirectivesRulesTest {

    @Autowired
    private LintingService lintingService;

    // === Security Rules ===

    @Test
    void noSecuritySchemes_shouldReportError() {
        String spec = """
                openapi: "3.0.3"
                info:
                  title: "Test"
                  version: "1.0.0"
                paths:
                  /test:
                    get:
                      operationId: getTest
                      responses:
                        "200":
                          description: "OK"
                """;

        LintingResult result = lintingService.analyze(spec);
        assertTrue(hasRuleIssue(result, "security-schemes-required"));
    }

    @Test
    void nonOauth2Scheme_shouldReportWarning() {
        String spec = """
                openapi: "3.0.3"
                info:
                  title: "Test"
                  version: "1.0.0"
                components:
                  securitySchemes:
                    apiKey:
                      type: apiKey
                      in: header
                      name: X-API-Key
                security:
                  - apiKey: []
                paths:
                  /test:
                    get:
                      operationId: getTest
                      responses:
                        "200":
                          description: "OK"
                """;

        LintingResult result = lintingService.analyze(spec);
        assertTrue(hasRuleIssue(result, "oauth2-required"));
    }

    @Test
    void httpServer_shouldReportError() {
        String spec = """
                openapi: "3.0.3"
                info:
                  title: "Test"
                  version: "1.0.0"
                servers:
                  - url: http://api.example.com
                paths:
                  /test:
                    get:
                      operationId: getTest
                      responses:
                        "200":
                          description: "OK"
                """;

        LintingResult result = lintingService.analyze(spec);
        assertTrue(hasRuleIssue(result, "https-required"));
    }

    @Test
    void httpsServer_shouldNotReportHttpsIssue() {
        String spec = """
                openapi: "3.0.3"
                info:
                  title: "Test"
                  version: "1.0.0"
                servers:
                  - url: https://api.example.com
                paths:
                  /test:
                    get:
                      operationId: getTest
                      responses:
                        "200":
                          description: "OK"
                """;

        LintingResult result = lintingService.analyze(spec);
        assertFalse(hasRuleIssue(result, "https-required"));
    }

    // === Quality Rules ===

    @Test
    void missingResponseSchema_shouldReportWarning() {
        String spec = """
                openapi: "3.0.3"
                info:
                  title: "Test"
                  version: "1.0.0"
                paths:
                  /test:
                    get:
                      operationId: getTest
                      responses:
                        "200":
                          description: "OK"
                """;

        LintingResult result = lintingService.analyze(spec);
        assertTrue(hasRuleIssue(result, "response-schema-required"));
    }

    @Test
    void missingExamples_shouldReportInfo() {
        String spec = """
                openapi: "3.0.3"
                info:
                  title: "Test"
                  version: "1.0.0"
                paths:
                  /test:
                    get:
                      operationId: getTest
                      responses:
                        "200":
                          description: "OK"
                          content:
                            application/json:
                              schema:
                                type: object
                """;

        LintingResult result = lintingService.analyze(spec);
        assertTrue(hasRuleIssue(result, "provide-examples-on-payloads"));
    }

    // === Integration Rules ===

    @Test
    void missingCorrelationId_shouldReportWarning() {
        String spec = """
                openapi: "3.0.3"
                info:
                  title: "Test"
                  version: "1.0.0"
                paths:
                  /test:
                    get:
                      operationId: getTest
                      responses:
                        "200":
                          description: "OK"
                """;

        LintingResult result = lintingService.analyze(spec);
        assertTrue(hasRuleIssue(result, "correlation-id-required"));
    }

    @Test
    void withCorrelationId_shouldNotReportCorrelationIssue() {
        String spec = """
                openapi: "3.0.3"
                info:
                  title: "Test"
                  version: "1.0.0"
                paths:
                  /test:
                    get:
                      operationId: getTest
                      parameters:
                        - name: X-Correlation-Id
                          in: header
                          schema:
                            type: string
                      responses:
                        "200":
                          description: "OK"
                """;

        LintingResult result = lintingService.analyze(spec);
        assertFalse(hasRuleIssue(result, "correlation-id-required"));
    }

    @Test
    void missingHealthEndpoints_shouldReportWarning() {
        String spec = """
                openapi: "3.0.3"
                info:
                  title: "Test"
                  version: "1.0.0"
                paths:
                  /users:
                    get:
                      operationId: getUsers
                      responses:
                        "200":
                          description: "OK"
                """;

        LintingResult result = lintingService.analyze(spec);
        assertTrue(hasRuleIssue(result, "health-check-complete"));
    }

    @Test
    void withHealthEndpoint_shouldNotReportHealthIssue() {
        String spec = """
                openapi: "3.0.3"
                info:
                  title: "Test"
                  version: "1.0.0"
                paths:
                  /health:
                    get:
                      operationId: health
                      responses:
                        "200":
                          description: "OK"
                """;

        LintingResult result = lintingService.analyze(spec);
        assertFalse(result.issues().stream()
                .anyMatch(i -> i.ruleId().equals("health-check-complete")
                        && i.message().contains("Missing liveness")));
    }

    @Test
    void nonSemverVersion_shouldReportWarning() {
        String spec = """
                openapi: "3.0.3"
                info:
                  title: "Test"
                  version: "v1"
                paths:
                  /test:
                    get:
                      operationId: getTest
                      responses:
                        "200":
                          description: "OK"
                """;

        LintingResult result = lintingService.analyze(spec);
        assertTrue(hasRuleIssue(result, "versioning-required"));
    }

    @Test
    void semverVersion_shouldNotReportVersionIssue() {
        String spec = """
                openapi: "3.0.3"
                info:
                  title: "Test"
                  version: "1.0.0"
                paths:
                  /v1/test:
                    get:
                      operationId: getTest
                      responses:
                        "200":
                          description: "OK"
                """;

        LintingResult result = lintingService.analyze(spec);
        assertFalse(result.issues().stream()
                .anyMatch(i -> i.ruleId().equals("versioning-required")
                        && i.message().contains("does not follow semantic versioning")));
    }

    // === Naming Convention ===

    @Test
    void snakeCaseProperty_shouldReportWarning() {
        String spec = """
                openapi: "3.0.3"
                info:
                  title: "Test"
                  version: "1.0.0"
                paths: {}
                components:
                  schemas:
                    User:
                      type: object
                      properties:
                        first_name:
                          type: string
                """;

        LintingResult result = lintingService.analyze(spec);
        assertTrue(hasRuleIssue(result, "naming-convention-schemas"));
    }

    @Test
    void camelCaseProperty_shouldNotReportNamingIssue() {
        String spec = """
                openapi: "3.0.3"
                info:
                  title: "Test"
                  version: "1.0.0"
                paths: {}
                components:
                  schemas:
                    User:
                      type: object
                      properties:
                        firstName:
                          type: string
                """;

        LintingResult result = lintingService.analyze(spec);
        assertFalse(result.issues().stream()
                .anyMatch(i -> i.ruleId().equals("naming-convention-schemas")
                        && i.path().contains("firstName")));
    }

    // === Governance Extensions ===

    @Test
    void missingGovernanceExtensions_shouldReportWarnings() {
        String spec = """
                openapi: "3.0.3"
                info:
                  title: "Test"
                  version: "1.0.0"
                paths: {}
                """;

        LintingResult result = lintingService.analyze(spec);
        assertTrue(hasRuleIssue(result, "x-extensions-governance"));
    }

    // === Deprecated Sunset ===

    @Test
    void deprecatedWithoutSunset_shouldReportWarning() {
        String spec = """
                openapi: "3.0.3"
                info:
                  title: "Test"
                  version: "1.0.0"
                paths:
                  /old-endpoint:
                    get:
                      operationId: getOld
                      deprecated: true
                      responses:
                        "200":
                          description: "OK"
                """;

        LintingResult result = lintingService.analyze(spec);
        assertTrue(hasRuleIssue(result, "deprecated-sunset"));
    }

    // === OAS Commons ===

    @Test
    void noErrorSchema_shouldReportWarning() {
        String spec = """
                openapi: "3.0.3"
                info:
                  title: "Test"
                  version: "1.0.0"
                paths:
                  /test:
                    get:
                      operationId: getTest
                      responses:
                        "200":
                          description: "OK"
                components:
                  schemas:
                    User:
                      type: object
                """;

        LintingResult result = lintingService.analyze(spec);
        assertTrue(hasRuleIssue(result, "oas-commons-usage"));
    }

    // === Helper ===

    private boolean hasRuleIssue(LintingResult result, String ruleId) {
        return result.issues().stream().anyMatch(i -> i.ruleId().equals(ruleId));
    }
}
