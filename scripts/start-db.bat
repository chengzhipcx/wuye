@echo off
REM Start MariaDB for the property management system (port 3307)
set "BASE=%~dp0.."
echo Starting database on port 3307 ...
"%BASE%\runtime\mariadb\bin\mariadbd.exe" --no-defaults --datadir="%BASE%\runtime\mariadb\data" --port=3307 --character-set-server=utf8 --console
