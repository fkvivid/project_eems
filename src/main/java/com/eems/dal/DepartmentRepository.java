package com.eems.dal;

import com.eems.domain.Department;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Layer: Department Repository
 * Handles all database operations for Department entity
 */
public class DepartmentRepository {

    public Department create(Department department) throws SQLException {
        String sql = "INSERT INTO Department (name, location, annual_budget) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, department.getName());
            stmt.setString(2, department.getLocation());
            stmt.setBigDecimal(3, department.getAnnualBudget());

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        department.setDepartmentId(generatedKeys.getInt(1));
                    }
                }
            }

            return department;
        }
    }

    public Department findById(int departmentId) throws SQLException {
        String sql = "SELECT * FROM Department WHERE department_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, departmentId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToDepartment(rs);
                }
            }
        }

        return null;
    }

    public List<Department> findAll() throws SQLException {
        String sql = "SELECT * FROM Department";
        List<Department> departments = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                departments.add(mapResultSetToDepartment(rs));
            }
        }

        return departments;
    }

    public boolean update(Department department) throws SQLException {
        String sql = "UPDATE Department SET name = ?, location = ?, annual_budget = ? WHERE department_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, department.getName());
            stmt.setString(2, department.getLocation());
            stmt.setBigDecimal(3, department.getAnnualBudget());
            stmt.setInt(4, department.getDepartmentId());

            return stmt.executeUpdate() > 0;
        }
    }

    public boolean delete(int departmentId) throws SQLException {
        // Check if department has employees
        String checkSql = "SELECT COUNT(*) FROM Employee WHERE department_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {

            checkStmt.setInt(1, departmentId);
            try (ResultSet rs = checkStmt.executeQuery()) {
                if (rs.next() && rs.getInt(1) > 0) {
                    throw new SQLException("Cannot delete department with existing employees");
                }
            }
        }

        String sql = "DELETE FROM Department WHERE department_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, departmentId);
            return stmt.executeUpdate() > 0;
        }
    }

    private Department mapResultSetToDepartment(ResultSet rs) throws SQLException {
        return new Department(
                rs.getInt("department_id"),
                rs.getString("name"),
                rs.getString("location"),
                rs.getBigDecimal("annual_budget")
        );
    }
}