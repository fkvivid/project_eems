package com.eems.controller;

import com.eems.domain.Department;
import com.eems.service.EEMSService;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

/**
 * Presentation Layer: Department Controller
 * Handles all department-related operations
 */
public class DepartmentController {

    private EEMSService service;

    public DepartmentController() {
        this.service = new EEMSService();
    }

    public void createDepartment(String name, String location, BigDecimal budget) {
        try {
            Department dept = new Department(name, location, budget);
            Department created = service.createDepartment(dept);
            System.out.println("Department created successfully: " + created);
        } catch (SQLException | IllegalArgumentException e) {
            System.err.println("Error creating department: " + e.getMessage());
        }
    }

    public void getDepartmentById(int id) {
        try {
            Department dept = service.getDepartmentById(id);
            if (dept != null) {
                System.out.println("Department found: " + dept);
            } else {
                System.out.println("Department not found with ID: " + id);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving department: " + e.getMessage());
        }
    }

    public void getAllDepartments() {
        try {
            List<Department> departments = service.getAllDepartments();
            System.out.println("\n=== All Departments ===");
            for (Department dept : departments) {
                System.out.println(dept);
            }
            System.out.println("Total: " + departments.size() + " departments");
        } catch (SQLException e) {
            System.err.println("Error retrieving departments: " + e.getMessage());
        }
    }

    public void updateDepartment(int id, String name, String location, BigDecimal budget) {
        try {
            Department dept = new Department(id, name, location, budget);
            boolean updated = service.updateDepartment(dept);
            if (updated) {
                System.out.println("Department updated successfully");
            } else {
                System.out.println("Department update failed");
            }
        } catch (SQLException | IllegalArgumentException e) {
            System.err.println("Error updating department: " + e.getMessage());
        }
    }

    public void deleteDepartment(int id) {
        try {
            boolean deleted = service.deleteDepartment(id);
            if (deleted) {
                System.out.println("Department deleted successfully");
            } else {
                System.out.println("Department deletion failed");
            }
        } catch (SQLException e) {
            System.err.println("Error deleting department: " + e.getMessage());
        }
    }
}