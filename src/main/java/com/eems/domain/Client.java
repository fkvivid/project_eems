package com.eems.domain;

/**
 * Domain Model: Client
 * Represents an external client or partner organization
 */
public class Client {
    private int clientId;
    private String name;
    private String industry;
    private String contactPerson;
    private String contactPhone;
    private String contactEmail;

    // Constructors
    public Client() {}

    public Client(int clientId, String name, String industry, String contactPerson,
                  String contactPhone, String contactEmail) {
        this.clientId = clientId;
        this.name = name;
        this.industry = industry;
        this.contactPerson = contactPerson;
        this.contactPhone = contactPhone;
        this.contactEmail = contactEmail;
    }

    public Client(String name, String industry, String contactPerson,
                  String contactPhone, String contactEmail) {
        this.name = name;
        this.industry = industry;
        this.contactPerson = contactPerson;
        this.contactPhone = contactPhone;
        this.contactEmail = contactEmail;
    }

    // Getters and Setters
    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    // Business Logic Methods
    public boolean hasValidContactInfo() {
        return contactEmail != null && !contactEmail.isEmpty() && contactEmail.contains("@");
    }

    @Override
    public String toString() {
        return "Client{" +
                "clientId=" + clientId +
                ", name='" + name + '\'' +
                ", industry='" + industry + '\'' +
                ", contactPerson='" + contactPerson + '\'' +
                ", contactPhone='" + contactPhone + '\'' +
                ", contactEmail='" + contactEmail + '\'' +
                '}';
    }
}