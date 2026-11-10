@echo off
REM Stop the Spring Boot application listening on port 8088
for /f "tokens=5" %%a in ('netstat -ano ^| findstr ":8088 .*LISTENING"') do (
    echo Killing process %%a ...
    taskkill /F /PID %%a
)
echo Application stopped.
