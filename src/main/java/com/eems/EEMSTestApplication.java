package com.eems;

import com.eems.controller.ClientController;
import com.eems.controller.DepartmentController;
import com.eems.controller.EmployeeController;
import com.eems.controller.ProjectController;

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
        // TEST TASK 1 - Calculate Project HR Cost
        // ==========================================
        System.out.println("\n\n========== TASK 1: CALCULATE PROJECT HR COST ==========");
        System.out.println("Project 1 (Cloud Migration):");
        projController.calculateProjectHRCost(1);
        System.out.println("\nProject 2 (Brand Refresh):");
        projController.calculateProjectHRCost(2);
        System.out.println("\nProject 5 (Supply Chain Optimization):");
        projController.calculateProjectHRCost(5);

        // ==========================================
        // TEST TASK 2 - Get Projects by Department
        // ==========================================
        System.out.println("\n\n========== TASK 2: GET PROJECTS BY DEPARTMENT ==========");
        System.out.println("\nDepartment 1 (Software Engineering) Projects:");
        System.out.println("Expected projects (4 total):");
        System.out.println("1. Cloud Migration ($2,000,000)");
        System.out.println("2. Financial System Upgrade ($1,500,000)");
        System.out.println("3. Supply Chain Optimization ($800,000)");
        System.out.println("4. Brand Refresh ($500,000)");
        
        System.out.println("\n--- Sorted by Budget (ascending) ---");
        projController.getProjectsByDepartment(1, "budget");

        System.out.println("\n--- Sorted by End Date (ascending) ---");
        projController.getProjectsByDepartment(1, "end_date");

        // ==========================================
        // TEST TASK 3 - Find Clients by Upcoming Deadline
        // ==========================================
        System.out.println("\n\n========== TASK 3: FIND CLIENTS BY UPCOMING DEADLINE ==========");
        clientController.findClientsByUpcomingProjectDeadline(90);  // Projects ending in 90 days
        clientController.findClientsByUpcomingProjectDeadline(365); // Projects ending in 1 year

        // ==========================================
        // TEST TASK 4 - Transfer Employee
        // ==========================================
        System.out.println("\n\n========== TASK 4: TRANSFER EMPLOYEE TO DEPARTMENT ==========");

        
        System.out.println("\nTest Case 1: Valid Transfer");
        System.out.println("Transferring Michael Brown (ID: 3) from HR (Dept 3) to Marketing (Dept 2)");
        System.out.println("--- Initial State (In HR Department) ---");
        empController.getEmployeeById(3);

        System.out.println("\n--- Performing Transfer ---");
        empController.transferEmployeeToDepartment(3, 2);

        System.out.println("\n--- After Transfer to Marketing ---");
        empController.getEmployeeById(3);

        System.out.println("\nTest Case 2: Invalid Transfer (Same Department)");
        System.out.println("Attempting to transfer Michael Brown to Marketing again (should fail)");
        empController.transferEmployee(3, 2);

        System.out.println("\nTest Case 3: Invalid Transfer (Non-existent Department)");
        System.out.println("Attempting to transfer to non-existent department 10 (should fail)");
        empController.transferEmployee(3, 10);

        System.out.println("\nTest Case 4: Restoring Original State");
        System.out.println("Transferring Michael Brown back to HR (Dept 3)");
        empController.transferEmployee(3, 3);

        System.out.println("\n--- Final State (Should Match Initial State) ---");
        empController.getEmployeeById(3);


        System.out.println("\n\n======================================");
        System.out.println("ALL TESTS COMPLETED SUCCESSFULLY");
        System.out.println("======================================");
    }
}