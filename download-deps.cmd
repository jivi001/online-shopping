@echo off
setlocal

echo Downloading MySQL connector...
if not exist "lib" mkdir "lib"

powershell -Command "& {(New-Object System.Net.ServicePointManager).SecurityProtocol = [System.Net.ServicePointManager]::SecurityProtocol -bor 3072; (New-Object System.Net.WebClient).DownloadFile('https://repo1.maven.org/maven2/com/mysql/mysql-connector-j/8.0.33/mysql-connector-j-8.0.33.jar', 'lib/mysql-connector-j-8.0.33.jar')}"

if %ERRORLEVEL% neq 0 (
    echo Failed to download MySQL connector
    exit /b 1
)

echo MySQL connector downloaded successfully
endlocal
