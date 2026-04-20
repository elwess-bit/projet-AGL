# Script PowerShell pour compiler et executer le projet Smart Planner

# Couleurs pour l'affichage
$host.ui.RawUI.ForegroundColor = "Green"
Write-Host "========================================" -ForegroundColor Yellow
Write-Host "  Smart Planner - Compilation Script" -ForegroundColor Yellow
Write-Host "========================================" -ForegroundColor Yellow

# Etape 1 : Creer les repertoires necessaires
Write-Host "`n[STEP 1] Creation des repertoires..." -ForegroundColor Cyan
$libDir = "lib"
$classesDir = "target/classes"

if (!(Test-Path $libDir)) {
    New-Item -ItemType Directory -Path $libDir | Out-Null
    Write-Host "[OK] Repertoire 'lib' cree" -ForegroundColor Green
} else {
    Write-Host "[OK] Repertoire 'lib' existe deja" -ForegroundColor Green
}

if (!(Test-Path $classesDir)) {
    New-Item -ItemType Directory -Path $classesDir -Force | Out-Null
    Write-Host "[OK] Repertoire 'target/classes' cree" -ForegroundColor Green
} else {
    Write-Host "[OK] Repertoire 'target/classes' existe deja" -ForegroundColor Green
}

# Etape 2 : Telecharger les dependances si necessaire
Write-Host "`n[STEP 2] Verification des dependances..." -ForegroundColor Cyan

$dependencies = @(
    "https://repo1.maven.org/maven2/org/slf4j/slf4j-api/2.0.5/slf4j-api-2.0.5.jar",
    "https://repo1.maven.org/maven2/org/slf4j/slf4j-simple/2.0.5/slf4j-simple-2.0.5.jar",
    "https://repo1.maven.org/maven2/com/google/code/gson/gson/2.10.1/gson-2.10.1.jar"
)

foreach ($dep in $dependencies) {
    $jarName = Split-Path $dep -Leaf
    $jarPath = Join-Path $libDir $jarName
    
    if (!(Test-Path $jarPath)) {
        Write-Host "[DOWNLOAD] Telechargement de $jarName..." -ForegroundColor Yellow
        try {
            Invoke-WebRequest -Uri $dep -OutFile $jarPath -UseBasicParsing
            Write-Host "[OK] $jarName telecharge avec succes" -ForegroundColor Green
        } catch {
            Write-Host "[WARNING] Echec du telechargement de $jarName - compilation sans le fichier" -ForegroundColor Yellow
        }
    } else {
        Write-Host "[OK] $jarName existe deja" -ForegroundColor Green
    }
}

# Etape 3 : Construire le classpath
Write-Host "`n[STEP 3] Construction du classpath..." -ForegroundColor Cyan
$classpath = "$classesDir"
$jars = Get-ChildItem -Path $libDir -Filter "*.jar" -ErrorAction SilentlyContinue
foreach ($jar in $jars) {
    $classpath += ";" + (Join-Path $libDir $jar.Name)
}
Write-Host "[OK] Classpath: $classpath" -ForegroundColor Green

# Etape 4 : Compiler les fichiers
Write-Host "`n[STEP 4] Compilation des fichiers Java..." -ForegroundColor Cyan
$javaFiles = Get-ChildItem -Path "src/main/java" -Filter "*.java" -Recurse | Select-Object -ExpandProperty FullName

Write-Host "[INFO] Compilation avec encoding UTF-8..." -ForegroundColor Cyan
$compileCmd = "javac -encoding UTF-8 -d `"$classesDir`" -sourcepath `"src/main/java`" -cp `"$classpath`" $($javaFiles -join ' ')"

Write-Host "[DEBUG] Execution: javac (multiple files with UTF-8 encoding)" -ForegroundColor Gray
$compileOutput = & javac -encoding UTF-8 -d "$classesDir" -sourcepath "src/main/java" -cp "$classpath" $javaFiles 2>&1

if ($LASTEXITCODE -eq 0) {
    Write-Host "[OK] Compilation reussie!" -ForegroundColor Green
} else {
    Write-Host "[ERROR] Erreurs de compilation detectees:" -ForegroundColor Red
    Write-Host $compileOutput -ForegroundColor Red
    Write-Host "[INFO] Tentative d'execution quand meme..." -ForegroundColor Yellow
}

# Etape 5 : Executer le programme
Write-Host "`n[STEP 5] Execution du programme..." -ForegroundColor Cyan
Write-Host "[INFO] Lancement de: java -cp `"$classpath`" com.smartplanner.Main" -ForegroundColor Gray

java -cp "$classpath" com.smartplanner.Main

Write-Host "`n[INFO] Programme termine." -ForegroundColor Cyan
