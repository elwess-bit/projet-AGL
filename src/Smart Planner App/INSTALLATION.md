# Installation and Setup Guide

## Prerequisites

Before installing the Smart Daily Planner, ensure you have:

### 1. Java Development Kit (JDK)
**Minimum**: Java 11
**Recommended**: Java 17 or higher

#### Windows
```
1. Download from https://www.oracle.com/java/technologies/downloads/
2. Run the installer
3. Follow installation wizard
4. Set JAVA_HOME environment variable
   - Right-click "This PC" → Properties
   - Advanced system settings → Environment Variables
   - New variable: JAVA_HOME = C:\Program Files\Java\jdk-xx
```

#### macOS
```bash
# Using Homebrew
brew install java@11

# Or download from Oracle website
```

#### Linux
```bash
# Ubuntu/Debian
sudo apt-get install default-jdk

# Or download from Oracle website
```

**Verify Installation:**
```bash
java -version
javac -version
```

### 2. Maven
**Minimum**: Maven 3.6
**Recommended**: Maven 3.8 or higher

#### Windows
```
1. Download from https://maven.apache.org/download.cgi
2. Extract to preferred location (e.g., C:\Maven)
3. Set M2_HOME environment variable
   - Environment Variables → New: M2_HOME = C:\Maven\apache-maven-x.x.x
   - Edit PATH → Add: %M2_HOME%\bin
```

#### macOS
```bash
# Using Homebrew
brew install maven

# Or manual installation
# Download, extract, and set PATH
```

#### Linux
```bash
# Ubuntu/Debian
sudo apt-get install maven

# Or manual installation
```

**Verify Installation:**
```bash
mvn -version
```

### 3. Git (Optional but Recommended)
For version control and cloning the repository.

---

## Installation Steps

### Step 1: Obtain the Project

#### Option A: Download ZIP
1. Download the project as ZIP
2. Extract to desired location
3. Navigate to project directory

#### Option B: Git Clone
```bash
git clone <repository-url>
cd smart-daily-planner
```

### Step 2: Verify Project Structure

Ensure the directory contains:
```
smart-daily-planner/
├── pom.xml
├── README.md
├── src/
│   ├── main/
│   └── test/
├── target/ (will be created during build)
└── ... (other files)
```

### Step 3: Build the Project

#### Using Maven Command Line
```bash
# Navigate to project directory
cd smart-daily-planner

# Clean and build
mvn clean package

# Or just compile
mvn compile

# Or package without tests
mvn clean package -DskipTests
```

**Expected Output:**
```
[INFO] BUILD SUCCESS
[INFO] Total time: XX.XXs
[INFO] Finished at: ...
```

#### Troubleshooting Build Issues

**Issue: "JAVA_HOME is not defined"**
```bash
# Set JAVA_HOME temporarily
export JAVA_HOME=/path/to/jdk  # Linux/macOS
set JAVA_HOME=C:\Path\to\jdk   # Windows

# Then run Maven
mvn clean package
```

**Issue: "Maven command not found"**
```bash
# Check Maven installation
mvn -version

# Add Maven to PATH if needed
export PATH=$PATH:/path/to/maven/bin
```

**Issue: Dependency Download Fails**
```bash
# Clear Maven cache
rm -rf ~/.m2/repository

# Or rebuild with offline flag
mvn clean package -o
```

### Step 4: Run the Application

#### Option 1: Using Maven
```bash
mvn exec:java -Dexec.mainClass="com.smartplanner.Main"
```

#### Option 2: Using JAR File
```bash
# Build first
mvn clean package

# Run JAR
java -jar target/smart-daily-planner-1.0.0.jar
```

#### Option 3: From IDE
If using an IDE like IntelliJ IDEA or Eclipse:
1. Open the project
2. Right-click on Main.java
3. Select "Run 'Main.main()'"

### Step 5: Run Unit Tests
```bash
# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=SmartPlannerApplicationTest

# Run with coverage
mvn test jacoco:report
```

---

## Verification Checklist

After installation, verify everything works:

```
☑ Java is installed correctly
  Command: java -version
  
☑ Maven is installed correctly
  Command: mvn -version
  
☑ Project builds successfully
  Command: mvn clean package
  
☑ Application runs
  Command: mvn exec:java -Dexec.mainClass="com.smartplanner.Main"
  
☑ Console UI appears
  Should see the Smart Planner welcome banner
  
☑ Main menu displays
  Options for Register, Login, Exit
  
☑ Tests pass
  Command: mvn test
  Result: BUILD SUCCESS
```

---

## Project Structure Verification

After build, your project should have:

```
smart-daily-planner/
├── pom.xml
├── README.md
├── GETTING_STARTED.md
├── API_DOCUMENTATION.md
├── ARCHITECTURE.md
├── PROJECT_SUMMARY.md
├── INSTALLATION.md
├── .gitignore
├── src/
│   ├── main/
│   │   ├── java/com/smartplanner/
│   │   │   ├── Main.java
│   │   │   ├── SmartPlannerApplication.java
│   │   │   ├── enums/
│   │   │   │   ├── PriorityLevel.java
│   │   │   │   ├── TaskType.java
│   │   │   │   └── TimeSlotType.java
│   │   │   ├── models/
│   │   │   │   ├── Utilisateur.java
│   │   │   │   ├── Tache.java
│   │   │   │   ├── Disponibilite.java
│   │   │   │   ├── Contrainte.java
│   │   │   │   ├── Notification.java
│   │   │   │   ├── Preference.java
│   │   │   │   ├── Planning.java
│   │   │   │   └── ElementPlanning.java
│   │   │   ├── services/
│   │   │   │   ├── UserService.java
│   │   │   │   ├── TaskService.java
│   │   │   │   ├── AvailabilityService.java
│   │   │   │   ├── PlanningService.java
│   │   │   │   ├── NotificationService.java
│   │   │   │   └── PreferenceService.java
│   │   │   └── utils/
│   │   │       └── FormattingUtils.java
│   │   └── resources/
│   └── test/
│       └── java/com/smartplanner/
│           └── SmartPlannerApplicationTest.java
└── target/
    ├── classes/
    ├── test-classes/
    └── smart-daily-planner-1.0.0.jar
```

---

## Environment Variables (Optional)

### Set Permanent Environment Variables

#### Windows
```
JAVA_HOME: C:\Program Files\Java\jdk-xx
M2_HOME: C:\Maven\apache-maven-x.x.x
CLASSPATH: %JAVA_HOME%\lib
PATH: %JAVA_HOME%\bin;%M2_HOME%\bin;...
```

#### macOS/Linux
Add to `~/.bashrc` or `~/.zshrc`:
```bash
export JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk-xx.jdk/Contents/Home
export M2_HOME=/usr/local/apache-maven-x.x.x
export PATH=$JAVA_HOME/bin:$M2_HOME/bin:$PATH
```

Then reload:
```bash
source ~/.bashrc
# or
source ~/.zshrc
```

---

## IDE Setup

### IntelliJ IDEA

1. **Open Project**
   - File → Open → Select project folder

2. **Configure JDK**
   - Settings → Project Structure → Project
   - Select JDK 11+

3. **Configure Maven**
   - Settings → Build Tools → Maven
   - Maven home path: Set to Maven installation

4. **Run Application**
   - Right-click Main.java → Run

5. **Run Tests**
   - Right-click SmartPlannerApplicationTest.java → Run

### Eclipse

1. **Import Project**
   - File → Import → Existing Maven Projects
   - Select project folder

2. **Configure JDK**
   - Project → Properties → Java Compiler
   - Select Compiler Compliance Level: 11+

3. **Run Application**
   - Right-click Main.java → Run As → Java Application

4. **Run Tests**
   - Right-click SmartPlannerApplicationTest.java → Run As → JUnit Test

### Visual Studio Code

1. **Install Extensions**
   - Extension Pack for Java
   - Maven for Java

2. **Open Workspace**
   - File → Open Folder → Select project folder

3. **Run Application**
   - Terminal → Run Task → Maven build
   - Or use Run button in editor

---

## Common Installation Issues

### Issue 1: "Java command not found"
**Solution:**
```bash
# Check if Java is installed
java -version

# If not, install Java for your OS
# Set JAVA_HOME environment variable
# Add Java bin directory to PATH
```

### Issue 2: "Maven command not found"
**Solution:**
```bash
# Check if Maven is installed
mvn -version

# If not, install Maven
# Set M2_HOME environment variable
# Add Maven bin directory to PATH
```

### Issue 3: Build fails with "Cannot find symbol"
**Solution:**
```bash
# Clean and rebuild
mvn clean

# Update dependencies
mvn dependency:resolve

# Rebuild
mvn package
```

### Issue 4: "OutOfMemoryError" during build
**Solution:**
```bash
# Increase heap memory for Maven
export MAVEN_OPTS="-Xmx1024m"

# Then rebuild
mvn clean package
```

### Issue 5: Port already in use (when running)
**Solution:**
- Close other Java applications
- Wait for other instances to terminate
- Use different port if needed

---

## Performance Optimization

### For Development
```bash
# Skip tests during development builds
mvn clean package -DskipTests

# Use offline mode if offline
mvn -o clean package
```

### For Production
```bash
# Full build with all checks
mvn clean verify

# Include integration tests
mvn clean package -P integration-tests
```

---

## Next Steps After Installation

1. **Read Documentation**
   - Start with GETTING_STARTED.md
   - Review API_DOCUMENTATION.md

2. **Run Sample Scenario**
   - Follow examples in GETTING_STARTED.md
   - Test all features

3. **Explore Source Code**
   - Review src/main/java/com/smartplanner/
   - Understand architecture from ARCHITECTURE.md

4. **Run Unit Tests**
   - Execute `mvn test`
   - All tests should pass

5. **Customize Application**
   - Modify services as needed
   - Add new features
   - Extend functionality

---

## Uninstallation

To completely remove the application:

```bash
# Remove project directory
rm -rf /path/to/smart-daily-planner

# Optional: Remove Maven cache
rm -rf ~/.m2/repository

# Optional: Remove Java (if desired)
# Follow OS-specific removal procedures
```

---

## Support Resources

### Documentation
- README.md - Project overview
- GETTING_STARTED.md - User guide
- API_DOCUMENTATION.md - API reference
- ARCHITECTURE.md - System design

### Online Resources
- Java Documentation: https://docs.oracle.com/javase/11/
- Maven Documentation: https://maven.apache.org/
- Spring Boot (for extensions): https://spring.io/projects/spring-boot

### Troubleshooting
- Check console output for error messages
- Review log files if generated
- Search for error messages online
- Contact project maintainers

---

**Installation Complete!** 🎉

You're now ready to use the Smart Daily Planner. Start by reading GETTING_STARTED.md for usage instructions.
