/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utilities;

/**
 *
 * @author pukarsharma
 */
public class DatabaseConstants {

    // database configurations
    public static final String MYSQL_DB = "payroll_db";
    public static final String MYSQL_URL = "jdbc:mysql://127.0.0.1:3306";
    public static final String DB_URL = MYSQL_URL + "/" + MYSQL_DB + "?useSSL=false";
    public static final String USER_NAME = "root";
    public static final String PASSWORD = "password";

    // table names
    public static final String TABLE_EMPLOYEE = "employee";
    public static final String TABLE_PATIENT = "patient";
    public static final String TABLE_ACCOUNT = "account";
    public static final String TABLE_ATTENDANCE = "attendance";
    public static final String TABLE_ATTENDANCE_TYPE = "attendance_type";

    public static final String QRY_CREATE_DB = "CREATE DATABASE IF NOT EXISTS " + MYSQL_DB;

    public static final String QRY_CREATE_EMPLOYEE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_EMPLOYEE + " ("
            + "uid INT NOT NULL AUTO_INCREMENT,"
            + "name VARCHAR(255),"
            + "email VARCHAR(255),"
            + "password TEXT,"
            + "isManager BOOLEAN,"
            + "address TEXT,"
            + "phone TEXT,"
            + "PRIMARY KEY (uid)"
            + ") AUTO_INCREMENT = 1001;";

    public static final String QRY_CREATE_PATIENT_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_PATIENT + " ("
            + "patientId INT NOT NULL AUTO_INCREMENT,"
            + "name VARCHAR(255),"
            + "email VARCHAR(255),"
            + "address TEXT,"
            + "phone TEXT,"
            + "createdDate DATE,"
            + "doesRequireImaging BOOLEAN,"
            + "isOutpatient BOOLEAN,"
            + "isInPatient BOOLEAN,"
            + "PRIMARY KEY (patientId)"
            + ") AUTO_INCREMENT = 1001;";

    public static final String QRY_CREATE_ACCOUNT_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_ACCOUNT + " ("
            + " id INT NOT NULL AUTO_INCREMENT,"
            + " bankName VARCHAR(255),"
            + " addedOn VARCHAR(255),"
            + " hourlyRate DOUBLE NOT NULL,"
            + " employee_id INT NOT NULL,"
            + " PRIMARY KEY (id),"
            + "FOREIGN KEY (employee_id) REFERENCES " + TABLE_EMPLOYEE + "(uid)"
            + ");";

    public static final String QRY_CREATE_ATTENDANCE_TYPE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_ATTENDANCE_TYPE + " ("
            + " id INT NOT NULL AUTO_INCREMENT,"
            + " type ENUM('MANUAL', 'WEBBASED', 'BIOMETRIC'),"
            + " PRIMARY KEY (id),"
            + " UNIQUE (type)"
            + ");";

    // Query to create user_computer_usage table if it doesn't exit
    public static final String QRY_CREATE_ATTENDANCE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_ATTENDANCE + " ("
            + "id INT NOT NULL AUTO_INCREMENT,"
            + "date VARCHAR(255) NOT NULL,"
            + "hoursWorked DOUBLE NOT NULL,"
            + "attendance_type_id INT NOT NULL,"
            + "employee_id INT NOT NULL,"
            + "PRIMARY KEY (id),"
            + "FOREIGN KEY (attendance_type_id) REFERENCES " + TABLE_ATTENDANCE_TYPE + "(id),"
            + "FOREIGN KEY (employee_id) REFERENCES " + TABLE_EMPLOYEE + "(uid)"
            + ");";

    // Only insert a superuser if the superuser is non existant or previously present
    public static final String QRY_INSERT_SUPERUSER = "INSERT INTO " + TABLE_EMPLOYEE + " (name, email, password, isManager, address, phone) "
            + "SELECT 'Super user', 'superuser@gmail.com', 'admin', true, '', '' "
            + "FROM DUAL "
            + "WHERE NOT EXISTS (SELECT 1 FROM " + TABLE_EMPLOYEE + " WHERE isManager = true)";

    public static final String QRY_INSERT_ATTENDANCE_TYPE = "INSERT IGNORE INTO " + TABLE_ATTENDANCE_TYPE + " (type) "
            + "VALUES ('MANUAL'),"
            + "('WEBBASED'),"
            + "('BIOMETRIC')";
}
