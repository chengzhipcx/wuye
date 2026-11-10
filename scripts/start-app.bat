@echo off
REM Start the Spring Boot application (port 8088)
REM Requires the database to be running first (scripts\start-db.bat)
set "BASE=%~dp0.."
if not exist "%BASE%\target\wuye.war" (
    echo [ERROR] target\wuye.war not found. Run scripts\build.sh first.
    pause
    exit /b 1
)
echo Starting PropertyManagerSystem on http://localhost:8088 ...
"C:\Program Files\Java\jdk1.8.0_333\bin\java" -jar "%BASE%\target\wuye.war" --server.port=8088
