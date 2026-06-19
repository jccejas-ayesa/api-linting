package com.ayesa.api.linting;

import com.ayesa.api.linting.model.LintingRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class LintingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private static final String VALID_OAS = """
            openapi: "3.0.3"
            info:
              title: "Sample API"
              description: "A sample API for testing"
              version: "1.0.0"
              contact:
                name: "Test"
                email: "test@example.com"
              x-domain: "testing"
              x-business-capability: "test-capability"
            servers:
              - url: https://api.example.com
            components:
              securitySchemes:
                oauth2:
                  type: oauth2
                  flows:
                    clientCredentials:
                      tokenUrl: https://auth.example.com/token
                      scopes:
                        read:users: "Read users"
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
                  - read:users
            paths:
              /v1/users:
                get:
                  operationId: getUsers
                  summary: "List all users"
                  description: "Returns a list of users"
                  parameters:
                    - name: X-Correlation-Id
                      in: header
                      schema:
                        type: string
                  responses:
                    "200":
                      description: "Successful response"
                      content:
                        application/json:
                          schema:
                            type: array
                            items:
                              type: object
                          example:
                            - id: 1
                              name: "John"
                    "500":
                      description: "Internal server error"
                      content:
                        application/json:
                          schema:
                            $ref: '#/components/schemas/Error'
                          example:
                            type: "about:blank"
                            title: "Internal Server Error"
                            status: 500
                            detail: "An unexpected error occurred"
              /health:
                get:
                  operationId: healthCheck
                  summary: "Health check"
                  description: "Returns service health status"
                  responses:
                    "200":
                      description: "Service is healthy"
                      content:
                        application/json:
                          schema:
                            type: object
                          example:
                            status: "UP"
            """;

    private static final String MINIMAL_OAS = """
            openapi: "3.0.3"
            info:
              title: "Minimal"
              version: "1.0.0"
            paths: {}
            """;

    private static final String INVALID_CONTENT = "this is not valid openapi content";

    @Test
    void analyzeValidSpec_shouldReturnNoErrors() throws Exception {
        String json = """
                {"content": "%s"}
                """.formatted(VALID_OAS.replace("\"", "\\\"").replace("\n", "\\n"));

        mockMvc.perform(post("/api/v1/lint")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.valid").value(true))
                .andExpect(jsonPath("$.issues").isArray());
    }

    @Test
    void analyzeRawYaml_shouldWork() throws Exception {
        mockMvc.perform(post("/api/v1/lint/raw")
                        .contentType("application/yaml")
                        .content(VALID_OAS))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.valid").value(true));
    }

    @Test
    void analyzeMinimalSpec_shouldReturnWarnings() throws Exception {
        mockMvc.perform(post("/api/v1/lint/raw")
                        .contentType(MediaType.TEXT_PLAIN)
                        .content(MINIMAL_OAS))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalIssues").isNumber());
    }

    @Test
    void analyzeInvalidContent_shouldReturnParseErrors() throws Exception {
        mockMvc.perform(post("/api/v1/lint/raw")
                        .contentType(MediaType.TEXT_PLAIN)
                        .content(INVALID_CONTENT))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.valid").value(false))
                .andExpect(jsonPath("$.issues[0].ruleId").value("parser"));
    }

    @Test
    void listRules_shouldReturnAllRules() throws Exception {
        mockMvc.perform(get("/api/v1/lint/rules"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(33));
    }

    @Test
    void analyzeEmptyBody_shouldReturn400() throws Exception {
        mockMvc.perform(post("/api/v1/lint")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest());
    }
}
