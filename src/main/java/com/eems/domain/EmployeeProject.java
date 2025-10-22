package com.eems.domain;

/**
 * Domain Model: EmployeeProject
 * Represents the many-to-many relationship between Employee and Project
 */
public class EmployeeProject {
    private int employeeId;
    private int projectId;
    private int timeAllocationPercent;

    // Constructors
    public EmployeeProject() {}

    public EmployeeProject(int employeeId, int projectId, int timeAllocationPercent) {
        this.employeeId = employeeId;
        this.projectId = projectId;
        this.timeAllocationPercent = timeAllocationPercent;
    }

    // Getters and Setters
    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public int getTimeAllocationPercent() {
        return timeAllocationPercent;
    }

    public void setTimeAllocationPercent(int timeAllocationPercent) {
        this.timeAllocationPercent = timeAllocationPercent;
    }

    // Business Logic Methods
    public boolean isValidAllocation() {
        return timeAllocationPercent > 0 && timeAllocationPercent <= 100;
    }

    @Override
    public String toString() {
        return "EmployeeProject{" +
                "employeeId=" + employeeId +
                ", projectId=" + projectId +
                ", timeAllocationPercent=" + timeAllocationPercent +
                '}';
    }
}