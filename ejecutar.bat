@echo off
chcp 65001 >nul
echo ════════════════════════════════════════════
echo   FAMILY FIT GYM - Compilando proyecto...
echo ════════════════════════════════════════════

REM Crear carpeta bin
if not exist bin mkdir bin

REM Compilar
echo Compilando archivos Java...
for /r src %%f in (*.java) do (
    javac -d bin -cp "lib\*" "%%f" 2>nul
)

if %ERRORLEVEL% NEQ 0 (
    echo.
    echo ❌ Error en la compilación
    pause
    exit /b 1
)

echo ✅ Compilación exitosa
echo.
echo ════════════════════════════════════════════
echo   Iniciando aplicación...
echo ════════════════════════════════════════════
echo.

REM Ejecutar
java -cp "bin;lib\*" com.familyfitgym.console.ConsoleApp

pause
