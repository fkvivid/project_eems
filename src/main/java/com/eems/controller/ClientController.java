package com.eems.controller;

import java.sql.SQLException;
import java.util.List;

import com.eems.domain.Client;
import com.eems.service.EEMSService;

/**
 * Presentation Layer: Client Controller
 * Handles all client-related operations
 */
public class ClientController {

    private final EEMSService service;

    public ClientController() {
        this.service = new EEMSService();
    }

    public void createClient(String name, String industry, String contactPerson,
                             String contactPhone, String contactEmail) {
        try {
            Client client = new Client(name, industry, contactPerson, contactPhone, contactEmail);
            Client created = service.createClient(client);
            System.out.println("Client created successfully: " + created);
        } catch (SQLException | IllegalArgumentException e) {
            System.err.println("Error creating client: " + e.getMessage());
        }
    }

    public void getClientById(int id) {
        try {
            Client client = service.getClientById(id);
            if (client != null) {
                System.out.println("Client found: " + client);
            } else {
                System.out.println("Client not found with ID: " + id);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving client: " + e.getMessage());
        }
    }

    public void getAllClients() {
        try {
            List<Client> clients = service.getAllClients();
            System.out.println("\n=== All Clients ===");
            for (Client client : clients) {
                System.out.println(client);
            }
            System.out.println("Total: " + clients.size() + " clients");
        } catch (SQLException e) {
            System.err.println("Error retrieving clients: " + e.getMessage());
        }
    }

    public void updateClient(int id, String name, String industry, String contactPerson,
                             String contactPhone, String contactEmail) {
        try {
            Client client = new Client(id, name, industry, contactPerson, contactPhone, contactEmail);
            boolean updated = service.updateClient(client);
            if (updated) {
                System.out.println("Client updated successfully");
            } else {
                System.out.println("Client update failed");
            }
        } catch (SQLException | IllegalArgumentException e) {
            System.err.println("Error updating client: " + e.getMessage());
        }
    }

    public void deleteClient(int id) {
        try {
            boolean deleted = service.deleteClient(id);
            if (deleted) {
                System.out.println("Client deleted successfully");
            } else {
                System.out.println("Client deletion failed");
            }
        } catch (SQLException e) {
            System.err.println("Error deleting client: " + e.getMessage());
        }
    }

    public void findClientsByUpcomingProjectDeadline(int daysUntilDeadline) {
        try {
            List<Client> clients = service.findClientsByUpcomingProjectDeadline(daysUntilDeadline);
            System.out.println("\n=== Clients with Projects Ending in " + daysUntilDeadline + " Days ===");
            for (Client client : clients) {
                System.out.println(client);
            }
            System.out.println("Total: " + clients.size() + " clients");
        } catch (SQLException | IllegalArgumentException e) {
            System.err.println("Error finding clients: " + e.getMessage());
        }
    }
}