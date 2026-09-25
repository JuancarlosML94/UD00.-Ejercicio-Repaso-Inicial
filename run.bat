@echo off
REM ============================================================
REM  Tres en Raya - Script de compilacion y ejecucion (Windows)
REM ============================================================

echo === Compilando fuentes principales ===
javac -encoding UTF-8 -d out\main src\main\java\tresEnRaya\Ficha.java src\main\java\tresEnRaya\Tablero.java src\main\java\tresEnRaya\Partida.java src\main\java\tresEnRaya\TresEnRayaGUI.java
if errorlevel 1 (
    echo ERROR en compilacion principal.
    exit /b 1
)

echo === Compilando tests ===
javac -encoding UTF-8 -cp "out\main;lib\junit-platform-console-standalone-1.10.2.jar" -d out\test src\test\java\tresEnRaya\TresEnRayaTest.java
if errorlevel 1 (
    echo ERROR en compilacion de tests.
    exit /b 1
)

echo === Ejecutando tests ===
java -cp "out\main;out\test;lib\junit-platform-console-standalone-1.10.2.jar" org.junit.platform.console.ConsoleLauncher --scan-class-path="out\test"

echo.
echo === Lanzando interfaz grafica ===
java -cp out\main tresEnRaya.TresEnRayaGUI
