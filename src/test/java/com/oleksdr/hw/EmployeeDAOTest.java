package com.oleksdr.hw;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * author: user,
 * date: 07.04.2026
 */
class EmployeeDAOTest {

    private EmployeeDAO employeeDAO = new EmployeeDAO();

    @BeforeEach
    void setEmployeeDAO() {
        employeeDAO = new EmployeeDAO();
    }

    @AfterEach
    void clearEmployeeDAO() {
        employeeDAO.clearTable();
        employeeDAO = null;
    }

    @Test
    void createEmployeesTable() {
        assertTrue(employeeDAO.createEmployeesTable());
    }

    @Test
    void addEmployee() {
        assertTrue(employeeDAO.addEmployee("Oleksandr", 21, "Developer", 0.1));
        assertFalse(employeeDAO.addEmployee("S", -1, "S", 0.0));
    }

    @Test
    void updateEmployee() {
        employeeDAO.createEmployeesTable();
        employeeDAO.addEmployee("Oleksandr", 21, "Developer", 0.1);

        assertTrue(employeeDAO.updateEmployee(1L,"Oleksandr", 21, "Developer", 0.1));
        assertFalse(employeeDAO.updateEmployee(-1L,"S", -1, "S", 0.0));
    }

    @Test
    void deleteEmployee() {
        employeeDAO.createEmployeesTable();
        employeeDAO.addEmployee("Oleksandr", 21, "Developer", 0.1);

        assertTrue(employeeDAO.deleteEmployee(1L));
        assertFalse(employeeDAO.deleteEmployee(-1L));
        assertTrue(employeeDAO.deleteEmployee(1L));
    }

    @Test
    void getAllEmployees() {
        assertTrue(employeeDAO.getAllEmployees());
    }

    @Test
    void getEmployeeById() {
        assertTrue(employeeDAO.getEmployeeById(-1L));
        assertTrue(employeeDAO.getEmployeeById(1L));

        employeeDAO.createEmployeesTable();
        employeeDAO.addEmployee("Oleksandr", 21, "Developer", 0.1);

        assertTrue(employeeDAO.getEmployeeById(1L));
    }
    @Test
    void clearTable() {
        assertTrue(employeeDAO.clearTable());
    }
}
