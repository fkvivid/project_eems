package com.eems.domain;

import java.math.BigDecimal;

/**
 * Domain Model: Department
 * Represents an organizational unit within the company
 */
public class Department {
    private int departmentId;
    private String name;
    private String location;
    private BigDecimal annualBudget;

    // Constructors
    public Department() {}

    public Department(int departmentId, String name, String location, BigDecimal annualBudget) {
        this.departmentId = departmentId;
        this.name = name;
        this.location = location;
        this.annualBudget = annualBudget;
    }

    public Department(String name, String location, BigDecimal annualBudget) {
        this.name = name;
        this.location = location;
        this.annualBudget = annualBudget;
    }

    // Getters and Setters
    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public BigDecimal getAnnualBudget() {
        return annualBudget;
    }

    public void setAnnualBudget(BigDecimal annualBudget) {
        this.annualBudget = annualBudget;
    }

    // Business Logic Methods
    public boolean isValidBudget() {
        return annualBudget != null && annualBudget.compareTo(BigDecimal.ZERO) > 0;
    }

    @Override
    public String toString() {
        return "Department{" +
                "departmentId=" + departmentId +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", annualBudget=" + annualBudget +
                '}';
    }
}