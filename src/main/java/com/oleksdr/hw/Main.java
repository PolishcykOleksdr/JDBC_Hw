package com.oleksdr.hw;

import java.sql.SQLException;

/**
 * author: user,
 * date: 07.04.2026
 */
public class Main {
    public static void main(String[] args) throws SQLException {
        EmployeeDAO employeeDAO = new EmployeeDAO();
        employeeDAO.createEmployeesTable();
        employeeDAO.clearTable();
        try{
            employeeDAO.addEmployee("Oleksandr", 21, "Developer", 0.1);
            employeeDAO.addEmployee("Sergiy", 18, "Intern", 0.0);
            employeeDAO.addEmployee("Maria", 11, "Backend dev", 10_000.0);
            employeeDAO.addEmployee("Nikita", 21, "Developer", 8_000.0);

            employeeDAO.getAllEmployees();

            employeeDAO.updateEmployee(1L, "Artem", 56, "QA", 12_000.0);

            employeeDAO.getAllEmployees();

            employeeDAO.deleteEmployee(3L);
            employeeDAO.getAllEmployees();

            employeeDAO.getEmployeeById(2L);
        } catch (RuntimeException e){
            System.out.println("Failed - " + e.getMessage());
        }
    }
}
