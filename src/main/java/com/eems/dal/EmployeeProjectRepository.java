package com.eems.dal;

import com.eems.domain.EmployeeProject;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Layer: EmployeeProject Repository
 * Handles all database operations for Employee-Project relationship
 */
public class EmployeeProjectRepository {

    public boolean create(EmployeeProject employeeProject) throws SQLException {
        String sql = "INSERT INTO Employee_Project (employee_id, project_id, time_allocation_percent) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, employeeProject.getEmployeeId());
            stmt.setInt(2, employeeProject.getProjectId());
            stmt.setInt(3, employeeProject.getTimeAllocationPercent());

            return stmt.executeUpdate() > 0;
        }
    }

    public EmployeeProject findByIds(int employeeId, int projectId) throws SQLException {
        String sql = "SELECT * FROM Employee_Project WHERE employee_id = ? AND project_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, employeeId);
            stmt.setInt(2, projectId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToEmployeeProject(rs);
                }
            }
        }

        return null;
    }

    public List<EmployeeProject> findByProjectId(int projectId) throws SQLException {
        String sql = "SELECT * FROM Employee_Project WHERE project_id = ?";
        return getEmployeeProjects(projectId, sql);
    }

    private List<EmployeeProject> getEmployeeProjects(int projectId, String sql) throws SQLException {
        List<EmployeeProject> assignments = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, projectId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    assignments.add(mapResultSetToEmployeeProject(rs));
                }
            }
        }

        return assignments;
    }

    public List<EmployeeProject> findByEmployeeId(int employeeId) throws SQLException {
        String sql = "SELECT * FROM Employee_Project WHERE employee_id = ?";
        return getEmployeeProjects(employeeId, sql);
    }

    public boolean update(EmployeeProject employeeProject) throws SQLException {
        String sql = "UPDATE Employee_Project SET time_allocation_percent = ? WHERE employee_id = ? AND project_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, employeeProject.getTimeAllocationPercent());
            stmt.setInt(2, employeeProject.getEmployeeId());
            stmt.setInt(3, employeeProject.getProjectId());

            return stmt.executeUpdate() > 0;
        }
    }

    public boolean delete(int employeeId, int projectId) throws SQLException {
        String sql = "DELETE FROM Employee_Project WHERE employee_id = ? AND project_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, employeeId);
            stmt.setInt(2, projectId);
            return stmt.executeUpdate() > 0;
        }
    }

    private EmployeeProject mapResultSetToEmployeeProject(ResultSet rs) throws SQLException {
        return new EmployeeProject(
                rs.getInt("employee_id"),
                rs.getInt("project_id"),
                rs.getInt("time_allocation_percent")
        );
    }
}