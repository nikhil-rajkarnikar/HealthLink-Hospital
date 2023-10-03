/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author pukarsharma
 */
public class Attendance {
    
    String date;
    Double hoursWorked;
    Integer attendance_type_id;
    Integer employeeID;

    public Attendance(String date, Double hoursWorked, Integer attendance_type_id, Integer employeeID) {
        this.date = date;
        this.hoursWorked = hoursWorked;
        this.attendance_type_id = attendance_type_id;
        this.employeeID = employeeID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Double getHoursWorked() {
        return hoursWorked;
    }

    public void setHoursWorked(Double hoursWorked) {
        this.hoursWorked = hoursWorked;
    }

    public Integer getAttendance_type_id() {
        return attendance_type_id;
    }

    public void setAttendance_type_id(Integer attendance_type_id) {
        this.attendance_type_id = attendance_type_id;
    }

    public Integer getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(Integer employeeID) {
        this.employeeID = employeeID;
    }
}
