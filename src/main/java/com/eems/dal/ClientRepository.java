package com.eems.dal;

import com.eems.domain.Client;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Layer: Client Repository
 * Handles all database operations for Client entity
 */
public class ClientRepository {

    public Client create(Client client) throws SQLException {
        String sql = "INSERT INTO Client (name, industry, contact_person, contact_phone, contact_email) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, client.getName());
            stmt.setString(2, client.getIndustry());
            stmt.setString(3, client.getContactPerson());
            stmt.setString(4, client.getContactPhone());
            stmt.setString(5, client.getContactEmail());

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        client.setClientId(generatedKeys.getInt(1));
                    }
                }
            }

            return client;
        }
    }

    public Client findById(int clientId) throws SQLException {
        String sql = "SELECT * FROM Client WHERE client_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, clientId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToClient(rs);
                }
            }
        }

        return null;
    }

    public List<Client> findAll() throws SQLException {
        String sql = "SELECT * FROM Client";
        List<Client> clients = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                clients.add(mapResultSetToClient(rs));
            }
        }

        return clients;
    }

    public boolean update(Client client) throws SQLException {
        String sql = "UPDATE Client SET name = ?, industry = ?, contact_person = ?, contact_phone = ?, contact_email = ? WHERE client_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, client.getName());
            stmt.setString(2, client.getIndustry());
            stmt.setString(3, client.getContactPerson());
            stmt.setString(4, client.getContactPhone());
            stmt.setString(5, client.getContactEmail());
            stmt.setInt(6, client.getClientId());

            return stmt.executeUpdate() > 0;
        }
    }

    public boolean delete(int clientId) throws SQLException {
        String sql = "DELETE FROM Client WHERE client_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, clientId);
            return stmt.executeUpdate() > 0;
        }
    }

    public List<Client> findByProjectId(int projectId) throws SQLException {
        String sql = "SELECT c.* FROM Client c " +
                "INNER JOIN Project_Client pc ON c.client_id = pc.client_id " +
                "WHERE pc.project_id = ?";
        List<Client> clients = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, projectId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    clients.add(mapResultSetToClient(rs));
                }
            }
        }

        return clients;
    }

    public List<Client> findByUpcomingProjectDeadline(LocalDate deadline) throws SQLException {
        String sql = "SELECT DISTINCT c.* FROM Client c " +
                "INNER JOIN Project_Client pc ON c.client_id = pc.client_id " +
                "INNER JOIN Project p ON pc.project_id = p.project_id " +
                "WHERE p.end_date <= ?";
        List<Client> clients = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDate(1, Date.valueOf(deadline));

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    clients.add(mapResultSetToClient(rs));
                }
            }
        }

        return clients;
    }

    private Client mapResultSetToClient(ResultSet rs) throws SQLException {
        return new Client(
                rs.getInt("client_id"),
                rs.getString("name"),
                rs.getString("industry"),
                rs.getString("contact_person"),
                rs.getString("contact_phone"),
                rs.getString("contact_email")
        );
    }
}