# API Linting v1.0 - Guía Operacional

## 📋 Tabla de Contenidos
1. [Precondiciones](#precondiciones)
2. [Subir el Servicio](#1-subir-el-servicio)
3. [Probar el Servicio](#2-probar-el-servicio)
4. [Bajar el Servicio](#3-bajar-el-servicio)
5. [Troubleshooting](#troubleshooting)

---

## Precondiciones

### Directorios Requeridos
```
C:\toolbox\jdk\jdk-21.0.6+7          (JDK 21)
C:\stack\maven\apache-maven-3.9.12   (Maven 3.9.12)
```

### Verificar Instalación
```powershell
java -version
mvn -version
```

---

## 1. SUBIR EL SERVICIO

### Opción A: Compilar + Ejecutar (Recomendado para DEV)

**Paso 1: Establecer variables de entorno**
```powershell
$env:JAVA_HOME="C:\toolbox\jdk\jdk-21.0.6+7"
$env:PATH="$env:JAVA_HOME\bin;$env:PATH"
$env:M2_HOME="C:\stack\maven\apache-maven-3.9.12"
$env:PATH="$env:M2_HOME\bin;$env:PATH"
```

**Paso 2: Navegar al directorio del proyecto**
```powershell
cd C:\Users\jccejas\.copilot\repos\copilot-worktrees\api-linting\jccejas-ayesa-scaling-waddle
```

**Paso 3: Compilar**
```powershell
mvn clean package -DskipTests
```
**Salida esperada:**
```
[INFO] BUILD SUCCESS
[INFO] Total time: 8-10 s
[INFO] Building jar: .../target/api-linting-0.0.1-SNAPSHOT.jar
```

**Paso 4: Ejecutar**
```powershell
java -jar target/api-linting-0.0.1-SNAPSHOT.jar
```

**Salida esperada:**
```
2026-06-22T13:24:14.989+02:00  INFO ... : Tomcat started on port 8080 (http)
2026-06-22T13:24:14.989+02:00  INFO ... : Started Application in 6.7 seconds
```

---

### Opción B: Solo Ejecutar (Si JAR ya existe)

```powershell
$env:JAVA_HOME="C:\toolbox\jdk\jdk-21.0.6+7"
$env:PATH="$env:JAVA_HOME\bin;$env:PATH"

cd C:\Users\jccejas\.copilot\repos\copilot-worktrees\api-linting\jccejas-ayesa-scaling-waddle

java -jar target/api-linting-0.0.1-SNAPSHOT.jar
```

---

## 2. PROBAR EL SERVICIO

### Abrir NUEVA terminal (NO cierres la anterior donde corre el servicio)

### Test 1: Health Check
```powershell
Invoke-WebRequest -Uri "http://localhost:8080/actuator/health"
```
**Esperado:** `{"status":"UP"}`

---

### Test 2: Listar Rulesets
```powershell
$response = Invoke-WebRequest -Uri "http://localhost:8080/api/v1/rulesets"
$rulesets = $response.Content | ConvertFrom-Json
Write-Output "Rulesets: $($rulesets.Length) encontrados"
$rulesets | Select-Object -First 2 | ForEach-Object { 
  Write-Output "• $($_.id): $($_.rules.Length) rules"
}
```
**Esperado:** 12 rulesets

---

### Test 3: Linting Simple (SIN ERRORES)

```powershell
$jsonBody = @{
  content = @"
openapi: 3.0.0
info:
  title: Test API
  version: 1.0.0
  license:
    name: MIT
    url: https://opensource.org/licenses/MIT
  contact:
    name: API Support
    url: https://example.com
  description: A valid test API
servers:
  - url: https://api.example.com/api/v1
paths:
  /users:
    get:
      operationId: get-users
      tags:
        - Users
      description: List users
      responses:
        '200':
          description: Success
          content:
            application/json:
              schema:
                type: array
                items:
                  type: object
                  properties:
                    id:
                      type: string
                      description: User ID
                    name:
                      type: string
                      description: User name
                  required:
                    - id
                    - name
                  example:
                    id: "123"
                    name: "John Doe"
"@
} | ConvertTo-Json

$response = Invoke-WebRequest -Uri "http://localhost:8080/api/v1/lint" `
  -Method POST `
  -Headers @{"Content-Type"="application/json"} `
  -Body $jsonBody

$result = $response.Content | ConvertFrom-Json
Write-Output "Valid: $($result.valid)"
Write-Output "Errors: $($result.errors)"
Write-Output "Warnings: $($result.warnings)"
Write-Output "Total Issues: $($result.totalIssues)"
```

**Esperado:** Menos de 10 warnings, 0 errors

---

### Test 4: Linting con Archivo YAML Existente

```powershell
# Usar test case #1 (bajo nivel de complejidad)
$yaml = Get-Content "test-cases/tc-001.yaml" -Raw
$jsonBody = @{ content = $yaml } | ConvertTo-Json

$response = Invoke-WebRequest -Uri "http://localhost:8080/api/v1/lint" `
  -Method POST `
  -Headers @{"Content-Type"="application/json"} `
  -Body $jsonBody

$result = $response.Content | ConvertFrom-Json
Write-Output "Errors: $($result.errors), Warnings: $($result.warnings)"
```

---

### Test 5: Toggle Ruleset (Desabilitar HTTPS)

**Desabilitar:**
```powershell
$body = @{
  rulesetId = "https-enforcement"
} | ConvertTo-Json

Invoke-WebRequest -Uri "http://localhost:8080/api/v1/rulesets-toggle/disable" `
  -Method POST `
  -Headers @{"Content-Type"="application/json"} `
  -Body $body
```
**Esperado:** Status 200

**Ver Estado:**
```powershell
Invoke-WebRequest -Uri "http://localhost:8080/api/v1/rulesets-toggle/status"
```
**Esperado:** `https-enforcement: false`

**Re-habilitar:**
```powershell
$body = @{
  rulesetId = "https-enforcement"
} | ConvertTo-Json

Invoke-WebRequest -Uri "http://localhost:8080/api/v1/rulesets-toggle/enable" `
  -Method POST `
  -Headers @{"Content-Type"="application/json"} `
  -Body $body
```

---

## 3. BAJAR EL SERVICIO

### Opción A: Presionar Ctrl+C en la terminal donde corre

```
[PRESIONAR] Ctrl + C
```

**Salida esperada:**
```
2026-06-22T13:41:00.000+02:00  INFO ... : Shutting down
2026-06-22T13:41:01.000+02:00  INFO ... : Application stopped
```

---

### Opción B: Matar proceso desde otra terminal

```powershell
# Listar procesos Java
Get-Process java

# Matar específico (usar PID de arriba)
Stop-Process -Id <PID> -Force

# O matar todos los Java
& $env:ComSpec /c "taskkill /F /IM java.exe"
```

---

## CICLO COMPLETO (Copy-Paste Ready)

### 📝 SCRIPT: INICIAR SERVICIO

```powershell
# ===== TERMINAL 1: START SERVICE =====

$env:JAVA_HOME="C:\toolbox\jdk\jdk-21.0.6+7"
$env:PATH="$env:JAVA_HOME\bin;$env:PATH"
$env:M2_HOME="C:\stack\maven\apache-maven-3.9.12"
$env:PATH="$env:M2_HOME\bin;$env:PATH"

cd C:\Users\jccejas\.copilot\repos\copilot-worktrees\api-linting\jccejas-ayesa-scaling-waddle

Write-Output "🔨 Compilando..."
mvn clean package -DskipTests

Write-Output ""
Write-Output "🚀 Iniciando servicio..."
Write-Output "   → Presiona Ctrl+C para detener"
Write-Output ""

java -jar target/api-linting-0.0.1-SNAPSHOT.jar
```

---

### 📝 SCRIPT: TESTS

```powershell
# ===== TERMINAL 2: TESTS =====

Write-Output "⏳ Esperando que inicie el servicio (5s)..."
Start-Sleep -Seconds 5

Write-Output ""
Write-Output "🧪 TEST 1: Health"
try {
  $r = Invoke-WebRequest "http://localhost:8080/actuator/health" -ErrorAction Stop
  Write-Output "   ✅ $($r.StatusCode) - $((($r.Content | ConvertFrom-Json).status))"
} catch {
  Write-Output "   ❌ Error: $($_.Exception.Message)"
}

Write-Output ""
Write-Output "🧪 TEST 2: Rulesets"
try {
  $r = Invoke-WebRequest "http://localhost:8080/api/v1/rulesets" -ErrorAction Stop
  $rulesets = $r.Content | ConvertFrom-Json
  Write-Output "   ✅ $($rulesets.Length) rulesets encontrados"
} catch {
  Write-Output "   ❌ Error: $($_.Exception.Message)"
}

Write-Output ""
Write-Output "🧪 TEST 3: Lint Simple"
try {
  $json = @{content = "openapi: 3.0.0`ninfo:`n  title: Test`n  version: 1.0.0`n  license:`n    name: MIT`n  contact:`n    name: Support`n  description: Test`nservers:`n  - url: https://api.example.com/api/v1`npaths:`n  /test:`n    get:`n      operationId: test-get`n      tags:`n        - Test`n      description: Test`n      responses:`n        '200':`n          description: OK`n          content:`n            application/json:`n              schema:`n                type: object`n                properties:`n                  id:`n                    type: string`n                    description: ID`n                    example: '123'"} | ConvertTo-Json
  $r = Invoke-WebRequest "http://localhost:8080/api/v1/lint" -Method POST -Headers @{"Content-Type"="application/json"} -Body $json -ErrorAction Stop
  $result = $r.Content | ConvertFrom-Json
  Write-Output "   ✅ Valid: $($result.valid), Errors: $($result.errors), Warnings: $($result.warnings)"
} catch {
  Write-Output "   ❌ Error: $($_.Exception.Message)"
}

Write-Output ""
Write-Output "✅ TESTS COMPLETADOS"
```

---

### 📝 SCRIPT: DETENER SERVICIO

```powershell
# ===== Opción 1: NATURAL (Ctrl+C en Terminal 1) =====
# [PRESIONAR] Ctrl + C en la terminal donde corre el servicio


# ===== Opción 2: FORZADO =====
$env:ComSpec /c "taskkill /F /IM java.exe"
Write-Output "✅ Servicio detenido"
```

---

## URLS DE REFERENCIA

| Servicio | URL |
|----------|-----|
| **Health** | http://localhost:8080/actuator/health |
| **Metrics** | http://localhost:8080/actuator/metrics |
| **H2 Console** | http://localhost:8080/h2-console |
| **Swagger UI** | http://localhost:8080/swagger-ui.html |
| **API Docs** | http://localhost:8080/v3/api-docs |

---

## PUERTOS & CONFIGURACIÓN

### Puerto por defecto
```
8080
```

### Cambiar puerto (editar src/main/resources/application.yml)
```yaml
server:
  port: 9090  # Cambiar aquí
```
Luego: `mvn clean package -DskipTests` y compilar de nuevo

---

## TROUBLESHOOTING

### ❌ Error: "No se puede conectar con el servidor"
**Causa:** Servicio no inició correctamente

**Solución:**
```powershell
# Ver logs detallados
java -jar target/api-linting-0.0.1-SNAPSHOT.jar 2>&1 | head -50

# Verificar que el puerto esté libre
netstat -ano | findstr :8080

# Matar proceso en puerto 8080 (si existe otro)
& $env:ComSpec /c "netstat -ano | findstr :8080"
# Luego: Stop-Process -Id <PID> -Force
```

---

### ❌ Error: "BUILD FAILURE"
**Causa:** Problemas de compilación

**Solución:**
```powershell
# Limpiar cache Maven
mvn clean

# Recompilar
mvn clean package -DskipTests

# Si persiste, verificar:
java -version          # ¿JDK 21?
mvn -version           # ¿Maven 3.9+?
```

---

### ❌ Error: "Cannot find pom.xml"
**Causa:** Directorio incorrecto

**Solución:**
```powershell
# Verificar ubicación
cd C:\Users\jccejas\.copilot\repos\copilot-worktrees\api-linting\jccejas-ayesa-scaling-waddle
ls -la pom.xml
```

---

## COMANDOS RÁPIDOS (Cheat Sheet)

```powershell
# Set env
$env:JAVA_HOME="C:\toolbox\jdk\jdk-21.0.6+7"; $env:PATH="$env:JAVA_HOME\bin;$env:PATH"; $env:M2_HOME="C:\stack\maven\apache-maven-3.9.12"; $env:PATH="$env:M2_HOME\bin;$env:PATH"

# Compile
cd C:\Users\jccejas\.copilot\repos\copilot-worktrees\api-linting\jccejas-ayesa-scaling-waddle && mvn clean package -DskipTests

# Run
java -jar target/api-linting-0.0.1-SNAPSHOT.jar

# Health
Invoke-WebRequest "http://localhost:8080/actuator/health"

# Kill
& $env:ComSpec /c "taskkill /F /IM java.exe"
```

---

## RESUMEN

| Acción | Comando |
|--------|---------|
| **Subir** | `mvn clean package -DskipTests` → `java -jar target/api-linting-0.0.1-SNAPSHOT.jar` |
| **Testear** | `Invoke-WebRequest "http://localhost:8080/actuator/health"` |
| **Bajar** | `Ctrl+C` o `taskkill /F /IM java.exe` |

---

**Documento generado:** 2026-06-22
**Versión API:** v1.0
**Estado:** Production-Ready
