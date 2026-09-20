@echo off
echo ========================================================
echo Launching HRMS in Docker with MySQL
echo ========================================================
docker --version >nul 2>&1
if errorlevel 1 (
    echo [ERROR] Docker was not found in your PATH.
    echo Please make sure Docker Desktop is installed and running.
    echo Download: https://www.docker.com/products/docker-desktop/
    pause
    exit /b 1
)

echo Building and starting containers in detached mode...
docker compose up -d --build

echo Application starting! 
echo Once ready, visit: http://localhost:8181/
echo To view live logs: docker compose logs -f
pause
