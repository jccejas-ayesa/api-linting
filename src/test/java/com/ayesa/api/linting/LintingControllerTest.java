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
            paths:
              /users:
                get:
                  operationId: getUsers
                  summary: "List all users"
                  description: "Returns a list of users"
                  responses:
                    "200":
                      description: "Successful response"
                    "500":
                      description: "Internal server error"
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
                .andExpect(jsonPath("$.length()").value(5));
    }

    @Test
    void analyzeEmptyBody_shouldReturn400() throws Exception {
        mockMvc.perform(post("/api/v1/lint")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest());
    }
}
