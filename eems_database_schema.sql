-- ============================================
-- EEMS Database Schema
-- ============================================

-- Drop tables if they exist (for clean setup)
DROP TABLE IF EXISTS Employee_Project;
DROP TABLE IF EXISTS Project_Client;
DROP TABLE IF EXISTS Project_Department;
DROP TABLE IF EXISTS Employee;
DROP TABLE IF EXISTS Project;
DROP TABLE IF EXISTS Client;
DROP TABLE IF EXISTS Department;

-- ============================================
-- Create Tables
-- ============================================

-- Department Table
CREATE TABLE Department (
    department_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    location VARCHAR(100) NOT NULL,
    annual_budget DECIMAL(15, 2) NOT NULL
);

-- Employee Table
CREATE TABLE Employee (
    employee_id INT PRIMARY KEY AUTO_INCREMENT,
    full_name VARCHAR(100) NOT NULL,
    title VARCHAR(50) NOT NULL,
    hire_date DATE NOT NULL,
    salary DECIMAL(10, 2) NOT NULL,
    department_id INT NOT NULL,
    FOREIGN KEY (department_id) REFERENCES Department(department_id)
);

-- Project Table
CREATE TABLE Project (
    project_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    budget DECIMAL(15, 2) NOT NULL,
    status VARCHAR(20) NOT NULL
);

-- Client Table
CREATE TABLE Client (
    client_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    industry VARCHAR(50) NOT NULL,
    contact_person VARCHAR(100) NOT NULL,
    contact_phone VARCHAR(20),
    contact_email VARCHAR(100)
);

-- Junction Table: Employee-Project (M:M)
CREATE TABLE Employee_Project (
    employee_id INT NOT NULL,
    project_id INT NOT NULL,
    time_allocation_percent INT NOT NULL,
    PRIMARY KEY (employee_id, project_id),
    FOREIGN KEY (employee_id) REFERENCES Employee(employee_id) ON DELETE CASCADE,
    FOREIGN KEY (project_id) REFERENCES Project(project_id) ON DELETE CASCADE
);

-- Junction Table: Project-Client (M:M)
CREATE TABLE Project_Client (
    project_id INT NOT NULL,
    client_id INT NOT NULL,
    PRIMARY KEY (project_id, client_id),
    FOREIGN KEY (project_id) REFERENCES Project(project_id) ON DELETE CASCADE,
    FOREIGN KEY (client_id) REFERENCES Client(client_id) ON DELETE CASCADE
);

-- Junction Table: Project-Department (M:M)
CREATE TABLE Project_Department (
    project_id INT NOT NULL,
    department_id INT NOT NULL,
    PRIMARY KEY (project_id, department_id),
    FOREIGN KEY (project_id) REFERENCES Project(project_id) ON DELETE CASCADE,
    FOREIGN KEY (department_id) REFERENCES Department(department_id) ON DELETE CASCADE
);

-- ============================================
-- Sample Data Inserts
-- ============================================

-- Insert Departments
INSERT INTO Department (name, location, annual_budget) VALUES
('Software Engineering', 'Building A - New York', 5000000.00),
('Marketing', 'Building B - Los Angeles', 2500000.00),
('Human Resources', 'Building A - New York', 1500000.00),
('Finance', 'Building C - Chicago', 3000000.00),
('Operations', 'Building D - Seattle', 4000000.00);

-- Insert Employees
INSERT INTO Employee (full_name, title, hire_date, salary, department_id) VALUES
('John Smith', 'Senior Developer', '2020-03-15', 95000.00, 1),
('Sarah Johnson', 'Marketing Manager', '2019-07-22', 85000.00, 2),
('Michael Brown', 'HR Specialist', '2021-01-10', 65000.00, 3),
('Emily Davis', 'Financial Analyst', '2018-11-05', 78000.00, 4),
('David Wilson', 'Operations Manager', '2020-06-18', 92000.00, 5),
('Jennifer Martinez', 'Software Architect', '2017-09-12', 115000.00, 1),
('Robert Taylor', 'Marketing Coordinator', '2022-02-28', 55000.00, 2),
('Lisa Anderson', 'HR Director', '2016-05-20', 105000.00, 3),
('James Thomas', 'Senior Accountant', '2019-08-14', 82000.00, 4),
('Mary Jackson', 'Operations Specialist', '2021-04-03', 68000.00, 5);

-- Insert Projects
INSERT INTO Project (name, description, start_date, end_date, budget, status) VALUES
('Cloud Migration', 'Migrate all legacy systems to cloud infrastructure', '2024-01-15', '2025-12-31', 2000000.00, 'Active'),
('Brand Refresh', 'Complete company rebranding initiative', '2024-06-01', '2025-03-15', 500000.00, 'Active'),
('Employee Wellness Program', 'Implement comprehensive wellness program', '2024-03-01', '2025-06-30', 300000.00, 'Active'),
('Financial System Upgrade', 'Upgrade to new ERP system', '2023-09-01', '2024-11-30', 1500000.00, 'Completed'),
('Supply Chain Optimization', 'Optimize supply chain processes', '2024-08-01', '2025-11-20', 800000.00, 'Active');

-- Insert Clients
INSERT INTO Client (name, industry, contact_person, contact_phone, contact_email) VALUES
('TechCorp Solutions', 'Technology', 'Alice Cooper', '555-0101', 'alice.cooper@techcorp.com'),
('Global Retailers Inc', 'Retail', 'Bob Miller', '555-0102', 'bob.miller@globalretail.com'),
('Healthcare Partners', 'Healthcare', 'Carol White', '555-0103', 'carol.white@healthpartners.com'),
('Finance First', 'Financial Services', 'Dan Green', '555-0104', 'dan.green@financefirst.com'),
('Manufacturing Pro', 'Manufacturing', 'Eve Black', '555-0105', 'eve.black@mfgpro.com');

-- Insert Employee-Project relationships
INSERT INTO Employee_Project (employee_id, project_id, time_allocation_percent) VALUES
(1, 1, 80),
(6, 1, 100),
(2, 2, 60),
(7, 2, 80),
(3, 3, 50),
(8, 3, 70),
(4, 4, 40),
(9, 4, 90),
(5, 5, 75),
(10, 5, 85),
(1, 5, 20),
(2, 3, 40),
(4, 1, 60),
(6, 2, 40),
(9, 5, 10);

-- Insert Project-Client relationships
INSERT INTO Project_Client (project_id, client_id) VALUES
(1, 1),
(1, 3),
(2, 2),
(3, 3),
(4, 4),
(5, 5),
(5, 2),
(2, 1),
(3, 1),
(1, 4);

-- Insert Project-Department relationships
INSERT INTO Project_Department (project_id, department_id) VALUES
(1, 1),
(1, 5),
(2, 2),
(3, 3),
(4, 4),
(4, 1),
(5, 5),
(5, 1),
(2, 1),
(3, 2);