# EEMS Quick Start Guide

## 5-Minute Setup

### Step 1: Setup MySQL Database
```bash
# Login to MySQL
mysql -u root -p

# Create database
CREATE DATABASE eems_db;
USE eems_db;

# Run the schema script
source eems_database_schema.sql;

# Verify tables created
SHOW TABLES;
```

### Step 2: Configure Database Connection
Edit `DatabaseConnection.java` (line 10-12):
```java
private static final String URL = "jdbc:mysql://localhost:3306/eems_db";
private static final String USER = "root";           // Your MySQL username
private static final String PASSWORD = "password";   // Your MySQL password
```

### Step 3: Download MySQL JDBC Driver
Download from: https://dev.mysql.com/downloads/connector/j/
Or use Maven dependency in `pom.xml`

### Step 4: Project Structure
Create this folder structure:
```
eems-project/
├── src/
│   └── com/
│       └── eems/
│           ├── domain/          (5 files)
│           ├── dal/             (7 files)
│           ├── service/         (1 file)
│           ├── controller/      (4 files)
│           └── EEMSTestApplication.java
├── lib/
│   └── mysql-connector-java-8.0.33.jar
└── README.md
```

### Step 5: Compile and Run
```bash
# Navigate to project directory
cd eems-project

# Compile (Windows)
javac -d bin -cp "lib/*;src" src/com/eems/**/*.java

# Compile (Mac/Linux)
javac -d bin -cp "lib/*:src" src/com/eems/**/*.java

# Run (Windows)
java -cp "bin;lib/*" com.eems.EEMSTestApplication

# Run (Mac/Linux)
java -cp "bin:lib/*" com.eems.EEMSTestApplication
```

## File Checklist

### Domain Layer (5 files)
- ✅ Department.java
- ✅ Employee.java
- ✅ Project.java
- ✅ Client.java
- ✅ EmployeeProject.java

### Data Access Layer (7 files)
- ✅ DatabaseConnection.java
- ✅ DepartmentRepository.java
- ✅ EmployeeRepository.java
- ✅ ProjectRepository.java
- ✅ ClientRepository.java
- ✅ EmployeeProjectRepository.java
- ✅ ProjectClientRepository.java (optional)

### Business Logic Layer (1 file)
- ✅ EEMSService.java

### Presentation Layer (4 files)
- ✅ DepartmentController.java
- ✅ EmployeeController.java
- ✅ ProjectController.java
- ✅ ClientController.java

### Main Application (1 file)
- ✅ EEMSTestApplication.java

### Database (1 file)
- ✅ eems_database_schema.sql

## Testing Checklist

Run `EEMSTestApplication.java` and verify:

### CRUD Operations
- ✅ Create new department
- ✅ Read department by ID
- ✅ Update department details
- ✅ Delete department (with referential integrity check)
- ✅ Same for Employee, Project, Client

### Business Logic Tasks
- ✅ Task 1: Calculate Project HR Cost
- ✅ Task 2: Get Projects by Department (sorted)
- ✅ Task 3: Find Clients by Upcoming Deadline
- ✅ Task 4: Transfer Employee to Department (transactional)

## Common Commands

### View Database Contents
```sql
-- All employees with their departments
SELECT e.full_name, e.title, d.name as department
FROM Employee e
JOIN Department d ON e.department_id = d.department_id;

-- Project assignments
SELECT p.name as project, e.full_name, ep.time_allocation_percent
FROM Project p
JOIN Employee_Project ep ON p.project_id = ep.project_id
JOIN Employee e ON ep.employee_id = e.employee_id;

-- Projects with clients
SELECT p.name as project, c.name as client
FROM Project p
JOIN Project_Client pc ON p.project_id = pc.project_id
JOIN Client c ON pc.client_id = c.client_id;
```

### Reset Database
```sql
DROP DATABASE eems_db;
CREATE DATABASE eems_db;
USE eems_db;
source eems_database_schema.sql;
```

## Presentation Preparation

### What to Explain
1. **Architecture**: Show the 4-tier separation
2. **Database Design**: ER diagram with relationships
3. **CRUD Demo**: Live demonstration of operations
4. **Business Logic**: Explain each of the 4 tasks
5. **Error Handling**: Show referential integrity

### Key Points to Mention
- Pure JDBC (no ORM)
- N-Tier architecture strictly followed
- Proper transaction management
- Validation at service layer
- Junction tables for M:M relationships

## Troubleshooting

**MySQL Connection Failed**
```
Check: MySQL is running (services.msc on Windows)
Check: Username and password correct
Check: Database eems_db exists
```

**JDBC Driver Not Found**
```
Check: mysql-connector-java jar is in lib/ folder
Check: Classpath includes lib/* during compilation and runtime
```

**Foreign Key Constraint Error**
```
This is normal! It shows referential integrity is working.
You cannot delete a department that has employees.
```

**Package Does Not Exist**
```
Check: All files are in correct package structure
Check: Package declarations match folder structure
```

## Success Indicators

When everything works, you should see:
```
======================================
EEMS - Employment Management System
======================================

========== DEPARTMENT CRUD TESTS ==========
=== All Departments ===
Department{departmentId=1, name='Software Engineering', ...}
...
ALL TESTS COMPLETED SUCCESSFULLY
```

## Next Steps

After basic setup works:
1. Customize test data in SQL script
2. Add your own test scenarios
3. Modify business logic as needed
4. Prepare UML diagrams for presentation
5. Document design decisions

## Support

If issues persist:
1. Check all package declarations
2. Verify MySQL is accessible
3. Ensure JDBC driver version matches MySQL version
4. Review error messages carefully
5. Test database connection separately first

Good luck with your project! 🚀