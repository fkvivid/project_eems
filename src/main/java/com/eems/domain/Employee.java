package com.eems.domain;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Domain Model: Employee
 * Represents a member of the workforce
 */
public class Employee {
    private int employeeId;
    private String fullName;
    private String title;
    private LocalDate hireDate;
    private BigDecimal salary;
    private int departmentId;

    // Constructors
    public Employee() {}

    public Employee(int employeeId, String fullName, String title, LocalDate hireDate,
                    BigDecimal salary, int departmentId) {
        this.employeeId = employeeId;
        this.fullName = fullName;
        this.title = title;
        this.hireDate = hireDate;
        this.salary = salary;
        this.departmentId = departmentId;
    }

    public Employee(String fullName, String title, LocalDate hireDate,
                    BigDecimal salary, int departmentId) {
        this.fullName = fullName;
        this.title = title;
        this.hireDate = hireDate;
        this.salary = salary;
        this.departmentId = departmentId;
    }

    // Getters and Setters
    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getHireDate() {
        return hireDate;
    }

    public void setHireDate(LocalDate hireDate) {
        this.hireDate = hireDate;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    // Business Logic Methods
    public BigDecimal getMonthlySalary() {
        return salary.divide(new BigDecimal("12"), 2, BigDecimal.ROUND_HALF_UP);
    }

    public boolean isValidSalary() {
        return salary != null && salary.compareTo(BigDecimal.ZERO) > 0;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "employeeId=" + employeeId +
                ", fullName='" + fullName + '\'' +
                ", title='" + title + '\'' +
                ", hireDate=" + hireDate +
                ", salary=" + salary +
                ", departmentId=" + departmentId +
                '}';
    }
}