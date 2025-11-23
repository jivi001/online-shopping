@echo off
setlocal

REM Build and run JavaFX app without Maven
set JAVA_VERSION=25.0.1
set JAVAFX_HOME=.\javafx-sdk-25.0.1
set MYSQL_JAR=.\lib\mysql-connector-j-8.0.33.jar
set SRC_DIR=src\main\java
set RESOURCES_DIR=src\main\resources
set OUT_DIR=out\classes

REM Create output directory
if not exist "%OUT_DIR%" mkdir "%OUT_DIR%"

echo Compiling Java files...
javac ^
  --module-path "%JAVAFX_HOME%\lib" ^
  --add-modules javafx.controls,javafx.fxml ^
  -d "%OUT_DIR%" ^
  -sourcepath "%SRC_DIR%" ^
  -encoding UTF-8 ^
  "%SRC_DIR%\app\*.java" ^
  "%SRC_DIR%\app\controllers\*.java" ^
  "%SRC_DIR%\app\dao\*.java" ^
  "%SRC_DIR%\app\db\*.java" ^
  "%SRC_DIR%\app\models\*.java"

if %ERRORLEVEL% neq 0 (
    echo Compilation failed!
    exit /b 1
)

echo Copying FXML resources...
if not exist "%OUT_DIR%\fxml" mkdir "%OUT_DIR%\fxml"
xcopy "%RESOURCES_DIR%\fxml" "%OUT_DIR%\fxml" /E /I /Y >nul

echo Running JavaFX application...
java ^
  --module-path "%JAVAFX_HOME%\lib" ^
  --add-modules javafx.controls,javafx.fxml ^
  -cp "%OUT_DIR%;%MYSQL_JAR%" ^
  app.Main

endlocal
