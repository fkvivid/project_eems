package com.eems.controller;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import com.eems.domain.Employee;
import com.eems.service.EEMSService;

/**
 * Presentation Layer: Employee Controller
 * Handles all employee-related operations
 */
public class EmployeeController {

    private final EEMSService service;

    public EmployeeController() {
        this.service = new EEMSService();
    }

    public void createEmployee(String fullName, String title, LocalDate hireDate,
                               BigDecimal salary, int departmentId) {
        try {
            Employee emp = new Employee(fullName, title, hireDate, salary, departmentId);
            Employee created = service.createEmployee(emp);
            System.out.println("Employee created successfully: " + created);
        } catch (SQLException | IllegalArgumentException e) {
            System.err.println("Error creating employee: " + e.getMessage());
        }
    }

    public void getEmployeeById(int id) {
        try {
            Employee emp = service.getEmployeeById(id);
            if (emp != null) {
                System.out.println("Employee found: " + emp);
            } else {
                System.out.println("Employee not found with ID: " + id);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving employee: " + e.getMessage());
        }
    }

    public void getAllEmployees() {
        try {
            List<Employee> employees = service.getAllEmployees();
            System.out.println("\n=== All Employees ===");
            employees.forEach(System.out::println);
            System.out.println("Total: " + employees.size() + " employees");
        } catch (SQLException e) {
            System.err.println("Error retrieving employees: " + e.getMessage());
        }
    }

    public void updateEmployee(int id, String fullName, String title, LocalDate hireDate,
                               BigDecimal salary, int departmentId) {
        try {
            Employee emp = new Employee(id, fullName, title, hireDate, salary, departmentId);
            boolean updated = service.updateEmployee(emp);
            if (updated) {
                System.out.println("Employee updated successfully");
            } else {
                System.out.println("Employee update failed");
            }
        } catch (SQLException | IllegalArgumentException e) {
            System.err.println("Error updating employee: " + e.getMessage());
        }
    }

    public void deleteEmployee(int id) {
        try {
            boolean deleted = service.deleteEmployee(id);
            if (deleted) {
                System.out.println("Employee deleted successfully");
            } else {
                System.out.println("Employee deletion failed");
            }
        } catch (SQLException e) {
            System.err.println("Error deleting employee: " + e.getMessage());
        }
    }

    public void transferEmployeeToDepartment(int employeeId, int newDepartmentId) {
        try {
            boolean transferred = service.transferEmployeeToDepartment(employeeId, newDepartmentId);
            if (transferred) {
                System.out.println("Employee transferred successfully to department " + newDepartmentId);
            } else {
                System.out.println("Employee transfer failed");
            }
        } catch (SQLException | IllegalArgumentException e) {
            System.err.println("Error transferring employee: " + e.getMessage());
        }
    }
}