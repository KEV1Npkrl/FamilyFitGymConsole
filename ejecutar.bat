@echo off
chcp 65001 >nul
cls
echo ════════════════════════════════════════════
echo   FAMILY FIT GYM - Compilando proyecto...
echo ════════════════════════════════════════════
echo.

REM Crear carpeta bin si no existe
if not exist bin mkdir bin

REM Limpiar carpeta bin solo si existe
if exist bin\com (
    echo Limpiando compilacion anterior...
    rmdir /S /Q bin\com 2>nul
)

REM Compilar todos los archivos
echo Compilando archivos Java...
javac -encoding UTF-8 -d bin src\com\familyfitgym\console\ConsoleApp.java src\com\familyfitgym\console\modelo\*.java src\com\familyfitgym\console\repositorio\*.java src\com\familyfitgym\console\repositorio\impl\*.java src\com\familyfitgym\console\servicio\*.java src\com\familyfitgym\console\utilidad\*.java 2>error.log

if %ERRORLEVEL% NEQ 0 (
    echo.
    echo ════════════════════════════════════════════
    echo   ERROR EN LA COMPILACION
    echo ════════════════════════════════════════════
    echo.
    type error.log
    del error.log 2>nul
    echo.
    pause
    exit /b 1
)

if exist error.log del error.log 2>nul

echo.
echo ✅ Compilacion exitosa
echo.
echo ════════════════════════════════════════════
echo   Iniciando aplicacion...
echo ════════════════════════════════════════════
echo.

REM Ejecutar la aplicacion
java -cp bin com.familyfitgym.console.ConsoleApp

echo.
echo ════════════════════════════════════════════
echo   Aplicacion finalizada
echo ════════════════════════════════════════════
pause
