package com.ayesa.api.linting.service;

import com.ayesa.api.linting.engine.LintingEngine;
import com.ayesa.api.linting.model.LintingIssue;
import com.ayesa.api.linting.model.LintingResult;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.parser.OpenAPIV3Parser;
import io.swagger.v3.parser.core.models.SwaggerParseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LintingService {

    private static final Logger log = LoggerFactory.getLogger(LintingService.class);

    private final LintingEngine engine;

    public LintingService(LintingEngine engine) {
        this.engine = engine;
    }

    public LintingResult analyze(String oasContent) {
        log.info("Starting OAS analysis");

        List<LintingIssue> issues = new ArrayList<>();

        // Parse the OAS specification
        SwaggerParseResult parseResult = new OpenAPIV3Parser().readContents(oasContent, null, null);

        // Collect parsing errors
        if (parseResult.getMessages() != null) {
            for (String message : parseResult.getMessages()) {
                issues.add(LintingIssue.error("parser", "/", "Parse error: " + message, "Fix the specification syntax"));
            }
        }

        OpenAPI openAPI = parseResult.getOpenAPI();
        if (openAPI == null) {
            issues.add(LintingIssue.error("parser", "/", "Failed to parse OpenAPI specification", "Ensure the content is a valid OpenAPI 3.x or Swagger 2.x document"));
            return LintingResult.of(issues);
        }

        // Run linting rules
        issues.addAll(engine.analyze(openAPI));

        LintingResult result = LintingResult.of(issues);
        log.info("Analysis complete: {} issues found (valid={})", result.totalIssues(), result.valid());

        return result;
    }
}
