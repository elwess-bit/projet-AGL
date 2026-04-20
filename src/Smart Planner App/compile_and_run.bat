@echo off
REM Script Batch pour compiler et executer le projet Smart Planner

setlocal enabledelayedexpansion
cls
echo.
echo ========================================
echo   Smart Planner - Compilation Script
echo ========================================
echo.

REM Creer les repertoires
echo [STEP 1] Creation des repertoires...
if not exist "lib" mkdir lib
if not exist "target\classes" mkdir target\classes
echo [OK] Repertoires crees
echo.

REM Compiler les fichiers
echo [STEP 2] Compilation des fichiers Java...
echo [INFO] Compilation avec encoding UTF-8...
setlocal enabledelayedexpansion

pushd src\main\java
for /r %%F in (*.java) do (
    set "files=!files! %%F"
)
popd

javac -encoding UTF-8 -d target\classes -sourcepath src\main\java %files% 2>&1

if errorlevel 0 (
    echo [OK] Compilation reussie!
) else (
    echo [ERROR] Erreurs de compilation detectees
    echo [INFO] Tentative d'execution quand meme...
)
echo.

REM Executer le programme
echo [STEP 3] Execution du programme...
echo [INFO] Lancement de: java -cp target\classes com.smartplanner.Main
echo.

java -cp target\classes com.smartplanner.Main

echo.
echo [INFO] Programme termine.
pause
