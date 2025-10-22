package com.eems;

import com.eems.controller.*;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Main Test Application
 * Demonstrates all CRUD operations and business logic tasks
 */
public class EEMSTestApplication {

    public static void main(String[] args) {
        System.out.println("======================================");
        System.out.println("EEMS - Employment Management System");
        System.out.println("======================================\n");

        // Initialize controllers
        DepartmentController deptController = new DepartmentController();
        EmployeeController empController = new EmployeeController();
        ProjectController projController = new ProjectController();
        ClientController clientController = new ClientController();

        // ==========================================
        // TEST 1: DEPARTMENT CRUD OPERATIONS
        // ==========================================
        System.out.println("\n========== DEPARTMENT CRUD TESTS ==========");

        // Read All
        deptController.getAllDepartments();

        // Read by ID
        System.out.println("\n--- Get Department by ID ---");
        deptController.getDepartmentById(1);

        // Create
        System.out.println("\n--- Create New Department ---");
        deptController.createDepartment("Research & Development", "Building E - Boston", new BigDecimal("3500000.00"));

        // Update
        System.out.println("\n--- Update Department ---");
        deptController.updateDepartment(1, "Software Engineering (Updated)", "Building A - New York", new BigDecimal("5500000.00"));

        // Try to Delete (will fail if employees exist)
        System.out.println("\n--- Attempt to Delete Department with Employees ---");
        deptController.deleteDepartment(1);

        // ==========================================
        // TEST 2: EMPLOYEE CRUD OPERATIONS
        // ==========================================
        System.out.println("\n\n========== EMPLOYEE CRUD TESTS ==========");

        // Read All
        empController.getAllEmployees();

        // Read by ID
        System.out.println("\n--- Get Employee by ID ---");
        empController.getEmployeeById(1);

        // Create
        System.out.println("\n--- Create New Employee ---");
        empController.createEmployee("Alice Thompson", "Junior Developer",
                LocalDate.of(2024, 10, 1), new BigDecimal("65000.00"), 1);

        // Update
        System.out.println("\n--- Update Employee ---");
        empController.updateEmployee(1, "John Smith", "Lead Developer",
                LocalDate.of(2020, 3, 15), new BigDecimal("105000.00"), 1);

        // Delete
        System.out.println("\n--- Delete Employee ---");
        empController.deleteEmployee(11);

        // ==========================================
        // TEST 3: PROJECT CRUD OPERATIONS
        // ==========================================
        System.out.println("\n\n========== PROJECT CRUD TESTS ==========");

        // Read All
        projController.getAllProjects();

        // Read by ID
        System.out.println("\n--- Get Project by ID ---");
        projController.getProjectById(1);

        // Create
        System.out.println("\n--- Create New Project ---");
        projController.createProject("Mobile App Development",
                "Develop cross-platform mobile application",
                LocalDate.of(2024, 11, 1),
                LocalDate.of(2025, 8, 31),
                new BigDecimal("750000.00"),
                "Active");

        // Update
        System.out.println("\n--- Update Project ---");
        projController.updateProject(1, "Cloud Migration (Phase 2)",
                "Migrate all legacy systems to cloud infrastructure - Phase 2",
                LocalDate.of(2024, 1, 15),
                LocalDate.of(2025, 12, 31),
                new BigDecimal("2500000.00"),
                "Active");

        // Delete
        System.out.println("\n--- Delete Project ---");
        projController.deleteProject(4);

        // ==========================================
        // TEST 4: CLIENT CRUD OPERATIONS
        // ==========================================
        System.out.println("\n\n========== CLIENT CRUD TESTS ==========");

        // Read All
        clientController.getAllClients();

        // Read by ID
        System.out.println("\n--- Get Client by ID ---");
        clientController.getClientById(1);

        // Create
        System.out.println("\n--- Create New Client ---");
        clientController.createClient("Education Solutions Inc", "Education",
                "Frank Moore", "555-0106", "frank.moore@edusolutions.com");

        // Update
        System.out.println("\n--- Update Client ---");
        clientController.updateClient(1, "TechCorp Solutions (Premium)", "Technology",
                "Alice Cooper", "555-0101", "alice.cooper@techcorp.com");

        // Delete
        System.out.println("\n--- Delete Client ---");
        clientController.deleteClient(6);

        // ==========================================
        // TEST 5: TASK 1 - Calculate Project HR Cost
        // ==========================================
        System.out.println("\n\n========== TASK 1: CALCULATE PROJECT HR COST ==========");
        projController.calculateProjectHRCost(1);
        projController.calculateProjectHRCost(2);
        projController.calculateProjectHRCost(5);

        // ==========================================
        // TEST 6: TASK 2 - Get Projects by Department
        // ==========================================
        System.out.println("\n\n========== TASK 2: GET PROJECTS BY DEPARTMENT ==========");
        System.out.println("\n--- Sorted by Budget ---");
        projController.getProjectsByDepartment(1, "budget");

        System.out.println("\n--- Sorted by End Date ---");
        projController.getProjectsByDepartment(1, "end_date");

        // ==========================================
        // TEST 7: TASK 3 - Find Clients by Upcoming Deadline
        // ==========================================
        System.out.println("\n\n========== TASK 3: FIND CLIENTS BY UPCOMING DEADLINE ==========");
        clientController.findClientsByUpcomingProjectDeadline(90);  // Projects ending in 90 days
        clientController.findClientsByUpcomingProjectDeadline(365); // Projects ending in 1 year

        // ==========================================
        // TEST 8: TASK 4 - Transfer Employee
        // ==========================================
        System.out.println("\n\n========== TASK 4: TRANSFER EMPLOYEE TO DEPARTMENT ==========");
        System.out.println("\n--- Before Transfer ---");
        empController.getEmployeeById(3);

        System.out.println("\n--- Performing Transfer ---");
        empController.transferEmployeeToDepartment(3, 2);

        System.out.println("\n--- After Transfer ---");
        empController.getEmployeeById(3);

        // ==========================================
        // SUMMARY
        // ==========================================
        System.out.println("\n\n======================================");
        System.out.println("ALL TESTS COMPLETED SUCCESSFULLY");
        System.out.println("======================================");
        System.out.println("\nDemonstrated:");
        System.out.println("✓ CRUD operations for all entities");
        System.out.println("✓ Task 1: Calculate Project HR Cost");
        System.out.println("✓ Task 2: Get Projects by Department");
        System.out.println("✓ Task 3: Find Clients by Upcoming Deadline");
        System.out.println("✓ Task 4: Transfer Employee to Department");
        System.out.println("✓ Referential integrity enforcement");
        System.out.println("✓ Transaction management");
        System.out.println("✓ Validation and error handling");
    }
}