/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utilities;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;

/**
 *
 * @author pukarsharma
 */
public class DatabasePreparedQueries {

    private final Connection dbConnection;

    public DatabasePreparedQueries(Connection dbConnection) {
        this.dbConnection = dbConnection;
    }

    // Define prepared statements for your queries
    public PreparedStatement getPassword(String email) throws SQLException {
        String query = "SELECT password FROM employee WHERE email = ?";
        PreparedStatement preparedStatement = dbConnection.prepareStatement(query);
        preparedStatement.setString(1, email);
        return preparedStatement;
    }

    public PreparedStatement getAllEmployees() throws SQLException {
        String query = "SELECT * FROM employee";
        return dbConnection.prepareStatement(query);
    }

    public PreparedStatement getEmployeeDetail(String email) throws SQLException {
        String query = "SELECT uid, name, email, isManager, address, phone FROM employee WHERE email = ?";
        PreparedStatement preparedStatement = dbConnection.prepareStatement(query);
        preparedStatement.setString(1, email);
        return preparedStatement;
    }

    public PreparedStatement getTimesheetDatesForEmployeeLastWeek(Integer employeeID) throws SQLException {
        // Calculate the date range for the last week
        LocalDate today = LocalDate.now();
        LocalDate lastWeekStart = today.minusWeeks(1).with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate lastWeekEnd = lastWeekStart.plusDays(6);

// Format the LocalDate objects as strings in yyyy-MM-dd format
        String lastWeekStartDateStr = lastWeekStart.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String lastWeekEndDateStr = lastWeekEnd.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

// Parse the strings to create java.sql.Date objects
        Date startDate = java.sql.Date.valueOf(lastWeekStartDateStr);
        Date endDate = java.sql.Date.valueOf(lastWeekEndDateStr);

        // SQL query to retrieve dates from last week for the given employee
        String query = "SELECT date FROM attendance WHERE employee_id = ? AND date BETWEEN ? AND ?";
        PreparedStatement preparedStatement = dbConnection.prepareStatement(query);
        preparedStatement.setInt(1, employeeID);
        preparedStatement.setDate(2, startDate);
        preparedStatement.setDate(3, endDate);

        return preparedStatement;
    }

    public PreparedStatement getInsertEmployee() throws SQLException {
        String query = "INSERT INTO employee (name, email, password, isManager, address, phone) VALUES (?, ?, ?, ?, ?, ?)";
        return dbConnection.prepareStatement(query);
    }

    public PreparedStatement checkSuperuserExists() throws SQLException {
        String query = "SELECT 1 FROM employee WHERE isManager = true LIMIT 1";
        return dbConnection.prepareStatement(query);
    }

    public PreparedStatement getInsertAttendance() throws SQLException {
        String query = "INSERT INTO attendance (date, hoursWorked, attendance_type_id, employee_id) VALUES (?, ?, ?, ?)";
        return dbConnection.prepareStatement(query);
    }

    public PreparedStatement getInsertAccounts() throws SQLException {
        String query = "INSERT INTO account (bankName, addedOn, hourlyRate, employee_id) VALUES (?, ?, ?, ?)";
        return dbConnection.prepareStatement(query);
    }
}