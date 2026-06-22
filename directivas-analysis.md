# Análisis de Directivas API — Viabilidad de Linting OAS

## Criterio de clasificación

- ✅ **IMPLEMENTABLE**: Verificable automáticamente analizando el documento OAS
- ⚠️ **PARCIALMENTE IMPLEMENTABLE**: Solo algunos aspectos son verificables en el OAS
- ❌ **NO IMPLEMENTABLE**: Directiva organizativa/proceso que no puede verificarse en el OAS

---

## 1. GOBIERNO Y ESTRATEGIA

| # | Directiva | Aplica | Justificación | Estrategia de implementación |
|---|-----------|--------|---------------|------------------------------|
| 1.1 | API-Led Connectivity | ⚠️ | Se puede inferir la capa (System/Process/Experience) si se establece una convención de nombrado en el título o tags del OAS | Regla que valide prefijos/tags que identifiquen la capa (ej: tag `system-api`, `process-api`, `experience-api`) |
| 1.2 | Vinculación a capacidades de negocio | ⚠️ | Se puede exigir un campo `x-business-capability` como extensión OAS | Regla que verifique la presencia de extensión `x-business-capability` en info |
| 1.3 | Registro en catálogo corporativo | ❌ | Es un proceso organizativo externo al OAS | No aplica — requiere integración con catálogo |
| 1.4 | Clasificación por dominio | ⚠️ | Se puede exigir `x-domain` como extensión o validar tags contra un catálogo de dominios | Regla que verifique extensión `x-domain` en info |
| 1.5 | Ownership de APIs | ✅ | Se puede verificar `info.contact` con campos obligatorios | Regla que valide `contact.name`, `contact.email` y extensión `x-technical-owner` |
| 1.6 | Reutilización antes de crear | ❌ | Proceso organizativo que requiere consulta al catálogo | No aplica |
| 1.7 | Gestión del ciclo de vida | ⚠️ | Se puede exigir `x-lifecycle-status` en el OAS | Regla que verifique extensión `x-lifecycle-status` con valores válidos |
| 1.8 | Deprecación controlada | ✅ | OAS soporta `deprecated: true` en operaciones y el campo `x-sunset-date` | Regla que verifique que operaciones deprecated tengan `x-sunset-date` y description de alternativa |

## 2. DISEÑO Y ARQUITECTURA

| # | Directiva | Aplica | Justificación | Estrategia de implementación |
|---|-----------|--------|---------------|------------------------------|
| 2.1 | API-First | ❌ | Es un principio de proceso (diseño antes que código) | No aplica — metodológico |
| 2.2 | Contrato previo al desarrollo | ❌ | Proceso de trabajo, no verificable en OAS | No aplica |
| 2.3 | Especificación mediante OpenAPI | ✅ | El propio hecho de pasar por el linter lo verifica | Regla de validación de formato OAS 3.x (ya implementada via swagger-parser) |
| 2.4 | OAS Commons corporativo | ✅ | Se puede verificar que se usen `$ref` a componentes comunes | Regla que verifique uso de schemas comunes de error, paginación, auditoría (configurable vía lista de schemas requeridos) |
| 2.5 | Modelo de datos canónico | ⚠️ | Se puede verificar nomenclatura y estructura de schemas | Regla que valide convenciones de nombrado de schemas |
| 2.6 | Modelo agnóstico | ⚠️ | Se puede detectar nombres de schemas que sugieran tecnología específica (ej: `OracleUser`, `DB_TABLE`) | Regla con diccionario de términos tecnológicos prohibidos en nombres de schemas/propiedades |
| 2.7 | Consistencia de diseño | ✅ | Se puede verificar homogeneidad en respuestas, errores, nombrado | Múltiples reglas: formato de errores estándar, respuestas homogéneas, convenciones de naming |
| 2.8 | Convención de nombrado | ✅ | Se puede verificar kebab-case en paths, camelCase en propiedades, PascalCase en schemas | Regla de naming convention configurable (ya parcialmente implementada) |
| 2.9 | Idempotencia en operaciones críticas | ⚠️ | Se puede verificar que PUT/DELETE tengan header `Idempotency-Key` documentado | Regla que verifique parámetros de idempotencia en operaciones PUT/DELETE |
| 2.10 | Compatibilidad retroactiva | ❌ | Requiere comparar dos versiones del OAS (diff) | Podría implementarse como feature separado (comparación de specs) |
| 2.11 | Versionado | ✅ | Se puede verificar presencia de versión en info y/o en paths (`/v1/`, `/v2/`) | Regla que valide formato semántico de `info.version` y consistencia con prefijo de path |

## 3. SEGURIDAD

| # | Directiva | Aplica | Justificación | Estrategia de implementación |
|---|-----------|--------|---------------|------------------------------|
| 3.1 | Seguridad en APIs | ✅ | OAS define `securitySchemes` y `security` a nivel global/operación | Regla que verifique que existan security schemes y estén aplicados |
| 3.2 | Autenticación OAuth2/OIDC | ✅ | Se puede verificar tipo de securityScheme | Regla que valide que los schemes sean `oauth2` u `openIdConnect` |
| 3.3 | Autorización por scopes | ✅ | OAS soporta definición de scopes en OAuth2 | Regla que verifique que las operaciones tengan scopes definidos y no usen `[]` vacío |
| 3.4 | Cifrado TLS | ✅ | Se puede verificar que `servers[].url` use `https://` | Regla que valide protocolo HTTPS en todos los servers |
| 3.5 | Gestión de secretos | ❌ | Aspecto de infraestructura, no del OAS | No aplica |
| 3.6 | OWASP API Security | ⚠️ | Algunos aspectos son verificables: validación de entrada, rate limiting headers, auth | Conjunto de reglas que cubran aspectos verificables en OAS |
| 3.7 | Clasificación de datos | ⚠️ | Se puede exigir `x-data-classification` en schemas sensibles | Regla que verifique extensión de clasificación en schemas |
| 3.8 | Enmascaramiento de datos | ❌ | Aspecto de implementación, no del contrato OAS | No aplica |

## 4. CALIDAD Y DESARROLLO

| # | Directiva | Aplica | Justificación | Estrategia de implementación |
|---|-----------|--------|---------------|------------------------------|
| 4.1 | API Mock previo | ❌ | Proceso externo | No aplica |
| 4.2 | Validación con consumidores | ❌ | Proceso colaborativo | No aplica |
| 4.3 | Validación de entrada | ✅ | Se puede verificar que los schemas de request tengan validaciones (`required`, `pattern`, `minLength`, `maxLength`, `enum`, etc.) | Regla que verifique restricciones de validación en request bodies y parámetros |
| 4.4 | Validación de salida | ✅ | Se puede verificar que los responses tengan schemas definidos | Regla que verifique schemas en response bodies |
| 4.5 | Manejo global de errores | ✅ | Se puede verificar estructura estándar de errores (RFC 7807 / Problem Details) | Regla que valide que los responses de error usen el schema corporativo de error |
| 4.6-4.10 | Pruebas (unitarias, contrato, rendimiento, resiliencia) | ❌ | Son actividades de desarrollo/QA | No aplica |

## 5. INTEGRACIÓN Y MENSAJERÍA

| # | Directiva | Aplica | Justificación | Estrategia de implementación |
|---|-----------|--------|---------------|------------------------------|
| 5.1 | Comunicación mediante contratos | ✅ | El propio OAS es el contrato | Validado implícitamente |
| 5.2 | Identificadores de correlación | ✅ | Se puede verificar header `X-Correlation-Id` / `X-Request-Id` en operaciones | Regla que verifique presencia de headers de correlación |
| 5.3 | Trazabilidad E2E | ⚠️ | Se puede verificar headers de trazabilidad (`traceparent`, `X-Trace-Id`) | Regla que verifique headers de tracing estándar |
| 5.4 | UUID para entidades | ⚠️ | Se puede verificar formato `uuid` en propiedades de tipo ID | Regla que valide `format: uuid` en propiedades que representen IDs |
| 5.5 | Eventos canónicos | ❌ | AsyncAPI, no OAS estándar | No aplica (requeriría soporte AsyncAPI) |
| 5.6 | Versionado de eventos | ❌ | AsyncAPI | No aplica |
| 5.7 | Patrón anticorrupción | ❌ | Patrón arquitectónico, no verificable en OAS | No aplica |

## 6. OPERACIÓN Y OBSERVABILIDAD

| # | Directiva | Aplica | Justificación | Estrategia de implementación |
|---|-----------|--------|---------------|------------------------------|
| 6.1 | Endpoints de salud | ✅ | Se puede verificar que existan paths `/health`, `/ready`, `/live` | Regla que verifique presencia de health endpoints |
| 6.2 | Monitoreo | ❌ | Infraestructura | No aplica |
| 6.3 | Observabilidad | ⚠️ | Se puede verificar headers de tracing | Parcialmente cubierto por regla de correlación |
| 6.4 | Logs estructurados | ❌ | Implementación | No aplica |
| 6.5 | Métricas de servicio | ❌ | Infraestructura | No aplica |
| 6.6 | Alertamiento | ❌ | Infraestructura | No aplica |
| 6.7 | SLA obligatorio | ⚠️ | Se puede exigir `x-sla` con campos de disponibilidad y latencia | Regla que verifique extensión `x-sla` en info |

## 7. RESILIENCIA

| # | Directiva | Aplica | Justificación | Estrategia de implementación |
|---|-----------|--------|---------------|------------------------------|
| 7.1 | Resiliencia | ❌ | Implementación | No aplica |
| 7.2 | Timeouts | ⚠️ | Se puede documentar via `x-timeout` | Regla que verifique extensión `x-timeout` en operaciones |
| 7.3 | Retry controlado | ❌ | Implementación | No aplica |
| 7.4 | Circuit Breaker | ❌ | Implementación | No aplica |
| 7.5 | Bulkhead | ❌ | Implementación | No aplica |
| 7.6 | Degradación controlada | ❌ | Implementación | No aplica |

## 8. PLATAFORMA Y DESPLIEGUE

| # | Directiva | Aplica | Justificación | Estrategia de implementación |
|---|-----------|--------|---------------|------------------------------|
| 8.1-8.6 | Todas (chassis, pipeline, escaneo, config, deps) | ❌ | Infraestructura/DevOps | No aplica |

## 9. CONSUMO Y EXPERIENCIA

| # | Directiva | Aplica | Justificación | Estrategia de implementación |
|---|-----------|--------|---------------|------------------------------|
| 9.1 | Documentación para consumidores | ✅ | Se puede verificar descriptions, summaries, externalDocs | Regla de completitud de documentación (ya parcialmente implementada) |
| 9.2 | Portal para desarrolladores | ❌ | Infraestructura | No aplica |
| 9.3 | Ejemplos de uso | ✅ | OAS soporta `example`/`examples` en schemas, parameters, responses | Regla que verifique presencia de examples en request/response |

## 10. GATEWAY

| # | Directiva | Aplica | Justificación | Estrategia de implementación |
|---|-----------|--------|---------------|------------------------------|
| 10.1 | API Gateway obligatorio | ❌ | Infraestructura | No aplica |
| 10.2 | Políticas en Gateway | ❌ | Configuración de gateway | No aplica |
| 10.3 | Rate Limiting | ⚠️ | Se puede verificar que se documenten headers de rate limiting (`X-RateLimit-Limit`, etc.) | Regla que verifique documentación de headers de rate limiting |
| 10.4 | Throttling | ❌ | Configuración de gateway | No aplica |
| 10.5 | Gestión de acceso | ❌ | Configuración de gateway | No aplica |

---

## RESUMEN

| Clasificación | Cantidad | Porcentaje |
|---------------|----------|------------|
| ✅ Implementable | 20 | ~33% |
| ⚠️ Parcialmente implementable | 15 | ~25% |
| ❌ No implementable | 25 | ~42% |

### Reglas a implementar (prioridad alta)

1. **security-schemes-required** — Verificar security schemes y su aplicación
2. **oauth2-required** — Verificar que los schemes sean OAuth2/OIDC
3. **https-required** — Verificar HTTPS en servers
4. **scopes-required** — Verificar scopes en operaciones
5. **error-model-standard** — Verificar modelo estándar de errores (RFC 7807)
6. **correlation-id-required** — Verificar header X-Correlation-Id
7. **health-endpoints-required** — Verificar endpoints de salud
8. **examples-required** — Verificar examples en schemas
9. **versioning-required** — Verificar versionado semántico
10. **input-validation-required** — Verificar restricciones en schemas de entrada
11. **response-schema-required** — Verificar schemas en respuestas
12. **naming-convention-schemas** — camelCase propiedades, PascalCase schemas
13. **deprecated-sunset** — Verificar x-sunset-date en operaciones deprecated
14. **x-extensions-governance** — Verificar extensiones corporativas (x-domain, x-business-capability, etc.)
15. **oas-commons-usage** — Verificar uso de componentes comunes

### Reglas existentes que cubren directivas

- `info-completeness` → cubre parcialmente 1.5 (Ownership) y 9.1 (Documentación)
- `path-naming-convention` → cubre 2.8 (Convención de nombrado)
- `operation-id-required` → cubre 2.7 (Consistencia de diseño)
- `response-codes-validation` → cubre 4.5 (Errores) parcialmente
- `description-required` → cubre 9.1 (Documentación) parcialmente
