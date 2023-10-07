/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import utilities.AlertUtils;
import utilities.DatabaseConstants;
import utilities.DatabasePreparedQueries;
import utilities.PasswordUtils;

public class DatabaseModel {

    private Connection dbConnection;
    private final DatabasePreparedQueries dbQueries;

    public DatabaseModel(String mysqlUrl, String dbUrl, String dbCreateStatement, String userName, String password) {
        try {
            // Connect to MySQL server
            dbConnection = DriverManager.getConnection(mysqlUrl, userName, password);
            createDatabaseIfNotExists(dbCreateStatement);

            // Reconnect to the specific database
            dbConnection.close();
            dbConnection = DriverManager.getConnection(dbUrl, userName, password);

            dbQueries = new DatabasePreparedQueries(dbConnection);

            // Create tables if needed
            createTablesIfNotExists();

        } catch (SQLException e) {
            handleSQLException(e);
            throw new RuntimeException("Database initialization failed.", e);
        }
    }

    private void createDatabaseIfNotExists(String dbCreateStatement) {
        try (Statement statement = dbConnection.createStatement()) {
            statement.executeUpdate(dbCreateStatement);
        } catch (SQLException e) {
            handleSQLException(e);
        }
    }

    private void createTablesIfNotExists() {
        createEmployeeTableIfNotExists();
        createAttendaceTypeTableIfNotExists();
        createAttendaceTableIfNotExists();
        createAccountTableIfNotExists();
    }

    private void createEmployeeTableIfNotExists() {
        try (Statement statement = dbConnection.createStatement()) {
            statement.executeUpdate(DatabaseConstants.QRY_CREATE_EMPLOYEE_TABLE);

            // insert default super user
            insertSuperuserIfNotExists();
        } catch (SQLException e) {
            handleSQLException(e);
        }
    }

    private void createAttendaceTypeTableIfNotExists() {
        try (Statement statement = dbConnection.createStatement()) {
            statement.executeUpdate(DatabaseConstants.QRY_CREATE_ATTENDANCE_TYPE_TABLE);
            insertDefaultAttendanceType();
        } catch (SQLException e) {
            handleSQLException(e);
        }
    }

    private void createAttendaceTableIfNotExists() {
        try (Statement statement = dbConnection.createStatement()) {
            statement.executeUpdate(DatabaseConstants.QRY_CREATE_ATTENDANCE_TABLE);
        } catch (SQLException e) {
            handleSQLException(e);
        }
    }

    private void createAccountTableIfNotExists() {
        try (Statement statement = dbConnection.createStatement()) {
            statement.executeUpdate(DatabaseConstants.QRY_CREATE_ACCOUNT_TABLE);
        } catch (SQLException e) {
            handleSQLException(e);
        }
    }

    public boolean insertSuperuserIfNotExists() {
        try {
            // Check if a superuser already exists
            PreparedStatement checkSuperuserStatement = dbQueries.checkSuperuserExists();
            ResultSet resultSet = checkSuperuserStatement.executeQuery();

            if (!resultSet.next()) {
                // Superuser does not exist, insert it securely
                String name = "superuser";
                String password = "admin";
                String email = "superuser@gmail.com";
                String address = "1234, abc road, nsw";
                String phone = "0406277803";
                boolean isManager = true;
                return insertEmployee(name, email, password, isManager, address, phone);
            }
        } catch (SQLException e) {
            handleSQLException(e);
            return false;
        }
        return false;
    }

//    public boolean insertAppointment(Appointment attn) {
//        try {
//            PreparedStatement insertStatement = dbQueries.getInsertAttendance();
//            insertStatement.setString(1, attn.getDate());
//            insertStatement.setDouble(2, attn.getHoursWorked());
//            insertStatement.setInt(3, attn.getAttendance_type_id());
//            insertStatement.setInt(4, attn.getEmployeeID());
//
//            int rowsAffected = insertStatement.executeUpdate();
//            return rowsAffected > 0;
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return false;
//        }
//    }

    public boolean insertEmployee(String name, String email, String password, boolean isAdmin, String address, String phone) {
        try {
            if (getEmployeeDetails(email) == null) {
                String hashedAndSaltedPassword = PasswordUtils.hashPassword(password);
                PreparedStatement insertStatement = dbQueries.getInsertEmployee();
                insertStatement.setString(1, name);
                insertStatement.setString(2, email);
                insertStatement.setString(3, hashedAndSaltedPassword);
                insertStatement.setBoolean(4, isAdmin);
                insertStatement.setString(5, address);
                insertStatement.setString(6, phone);

                int rowsAffected = insertStatement.executeUpdate();
                AlertUtils.showConfirmationAlert("Success", "Employee successfully added");
                return rowsAffected > 0; // Check if the insertion was successful

            } else {

                // handle is the user is already present
                AlertUtils.showErrorAlert("Error", "Employee already exists");
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } catch (NoSuchAlgorithmException nsx) {
            return false;
        }
    }

    public boolean insertAccount(Account account) {
        try {
            PreparedStatement insertStatement = dbQueries.getInsertAccounts();
            insertStatement.setString(1, account.getBankName());
            insertStatement.setString(2, account.getAddedOn());
            insertStatement.setDouble(3, account.getHourlyRate());
            insertStatement.setInt(4, account.getEmployeeId());

            int rowsAffected = insertStatement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void insertDefaultAttendanceType() {
        try (Statement statement = dbConnection.createStatement()) {
            statement.executeUpdate(DatabaseConstants.QRY_INSERT_ATTENDANCE_TYPE);
        } catch (SQLException e) {
            handleSQLException(e);
        }
    }

    public synchronized HospitalStaff login(String email, String password) {
        try {
            String storedPassword = getPasswordFromDatabase(email);

            if (storedPassword != null && PasswordUtils.verifyPassword(storedPassword, password)) {
                // Passwords match, proceed with login
                return getEmployeeDetails(email);
            }

        } catch (NoSuchAlgorithmException e) {

        }
        return null; // Return null if login fails
    }

    private String getPasswordFromDatabase(String email) {

        try {
            PreparedStatement getPasswordStatement = dbQueries.getPassword(email);
            ResultSet result = getPasswordStatement.executeQuery();
            if (result.next()) {
                return result.getString("password");
            }
        } catch (SQLException e) {
            // Handle any SQL exception
            handleSQLException(e);
        }
        return null;
    }

    public HospitalStaff getEmployeeDetails(String email) {

        try {
            PreparedStatement getUserDetailsStatement = dbQueries.getEmployeeDetail(email);
            ResultSet result = getUserDetailsStatement.executeQuery();
            if (result.next()) {
                int uid = result.getInt("uid");
                String yourName = result.getString("name");
                String yourEmail = result.getString("email");
                String address = result.getString("address");
                String phone = result.getString("phone");
                boolean employmentStatus = result.getBoolean("isManager");
                return new HospitalStaff(uid, yourName, yourEmail, "password", employmentStatus, address, phone);
            }
        } catch (SQLException e) {
            // Handle any SQL exception
            handleSQLException(e);
        }
        return null;
    }

    public boolean updateEmployee(HospitalStaff employee) {
        String query = "UPDATE employee SET name=?, isManager=?, address=?, phone=? WHERE uid=?";
        try (PreparedStatement preparedStatement = dbConnection.prepareStatement(query)) {
            preparedStatement.setString(1, employee.getName());
            preparedStatement.setBoolean(2, employee.isManager());
            preparedStatement.setString(3, employee.getAddress());
            preparedStatement.setString(4, employee.getPhone());
            preparedStatement.setInt(5, employee.getUid());

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            handleSQLException(e);
            return false;
        }
    }

    public boolean updateAccount(HospitalStaff employee, Account account) {
        String query = "UPDATE account SET hourlyRate=? WHERE employee_id=?";
        try (PreparedStatement preparedStatement = dbConnection.prepareStatement(query)) {
            preparedStatement.setDouble(2, account.getHourlyRate());
            preparedStatement.setString(1, employee.getName());
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            handleSQLException(e);
            return false;
        }
    }

    public synchronized Account getAccountDetailsForEmployee(HospitalStaff employee) {
        String query = "SELECT * FROM Account WHERE employee_id=?";
        try (PreparedStatement preparedStatement = dbConnection.prepareStatement(query)) {
            preparedStatement.setInt(1, employee.getUid());

            ResultSet result = preparedStatement.executeQuery();
            if (result.next()) {
                int employeeID = result.getInt("employee_id");
                String bankName = result.getString("BankName");
                String addedOn = result.getString("addedOn");
                Double hourlyRate = result.getDouble("hourlyRate");

                return new Account(employeeID, bankName, addedOn, hourlyRate);
            }
        } catch (SQLException e) {
            // Handle any SQL exception
            handleSQLException(e);
        }
        return null;
    }

    public List<HospitalStaff> getAllEmployees() {
        List<HospitalStaff> employees = new ArrayList<>();
        try {
            PreparedStatement getUserDetailsStatement = dbQueries.getAllEmployees();
            ResultSet result = getUserDetailsStatement.executeQuery();
            while (result.next()) {
                int uid = result.getInt("uid");
                String yourName = result.getString("name");
                String yourEmail = result.getString("email");
                String yourPassword = result.getString("password");
                String address = result.getString("address");
                String phone = result.getString("phone");
                boolean employmentStatus = result.getBoolean("isManager");
                employees.add(new HospitalStaff(uid, yourName, yourEmail, yourPassword, employmentStatus, address, phone));
            }
        } catch (SQLException e) {
            // Handle any SQL exception
            handleSQLException(e);
        }
        return employees;
    }

    public synchronized List<String> getTimesheetDatesForEmployeeLastWeek(Integer employeeID) {

        List<String> timesheetDates = new ArrayList<>();

        // Execute the query and retrieve timesheet dates
        try {
            PreparedStatement getUserDetailsStatement = dbQueries.getTimesheetDatesForEmployeeLastWeek(employeeID);
            ResultSet resultSet = getUserDetailsStatement.executeQuery();
            while (resultSet.next()) {
                String date = resultSet.getString("date");
                timesheetDates.add(date);
            }
        } catch (SQLException e) {
            // Handle any SQL exception
            handleSQLException(e);
        }
        return timesheetDates;
    }

    private void handleSQLException(SQLException e) {
        System.err.println("SQLState: " + e.getSQLState());
        System.err.println("Error Message: " + e.getMessage());
    }
}
