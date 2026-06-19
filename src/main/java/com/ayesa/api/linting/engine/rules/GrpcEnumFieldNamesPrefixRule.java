package com.ayesa.api.linting.engine.rules;

import com.ayesa.api.linting.engine.LintingRule;
import com.ayesa.api.linting.model.LintingIssue;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.media.ArraySchema;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class GrpcEnumFieldNamesPrefixRule extends GrpcRuleSupport implements LintingRule {

    @Override
    public String getRuleId() {
        return "enum-field-names-prefix";
    }

    @Override
    public String getDescription() {
        return "Validates that string enum values are prefixed with their schema name in UPPER_SNAKE_CASE";
    }

    @Override
    public String getRulesetId() {
        return RULESET_ID;
    }

    @Override
    public List<LintingIssue> apply(OpenAPI openAPI) {
        List<LintingIssue> issues = new ArrayList<>();
        if (!isGrpcSpec(openAPI) || openAPI.getComponents() == null || openAPI.getComponents().getSchemas() == null) {
            return issues;
        }

        for (Map.Entry<String, Schema> entry : openAPI.getComponents().getSchemas().entrySet()) {
            String schemaName = entry.getKey();
            Schema<?> schema = entry.getValue();
            if (!isStringEnum(schema)) {
                continue;
            }

            String prefix = toUpperSnakeCase(schemaName) + "_";
            for (Object enumValue : schema.getEnum()) {
                String value = enumValue.toString();
                if (!value.startsWith(prefix)) {
                    issues.add(LintingIssue.error(
                            getRuleId(),
                            schemaLocation(schemaName),
                            "Enum value '" + value + "' should be prefixed with '" + prefix + "'",
                            "Rename the enum value to use the '" + prefix + "...' convention"
                    ));
                }
            }
        }
        return issues;
    }
}

abstract class GrpcRuleSupport {

    protected static final String RULESET_ID = "grpc-best-practices";
    protected static final Pattern UPPER_SNAKE_CASE = Pattern.compile("^[A-Z][A-Z0-9_]*$");
    protected static final Pattern LOWER_SNAKE_CASE = Pattern.compile("^[a-z][a-z0-9_]*$");
    protected static final Pattern UPPER_CAMEL_CASE = Pattern.compile("^[A-Z][a-zA-Z0-9]*$");
    protected static final Pattern PACKAGE_PATTERN = Pattern.compile("^[a-z][a-z0-9]*(\\.[a-z][a-z0-9]*)*$");
    protected static final Pattern PLURALIZED_NAME = Pattern.compile(".*(?:s|es|ies)$", Pattern.CASE_INSENSITIVE);
    protected static final Pattern WORD_PATTERN = Pattern.compile("[A-Z]+(?![a-z])|[A-Z][a-z0-9]*");
    protected static final Set<String> PREPOSITIONS = Set.of("of", "with", "at", "from", "for", "in", "to", "by");
    protected static final Set<String> PRIMITIVE_TYPES = Set.of("string", "integer", "number", "boolean");

    protected boolean isGrpcSpec(OpenAPI openAPI) {
        if (openAPI == null) {
            return false;
        }
        if (openAPI.getInfo() != null) {
            if (containsGrpcKeyword(openAPI.getInfo().getTitle()) || containsGrpcKeyword(openAPI.getInfo().getDescription())) {
                return true;
            }
        }
        if (openAPI.getExtensions() != null && openAPI.getExtensions().containsKey("x-grpc")) {
            return true;
        }
        if (openAPI.getPaths() == null) {
            return false;
        }
        for (PathItem pathItem : openAPI.getPaths().values()) {
            if (pathItem == null) {
                continue;
            }
            if (hasGrpcContent(pathItem.getGet())
                    || hasGrpcContent(pathItem.getPost())
                    || hasGrpcContent(pathItem.getPut())
                    || hasGrpcContent(pathItem.getDelete())
                    || hasGrpcContent(pathItem.getPatch())
                    || hasGrpcContent(pathItem.getHead())
                    || hasGrpcContent(pathItem.getOptions())
                    || hasGrpcContent(pathItem.getTrace())) {
                return true;
            }
        }
        return false;
    }

    protected boolean containsSnakePreposition(String value) {
        if (value == null || value.isBlank()) {
            return false;
        }
        String[] parts = value.toLowerCase(Locale.ROOT).split("_");
        for (String part : parts) {
            if (PREPOSITIONS.contains(part)) {
                return true;
            }
        }
        return false;
    }

    protected boolean containsMidNamePreposition(String value) {
        if (value == null || value.isBlank()) {
            return false;
        }
        List<String> parts = new ArrayList<>();
        Matcher matcher = WORD_PATTERN.matcher(value);
        while (matcher.find()) {
            parts.add(matcher.group());
        }
        for (int index = 1; index < parts.size() - 1; index++) {
            if (PREPOSITIONS.contains(parts.get(index).toLowerCase(Locale.ROOT))) {
                return true;
            }
        }
        return false;
    }

    protected String toUpperSnakeCase(String value) {
        if (value == null || value.isBlank()) {
            return "";
        }
        return value
                .replaceAll("([A-Z]+)([A-Z][a-z])", "$1_$2")
                .replaceAll("([a-z0-9])([A-Z])", "$1_$2")
                .replaceAll("[^A-Za-z0-9]+", "_")
                .toUpperCase(Locale.ROOT);
    }

    protected boolean isStringEnum(Schema<?> schema) {
        if (schema == null || schema.getEnum() == null || schema.getEnum().isEmpty()) {
            return false;
        }
        for (Object enumValue : schema.getEnum()) {
            if (!(enumValue instanceof String)) {
                return false;
            }
        }
        return true;
    }

    protected boolean isArray(Schema<?> schema) {
        return schema instanceof ArraySchema || (schema != null && "array".equals(schema.getType()));
    }

    protected boolean isPrimitiveArray(Schema<?> schema) {
        if (!isArray(schema)) {
            return false;
        }
        Schema<?> items = schema.getItems();
        return items != null && items.get$ref() == null && items.getType() != null && PRIMITIVE_TYPES.contains(items.getType());
    }

    protected Integer toInteger(Object value) {
        if (value instanceof Number number) {
            return number.intValue();
        }
        if (value instanceof String text) {
            try {
                return Integer.parseInt(text.trim());
            } catch (NumberFormatException ignored) {
                return null;
            }
        }
        return null;
    }

    protected String refName(String ref) {
        if (ref == null || ref.isBlank()) {
            return null;
        }
        int lastSlash = ref.lastIndexOf('/');
        return lastSlash >= 0 ? ref.substring(lastSlash + 1) : ref;
    }

    protected String propertyLocation(String schemaName, String propertyName) {
        return "/components/schemas/" + schemaName + "/properties/" + propertyName;
    }

    protected String schemaLocation(String schemaName) {
        return "/components/schemas/" + schemaName;
    }

    protected Set<String> collectTagNames(OpenAPI openAPI) {
        Set<String> tagNames = new LinkedHashSet<>();
        if (openAPI == null) {
            return tagNames;
        }
        if (openAPI.getTags() != null) {
            for (Tag tag : openAPI.getTags()) {
                if (tag != null && tag.getName() != null && !tag.getName().isBlank()) {
                    tagNames.add(tag.getName());
                }
            }
        }
        forEachOperation(openAPI, (path, method, operation) -> {
            if (operation.getTags() == null) {
                return;
            }
            for (String tag : operation.getTags()) {
                if (tag != null && !tag.isBlank()) {
                    tagNames.add(tag);
                }
            }
        });
        return tagNames;
    }

    protected String findRequestSchemaName(Operation operation) {
        if (operation == null || operation.getRequestBody() == null || operation.getRequestBody().getContent() == null) {
            return null;
        }
        for (MediaType mediaType : operation.getRequestBody().getContent().values()) {
            if (mediaType == null || mediaType.getSchema() == null) {
                continue;
            }
            Schema<?> schema = mediaType.getSchema();
            if (schema.get$ref() != null) {
                return refName(schema.get$ref());
            }
            if (schema.getTitle() != null && !schema.getTitle().isBlank()) {
                return schema.getTitle();
            }
        }
        return null;
    }

    protected void forEachOperation(OpenAPI openAPI, OperationConsumer consumer) {
        if (openAPI == null || openAPI.getPaths() == null || consumer == null) {
            return;
        }
        for (Map.Entry<String, PathItem> pathEntry : openAPI.getPaths().entrySet()) {
            String path = pathEntry.getKey();
            PathItem pathItem = pathEntry.getValue();
            if (pathItem == null) {
                continue;
            }
            acceptOperation(consumer, path, "GET", pathItem.getGet());
            acceptOperation(consumer, path, "POST", pathItem.getPost());
            acceptOperation(consumer, path, "PUT", pathItem.getPut());
            acceptOperation(consumer, path, "DELETE", pathItem.getDelete());
            acceptOperation(consumer, path, "PATCH", pathItem.getPatch());
            acceptOperation(consumer, path, "HEAD", pathItem.getHead());
            acceptOperation(consumer, path, "OPTIONS", pathItem.getOptions());
            acceptOperation(consumer, path, "TRACE", pathItem.getTrace());
        }
    }

    @SuppressWarnings("unchecked")
    protected Map<String, Schema> propertiesOf(Schema<?> schema) {
        return schema == null || schema.getProperties() == null ? Map.of() : (Map<String, Schema>) schema.getProperties();
    }

    private void acceptOperation(OperationConsumer consumer, String path, String method, Operation operation) {
        if (operation != null) {
            consumer.accept(path, method, operation);
        }
    }

    private boolean containsGrpcKeyword(String value) {
        if (value == null) {
            return false;
        }
        String normalized = value.toLowerCase(Locale.ROOT);
        return normalized.contains("grpc") || normalized.contains("protobuf");
    }

    private boolean hasGrpcContent(Operation operation) {
        if (operation == null) {
            return false;
        }
        if (operation.getRequestBody() != null && containsGrpcContent(operation.getRequestBody().getContent())) {
            return true;
        }
        if (operation.getResponses() == null) {
            return false;
        }
        for (ApiResponse response : operation.getResponses().values()) {
            if (response != null && containsGrpcContent(response.getContent())) {
                return true;
            }
        }
        return false;
    }

    private boolean containsGrpcContent(Content content) {
        if (content == null) {
            return false;
        }
        for (String mediaType : content.keySet()) {
            if (mediaType != null && mediaType.toLowerCase(Locale.ROOT).contains("application/grpc")) {
                return true;
            }
        }
        return false;
    }

    @FunctionalInterface
    protected interface OperationConsumer {
        void accept(String path, String method, Operation operation);
    }
}
