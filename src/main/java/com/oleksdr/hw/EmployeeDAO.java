package com.oleksdr.hw;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

/**
 * author: user,
 * date: 07.04.2026
 */
public class EmployeeDAO {
    private final DatabaseConnector databaseConnector;
    private final static Logger logger = LoggerFactory.getLogger(EmployeeDAO.class);

    public EmployeeDAO(){
        this.databaseConnector = new DatabaseConnector(
                "jdbc:postgresql://localhost:5432/company?currentSchema=public",
                "postgres",
                "rootroot"
        );
    }
    public boolean createEmployeesTable() {
        String sql = """
                        create table if not exists employees (
                            id integer generated always as identity primary key,
                            name varchar(256) not null,
                            age integer not null check (age > 0),
                            position varchar(256),
                            salary float8
                        );
                        """;
        Connection connection = databaseConnector.getConnectionObject();

        try(Statement statement = connection.createStatement()) {
            statement.execute(sql);
            logger.info("Database created successfully");
            return true;
        } catch (SQLException e) {
            logger.error("Error creating failed - {}", e.getMessage());
            return false;
        }
    }
    public boolean addEmployee(String name, Integer age, String position, Double salary) {
        String sql = """
                        insert into employees (name, age, position, salary) values (?, ?, ?, ?);
                        """;
        Connection connection = databaseConnector.getConnectionObject();

        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, age);
            preparedStatement.setString(3, position);
            preparedStatement.setDouble(4, salary);

            preparedStatement.execute();
            logger.info("Employee added successfully!!!");
            return true;
        } catch (SQLException e) {
            logger.error("Failed adding employee - {}", e.getMessage());
            return false;
        }
    }
    public boolean updateEmployee(Long id, String name, Integer age, String position, Double salary) {
        String sql = """
                        update employees set name = ?, age = ?, position = ?, salary = ?
                        where id = ?;
                        """;
        if(id == -1){
            logger.error("Invalid employee id in update employee - {}", id);
            return false;
        }
        Connection connection = databaseConnector.getConnectionObject();

        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, age);
            preparedStatement.setString(3, position);
            preparedStatement.setDouble(4, salary);
            preparedStatement.setDouble(5, id);

            preparedStatement.execute();
            logger.info("Employee update successfully with id {}", id);
            return true;
        } catch (SQLException e) {
            logger.error("Failed updating employee - {}", e.getMessage());
            return false;
        }
    }
    public boolean deleteEmployee(Long id)  {
        String sql = """
                        delete from employees where id = ?;
                        """;

        if(id == -1){
            logger.error("Invalid employee id in delete employee - {}", id);
            return false;
        }

        Connection connection = databaseConnector.getConnectionObject();

        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);

            preparedStatement.execute();
            logger.info("Employee delete successfully with id {}", id);
            return true;
        } catch (SQLException e) {
            logger.error("Failed deleting employee - {}", e.getMessage());
            return false;
        }

    }
    public boolean getAllEmployees(){
        String sql = """
                        select * from employees;
        """;
        Connection connection = databaseConnector.getConnectionObject();
        try(Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);

            while(resultSet.next()) {
                System.out.println("ID: " + resultSet.getInt("id") +
                        ", Name: " + resultSet.getString("name") +
                        ", Age: " + resultSet.getInt("age") +
                        ", Position: " + resultSet.getString("position") +
                        ", Salary: " + resultSet.getDouble("salary")
                );
            }
            logger.info("All employees selected successfully!!!");
            return true;
        } catch (SQLException e) {
            logger.error("Failed getting all employees table - {}", e.getMessage());
            return false;
        }
    }
    public boolean getEmployeeById(Long id){
        String sql = """
                        select * from employees where id = ?;
        """;
        Connection connection = databaseConnector.getConnectionObject();
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                ResultSetPrinter.printInfo(resultSet);
            } else {
                logger.info("Employee with id {} not found", id);
            }
            return true;
        } catch (SQLException e) {
            logger.error("Failed getting employee by id - {}", e.getMessage());
            return false;
        }
    }
    public boolean clearTable(){
        String sql = """
                        truncate table employees restart identity;
        """;
        Connection connection = databaseConnector.getConnectionObject();
        try(Statement statement = connection.createStatement()){
            statement.execute(sql);
            logger.info("All employees truncated successfully!!!");
            return true;
        } catch (SQLException e) {
            logger.error("Error in clearing the table - {}", e.getMessage());
            return false;
        }
    }
    public void endDAO(){
        databaseConnector.closeConnection();
    }
}
