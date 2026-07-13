Write-Host "=== MoodBloom Deployment ==="

# Get latest code
git pull origin main

# Build the project
.\mvnw.cmd clean package

# Check if build succeeded
if ($LASTEXITCODE -ne 0) {
    "Build failed on $(Get-Date)" | Out-File build.log
    Write-Host "Build failed. See build.log"
    exit
}

# Create production folder if it doesn't exist
New-Item -ItemType Directory -Force -Path "C:\MoodBloomProduction" | Out-Null

# Copy the JAR
Copy-Item "target\*.jar" "C:\MoodBloomProduction\" -Force

Write-Host "Build successful!"
Write-Host "Application copied to C:\MoodBloomProduction"