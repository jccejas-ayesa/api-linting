package com.ayesa.api.linting;

import com.ayesa.api.linting.engine.LintingEngine;
import com.ayesa.api.linting.model.LintingIssue;
import com.ayesa.api.linting.model.LintingResult;
import com.ayesa.api.linting.service.LintingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
                  - url: https://api.test.com
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
                      properties:
                        type:
                          type: string
                        title:
                          type: string
                        status:
                          type: integer
                        detail:
                          type: string
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
                        "500":
                          description: "Error"
                          content:
                            application/json:
                              schema:
                                $ref: '#/components/schemas/Error'
                              example:
                                type: "about:blank"
                                title: "Error"
                                status: 500
                                detail: "Unexpected"
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
}
