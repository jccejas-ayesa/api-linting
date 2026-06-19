package com.ayesa.api.linting;

import com.ayesa.api.linting.model.LintingResult;
import com.ayesa.api.linting.service.LintingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BestPracticesRulesTest {

    @Autowired
    private LintingService lintingService;

    // === URL Depth Limit (API-BP-COMMON-008) ===

    @Test
    void deepUrl_shouldReportWarning() {
        String spec = """
                openapi: "3.0.3"
                info:
                  title: "Test"
                  version: "1.0.0"
                paths:
                  /customers/{customerId}/policies/{policyId}/claims/{claimId}/documents:
                    get:
                      operationId: getDeepDocs
                      responses:
                        "200":
                          description: "OK"
                """;

        LintingResult result = lintingService.analyze(spec);
        assertTrue(hasRuleIssue(result, "url-depth-limit"));
    }

    @Test
    void shallowUrl_shouldNotReportDepthIssue() {
        String spec = """
                openapi: "3.0.3"
                info:
                  title: "Test"
                  version: "1.0.0"
                paths:
                  /v1/customers/{customerId}/policies:
                    get:
                      operationId: getPolicies
                      responses:
                        "200":
                          description: "OK"
                """;

        LintingResult result = lintingService.analyze(spec);
        assertFalse(hasRuleIssue(result, "url-depth-limit"));
    }

    // === HTTP Verbs Usage (API-BP-COMMON-003/004) ===

    @Test
    void verbInPath_shouldReportWarning() {
        String spec = """
                openapi: "3.0.3"
                info:
                  title: "Test"
                  version: "1.0.0"
                paths:
                  /create-user:
                    post:
                      operationId: createUser
                      responses:
                        "201":
                          description: "Created"
                """;

        LintingResult result = lintingService.analyze(spec);
        assertTrue(hasRuleIssue(result, "http-verbs-usage"));
    }

    @Test
    void searchEndpoint_shouldBeAllowed() {
        String spec = """
                openapi: "3.0.3"
                info:
                  title: "Test"
                  version: "1.0.0"
                paths:
                  /customers/search:
                    post:
                      operationId: searchCustomers
                      responses:
                        "200":
                          description: "OK"
                """;

        LintingResult result = lintingService.analyze(spec);
        assertFalse(result.issues().stream()
                .anyMatch(i -> i.ruleId().equals("http-verbs-usage")
                        && i.path().contains("/search")));
    }

    // === Parameter Naming (API-BP-COMMON-006/007) ===

    @Test
    void snakeCasePathParam_shouldReportWarning() {
        String spec = """
                openapi: "3.0.3"
                info:
                  title: "Test"
                  version: "1.0.0"
                paths:
                  /users/{user_id}:
                    get:
                      operationId: getUser
                      parameters:
                        - name: user_id
                          in: path
                          required: true
                          schema:
                            type: string
                      responses:
                        "200":
                          description: "OK"
                """;

        LintingResult result = lintingService.analyze(spec);
        assertTrue(hasRuleIssue(result, "parameter-naming-convention"));
    }

    @Test
    void camelCaseQueryParam_shouldReportWarning() {
        String spec = """
                openapi: "3.0.3"
                info:
                  title: "Test"
                  version: "1.0.0"
                paths:
                  /users:
                    get:
                      operationId: listUsers
                      parameters:
                        - name: dateCreated
                          in: query
                          schema:
                            type: string
                      responses:
                        "200":
                          description: "OK"
                """;

        LintingResult result = lintingService.analyze(spec);
        assertTrue(hasRuleIssue(result, "parameter-naming-convention"));
    }

    // === Plural Resource Names (API-BP-COMMON-004) ===

    @Test
    void singularResourceName_shouldReportWarning() {
        String spec = """
                openapi: "3.0.3"
                info:
                  title: "Test"
                  version: "1.0.0"
                paths:
                  /notification:
                    get:
                      operationId: getNotifications
                      responses:
                        "200":
                          description: "OK"
                """;

        LintingResult result = lintingService.analyze(spec);
        assertTrue(hasRuleIssue(result, "plural-resource-names"));
    }

    // === Pagination Standard (API-BP-GET-002) ===

    @Test
    void collectionWithoutPagination_shouldReportWarning() {
        String spec = """
                openapi: "3.0.3"
                info:
                  title: "Test"
                  version: "1.0.0"
                paths:
                  /users:
                    get:
                      operationId: listUsers
                      responses:
                        "200":
                          description: "OK"
                """;

        LintingResult result = lintingService.analyze(spec);
        assertTrue(hasRuleIssue(result, "pagination-standard"));
    }

    @Test
    void collectionWithPagination_shouldNotReportIssue() {
        String spec = """
                openapi: "3.0.3"
                info:
                  title: "Test"
                  version: "1.0.0"
                paths:
                  /users:
                    get:
                      operationId: listUsers
                      parameters:
                        - name: page
                          in: query
                          schema:
                            type: integer
                        - name: pageSize
                          in: query
                          schema:
                            type: integer
                      responses:
                        "200":
                          description: "OK"
                """;

        LintingResult result = lintingService.analyze(spec);
        assertFalse(hasRuleIssue(result, "pagination-standard"));
    }

    // === POST Response Standard (API-BP-POST-004) ===

    @Test
    void postWith200InsteadOf201_shouldReportWarning() {
        String spec = """
                openapi: "3.0.3"
                info:
                  title: "Test"
                  version: "1.0.0"
                paths:
                  /users:
                    post:
                      operationId: createUser
                      responses:
                        "200":
                          description: "OK"
                """;

        LintingResult result = lintingService.analyze(spec);
        assertTrue(hasRuleIssue(result, "post-response-standard"));
    }

    @Test
    void postWith201_shouldNotReportIssue() {
        String spec = """
                openapi: "3.0.3"
                info:
                  title: "Test"
                  version: "1.0.0"
                paths:
                  /users:
                    post:
                      operationId: createUser
                      responses:
                        "201":
                          description: "Created"
                """;

        LintingResult result = lintingService.analyze(spec);
        assertFalse(hasRuleIssue(result, "post-response-standard"));
    }

    // === DELETE Response Standard (API-BP-DELETE-001/002) ===

    @Test
    void deleteWith200_shouldReportWarning() {
        String spec = """
                openapi: "3.0.3"
                info:
                  title: "Test"
                  version: "1.0.0"
                paths:
                  /users/{userId}:
                    delete:
                      operationId: deleteUser
                      parameters:
                        - name: userId
                          in: path
                          required: true
                          schema:
                            type: string
                      responses:
                        "200":
                          description: "OK"
                """;

        LintingResult result = lintingService.analyze(spec);
        assertTrue(hasRuleIssue(result, "delete-response-standard"));
    }

    @Test
    void deleteWith204_shouldNotReportIssue() {
        String spec = """
                openapi: "3.0.3"
                info:
                  title: "Test"
                  version: "1.0.0"
                paths:
                  /users/{userId}:
                    delete:
                      operationId: deleteUser
                      parameters:
                        - name: userId
                          in: path
                          required: true
                          schema:
                            type: string
                      responses:
                        "204":
                          description: "Deleted"
                        "404":
                          description: "Not found"
                """;

        LintingResult result = lintingService.analyze(spec);
        assertFalse(result.issues().stream()
                .anyMatch(i -> i.ruleId().equals("delete-response-standard")
                        && i.message().contains("200")));
    }

    // === Health Check Complete ===

    @Test
    void healthWithDependencies_shouldNotReportDepIssue() {
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
                          description: "Healthy"
                          content:
                            application/json:
                              schema:
                                type: object
                                properties:
                                  status:
                                    type: string
                                  dependencies:
                                    type: object
                        "503":
                          description: "Unhealthy"
                  /ready:
                    get:
                      operationId: readiness
                      responses:
                        "200":
                          description: "Ready"
                """;

        LintingResult result = lintingService.analyze(spec);
        assertFalse(result.issues().stream()
                .anyMatch(i -> i.ruleId().equals("health-check-complete")
                        && i.message().contains("Missing liveness")));
        assertFalse(result.issues().stream()
                .anyMatch(i -> i.ruleId().equals("health-check-complete")
                        && i.message().contains("Missing readiness")));
        assertFalse(result.issues().stream()
                .anyMatch(i -> i.ruleId().equals("health-check-complete")
                        && i.message().contains("dependency")));
    }

    // === Rate Limit Documented (API-BP-COMMON-020) ===

    @Test
    void noRateLimitInfo_shouldReportWarning() {
        String spec = """
                openapi: "3.0.3"
                info:
                  title: "Test"
                  version: "1.0.0"
                paths:
                  /users:
                    get:
                      operationId: listUsers
                      responses:
                        "200":
                          description: "OK"
                """;

        LintingResult result = lintingService.analyze(spec);
        assertTrue(hasRuleIssue(result, "rate-limit-documented"));
    }

    @Test
    void with429Response_shouldNotReportRateLimitIssue() {
        String spec = """
                openapi: "3.0.3"
                info:
                  title: "Test"
                  version: "1.0.0"
                paths:
                  /users:
                    get:
                      operationId: listUsers
                      responses:
                        "200":
                          description: "OK"
                        "429":
                          description: "Too many requests"
                """;

        LintingResult result = lintingService.analyze(spec);
        assertFalse(result.issues().stream()
                .anyMatch(i -> i.ruleId().equals("rate-limit-documented")
                        && i.message().contains("No rate limiting")));
    }

    // === Helper ===

    private boolean hasRuleIssue(LintingResult result, String ruleId) {
        return result.issues().stream().anyMatch(i -> i.ruleId().equals(ruleId));
    }
}
