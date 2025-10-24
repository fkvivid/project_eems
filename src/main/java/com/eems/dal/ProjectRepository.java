package com.eems.dal;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.eems.domain.Project;

/**
 * Data Access Layer: Project Repository
 * Handles all database operations for Project entity
 */
public class ProjectRepository {

    public Project create(Project project) throws SQLException {
        String sql = "INSERT INTO Project (name, description, start_date, end_date, budget, status) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, project.getName());
            stmt.setString(2, project.getDescription());
            stmt.setDate(3, Date.valueOf(project.getStartDate()));
            stmt.setDate(4, Date.valueOf(project.getEndDate()));
            stmt.setBigDecimal(5, project.getBudget());
            stmt.setString(6, project.getStatus());

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        project.setProjectId(generatedKeys.getInt(1));
                    }
                }
            }

            return project;
        }
    }

    public Project findById(int projectId) throws SQLException {
        String sql = "SELECT * FROM Project WHERE project_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, projectId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToProject(rs);
                }
            }
        }

        return null;
    }

    public List<Project> findAll() throws SQLException {
        String sql = "SELECT * FROM Project";
        List<Project> projects = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                projects.add(mapResultSetToProject(rs));
            }
        }

        return projects;
    }

    public boolean update(Project project) throws SQLException {
        String sql = "UPDATE Project SET name = ?, description = ?, start_date = ?, end_date = ?, budget = ?, status = ? WHERE project_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, project.getName());
            stmt.setString(2, project.getDescription());
            stmt.setDate(3, Date.valueOf(project.getStartDate()));
            stmt.setDate(4, Date.valueOf(project.getEndDate()));
            stmt.setBigDecimal(5, project.getBudget());
            stmt.setString(6, project.getStatus());
            stmt.setInt(7, project.getProjectId());

            return stmt.executeUpdate() > 0;
        }
    }

    public boolean delete(int projectId) throws SQLException {
        String sql = "DELETE FROM Project WHERE project_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, projectId);
            return stmt.executeUpdate() > 0;
        }
    }

    public List<Project> findActiveByDepartmentId(int departmentId, String sortBy) throws SQLException {
        String sql = "SELECT DISTINCT p.* FROM Project p " +
                "INNER JOIN Project_Department pd ON p.project_id = pd.project_id " +
                "WHERE pd.department_id = ? AND p.status = 'Active' " +
                "ORDER BY " + sortBy;

        List<Project> projects = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, departmentId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    projects.add(mapResultSetToProject(rs));
                }
            }
        }

        return projects;
    }

    public List<Project> findByEndDateBefore(LocalDate deadline) throws SQLException {
        String sql = "SELECT * FROM Project WHERE end_date <= ?";
        List<Project> projects = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDate(1, Date.valueOf(deadline));

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    projects.add(mapResultSetToProject(rs));
                }
            }
        }

        return projects;
    }

    private Project mapResultSetToProject(ResultSet rs) throws SQLException {
        return new Project(
                rs.getInt("project_id"),
                rs.getString("name"),
                rs.getString("description"),
                rs.getDate("start_date").toLocalDate(),
                rs.getDate("end_date").toLocalDate(),
                rs.getBigDecimal("budget"),
                rs.getString("status")
        );
    }
}