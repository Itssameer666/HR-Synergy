@echo off
echo ========================================================
echo Starting HRMS Production Standalone JAR
echo ========================================================
echo Checking for target\hrms-0.0.1-SNAPSHOT.jar...

if not exist target\hrms-0.0.1-SNAPSHOT.jar (
    echo JAR not found. Building now with Maven wrapper...
    call .\mvnw.cmd clean package -DskipTests
)

echo Starting application on port 8181...
java -jar target\hrms-0.0.1-SNAPSHOT.jar
pause
