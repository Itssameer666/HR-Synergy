@echo off
title HRMS Launcher - Server and Online Link
echo ========================================================
echo Starting HRMS Spring Boot Server + Online Live Link
echo ========================================================

echo 1. Launching Spring Boot server in background...
start "HRMS-Backend" /min cmd /c ".\mvnw.cmd spring-boot:run"

echo 2. Waiting 10 seconds for server to initialize on port 8181...
timeout /t 10 /nobreak >nul

echo 3. Generating Live Public HTTPS Link...
echo ========================================================
echo Your project is accessible locally at:
echo http://localhost:8181/
echo ========================================================
echo Launching Live Online Tunnel (Keep this window open)...
echo ========================================================
ssh -R 80:localhost:8181 -o StrictHostKeyChecking=no nokey@localhost.run
pause
