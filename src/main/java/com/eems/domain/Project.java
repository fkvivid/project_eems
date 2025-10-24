package com.eems.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * Domain Model: Project
 * Represents an operational task or initiative
 */
public class Project {
    private int projectId;
    private String name;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal budget;
    private String status;

    // Constructors
    public Project() {}

    public Project(int projectId, String name, String description, LocalDate startDate,
                   LocalDate endDate, BigDecimal budget, String status) {
        this.projectId = projectId;
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.budget = budget;
        this.status = status;
    }

    public Project(String name, String description, LocalDate startDate,
                   LocalDate endDate, BigDecimal budget, String status) {
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.budget = budget;
        this.status = status;
    }

    // Getters and Setters
    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public BigDecimal getBudget() {
        return budget;
    }

    public void setBudget(BigDecimal budget) {
        this.budget = budget;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Business Logic Methods
    public long getDurationInMonths() {
        // Calculate days between dates (inclusive)
        long days = ChronoUnit.DAYS.between(startDate, endDate) + 1;
        // Convert to months, rounding up partial months
        return (long) Math.ceil(days / 30.0);
    }

    public boolean isActive() {
        return "Active".equalsIgnoreCase(status);
    }

    public boolean isEndingSoon(int daysUntilDeadline) {
        LocalDate deadline = LocalDate.now().plusDays(daysUntilDeadline);
        return endDate.isBefore(deadline) || endDate.isEqual(deadline);
    }

    @Override
    public String toString() {
        return "Project{" +
                "projectId=" + projectId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", budget=" + budget +
                ", status='" + status + '\'' +
                '}';
    }
}