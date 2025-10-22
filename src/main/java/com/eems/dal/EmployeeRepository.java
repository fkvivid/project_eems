package com.eems.dal;

import com.eems.domain.Employee;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Layer: Employee Repository
 * Handles all database operations for Employee entity
 */
public class EmployeeRepository {

    public Employee create(Employee employee) throws SQLException {
        String sql = "INSERT INTO Employee (full_name, title, hire_date, salary, department_id) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, employee.getFullName());
            stmt.setString(2, employee.getTitle());
            stmt.setDate(3, Date.valueOf(employee.getHireDate()));
            stmt.setBigDecimal(4, employee.getSalary());
            stmt.setInt(5, employee.getDepartmentId());

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        employee.setEmployeeId(generatedKeys.getInt(1));
                    }
                }
            }

            return employee;
        }
    }

    public Employee findById(int employeeId) throws SQLException {
        String sql = "SELECT * FROM Employee WHERE employee_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, employeeId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToEmployee(rs);
                }
            }
        }

        return null;
    }

    public List<Employee> findAll() throws SQLException {
        String sql = "SELECT * FROM Employee";
        List<Employee> employees = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                employees.add(mapResultSetToEmployee(rs));
            }
        }

        return employees;
    }

    public boolean update(Employee employee) throws SQLException {
        String sql = "UPDATE Employee SET full_name = ?, title = ?, hire_date = ?, salary = ?, department_id = ? WHERE employee_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, employee.getFullName());
            stmt.setString(2, employee.getTitle());
            stmt.setDate(3, Date.valueOf(employee.getHireDate()));
            stmt.setBigDecimal(4, employee.getSalary());
            stmt.setInt(5, employee.getDepartmentId());
            stmt.setInt(6, employee.getEmployeeId());

            return stmt.executeUpdate() > 0;
        }
    }

    public boolean delete(int employeeId) throws SQLException {
        String sql = "DELETE FROM Employee WHERE employee_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, employeeId);
            return stmt.executeUpdate() > 0;
        }
    }

    public List<Employee> findByProjectId(int projectId) throws SQLException {
        String sql = "SELECT e.* FROM Employee e " +
                "INNER JOIN Employee_Project ep ON e.employee_id = ep.employee_id " +
                "WHERE ep.project_id = ?";
        List<Employee> employees = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, projectId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    employees.add(mapResultSetToEmployee(rs));
                }
            }
        }

        return employees;
    }

    private Employee mapResultSetToEmployee(ResultSet rs) throws SQLException {
        return new Employee(
                rs.getInt("employee_id"),
                rs.getString("full_name"),
                rs.getString("title"),
                rs.getDate("hire_date").toLocalDate(),
                rs.getBigDecimal("salary"),
                rs.getInt("department_id")
        );
    }
}