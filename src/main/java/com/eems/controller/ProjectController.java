package com.eems.controller;

import com.eems.domain.Project;
import com.eems.service.EEMSService;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

/**
 * Presentation Layer: Project Controller
 * Handles all project-related operations
 */
public class ProjectController {

    private EEMSService service;

    public ProjectController() {
        this.service = new EEMSService();
    }

    public void createProject(String name, String description, LocalDate startDate,
                              LocalDate endDate, BigDecimal budget, String status) {
        try {
            Project proj = new Project(name, description, startDate, endDate, budget, status);
            Project created = service.createProject(proj);
            System.out.println("Project created successfully: " + created);
        } catch (SQLException | IllegalArgumentException e) {
            System.err.println("Error creating project: " + e.getMessage());
        }
    }

    public void getProjectById(int id) {
        try {
            Project proj = service.getProjectById(id);
            if (proj != null) {
                System.out.println("Project found: " + proj);
            } else {
                System.out.println("Project not found with ID: " + id);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving project: " + e.getMessage());
        }
    }

    public void getAllProjects() {
        try {
            List<Project> projects = service.getAllProjects();
            System.out.println("\n=== All Projects ===");
            for (Project proj : projects) {
                System.out.println(proj);
            }
            System.out.println("Total: " + projects.size() + " projects");
        } catch (SQLException e) {
            System.err.println("Error retrieving projects: " + e.getMessage());
        }
    }

    public void updateProject(int id, String name, String description, LocalDate startDate,
                              LocalDate endDate, BigDecimal budget, String status) {
        try {
            Project proj = new Project(id, name, description, startDate, endDate, budget, status);
            boolean updated = service.updateProject(proj);
            if (updated) {
                System.out.println("Project updated successfully");
            } else {
                System.out.println("Project update failed");
            }
        } catch (SQLException | IllegalArgumentException e) {
            System.err.println("Error updating project: " + e.getMessage());
        }
    }

    public void deleteProject(int id) {
        try {
            boolean deleted = service.deleteProject(id);
            if (deleted) {
                System.out.println("Project deleted successfully");
            } else {
                System.out.println("Project deletion failed");
            }
        } catch (SQLException e) {
            System.err.println("Error deleting project: " + e.getMessage());
        }
    }

    public void calculateProjectHRCost(int projectId) {
        try {
            BigDecimal cost = service.calculateProjectHRCost(projectId);
            System.out.println("Project HR Cost for Project " + projectId + ": $" + cost);
        } catch (SQLException | IllegalArgumentException e) {
            System.err.println("Error calculating project cost: " + e.getMessage());
        }
    }

    public void getProjectsByDepartment(int departmentId, String sortBy) {
        try {
            List<Project> projects = service.getProjectsByDepartment(departmentId, sortBy);
            System.out.println("\n=== Active Projects for Department " + departmentId + " (sorted by " + sortBy + ") ===");
            for (Project proj : projects) {
                System.out.println(proj);
            }
            System.out.println("Total: " + projects.size() + " projects");
        } catch (SQLException | IllegalArgumentException e) {
            System.err.println("Error retrieving department projects: " + e.getMessage());
        }
    }
}