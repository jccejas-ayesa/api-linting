# API Linting Service

**API Governance & OpenAPI Specification Validator**

A Spring Boot REST API service that performs comprehensive linting and validation of OpenAPI Specifications (OAS) against 141 enterprise API governance rules organized in 14 semantic rulesets.

---

## 🎯 Propósito

Proporcionar un servicio **asesor de linting** (v1.0) para validar especificaciones OpenAPI contra estándares de gobernanza empresarial, identificando problemas de diseño, seguridad, documentación y cumplimiento regulatorio antes de que las APIs lleguen a producción.

**Visión futura:** Evolucionar hacia **gobernanza ejecutable** (v2.0+) con capacidad de enforcement y restricción de despliegues.

---

## ✨ Features (Estado: OPERATIVO)

### 🏥 Health Check Endpoints

- **`GET /api/v1/health/status`** - Estado general del servicio con componentes
- **`GET /api/v1/health/ready`** - Readiness probe (dependencias disponibles)
- **`GET /api/v1/health/live`** - Liveness probe (proceso activo)
- **`GET /api/v1/health/dependencies`** - Estado detallado de dependencias

### 📋 Ruleset Management

- **`GET /api/v1/rulesets`** - Listar todos los rulesets disponibles
- **`GET /api/v1/rulesets/{id}`** - Obtener ruleset específico con detalles
- **`POST /api/v1/rulesets/{id}/enable`** - Habilitar ruleset para linting
- **`POST /api/v1/rulesets/{id}/disable`** - Deshabilitar ruleset
- **`GET /api/v1/rulesets/toggle/status`** - Ver estado de toggle de ruleset

### 🔍 Linting Engine

- **`POST /api/v1/lint`** - Análisis completo de especificación OAS
  - Valida contra todos los rulesets habilitados (default)
  - Soporta filtrado por rulesets específicos vía header `X-Rulesets`
  - Retorna: issues (errores/warnings), métricas de validación, timestamps

### ⚙️ Toggle Mechanism (Zero-Downtime Configuration)

- **3-tier caching strategy:**
  - L1: In-memory cache (ConcurrentHashMap)
  - L2: H2 database (persistence)
  - L3: YAML configuration defaults (fallback)
- Cambios de configuración sin reiniciar servicio

---

## 📊 Rulesets Disponibles (14 total, 141 rules)

| Ruleset                                     | Reglas | Propósito                                 |
| ------------------------------------------- | ------ | ----------------------------------------- |
| **openapi-best-practices**                  | 12     | Estructura OAS, naming, REST patterns     |
| **api-documentation-best-practices**        | 8      | Documentación, descripcioness, ejemplos   |
| **authentication-security-best-practices**  | 11     | OAuth2, OpenID Connect, scopes            |
| **https-enforcement**                       | 5      | Validación HTTPS/TLS en URLs              |
| **owasp-api-security-top-10**               | 10     | Controles de seguridad OWASP Top 10       |
| **required-examples**                       | 6      | Ejemplos en schemas y responses           |
| **api-catalog-information-best-practices**  | 9      | Extensiones governance, domain, lifecycle |
| **reference-best-practices**                | 7      | Patrones MuleSoft Anypoint                |
| **reference-api-management-best-practices** | 11     | Rate limiting, health, resilience         |
| **agent-network-best-practices**            | 8      | Patrones agent-network específicos        |
| **datagraph-best-practices**                | 9      | Convenciones Datagraph                    |
| **grpc-best-practices**                     | 18     | Patrones y convenciones gRPC              |
| **asyncapi-best-practices**                 | 15     | Patrones AsyncAPI y eventos               |
| **sf-api-topic-action-enablement**          | 12     | Patrones Salesforce enablement            |

---

## 🏗️ Architecture

```
┌─────────────────────────────────────────────────────────┐
│                    REST API Endpoints                    │
│  /api/v1/lint  │  /api/v1/rulesets  │  /api/v1/health   │
└──────────────────────┬──────────────────────────────────┘
                       │
┌──────────────────────▼──────────────────────────────────┐
│              LintingService (Orchestrator)               │
│  ├─ getRules() → List<LintingRule>                      │
│  ├─ lint(spec) → LintingResult                          │
│  └─ lint(spec, rulesets) → filtered results             │
└──────────────────────┬──────────────────────────────────┘
                       │
┌──────────────────────▼──────────────────────────────────┐
│            LintingEngine (Rule Executor)                 │
│  ├─ getRules()           → 141 rules                    │
│  ├─ evaluateRule(rule)   → validation result            │
│  └─ evaluateAll(spec)    → aggregate results            │
└──────────────────────┬──────────────────────────────────┘
                       │
┌──────────────────────▼──────────────────────────────────┐
│  141 LintingRule Implementations (RulesetCatalog)        │
│  ├─ Security rules (15)                                 │
│  ├─ REST design rules (12)                              │
│  ├─ Documentation rules (8)                             │
│  ├─ gRPC/AsyncAPI/Salesforce rules (45)                 │
│  └─ Governance & platform-specific rules (61)           │
└──────────────────────┬──────────────────────────────────┘
                       │
          ┌────────────┴────────────┐
          │                         │
    ┌─────▼──────┐          ┌──────▼──────┐
    │  RulesetToggle      │  HealthCheck │
    │  Service (3-tier)   │  Service     │
    └─────┬──────┘          └──────┬──────┘
          │                        │
    ┌─────▼────────┐         ┌─────▼─────┐
    │ H2 Database  │         │ Service   │
    │ (Persistent) │         │ Metrics   │
    └──────────────┘         └───────────┘
```

---

## 🚀 Quick Start

### Prerequisites

- **JDK 21+**
- **Maven 3.9+**

### Build

```powershell
cd C:\stack\apps\copilot\api-linting
mvn clean package
```

### Run

```powershell
java -jar target/api-linting-0.0.1-SNAPSHOT.jar
```

Service starts on **http://localhost:8080**

### Test Health

```powershell
Invoke-WebRequest -Uri "http://localhost:8080/api/v1/health/status" -Method GET
```

---

## 📖 API Usage Examples

### Example 1: Lint OpenAPI Specification

```powershell
$spec = @{
  openapi = "3.0.0"
  info = @{ title = "My API"; version = "1.0.0" }
  paths = @{
    "/users" = @{
      get = @{
        summary = "List users"
        responses = @{ "200" = @{ description = "Success" } }
      }
    }
  }
} | ConvertTo-Json -Depth 10

Invoke-WebRequest -Uri "http://localhost:8080/api/v1/lint" `
  -Method POST -Body $spec -ContentType "application/json"
```

### Example 2: List Rulesets

```powershell
Invoke-WebRequest -Uri "http://localhost:8080/api/v1/rulesets" -Method GET | ConvertFrom-Json
```

### Example 3: Enable Specific Ruleset

```powershell
Invoke-WebRequest -Uri "http://localhost:8080/api/v1/rulesets/owasp-api-security-top-10/enable" -Method POST
```

### Example 4: Lint with Specific Rulesets

```powershell
Invoke-WebRequest -Uri "http://localhost:8080/api/v1/lint" `
  -Method POST -Body $spec -ContentType "application/json" `
  -Headers @{"X-Rulesets" = "openapi-best-practices,authentication-security-best-practices"}
```

---

## 📊 Current Status

| Aspecto                | Estado          | Detalles                                        |
| ---------------------- | --------------- | ----------------------------------------------- |
| **Linting Engine**     | ✅ OPERATIVO     | 141 rules, 14 rulesets                          |
| **Health Checks**      | ✅ OPERATIVO     | 4 endpoints (status, ready, live, dependencies) |
| **Ruleset Management** | ✅ OPERATIVO     | List, Get, Enable/Disable, Toggle               |
| **Tests**              | ✅ 52/52 PASADOS | All test suites passing                         |
| **Documentation**      | ✅ COMPLETA      | OpenAPI Swagger, guides, roadmap                |
| **CI/CD Ready**        | ✅ SÍ            | Ready for enterprise delivery pipelines         |

### Performance Metrics (Latest Build)

- **Build Time:** 37.4 seconds
- **Test Suite:** 52 tests ✅
- **JAR Size:** ~80 MB (with dependencies)
- **Startup Time:** ~5 seconds
- **Memory Usage:** ~200-300 MB (baseline)

---

## 📚 Documentation

| Documento                                  | Propósito                                          |
| ------------------------------------------ | -------------------------------------------------- |
| **FEATURES_AND_ROADMAP.md**                | Roadmap v1.0-v3.0, visión de gobernanza ejecutable |
| **RULES_TAXONOMY.md**                      | Catálogo completo de 141 reglas (nombre/propósito) |
| **api-linting-features-and-governance.md** | Features detallados y frameworks de gobernanza     |
| **OPERATIONAL_GUIDE.md**                   | Guía paso-a-paso para deployment y operación       |
| **cicd-integration-guide.md**              | Ejemplos CI/CD para pipelines de entrega           |
| **TEST_COMMANDS.md**                       | Comandos para ejecutar tests e validar endpoints   |

---

## 🧪 Testing

### Unit & Integration Tests

```powershell
cd C:\stack\apps\copilot\api-linting
mvn test
```

**Results:** 52/52 tests passing ✅

### Test Coverage

- **HealthControllerTest** - 5 tests for health endpoints
- **LintingControllerTest** - 15+ tests for linting core
- **RulesetToggleTest** - 4+ tests for toggle mechanism
- **LintingServiceTest** - 9+ tests for service logic
- **LintingEngineTest** - 19+ tests for rule execution
- 20 Integration tests with real OAS specifications

### Test Specifications

Included: 20 OAS test cases (low/medium/high complexity)
Location: `src/test/resources/oas/`

---

## 🔐 Security

- HTTPS enforcement validation (owasp-api-security-top-10 ruleset)
- Authentication/OAuth2 pattern detection
- Security header validation
- Injection vulnerability detection patterns
- Scope and permission model validation

---

## 🔄 Integration with CI/CD

### GitHub Actions Example

```yaml
- name: Lint OpenAPI with API Linting Service
  run: |
    curl -X POST http://api-linting:8080/api/v1/lint \
      -H "Content-Type: application/json" \
      -d @openapi.json > linting-report.json

    # Fail pipeline if critical issues found
    grep -q '"severity":"ERROR"' linting-report.json && exit 1 || exit 0
```

### Jenkins Pipeline Example

```groovy
stage('API Governance Check') {
  steps {
    sh '''
      curl -X POST http://api-linting:8080/api/v1/lint \
        -H "Content-Type: application/json" \
        --data-binary @openapi.yaml > report.json

      # Generate governance report
      python3 scripts/parse-linting-report.py report.json
    '''
  }
}
```

---

## 📈 Roadmap

### v1.0 (Current) - Advisory Linting ✅

- [x] 141 governance rules
- [x] 14 semantic rulesets
- [x] Advisory validation (reports issues, doesn't block)
- [x] Health checks & monitoring
- [x] Ruleset toggle mechanism
- [x] CI/CD integration examples

### v1.1 (Planned)

- [ ] Custom rule builder interface
- [ ] Rule import/export
- [ ] Batch linting (multiple specs)
- [ ] Advanced filtering & reporting

### v2.0 (Future) - Enforced Governance

- [ ] Deployment gates (block on errors)
- [ ] Severity levels & policies
- [ ] Automatic remediation suggestions
- [ ] Governance scoring & trends

### v3.0 (Future) - Executive Platform

- [ ] API inventory dashboard
- [ ] Compliance metrics & KPIs
- [ ] Governance trend analysis
- [ ] Federation & multi-org support

---

## 🛠️ Technology Stack

| Componente          | Tecnología        | Versión     |
| ------------------- | ----------------- | ----------- |
| **Framework**       | Spring Boot       | 3.3.6       |
| **Language**        | Java              | 21 (JDK 21) |
| **Build**           | Maven             | 3.9.12      |
| **Database**        | H2 (in-memory)    | Latest      |
| **JSON Processing** | Jackson           | 2.17.3      |
| **Documentation**   | OpenAPI/Swagger   | 3.0.0       |
| **Testing**         | JUnit 5 + MockMvc | Latest      |
| **Logging**         | SLF4J + Logback   | Latest      |

---

## 📦 Deployment

### Docker (Planned)

```dockerfile
FROM eclipse-temurin:21-jre
COPY target/api-linting-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
```

### Kubernetes (Planned)

- Health check probes configured
- Readiness: `/api/v1/health/ready`
- Liveness: `/api/v1/health/live`
- Custom startup probe: `/api/v1/health/status`

---

## 📞 Support & Contributions

**Issue Tracking:** GitHub Issues (jccejas-ayesa/api-linting)

**Documentation:** See `/docs` and `*.md` files in repository root

**Questions?** Review OPERATIONAL_GUIDE.md or TEST_COMMANDS.md

---

## 📄 License

Enterprise API Governance Service (Ayesa)

---

**Last Updated:** 2026-06-23  
**Version:** 1.0.0  
**Status:** ✅ Production Ready  
**Tests:** 52/52 PASSING  
**Build:** SUCCESS
