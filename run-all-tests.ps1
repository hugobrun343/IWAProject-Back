# PowerShell script to run tests for all microservices
# Usage: .\run-all-tests.ps1 [TestType]
# TestType: unit (default), integration, all, quality

param(
    [string]$TestType = "unit"
)

# Set correct JAVA_HOME for Java 21
$JavaHome = "C:\Program Files\Eclipse Adoptium\jdk-21.0.8.9-hotspot"
if (Test-Path $JavaHome) {
    $env:JAVA_HOME = $JavaHome
    Write-Host "Set JAVA_HOME to: $JavaHome" -ForegroundColor Cyan
} else {
    Write-Warning "Java 21 not found at expected location: $JavaHome"
    Write-Warning "Please ensure Java 21 is installed and update the script with the correct path"
}

# Services to test
$Services = @(
    "Gateway-Service",
    "User-Service", 
    "Announcement-Service",
    "Application-Service",
    "Chat-Service",
    "Favorite-Service",
    "Log-Service",
    "Payment-Service",
    "Rating-Service"
)

# Colors for output
function Write-Success { param($Message) Write-Host "[SUCCESS] $Message" -ForegroundColor Green }
function Write-Error { param($Message) Write-Host "[ERROR] $Message" -ForegroundColor Red }
function Write-Info { param($Message) Write-Host "[INFO] $Message" -ForegroundColor Blue }
function Write-Warning { param($Message) Write-Host "[WARNING] $Message" -ForegroundColor Yellow }

# Function to run tests for a service
function Test-Service {
    param(
        [string]$ServiceName,
        [string]$TestType
    )
    
    Write-Info "Running $TestType tests for $ServiceName..."
    
    if (-not (Test-Path $ServiceName)) {
        Write-Error "Directory $ServiceName not found"
        return $false
    }
    
    Push-Location $ServiceName
    
    try {
        $exitCode = 0
        switch ($TestType) {
            "unit" {
                & .\mvnw.cmd clean test -q
                $exitCode = $LASTEXITCODE
            }
            "integration" {
                & .\mvnw.cmd clean verify -Pintegration-tests -q
                $exitCode = $LASTEXITCODE
            }
            "all" {
                & .\mvnw.cmd clean verify -Pall-tests -q
                $exitCode = $LASTEXITCODE
            }
            "quality" {
                & .\mvnw.cmd clean verify -Pquality -q
                $exitCode = $LASTEXITCODE
            }
            default {
                Write-Error "Unknown test type: $TestType"
                return $false
            }
        }
        
        Write-Host "Maven exit code for $ServiceName`: $exitCode" -ForegroundColor DarkGray
        
        if ($exitCode -eq 0) {
            Write-Success "$ServiceName tests passed"
            return $true
        } else {
            Write-Error "$ServiceName tests failed (exit code: $exitCode)"
            return $false
        }
    }
    finally {
        Pop-Location
    }
}

# Function to generate test report
function New-TestReport {
    param(
        [array]$PassedServices,
        [array]$FailedServices,
        [string]$TestType
    )
    
    Write-Info "Generating test report..."
    
    $Report = @"
# Test Report - $(Get-Date)

## Test Results

| Service | Status | Coverage |
|---------|--------|----------|
"@
    
    foreach ($Service in $Services) {
        $JacocoPath = "$Service\target\site\jacoco\index.html"
        $SurefirePath = "$Service\target\surefire-reports"
        
        if (Test-Path $JacocoPath) {
            # Extract coverage percentage (simplified approach)
            $Coverage = "N/A"
            try {
                $Content = Get-Content $JacocoPath -Raw
                if ($Content -match '(\d+)%') {
                    $Coverage = $Matches[1] + "%"
                }
            } catch {
                $Coverage = "N/A"
            }
            $Status = if ($Service -in $PassedServices) { "✅" } else { "❌" }
            $Report += "`n| $Service | $Status | $Coverage |"
        } elseif (Test-Path $SurefirePath) {
            $Status = if ($Service -in $PassedServices) { "✅" } else { "❌" }
            $Report += "`n| $Service | $Status | N/A |"
        } else {
            $Report += "`n| $Service | ❌ | N/A |"
        }
    }
    
    $Report | Out-File -FilePath "test-report.md" -Encoding UTF8
    Write-Success "Test report generated: test-report.md"
}

# Main execution
function Main {
    Write-Info "Starting $TestType tests for all microservices..."
    Write-Info "Services to test: $($Services -join ', ')"
    
    $PassedServices = @()
    $FailedServices = @()
    
    foreach ($Service in $Services) {
        if (Test-Path $Service) {
            $testResult = Test-Service -ServiceName $Service -TestType $TestType
            # Write-Host "Test result for $Service`: $testResult" -ForegroundColor DarkGray
            if ($testResult -eq $true) {
                $PassedServices += $Service
                Write-Host "Added $Service to passed services. Current passed count: $($PassedServices.Count)" -ForegroundColor DarkGray
            } else {
                $FailedServices += $Service
                Write-Host "Added $Service to failed services. Current failed count: $($FailedServices.Count)" -ForegroundColor DarkGray
            }
        } else {
            Write-Warning "Service directory $Service not found, skipping..."
            $FailedServices += $Service
        }
    }
    
    # Summary
    Write-Host ""
    Write-Info "=== TEST SUMMARY ==="
    Write-Host "Test Type: $TestType"
    Write-Host "Total Services: $($Services.Count)"
    Write-Host "Passed: $($PassedServices.Count)"
    Write-Host "Failed: $($FailedServices.Count)"
    
    if ($PassedServices.Count -gt 0) {
        Write-Success "Passed services: $($PassedServices -join ', ')"
    }
    
    if ($FailedServices.Count -gt 0) {
        Write-Error "Failed services: $($FailedServices -join ', ')"
        # Generate report even with failures to show complete status
        New-TestReport -PassedServices $PassedServices -FailedServices $FailedServices -TestType $TestType
        Write-Error "Some tests failed! Check the output above for details."
        exit 1
    } else {
        # Generate report only if all tests passed
        New-TestReport -PassedServices $PassedServices -FailedServices $FailedServices -TestType $TestType
        Write-Success "All tests completed successfully!"
    }
}

# Show usage if help is requested
if ($TestType -eq "--help" -or $TestType -eq "-h") {
    Write-Host "Usage: .\run-all-tests.ps1 [TestType]"
    Write-Host ""
    Write-Host "Test types:"
    Write-Host "  unit        - Run unit tests only (default)"
    Write-Host "  integration - Run integration tests only"
    Write-Host "  all         - Run all tests (unit + integration)"
    Write-Host "  quality     - Run quality checks (static analysis, style)"
    Write-Host ""
    Write-Host "Examples:"
    Write-Host "  .\run-all-tests.ps1              # Run unit tests"
    Write-Host "  .\run-all-tests.ps1 integration  # Run integration tests"
    Write-Host "  .\run-all-tests.ps1 all          # Run all tests"
    Write-Host "  .\run-all-tests.ps1 quality      # Run quality checks"
    exit 0
}

# Validate test type
$ValidTestTypes = @("unit", "integration", "all", "quality")
if ($TestType -notin $ValidTestTypes) {
    Write-Error "Invalid test type: $TestType"
    Write-Host "Valid test types: $($ValidTestTypes -join ', ')"
    exit 1
}

# Run main function
Main