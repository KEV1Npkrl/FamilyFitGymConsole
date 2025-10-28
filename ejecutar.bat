@echo off
chcp 65001 >nul
echo ════════════════════════════════════════════
echo   FAMILY FIT GYM - Compilando proyecto...
echo ════════════════════════════════════════════

REM Crear carpeta bin
if not exist bin mkdir bin

REM Limpiar carpeta bin
del /Q bin\com\familyfitgym\console\*.class 2>nul
rmdir /S /Q bin\com 2>nul

REM Compilar todos los archivos
echo Compilando archivos Java...
javac -encoding UTF-8 -d bin -cp "lib\*" src\com\familyfitgym\console\ConsoleApp.java src\com\familyfitgym\console\modelo\*.java src\com\familyfitgym\console\repositorio\*.java src\com\familyfitgym\console\repositorio\impl\*.java src\com\familyfitgym\console\servicio\*.java src\com\familyfitgym\console\utilidad\*.java 2>error.log

if %ERRORLEVEL% NEQ 0 (
    echo.
    echo ❌ Error en la compilación
    type error.log
    del error.log
    pause
    exit /b 1
)

del error.log 2>nul

echo ✅ Compilación exitosa
echo.
echo ════════════════════════════════════════════
echo   Iniciando aplicación...
echo ════════════════════════════════════════════
echo.

REM Ejecutar
java -cp "bin;lib\*" com.familyfitgym.console.ConsoleApp

pause
