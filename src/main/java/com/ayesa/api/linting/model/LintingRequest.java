package com.ayesa.api.linting.model;

import jakarta.validation.constraints.NotBlank;

public record LintingRequest(
        @NotBlank(message = "The OpenAPI specification content is required")
        String content
) {
}
