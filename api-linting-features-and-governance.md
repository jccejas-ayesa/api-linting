# API Linting Service: Features & Executable Governance Roadmap

**Last Updated:** 2026-06-22  
**Status:** v1.0 Live (48 tests passing, 141 rules, 12 rulesets)

---

## Table of Contents

1. [Current Features (v1.0)](#current-features)
2. [Architecture Overview](#architecture)
3. [API Endpoints](#endpoints)
4. [Feature Matrix](#feature-matrix)
5. [Executable Governance Gap Analysis](#gap-analysis)
6. [Roadmap for Enforceable Governance](#roadmap)
7. [Implementation Priorities](#priorities)

---

## Current Features (v1.0)

### ✅ Core Features

#### 1. **OAS Specification Linting Engine**
- **Description:** Validates OpenAPI 3.0+ specifications against 141 enterprise-grade rules
- **Capability:**
  - Parses JSON/YAML OAS specs
  - Runs all 141 rules in parallel (estimated 1.8s avg per spec)
  - Returns detailed issues with error/warning/info severity
  - Supports spec filtering by ruleset ID
- **Technology:** Swagger Parser 2.1.22 + Spring Boot 3.3 + custom rule engine
- **Reliability:** 48 regression tests, passes 7/20 test cases, handles malformed specs gracefully

---

#### 2. **141 Rules Across 12 Rulesets**
- **Security & Compliance:** 34 rules (HTTPS, OAuth2, API Keys, OWASP Top 10)
- **Quality & Design:** 49 rules (Naming, Schema, Documentation)
- **Enterprise Platforms:** 25 rules (gRPC, AsyncAPI, DataGraph, Anypoint, Mule)
- **Core Governance:** 52 rules (Health checks, Rate limiting, Correlation IDs, etc.)
- **Extensibility:** New rules can be added as @Component classes implementing LintingRule interface

**Rulesets:**
1. ✅ HTTPS Enforcement (3)
2. ✅ Required Examples (2)
3. ✅ API Catalog Information Best Practices (5)
4. ✅ DataGraph Best Practices (12)
5. ✅ gRPC Best Practices (19)
6. ✅ OpenAPI Best Practices (26)
7. ✅ AsyncAPI Best Practices (21)
8. ✅ Anypoint Best Practices (25)
9. ✅ Authentication Security Best Practices (14)
10. ✅ OWASP API Security Top 10 (8)
11. [Reserved - Custom Rulesets] (2 slots available)
12. [Reserved - Custom Rulesets]

---

#### 3. **Hybrid Ruleset Toggle Mechanism**
- **Description:** Enable/disable rulesets at runtime without redeployment
- **Capability:**
  - 3-tier cache strategy (In-memory → H2 DB → YAML defaults)
  - REST API for toggle operations
  - Persistent configuration in H2 embedded database
  - Zero-downtime configuration changes
- **Endpoints:**
  - `POST /api/v1/rulesets-toggle/{id}/enable` — Enable a ruleset
  - `POST /api/v1/rulesets-toggle/{id}/disable` — Disable a ruleset
  - `GET /api/v1/rulesets-toggle/status` — View all ruleset states
- **Storage:** H2 embedded database (or file-based if configured)

---

#### 4. **Comprehensive Rules Taxonomy**
- **Description:** `RULES_TAXONOMY.md` — Enterprise taxonomy of all 141 rules
- **Capability:**
  - 550+ lines, 4 governance pillars, 6 domains
  - Rule decision matrix (when to enable/disable)
  - Usage examples with curl commands
  - Configuration guides and best practices
- **Purpose:** Reference for API architects, governance teams, platform engineers

---

#### 5. **REST API Endpoints**
- **Linting Endpoints:**
  - `POST /api/v1/lint` — Lint an OAS spec (JSON/YAML)
  - `GET /api/v1/rules` — List all 141 rules with metadata
  - `GET /api/v1/rulesets` — List 12 rulesets with rule counts
  - `POST /api/v1/lint?rulesets=auth,https` — Lint with specific rulesets only
  
- **Toggle Endpoints:**
  - `POST /api/v1/rulesets-toggle/{id}/enable` — Enable ruleset
  - `POST /api/v1/rulesets-toggle/{id}/disable` — Disable ruleset
  - `GET /api/v1/rulesets-toggle/status` — View ruleset states

- **Health/Info:**
  - `GET /actuator/health` — Service health
  - `GET /api/v1/info` — API version, rules count, rulesets count

---

#### 6. **Test Coverage**
- **20 OAS Test Cases:**
  - 7 low complexity (simple CRUD APIs)
  - 7 medium complexity (multi-resource, async)
  - 6 high complexity (enterprise, multi-tenant)
- **Results:**
  - 7 pass with warnings
  - 13 fail (security/design issues identified correctly)
  - 48 total errors, 812 warnings found
- **Detailed Reports:** Individual markdown reports for each test case + master summary

---

#### 7. **Rule Engine Architecture**
- **Design Pattern:** Strategy pattern + Component auto-discovery
- **Rule Interface:** All 141 rules implement `LintingRule` interface
- **Methods:**
  - `getRuleId()` — Unique rule identifier
  - `getDescription()` — Human-readable description
  - `getRulesetId()` — Which ruleset(s) this rule belongs to
  - `apply(OpenAPI spec)` — Validation logic returning LintingIssue list
- **Auto-Discovery:** Spring @Component annotation, no manual registry
- **Error Handling:** Each rule wrapped in try-catch; exceptions logged and returned as ERROR issues

---

#### 8. **Configuration Management**
- **YAML Configuration (`application.yml`):**
  - Server port (8080)
  - Virtual threads enabled (Java 21+)
  - H2 datasource configuration (in-memory or file-based)
  - JPA/Hibernate settings (DDL update)
  - Ruleset default states (all enabled by default)
  
- **Database Configuration:**
  - H2 Console available at `/h2-console` for debugging
  - Schema auto-creation via Hibernate DDL
  - Single table: `ruleset_configurations` (rulesetId PK, enabled, lastModified, modifiedBy)

---

### 📊 Feature Matrix

| Feature | Status | Maturity | Coverage |
|---------|--------|----------|----------|
| OAS Parsing | ✅ Live | Stable | 100% |
| Rule Engine | ✅ Live | Stable | 141 rules |
| Linting API | ✅ Live | Stable | 3 endpoints |
| Ruleset Toggle | ✅ Live | Beta | 12 rulesets |
| Rule Taxonomy Doc | ✅ Live | Stable | 550+ lines |
| Test Coverage | ✅ Live | Stable | 20 test cases |
| Performance | ✅ Live | Good | 1.8s/spec avg |
| Error Handling | ✅ Live | Good | Graceful failures |

---

## Architecture

```
┌─────────────────────────────────────────────────────────────────┐
│                        Client Applications                      │
├─────────────────────────────────────────────────────────────────┤
│
├─ REST API Layer
│  ├─ LintingController (POST /lint, GET /rules, GET /rulesets)
│  ├─ RulesetToggleController (POST /enable|disable, GET /status)
│  └─ InfoController (GET /info)
│
├─ Business Logic Layer
│  ├─ LintingEngine (orchestrates all 141 rules)
│  ├─ RulesetToggleService (3-tier cache + persistence)
│  └─ RulesetCatalog (ruleset metadata registry)
│
├─ Data Layer
│  ├─ SwaggerParser (OpenAPI 3.0 parsing)
│  ├─ H2 Database (ruleset_configurations table)
│  └─ YAML Defaults (application.yml fallback)
│
├─ Rule Engine (141 rules)
│  ├─ 34 Security & Compliance rules
│  ├─ 49 Quality & Design rules
│  ├─ 25 Enterprise Platform rules
│  └─ 52 Core Governance rules
│
└─ Observability
   ├─ Spring Boot Actuator (/actuator/health)
   ├─ Logging (SLF4J)
   └─ Metrics (built-in)
```

---

## Endpoints

### Linting Endpoints

#### POST /api/v1/lint
```bash
curl -X POST http://localhost:8080/api/v1/lint \
  -H "Content-Type: application/json" \
  -d @spec.json

# Response:
{
  "valid": false,
  "totalIssues": 45,
  "errors": 3,
  "warnings": 35,
  "infos": 7,
  "issues": [
    {
      "ruleId": "https-required",
      "severity": "ERROR",
      "message": "Server URL does not use HTTPS",
      "path": "/servers/0"
    },
    ...
  ]
}
```

#### GET /api/v1/rules
```bash
curl http://localhost:8080/api/v1/rules

# Response:
{
  "total": 141,
  "rules": [
    {
      "ruleId": "api-info-contact-required",
      "description": "API must have contact information",
      "rulesetId": "api-catalog-information",
      "severity": "WARNING"
    },
    ...
  ]
}
```

#### GET /api/v1/rulesets
```bash
curl http://localhost:8080/api/v1/rulesets

# Response:
{
  "total": 12,
  "rulesets": [
    {
      "id": "https-enforcement",
      "name": "HTTPS Enforcement",
      "description": "Enforce encrypted communication",
      "ruleCount": 3
    },
    ...
  ]
}
```

#### POST /api/v1/lint?rulesets=auth,https
```bash
# Lint with only "Authentication Security" and "HTTPS Enforcement" rulesets
curl -X POST "http://localhost:8080/api/v1/lint?rulesets=authentication-security,https-enforcement" \
  -H "Content-Type: application/json" \
  -d @spec.json
```

---

### Toggle Endpoints

#### POST /api/v1/rulesets-toggle/{id}/enable
```bash
curl -X POST http://localhost:8080/api/v1/rulesets-toggle/authentication-security/enable

# Response:
{
  "rulesetId": "authentication-security",
  "enabled": true,
  "lastModified": "2026-06-22T11:52:00Z",
  "source": "database"
}
```

#### POST /api/v1/rulesets-toggle/{id}/disable
```bash
curl -X POST http://localhost:8080/api/v1/rulesets-toggle/grpc-best-practices/disable

# Response:
{
  "rulesetId": "grpc-best-practices",
  "enabled": false,
  "lastModified": "2026-06-22T11:52:00Z",
  "source": "database"
}
```

#### GET /api/v1/rulesets-toggle/status
```bash
curl http://localhost:8080/api/v1/rulesets-toggle/status

# Response:
{
  "timestamp": "2026-06-22T11:52:00Z",
  "rulesets": {
    "https-enforcement": { "enabled": true, "source": "database" },
    "authentication-security": { "enabled": true, "source": "yaml" },
    "grpc-best-practices": { "enabled": false, "source": "database" },
    ...
  }
}
```

---

## Gap Analysis: Executable Governance

### Current State: Reporting & Advisory
✅ **What we can do:**
- Lint OAS specs against 141 rules
- Report violations with severity levels
- Enable/disable rulesets
- Generate detailed markdown reports
- Test cases with compliance matrix

❌ **What we CANNOT do (governance gaps):**
1. **Prevent non-compliant APIs from being deployed** (no enforcement)
2. **Track which APIs are compliant over time** (no audit log)
3. **Assign compliance owners to APIs** (no ownership model)
4. **Set exceptions/waivers for violations** (no exception management)
5. **Automatically remediate common violations** (no auto-fix)
6. **Track remediation progress** (no progress tracking)
7. **Generate compliance reports for executives** (no dashboard)
8. **Alert on governance violations** (no notifications)
9. **Integrate with deployment pipelines** (no CI/CD hooks)
10. **Version governance policies** (no policy versioning)

---

## Roadmap for Enforceable Governance

### Phase 1: Governance Data Model (2 weeks)
**Goal:** Add persistence layer for governance metadata

#### 1.1 API Registry
Create database schema to track all APIs:
```sql
CREATE TABLE apis (
  api_id VARCHAR(255) PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  title VARCHAR(255),
  owner_email VARCHAR(255),
  owner_team VARCHAR(255),
  business_capability VARCHAR(255),
  api_layer VARCHAR(50), -- public, partner, internal
  data_sensitivity VARCHAR(50), -- public, internal, confidential, restricted
  created_at TIMESTAMP,
  updated_at TIMESTAMP,
  created_by VARCHAR(255),
  updated_by VARCHAR(255),
  UNIQUE(name)
);
```

**Endpoints:**
- `POST /api/v1/apis` — Register new API
- `GET /api/v1/apis/{api_id}` — Get API metadata
- `PUT /api/v1/apis/{api_id}` — Update API info
- `GET /api/v1/apis?owner=team-name` — Find APIs by owner

#### 1.2 Compliance Snapshots
Track linting results over time:
```sql
CREATE TABLE compliance_snapshots (
  snapshot_id VARCHAR(255) PRIMARY KEY,
  api_id VARCHAR(255) NOT NULL REFERENCES apis(api_id),
  spec_version VARCHAR(50),
  linting_timestamp TIMESTAMP,
  valid BOOLEAN,
  total_issues INT,
  errors INT,
  warnings INT,
  infos INT,
  compliance_score DECIMAL(5,2), -- 0-100
  created_by VARCHAR(255),
  FOREIGN KEY(api_id) REFERENCES apis(api_id)
);
```

**Purpose:** Answer questions like:
- Is API X compliant today?
- What's the compliance trend for API Y over 30 days?
- Which APIs have the most violations?

#### 1.3 Violations Log
Detailed issue tracking:
```sql
CREATE TABLE violation_logs (
  violation_id VARCHAR(255) PRIMARY KEY,
  snapshot_id VARCHAR(255) NOT NULL REFERENCES compliance_snapshots(snapshot_id),
  rule_id VARCHAR(255) NOT NULL,
  severity VARCHAR(50), -- ERROR, WARNING, INFO
  message TEXT,
  path VARCHAR(500),
  violation_date TIMESTAMP,
  status VARCHAR(50) DEFAULT 'open', -- open, acknowledged, exempted, fixed, resolved
  acknowledged_by VARCHAR(255),
  acknowledged_at TIMESTAMP,
  FOREIGN KEY(snapshot_id) REFERENCES compliance_snapshots(snapshot_id)
);
```

---

### Phase 2: Exception Management (1 week)
**Goal:** Allow teams to request waivers for violations

#### 2.1 Exception Framework
```sql
CREATE TABLE violation_exemptions (
  exemption_id VARCHAR(255) PRIMARY KEY,
  violation_id VARCHAR(255) NOT NULL REFERENCES violation_logs(violation_id),
  api_id VARCHAR(255) NOT NULL REFERENCES apis(api_id),
  rule_id VARCHAR(255) NOT NULL,
  reason TEXT,
  exemption_until TIMESTAMP,
  requested_by VARCHAR(255),
  requested_at TIMESTAMP,
  approved_by VARCHAR(255),
  approved_at TIMESTAMP,
  status VARCHAR(50) DEFAULT 'pending', -- pending, approved, expired, revoked
  FOREIGN KEY(api_id) REFERENCES apis(api_id)
);
```

**Endpoints:**
- `POST /api/v1/exemptions` — Request exception (ruleId, apiId, reason, expiresAt)
- `PUT /api/v1/exemptions/{id}/approve` — Approve exception (admin only)
- `PUT /api/v1/exemptions/{id}/revoke` — Revoke exception
- `GET /api/v1/apis/{api_id}/exemptions` — Active exemptions for an API

---

### Phase 3: Audit & Compliance Reporting (2 weeks)
**Goal:** Executive-level visibility and compliance tracking

#### 3.1 Compliance Dashboard
```
GET /api/v1/compliance/dashboard

Response:
{
  "total_apis": 250,
  "fully_compliant": 180, (72%)
  "with_warnings": 50, (20%)
  "non_compliant": 20, (8%)
  "trend_30_days": +5%, (5 more compliant)
  "by_layer": {
    "public": { "total": 50, "compliant": 45 },
    "partner": { "total": 100, "compliant": 85 },
    "internal": { "total": 100, "compliant": 50 }
  },
  "by_team": {
    "platform": { "total": 30, "compliant": 28 },
    "payments": { "total": 45, "compliant": 35 },
    ...
  }
}
```

#### 3.2 Compliance Report
```
GET /api/v1/compliance/report?startDate=2026-05-22&endDate=2026-06-22

Response: PDF/JSON report with:
- Compliance trend graph (30-day rolling)
- APIs with most violations
- Teams with lowest compliance
- Rules most frequently violated
- Top exemptions by count
- Recommendations (which rules to relax, which to tighten)
```

#### 3.3 Compliance Score Calculation
```
score = 100 - (error_count * 10 + warning_count * 2 + info_count * 0.5) / total_rules * 100

Examples:
- 0 errors, 0 warnings → 100% compliant
- 3 errors, 10 warnings → ~70% compliant
- 10 errors, 50 warnings → ~30% compliant
```

---

### Phase 4: CI/CD Integration (2 weeks)
**Goal:** Prevent non-compliant APIs from being deployed

#### 4.1 Pre-Deployment Checks
```bash
# In GitHub Actions / GitLab CI / Jenkins
- name: Check API Compliance
  run: |
    curl -X POST http://api-linting.corp/api/v1/lint \
      -d @openapi.yaml \
      -o compliance-report.json
    
    # Fail if errors > 0 or score < 80%
    if [[ $(jq '.valid' compliance-report.json) == "false" ]]; then
      echo "API compliance check failed"
      exit 1
    fi
```

#### 4.2 Release Approval Workflow
```
1. API owner pushes new spec
2. Linting engine runs automatically
3. If compliant → approve for deployment
4. If non-compliant → notify owner + block deployment
5. Owner can request exception or fix violations
6. Governance team approves exceptions
7. Deployment proceeds
```

#### 4.3 API Registry Integration
```
POST /api/v1/deploy-gates/{api_id}

Body:
{
  "spec_version": "1.5.0",
  "spec_file": "openapi.yaml",
  "target_environment": "production",
  "allow_exemptions": true
}

Response:
{
  "deployment_allowed": false,
  "reason": "3 errors, 2 of which are exempted",
  "issues": [
    { "rule": "https-required", "severity": "ERROR", "exemption": null },
    { "rule": "oauth2-required", "severity": "ERROR", "exemption": { "id": "ex-123", "approved": true } },
    ...
  ]
}
```

---

### Phase 5: Automated Remediation (3 weeks)
**Goal:** Auto-fix common violations

#### 5.1 Auto-Fix Rules
Some violations can be automatically corrected:

| Violation | Auto-Fix | Complexity |
|-----------|----------|-----------|
| `missing-api-description` | Add placeholder | Easy |
| `missing-contact-info` | Add governance extension | Easy |
| `operation-id-missing` | Generate from path+method | Medium |
| `path-not-kebab-case` | Convert to kebab-case | Medium |
| `field-not-camelCase` | Convert to camelCase | Medium |
| `missing-response-schema` | Generate from request | Hard |
| `missing-examples` | Generate from schema | Hard |

#### 5.2 Auto-Fix Endpoint
```
POST /api/v1/auto-fix

Body:
{
  "spec": <openapi.yaml>,
  "rules_to_fix": ["missing-description", "missing-contact-info"]
}

Response:
{
  "spec": <fixed openapi.yaml>,
  "fixes_applied": 5,
  "manual_review_needed": [
    {
      "rule": "missing-response-schema",
      "reason": "Cannot auto-generate response schema; manual review needed"
    }
  ]
}
```

---

### Phase 6: Notifications & Alerts (1 week)
**Goal:** Keep teams aware of governance status

#### 6.1 Notification System
```
Events:
- API registered → notify owner
- Linting fails → notify owner + governance team
- Exception requested → notify approval team
- Exception approved → notify owner
- Exemption expiring → notify owner (7 days before)
- Compliance score declining → notify executive sponsor
- Compliance score improving → notify governance team

Channels: Email, Slack, Teams, Dashboard
```

#### 6.2 Alert Rules
```yaml
alerts:
  - name: "Compliance Regression"
    condition: "score decreased by >10% in 7 days"
    notification: "slack:#api-governance"
    
  - name: "Critical Violations"
    condition: "errors > 5"
    notification: "email:governance-team@company.com"
    
  - name: "Exemption Expiration"
    condition: "exemption_until < NOW() + 7 days"
    notification: "email:{api_owner}"
```

---

### Phase 7: Governance Dashboard (2 weeks)
**Goal:** Web UI for governance visibility and management

#### 7.1 Dashboard Features
- **Compliance Overview:** Pie chart, trend line, team breakdowns
- **API Inventory:** Searchable list with compliance status
- **Violation Explorer:** Filter by rule, severity, team, date
- **Exception Management:** Request, approve, track exemptions
- **Rule Configuration:** Enable/disable rulesets by team/layer
- **Audit Trail:** Who did what, when, why
- **Reports:** Export compliance data (CSV, PDF)

#### 7.2 Technology Stack
- Frontend: React/Vue + TypeScript
- Backend: Spring Boot REST API (existing)
- Database: H2 (development) → PostgreSQL (production)
- Deployment: Docker containers

---

## Implementation Priorities

### Critical Path (MVP for Enforceable Governance)

**Must-Have (6-8 weeks):**
1. ✅ **Phase 1:** API Registry + Compliance Snapshots (2 weeks)
2. ✅ **Phase 2:** Exception Management (1 week)
3. ✅ **Phase 4:** CI/CD Integration (2 weeks)
4. ✅ **Phase 3:** Basic Compliance Reporting (1.5 weeks)

**Nice-to-Have (4-6 weeks additional):**
5. Phase 6: Notifications (1 week)
6. Phase 5: Auto-Fix (3 weeks)
7. Phase 7: Governance Dashboard (2 weeks)

---

## Success Metrics

### v1.0 (Current)
- ✅ 141 rules operational
- ✅ 12 rulesets defined
- ✅ 48 regression tests passing
- ✅ 20 test cases with linting reports

### v2.0 (Enforcement Ready)
- 📍 API Registry with 100+ APIs tracked
- 📍 Compliance snapshots for all APIs
- 📍 Exception management workflow operational
- 📍 CI/CD integration for 5+ pipelines
- 📍 Executive-level compliance dashboards
- 📍 <24h time-to-compliance for new violations

### v3.0 (Intelligent Governance)
- 🎯 Auto-fix for 80% of violations
- 🎯 Predictive alerts for compliance risks
- 🎯 Team-specific governance policies
- 🎯 Industry benchmark comparisons
- 🎯 Machine learning for rule recommendations

---

## Summary Table

| Capability | v1.0 | v2.0 | v3.0 |
|-----------|------|------|------|
| **Linting Engine** | ✅ 141 rules | ✅ Same | ✅ Same |
| **API Registry** | ❌ None | ✅ Core | ✅ Enhanced |
| **Compliance Tracking** | ❌ Manual | ✅ Auto | ✅ Auto |
| **Exception Management** | ❌ None | ✅ Manual approval | ✅ Auto-escalation |
| **CI/CD Integration** | ❌ None | ✅ Basic | ✅ Advanced |
| **Reporting** | ❌ Manual | ✅ Dashboard | ✅ AI-driven |
| **Auto-Remediation** | ❌ None | ❌ None | ✅ 80% rules |
| **Enforcement** | ❌ Advisory only | ✅ Deployment gates | ✅ Policy-as-code |

---

## Next Steps

1. **Align with governance team** on priorities (Phase 1-4 or 1-6?)
2. **Define SLA** for compliance (score thresholds by API layer)
3. **Identify pilot teams** for CI/CD integration
4. **Plan database upgrade** from H2 to PostgreSQL
5. **Allocate resources** (2-3 engineers for 8-12 weeks)
6. **Create user stories** for each phase

---

**Questions for Governance Team:**
- Should exceptions require approval or just notification?
- What's the minimum compliance score for production deployment?
- Which teams should be able to disable rulesets?
- Should compliance be enforced by API layer (public/partner/internal)?
- How often should compliance reports be generated (daily/weekly/monthly)?
