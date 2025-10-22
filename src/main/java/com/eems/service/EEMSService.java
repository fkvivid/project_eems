package com.eems.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import com.eems.dal.ClientRepository;
import com.eems.dal.DatabaseConnection;
import com.eems.dal.DepartmentRepository;
import com.eems.dal.EmployeeProjectRepository;
import com.eems.dal.EmployeeRepository;
import com.eems.dal.ProjectRepository;
import com.eems.domain.Client;
import com.eems.domain.Department;
import com.eems.domain.Employee;
import com.eems.domain.EmployeeProject;
import com.eems.domain.Project;

/**
 * Business Logic Layer: EEMS Service
 * Contains all business logic and validation
 */
public class EEMSService {

    private final DepartmentRepository departmentRepo;
    private final EmployeeRepository employeeRepo;
    private final ProjectRepository projectRepo;
    private final ClientRepository clientRepo;
    private final EmployeeProjectRepository empProjRepo;

    public EEMSService() {
        this.departmentRepo = new DepartmentRepository();
        this.employeeRepo = new EmployeeRepository();
        this.projectRepo = new ProjectRepository();
        this.clientRepo = new ClientRepository();
        this.empProjRepo = new EmployeeProjectRepository();
    }

    // ============================================
    // TASK 1: Calculate Project HR Cost
    // ============================================
    public BigDecimal calculateProjectHRCost(int projectId) throws SQLException {
        // Get the project
        Project project = projectRepo.findById(projectId);
        if (project == null) {
            throw new IllegalArgumentException("Project not found with ID: " + projectId);
        }

        // Calculate duration in months (rounded up)
        long durationMonths = project.getDurationInMonths();

    // Get all employee assignments for this project
    List<EmployeeProject> assignments = empProjRepo.findByProjectId(projectId);

    // Map employeeId -> total allocation percent for this project (in case an employee appears multiple times)
    java.util.Map<Integer, Integer> allocationByEmployee = assignments.stream()
        .collect(java.util.stream.Collectors.toMap(
            EmployeeProject::getEmployeeId,
            EmployeeProject::getTimeAllocationPercent,
            Integer::sum
        ));

    // Prefetch all employees used in this project to avoid checked exceptions inside streams
    List<Integer> employeeIds = allocationByEmployee.keySet().stream().toList();
    List<Employee> employees = employeeRepo.findByIds(employeeIds);

    java.util.Map<Integer, Employee> employeeById = employees.stream()
        .collect(java.util.stream.Collectors.toMap(Employee::getEmployeeId, e -> e));

    BigDecimal totalCost = allocationByEmployee.entrySet().stream()
        .map(entry -> {
            int empId = entry.getKey();
            int allocationPercent = entry.getValue();
            Employee employee = employeeById.get(empId);
            if (employee == null) return BigDecimal.ZERO;

            BigDecimal monthlySalary = employee.getMonthlySalary();
            BigDecimal allocationDecimal = new BigDecimal(allocationPercent)
                .divide(new BigDecimal("100"), 4, RoundingMode.HALF_UP);

            return monthlySalary
                .multiply(new BigDecimal(durationMonths))
                .multiply(allocationDecimal);
        })
        .reduce(BigDecimal.ZERO, BigDecimal::add);

    return totalCost.setScale(2, RoundingMode.HALF_UP);
    }

    // ============================================
    // TASK 2: Get Projects by Department
    // ============================================
    public List<Project> getProjectsByDepartment(int departmentId, String sortBy) throws SQLException {
        // Validate department exists
        Department department = departmentRepo.findById(departmentId);
        if (department == null) {
            throw new IllegalArgumentException("Department not found with ID: " + departmentId);
        }

        // Validate sortBy parameter
        if (!isValidSortField(sortBy)) {
            throw new IllegalArgumentException("Invalid sort field: " + sortBy);
        }

        return projectRepo.findByDepartmentId(departmentId, sortBy);
    }

    private boolean isValidSortField(String sortBy) {
        return sortBy.equals("budget") || sortBy.equals("end_date") ||
                sortBy.equals("name") || sortBy.equals("start_date");
    }

    // ============================================
    // TASK 3: Find Clients by Upcoming Deadline
    // ============================================
    public List<Client> findClientsByUpcomingProjectDeadline(int daysUntilDeadline) throws SQLException {
        if (daysUntilDeadline < 0) {
            throw new IllegalArgumentException("Days until deadline must be non-negative");
        }

        LocalDate deadline = LocalDate.now().plusDays(daysUntilDeadline);
        return clientRepo.findByUpcomingProjectDeadline(deadline);
    }

    // ============================================
    // TASK 4: Transfer Employee to Department
    // ============================================
    public boolean transferEmployeeToDepartment(int employeeId, int newDepartmentId) throws SQLException {
        Connection conn = null;
        try {
            // Get connection for transaction
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);

            // Validate employee exists
            Employee employee = employeeRepo.findById(employeeId);
            if (employee == null) {
                throw new IllegalArgumentException("Employee not found with ID: " + employeeId);
            }

            // Validate new department exists
            Department newDepartment = departmentRepo.findById(newDepartmentId);
            if (newDepartment == null) {
                throw new IllegalArgumentException("Department not found with ID: " + newDepartmentId);
            }

            // Check if employee is already in that department
            if (employee.getDepartmentId() == newDepartmentId) {
                throw new IllegalArgumentException("Employee is already in department: " + newDepartmentId);
            }

            // Update employee's department
            employee.setDepartmentId(newDepartmentId);
            boolean updated = employeeRepo.update(employee);

            if (updated) {
                conn.commit();
                return true;
            } else {
                conn.rollback();
                return false;
            }

        } catch (SQLException e) {
            if (conn != null) {
                conn.rollback();
            }
            throw e;
        } catch (RuntimeException e) {
            if (conn != null) {
                conn.rollback();
            }
            throw new SQLException("Transfer failed: " + e.getMessage(), e);
        } finally {
            if (conn != null) {
                conn.setAutoCommit(true);
                conn.close();
            }
        }
    }

    // ============================================
    // CRUD Operations - Department
    // ============================================
    public Department createDepartment(Department department) throws SQLException {
        validateDepartment(department);
        return departmentRepo.create(department);
    }

    public Department getDepartmentById(int id) throws SQLException {
        return departmentRepo.findById(id);
    }

    public List<Department> getAllDepartments() throws SQLException {
        return departmentRepo.findAll();
    }

    public boolean updateDepartment(Department department) throws SQLException {
        validateDepartment(department);
        return departmentRepo.update(department);
    }

    public boolean deleteDepartment(int id) throws SQLException {
        return departmentRepo.delete(id);
    }

    private void validateDepartment(Department dept) {
        if (dept.getName() == null || dept.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Department name is required");
        }
        if (dept.getLocation() == null || dept.getLocation().trim().isEmpty()) {
            throw new IllegalArgumentException("Department location is required");
        }
        if (!dept.isValidBudget()) {
            throw new IllegalArgumentException("Department budget must be positive");
        }
    }

    // ============================================
    // CRUD Operations - Employee
    // ============================================
    public Employee createEmployee(Employee employee) throws SQLException {
        validateEmployee(employee);
        return employeeRepo.create(employee);
    }

    public Employee getEmployeeById(int id) throws SQLException {
        return employeeRepo.findById(id);
    }

    public List<Employee> getAllEmployees() throws SQLException {
        return employeeRepo.findAll();
    }

    public boolean updateEmployee(Employee employee) throws SQLException {
        validateEmployee(employee);
        return employeeRepo.update(employee);
    }

    public boolean deleteEmployee(int id) throws SQLException {
        return employeeRepo.delete(id);
    }

    private void validateEmployee(Employee emp) {
        if (emp.getFullName() == null || emp.getFullName().trim().isEmpty()) {
            throw new IllegalArgumentException("Employee name is required");
        }
        if (emp.getTitle() == null || emp.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Employee title is required");
        }
        if (emp.getHireDate() == null) {
            throw new IllegalArgumentException("Hire date is required");
        }
        if (!emp.isValidSalary()) {
            throw new IllegalArgumentException("Employee salary must be positive");
        }
    }

    // ============================================
    // CRUD Operations - Project
    // ============================================
    public Project createProject(Project project) throws SQLException {
        validateProject(project);
        return projectRepo.create(project);
    }

    public Project getProjectById(int id) throws SQLException {
        return projectRepo.findById(id);
    }

    public List<Project> getAllProjects() throws SQLException {
        return projectRepo.findAll();
    }

    public boolean updateProject(Project project) throws SQLException {
        validateProject(project);
        return projectRepo.update(project);
    }

    public boolean deleteProject(int id) throws SQLException {
        return projectRepo.delete(id);
    }

    private void validateProject(Project proj) {
        if (proj.getName() == null || proj.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Project name is required");
        }
        if (proj.getStartDate() == null || proj.getEndDate() == null) {
            throw new IllegalArgumentException("Start and end dates are required");
        }
        if (proj.getEndDate().isBefore(proj.getStartDate())) {
            throw new IllegalArgumentException("End date must be after start date");
        }
        if (proj.getBudget() == null || proj.getBudget().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Project budget must be positive");
        }
    }

    // ============================================
    // CRUD Operations - Client
    // ============================================
    public Client createClient(Client client) throws SQLException {
        validateClient(client);
        return clientRepo.create(client);
    }

    public Client getClientById(int id) throws SQLException {
        return clientRepo.findById(id);
    }

    public List<Client> getAllClients() throws SQLException {
        return clientRepo.findAll();
    }

    public boolean updateClient(Client client) throws SQLException {
        validateClient(client);
        return clientRepo.update(client);
    }

    public boolean deleteClient(int id) throws SQLException {
        return clientRepo.delete(id);
    }

    private void validateClient(Client client) {
        if (client.getName() == null || client.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Client name is required");
        }
        if (client.getIndustry() == null || client.getIndustry().trim().isEmpty()) {
            throw new IllegalArgumentException("Client industry is required");
        }
        if (!client.hasValidContactInfo()) {
            throw new IllegalArgumentException("Valid email is required");
        }
    }

    // ============================================
    // CRUD Operations - Employee-Project Assignment
    // ============================================
    public boolean assignEmployeeToProject(int employeeId, int projectId, int timeAllocation) throws SQLException {
        // Validate employee exists
        Employee employee = employeeRepo.findById(employeeId);
        if (employee == null) {
            throw new IllegalArgumentException("Employee not found");
        }

        // Validate project exists
        Project project = projectRepo.findById(projectId);
        if (project == null) {
            throw new IllegalArgumentException("Project not found");
        }

        // Validate time allocation
        if (timeAllocation <= 0 || timeAllocation > 100) {
            throw new IllegalArgumentException("Time allocation must be between 1 and 100");
        }

        EmployeeProject assignment = new EmployeeProject(employeeId, projectId, timeAllocation);
        return empProjRepo.create(assignment);
    }

    public boolean updateEmployeeProjectAllocation(int employeeId, int projectId, int newAllocation) throws SQLException {
        if (newAllocation <= 0 || newAllocation > 100) {
            throw new IllegalArgumentException("Time allocation must be between 1 and 100");
        }

        EmployeeProject assignment = new EmployeeProject(employeeId, projectId, newAllocation);
        return empProjRepo.update(assignment);
    }

    public boolean removeEmployeeFromProject(int employeeId, int projectId) throws SQLException {
        return empProjRepo.delete(employeeId, projectId);
    }

    public List<EmployeeProject> getProjectAssignments(int projectId) throws SQLException {
        return empProjRepo.findByProjectId(projectId);
    }
}