package com.eems.dal;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * ProjectClientRepository - Manages Project-Client relationships
 */
public class ProjectClientRepository {

    public boolean assignClientToProject(int projectId, int clientId) throws SQLException {
        String sql = "INSERT INTO Project_Client (project_id, client_id) VALUES (?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, projectId);
            stmt.setInt(2, clientId);

            return stmt.executeUpdate() > 0;
        }
    }

    public boolean removeClientFromProject(int projectId, int clientId) throws SQLException {
        String sql = "DELETE FROM Project_Client WHERE project_id = ? AND client_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, projectId);
            stmt.setInt(2, clientId);

            return stmt.executeUpdate() > 0;
        }
    }

    public List<Integer> getClientIdsByProjectId(int projectId) throws SQLException {
        String sql = "SELECT client_id FROM Project_Client WHERE project_id = ?";
        List<Integer> clientIds = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, projectId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    clientIds.add(rs.getInt("client_id"));
                }
            }
        }

        return clientIds;
    }

    public List<Integer> getProjectIdsByClientId(int clientId) throws SQLException {
        String sql = "SELECT project_id FROM Project_Client WHERE client_id = ?";
        List<Integer> projectIds = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, clientId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    projectIds.add(rs.getInt("project_id"));
                }
            }
        }

        return projectIds;
    }
}

/**
 * ProjectDepartmentRepository - Manages Project-Department relationships
 */
class ProjectDepartmentRepository {

    public boolean assignDepartmentToProject(int projectId, int departmentId) throws SQLException {
        String sql = "INSERT INTO Project_Department (project_id, department_id) VALUES (?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, projectId);
            stmt.setInt(2, departmentId);

            return stmt.executeUpdate() > 0;
        }
    }

    public boolean removeDepartmentFromProject(int projectId, int departmentId) throws SQLException {
        String sql = "DELETE FROM Project_Department WHERE project_id = ? AND department_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, projectId);
            stmt.setInt(2, departmentId);

            return stmt.executeUpdate() > 0;
        }
    }

    public List<Integer> getDepartmentIdsByProjectId(int projectId) throws SQLException {
        String sql = "SELECT department_id FROM Project_Department WHERE project_id = ?";
        List<Integer> departmentIds = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, projectId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    departmentIds.add(rs.getInt("department_id"));
                }
            }
        }

        return departmentIds;
    }

    public List<Integer> getProjectIdsByDepartmentId(int departmentId) throws SQLException {
        String sql = "SELECT project_id FROM Project_Department WHERE department_id = ?";
        List<Integer> projectIds = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, departmentId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    projectIds.add(rs.getInt("project_id"));
                }
            }
        }

        return projectIds;
    }
}