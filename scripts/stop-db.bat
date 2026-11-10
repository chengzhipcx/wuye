@echo off
REM Stop MariaDB for the property management system (port 3307)
set "BASE=%~dp0.."
"%BASE%\runtime\mariadb\bin\mariadb-admin.exe" --no-defaults -u root -P 3307 shutdown
echo Database stopped.
