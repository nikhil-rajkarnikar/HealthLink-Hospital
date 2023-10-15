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
   
    public static final String TABLE_APPOINTMENT = "appointment";
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
            + "isOutPatient BOOLEAN,"
            + "isInPatient BOOLEAN,"
            + "PRIMARY KEY (patientId)"
            + ") AUTO_INCREMENT = 1001;";

    public static final String QRY_CREATE_APPOINTMENT_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_APPOINTMENT + " ("
            + " id INT NOT NULL AUTO_INCREMENT,"
            + " appointmentDate VARCHAR(255),"
            + " appointmentTime VARCHAR(255),"
            + " doctorId INT NOT NULL,"
            + " patientId INT NOT NULL,"
            + " staffId INT NOT NULL,"
            + " duration INT NOT NULL,"
            + " PRIMARY KEY (id),"
            + " FOREIGN KEY (patientId) REFERENCES " + TABLE_PATIENT + "(patientId)"
            + ");";
    
    // Only insert a superuser if the superuser is non existant or previously present
    public static final String QRY_INSERT_SUPERUSER = "INSERT INTO " + TABLE_EMPLOYEE + " (name, email, password, isManager, address, phone) "
            + "SELECT 'Super user', 'superuser@gmail.com', 'admin', true, '', '' "
            + "FROM DUAL "
            + "WHERE NOT EXISTS (SELECT 1 FROM " + TABLE_EMPLOYEE + " WHERE isManager = true)";

}
