# Weather API Automation Framework

This project demonstrates automated API testing for a weather service using a modern Java-based test automation stack.
The framework validates weather data using REST APIs and generates detailed test reports using Serenity.

---

# Technology Stack
Java<br>
REST Assured<br>
Cucumber BDD<br>
Serenity BDD<br>
Maven<br>

---

# Prerequisites

Ensure the following tools are installed before running the tests.

Java JDK 17<br>
Apache Maven 3.8 or higher<br>
Git (optional for cloning the repository)<br>

Verify installation:

java -version<br>
mvn -version

---

# Required Maven Plugins

The project uses the following Maven plugins defined in `pom.xml`.

Serenity Maven Plugin – Generates Serenity test reports<br>
Maven Surefire Plugin – Executes test cases<br>
Maven Compiler Plugin – Compiles the Java source code<br>


# Environment Configuration

The framework reads configuration values from:

src/test/resources/config.properties

Example configuration:

```
weather.api.key=YOUR_API_KEY ( For testing purpose its already included) 
weather.api.baseurl=https://api.weatherbit.io/v2.0/current
```

Update the API key before executing the tests.

---

# Running the Tests

Execute the full test suite using Maven:

mvn clean verify

This command will:

1. Compile the project
2. Execute Cucumber scenarios
3. Run API validations using REST Assured
4. Generate Serenity HTML reports

---

# Running Tests Using Tags

Tests can be filtered using Cucumber tags.

Example:

Run smoke tests:

mvn verify -Dcucumber.filter.tags="@smoke"

Run regression suite:

mvn verify -Dcucumber.filter.tags="@regression"



---

# Test Reports

After test execution, the Serenity report is generated at:

target/site/serenity/index.html

The report provides:

Scenario execution results<br>
Step-by-step API interactions<br>
Test execution summary<br>
Failure details (if any)<br>

---

# Framework Architecture

Project Structure & Modular Design
The framework follows a multi-layered BDD pattern to ensure separation between test intent (Gherkin) and technical implementation (Java/Rest-Assured).



Developed and managed by Sannath vijayakumar <br>
reach me at vijayask24@gmail.com
