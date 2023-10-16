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

    public PreparedStatement getAllPatients() throws SQLException {
        String query = "SELECT * FROM patient";
        return dbConnection.prepareStatement(query);
    }

    public PreparedStatement getInboundRevenue() throws SQLException {
        String query = "SELECT days_of_week.days, COALESCE(total_payment, 0) AS count "
                + "FROM ("
                + "    SELECT 'Mon' AS days "
                + "    UNION SELECT 'Tus' "
                + "    UNION SELECT 'Wed' "
                + "    UNION SELECT 'Thu' "
                + "    UNION SELECT 'Fri' "
                + "    UNION SELECT 'Sat' "
                + "    UNION SELECT 'Sun' "
                + ") AS days_of_week "
                + "LEFT JOIN ("
                + "    SELECT "
                + "        CASE "
                + "            WHEN DAYOFWEEK(generatedDate) = 1 THEN 'Sun' "
                + "            WHEN DAYOFWEEK(generatedDate) = 2 THEN 'Mon' "
                + "            WHEN DAYOFWEEK(generatedDate) = 3 THEN 'Tus' "
                + "            WHEN DAYOFWEEK(generatedDate) = 4 THEN 'Wed' "
                + "            WHEN DAYOFWEEK(generatedDate) = 5 THEN 'Thu' "
                + "            WHEN DAYOFWEEK(generatedDate) = 6 THEN 'Fri' "
                + "            WHEN DAYOFWEEK(generatedDate) = 7 THEN 'Sat' "
                + "        END AS days, "
                + "        SUM(b.amount) AS total_payment "
                + "    FROM payroll_db.billing AS b "
                + "    INNER JOIN payroll_db.patient AS p ON b.patientId = p.patientId "
                + "    WHERE p.isInPatient = 1 AND b.generatedDate >= CURDATE() - INTERVAL 1 WEEK "
                + "    GROUP BY days "
                + "    ORDER BY days"
                + ") AS b ON days_of_week.days = b.days;";

        return dbConnection.prepareStatement(query);
    }
    public PreparedStatement getOutboundRevenue() throws SQLException {
        String query = "SELECT days_of_week.days, COALESCE(total_payment, 0) AS count "
                + "FROM ("
                + "    SELECT 'Mon' AS days "
                + "    UNION SELECT 'Tus' "
                + "    UNION SELECT 'Wed' "
                + "    UNION SELECT 'Thu' "
                + "    UNION SELECT 'Fri' "
                + "    UNION SELECT 'Sat' "
                + "    UNION SELECT 'Sun' "
                + ") AS days_of_week "
                + "LEFT JOIN ("
                + "    SELECT "
                + "        CASE "
                + "            WHEN DAYOFWEEK(generatedDate) = 1 THEN 'Sun' "
                + "            WHEN DAYOFWEEK(generatedDate) = 2 THEN 'Mon' "
                + "            WHEN DAYOFWEEK(generatedDate) = 3 THEN 'Tus' "
                + "            WHEN DAYOFWEEK(generatedDate) = 4 THEN 'Wed' "
                + "            WHEN DAYOFWEEK(generatedDate) = 5 THEN 'Thu' "
                + "            WHEN DAYOFWEEK(generatedDate) = 6 THEN 'Fri' "
                + "            WHEN DAYOFWEEK(generatedDate) = 7 THEN 'Sat' "
                + "        END AS days, "
                + "        SUM(b.amount) AS total_payment "
                + "    FROM payroll_db.billing AS b "
                + "    INNER JOIN payroll_db.patient AS p ON b.patientId = p.patientId "
                + "    WHERE p.isOutPatient = 1 AND b.generatedDate >= CURDATE() - INTERVAL 1 WEEK "
                + "    GROUP BY days "
                + "    ORDER BY days"
                + ") AS b ON days_of_week.days = b.days;";

        return dbConnection.prepareStatement(query);
    }

    public PreparedStatement getInboundPatients() throws SQLException {
        String query = "SELECT days_of_week.days as day, coalesce(count, 0) as count "
                + "FROM ("
                + "    SELECT 'Mon' AS days "
                + "    UNION SELECT 'Tue' "
                + "    UNION SELECT 'Wed' "
                + "    UNION SELECT 'Thu' "
                + "    UNION SELECT 'Fri' "
                + "    UNION SELECT 'Sat' "
                + "    UNION SELECT 'Sun' "
                + ") AS days_of_week "
                + "LEFT JOIN "
                + "(SELECT "
                + "    CASE "
                + "        WHEN DAYOFWEEK(createdDate) = 1 THEN 'Sun' "
                + "        WHEN DAYOFWEEK(createdDate) = 2 THEN 'Mon' "
                + "        WHEN DAYOFWEEK(createdDate) = 3 THEN 'Tue' "
                + "        WHEN DAYOFWEEK(createdDate) = 4 THEN 'Wed' "
                + "        WHEN DAYOFWEEK(createdDate) = 5 THEN 'Thu' "
                + "        WHEN DAYOFWEEK(createdDate) = 6 THEN 'Fri' "
                + "        WHEN DAYOFWEEK(createdDate) = 7 THEN 'Sat' "
                + "    END AS day, "
                + "    COALESCE(COUNT(*), 0) AS count "
                + "FROM payroll_db.patient "
                + "WHERE createdDate >= CURDATE() - INTERVAL 1 WEEK AND isInPatient = 1 "
                + "GROUP BY day "
                + "ORDER BY day) AS b "
                + "ON days_of_week.days = b.day";

        return dbConnection.prepareStatement(query);
    }

    public PreparedStatement getOutboundPatients() throws SQLException {
        String query = "SELECT days_of_week.days as day, coalesce(count, 0) as count "
                + "FROM ("
                + "    SELECT 'Mon' AS days "
                + "    UNION SELECT 'Tue' "
                + "    UNION SELECT 'Wed' "
                + "    UNION SELECT 'Thu' "
                + "    UNION SELECT 'Fri' "
                + "    UNION SELECT 'Sat' "
                + "    UNION SELECT 'Sun' "
                + ") AS days_of_week "
                + "LEFT JOIN "
                + "(SELECT "
                + "    CASE "
                + "        WHEN DAYOFWEEK(createdDate) = 1 THEN 'Sun' "
                + "        WHEN DAYOFWEEK(createdDate) = 2 THEN 'Mon' "
                + "        WHEN DAYOFWEEK(createdDate) = 3 THEN 'Tue' "
                + "        WHEN DAYOFWEEK(createdDate) = 4 THEN 'Wed' "
                + "        WHEN DAYOFWEEK(createdDate) = 5 THEN 'Thu' "
                + "        WHEN DAYOFWEEK(createdDate) = 6 THEN 'Fri' "
                + "        WHEN DAYOFWEEK(createdDate) = 7 THEN 'Sat' "
                + "    END AS day, "
                + "    COALESCE(COUNT(*), 0) AS count "
                + "FROM payroll_db.patient "
                + "WHERE createdDate >= CURDATE() - INTERVAL 1 WEEK AND isOutPatient = 1 "
                + "GROUP BY day "
                + "ORDER BY day) AS b "
                + "ON days_of_week.days = b.day";

        return dbConnection.prepareStatement(query);
    }

    public PreparedStatement getEmployeeDetail(String email) throws SQLException {
        String query = "SELECT uid, name, email, isManager, address, phone FROM employee WHERE email = ?";
        PreparedStatement preparedStatement = dbConnection.prepareStatement(query);
        preparedStatement.setString(1, email);
        return preparedStatement;
    }

    public PreparedStatement getPatientDetail(int patientId) throws SQLException {
        String query = "SELECT name, email, address, phone, createdDate, doesRequireImaging, isOutPatient, isInPatient FROM patient WHERE patientId = ?";
        PreparedStatement preparedStatement = dbConnection.prepareStatement(query);
        preparedStatement.setInt(1, patientId);
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

    public PreparedStatement getInsertPatient() throws SQLException {
        String query = "INSERT INTO patient (name, email, address, phone, createdDate, doesRequireImaging, isOutPatient, isInPatient) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        return dbConnection.prepareStatement(query);
    }

    public PreparedStatement getInsertAppointment() throws SQLException {
        String query = "INSERT INTO appointment (appointmentDate, appointmentTime, doctorId, patientId, staffId, duration) "
                + "VALUES (?, ?, ?, ?, ?, ?)";
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

    public PreparedStatement getInsertPatientBilling() throws SQLException {
        String query = "INSERT INTO billing (generatedDate, generatedTime, amount, appointmentId, patientId) "
                + "VALUES (?, ?, ?, ?, ?)";
        return dbConnection.prepareStatement(query);
    }
}
