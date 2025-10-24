# EEMS N-Tier Architecture

## Layer Roles & Responsibilities

### 1. Presentation Layer (`src/main/java/com/eems/controller/`)
- Acts as the entry point and user interface layer of the application
- Handles input validation and request formatting
- Maps user actions to business operations
- Formats and presents results back to users
- **Key Components**: `DepartmentController`, `EmployeeController`, `ProjectController`, `ClientController`
- **Responsibilities**:
  - Input parameter validation
  - Error message formatting
  - Response presentation
  - No business logic
  - No direct data access

### 2. Business Layer (`src/main/java/com/eems/service/`)
- Implements core business rules and workflow logic
- Coordinates between multiple domain objects
- Enforces business constraints and validation rules
- Manages transactions and operation sequencing
- **Key Component**: `EEMSService`
- **Responsibilities**:
  - Business rule enforcement
  - Transaction management
  - Complex calculations (e.g., Project HR Cost)
  - Cross-entity operations
  - Domain object validation

### 3. Domain Layer (`src/main/java/com/eems/domain/`)
- Represents the business objects and their relationships
- Implements domain-specific validation rules
- Encapsulates the core business data structures
- **Key Components**: `Employee`, `Department`, `Project`, `Client`, `EmployeeProject`
- **Responsibilities**:
  - Business object state management
  - Basic validation rules
  - Domain relationships
  - Business object behavior
  - No external dependencies

### 4. Data Access Layer (`src/main/java/com/eems/dal/`)
- Manages persistence and data retrieval operations
- Isolates database operations from business logic
- Handles database connections and SQL operations
- Maps database records to domain objects
- **Key Components**: Repository classes (`EmployeeRepository`, etc.) and `DatabaseConnection`
- **Responsibilities**:
  - CRUD operations
  - SQL execution
  - Connection management
  - Result set mapping
  - Transaction boundaries

## Key Benefits of This Architecture
1. **Separation of Concerns**: Each layer has a distinct responsibility
2. **Maintainability**: Changes in one layer don't require changes in others
3. **Testability**: Layers can be tested in isolation
4. **Flexibility**: Database or UI changes don't affect business logic
5. **Scalability**: Layers can be scaled independently

## Data Flow Example
Using employee creation as an example:
1. **Presentation**: `EmployeeController` receives creation request and validates input format
2. **Business**: `EEMSService` validates business rules and manages transaction
3. **Domain**: `Employee` object encapsulates employee data and validates domain rules
4. **Data Access**: `EmployeeRepository` handles the SQL INSERT operation