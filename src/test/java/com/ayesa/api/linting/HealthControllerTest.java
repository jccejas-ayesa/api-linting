package com.ayesa.api.linting;

import com.ayesa.api.linting.model.HealthStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class HealthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void getStatus_shouldReturn200WithHealthStatus() throws Exception {
        mockMvc.perform(get("/api/v1/health/status"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("UP"))
                .andExpect(jsonPath("$.serviceName").value("api-linting"))
                .andExpect(jsonPath("$.port").value(8080))
                .andExpect(jsonPath("$.components").isArray())
                .andExpect(jsonPath("$.components.length()").value(3));
    }

    @Test
    public void getReady_shouldReturn200() throws Exception {
        mockMvc.perform(get("/api/v1/health/ready"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"ready\":true")));
    }

    @Test
    public void getLive_shouldReturn200() throws Exception {
        mockMvc.perform(get("/api/v1/health/live"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"alive\":true")));
    }

    @Test
    public void checkDependencies_shouldReturn200() throws Exception {
        mockMvc.perform(get("/api/v1/health/dependencies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("UP"))
                .andExpect(jsonPath("$.database.status").value("UP"));
    }

    @Test
    public void getStatus_shouldHaveAllRequiredFields() throws Exception {
        mockMvc.perform(get("/api/v1/health/status"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").exists())
                .andExpect(jsonPath("$.serviceName").exists())
                .andExpect(jsonPath("$.version").exists())
                .andExpect(jsonPath("$.timestamp").exists())
                .andExpect(jsonPath("$.port").exists())
                .andExpect(jsonPath("$.uptime").exists())
                .andExpect(jsonPath("$.database").exists())
                .andExpect(jsonPath("$.components").exists());
    }
}
