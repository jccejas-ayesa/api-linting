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
                paths:
                  /items:
                    get:
                      operationId: listItems
                      summary: "List items"
                      description: "Returns all items"
                      responses:
                        "200":
                          description: "OK"
                        "500":
                          description: "Error"
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
