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
        $outputFile = "maven-output.log"
        
        switch ($TestType) {
            "unit" {
                & .\mvnw.cmd clean test -q 2>&1 | Tee-Object -FilePath $outputFile
                $exitCode = $LASTEXITCODE
            }
            "integration" {
                & .\mvnw.cmd clean verify -Pintegration-tests -q 2>&1 | Tee-Object -FilePath $outputFile
                $exitCode = $LASTEXITCODE
            }
            "all" {
                & .\mvnw.cmd clean verify -Pall-tests -q 2>&1 | Tee-Object -FilePath $outputFile
                $exitCode = $LASTEXITCODE
            }
            "quality" {
                & .\mvnw.cmd clean verify -Pquality -q 2>&1 | Tee-Object -FilePath $outputFile
                $exitCode = $LASTEXITCODE
            }
            default {
                Write-Error "Unknown test type: $TestType"
                return $false
            }
        }
        
        Write-Host "Maven exit code for $ServiceName`: $exitCode" -ForegroundColor DarkGray
        
        # Additional verification using surefire reports
        $surefireDir = "target\surefire-reports"
        if (Test-Path $surefireDir) {
            $testStats = Get-TestStats -SurefirePath $surefireDir
            $hasFailures = $testStats.Failures -gt 0 -or $testStats.Errors -gt 0
            
            if ($exitCode -eq 0 -and -not $hasFailures) {
                Write-Success "$ServiceName tests passed ($($testStats.Tests) tests)"
                return $true
            } elseif ($hasFailures) {
                Write-Error "$ServiceName tests failed ($($testStats.Failures) failures, $($testStats.Errors) errors)"
                return $false
            }
        }
        
        if ($exitCode -eq 0) {
            Write-Success "$ServiceName tests passed"
            return $true
        } else {
            Write-Error "$ServiceName tests failed (exit code: $exitCode)"
            return $false
        }
    }
    catch {
        Write-Error "Exception running tests for $ServiceName`: $_"
        return $false
    }
    finally {
        # Clean up temp files
        if (Test-Path "maven-output.log") {
            Remove-Item "maven-output.log" -ErrorAction SilentlyContinue
        }
        Pop-Location
    }
}

# Function to extract coverage from JaCoCo report
function Get-CoverageFromJacoco {
    param([string]$ServicePath)
    
    # Try different possible paths for JaCoCo reports
    $possiblePaths = @(
        "$ServicePath\target\site\jacoco\index.html",
        "$ServicePath\target\jacoco-report\index.html",
        "$ServicePath\target\reports\jacoco\index.html"
    )
    
    foreach ($jacocoPath in $possiblePaths) {
        if (Test-Path $jacocoPath) {
            try {
                $Content = Get-Content $jacocoPath -Raw -Encoding UTF8
                # Look for Total row in tfoot with percentage
                if ($Content -match '<tfoot>.*?<td[^>]*>Total</td>.*?<td[^>]*>(\d+)[^>]*%</td>') {
                    return $Matches[1] + "%"
                }
                # Alternative simpler pattern
                if ($Content -match 'Total.*?(\d+).*?%') {
                    return $Matches[1] + "%"
                }
                # Look for first percentage in the HTML
                if ($Content -match '(\d+)\s*%') {
                    return $Matches[1] + "%"
                }
            } catch {
                Write-Warning "Error reading coverage from $jacocoPath`: $_"
            }
        }
    }
    
    # Try to check if jacoco.exec file exists (indicates coverage was collected)
    $execFile = "$ServicePath\target\jacoco.exec"
    if (Test-Path $execFile) {
        return "Collected*"
    }
    
    return "N/A"
}

# Function to get test statistics from surefire reports
function Get-TestStats {
    param([string]$SurefirePath)
    
    if (-not (Test-Path $SurefirePath)) {
        return @{ Tests = 0; Failures = 0; Errors = 0; Skipped = 0 }
    }
    
    $stats = @{ Tests = 0; Failures = 0; Errors = 0; Skipped = 0 }
    
    try {
        $xmlFiles = Get-ChildItem -Path $SurefirePath -Filter "TEST-*.xml" -ErrorAction SilentlyContinue
        foreach ($xmlFile in $xmlFiles) {
            [xml]$xml = Get-Content $xmlFile.FullName -Encoding UTF8
            if ($xml.testsuite) {
                $stats.Tests += [int]$xml.testsuite.tests
                $stats.Failures += [int]$xml.testsuite.failures
                $stats.Errors += [int]$xml.testsuite.errors
                $stats.Skipped += [int]$xml.testsuite.skipped
            }
        }
    } catch {
        Write-Warning "Error reading test stats from $SurefirePath`: $_"
    }
    
    return $stats
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
# Test Report - $(Get-Date -Format "yyyy-MM-dd HH:mm:ss")

## Test Type: $TestType

## Test Results Summary
- **Total Services**: $($Services.Count)
- **Passed**: $($PassedServices.Count)
- **Failed**: $($FailedServices.Count)

## Detailed Results

| Service | Status | Tests | Failures | Errors | Coverage |
|---------|--------|-------|----------|--------|----------|
"@
    
    foreach ($Service in $Services) {
        $JacocoPath = "$Service\target\site\jacoco\index.html"
        $SurefirePath = "$Service\target\surefire-reports"
        
        # Determine status with ASCII characters for better compatibility
        $Status = if ($Service -in $PassedServices) { "PASS" } else { "FAIL" }
        $StatusIcon = if ($Service -in $PassedServices) { "+" } else { "-" }
        
        # Get test statistics
        $stats = Get-TestStats -SurefirePath $SurefirePath
        $testInfo = "$($stats.Tests)"
        $failureInfo = "$($stats.Failures)"
        $errorInfo = "$($stats.Errors)"
        
        # Get coverage - pass the service path instead of specific file
        $Coverage = Get-CoverageFromJacoco -ServicePath $Service
        
        # Add row to report
        $StatusDisplay = "$StatusIcon $Status"
        $Report += "`n| $Service | $StatusDisplay | $testInfo | $failureInfo | $errorInfo | $Coverage |"
    }
    
    # Add footer with details
    $PassedList = if ($PassedServices.Count -gt 0) { $PassedServices | ForEach-Object { "- $_" } } else { "- None" }
    $FailedList = if ($FailedServices.Count -gt 0) { $FailedServices | ForEach-Object { "- $_" } } else { "- None" }
    
    $Report += @"

## Test Execution Details

### Passed Services
$($PassedList -join "`n")

### Failed Services
$($FailedList -join "`n")

## Notes
- **Coverage**: 
  - N/A = JaCoCo not configured or no report generated
  - Collected* = Coverage data collected but report not generated
  - Percentage = Actual coverage percentage from JaCoCo report
- **Status**: + PASS = All tests passed, - FAIL = Some tests failed

Generated on $(Get-Date -Format "yyyy-MM-dd HH:mm:ss") using PowerShell test runner
"@
    
    # Write report with UTF8 encoding
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
            } else {
                $FailedServices += $Service
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