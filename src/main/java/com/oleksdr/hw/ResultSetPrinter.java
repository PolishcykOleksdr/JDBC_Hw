package com.oleksdr.hw;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * author: user,
 * date: 07.04.2026
 */
public class ResultSetPrinter {
    public static void printInfo(ResultSet resultSet) throws SQLException {
        System.out.println("ID: " + resultSet.getInt("id") +
                ", Name: " + resultSet.getString("name") +
                ", Age: " + resultSet.getInt("age") +
                ", Position: " + resultSet.getString("position") +
                ", Salary: " + resultSet.getDouble("salary")
        );
    }
}
