/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import javafx.beans.property.StringProperty;

/**
 *
 * @author pukarsharma
 */
public class Patient {

    private int patientId;
    private String name;
    private String email;
    private String address;
    private String phone;
    private String createdDate;
    private boolean doesRequireImaging = false;
    private boolean isOutpatient = false;
    private boolean isInPatient = false;
    private StringProperty doesRequireImagingString;
    private StringProperty isInPatientString;
    private StringProperty isOutpatientString;

    public Patient(int patientId, String name, String email, String address, String phone, String createdDate, boolean doesRequireImaging, boolean isOutpatient, boolean isInPatient) {
        this.patientId = patientId;
        this.name = name;
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.createdDate = createdDate;
        this.doesRequireImaging = doesRequireImaging;
        this.isOutpatient = isOutpatient;
        this.isInPatient = isInPatient;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isIsInPatient() {
        return isInPatient;
    }

    public void setIsInPatient(boolean isInPatient) {
        this.isInPatient = isInPatient;
    }

    public boolean isIsOutpatient() {
        return isOutpatient;
    }

    public void setIsOutpatient(boolean isOutpatient) {
        this.isOutpatient = isOutpatient;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isDoesRequireImaging() {
        return doesRequireImaging;
    }

    public void setDoesRequireImaging(boolean doesRequireImaging) {
        this.doesRequireImaging = doesRequireImaging;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public StringProperty getDoesRequireImagingString() {
        return doesRequireImagingString;
    }

    public void setDoesRequireImagingString(StringProperty doesRequireImagingString) {
        this.doesRequireImagingString = doesRequireImagingString;
    }

    public StringProperty getIsInPatientString() {
        return isInPatientString;
    }

    public void setIsInPatientString(StringProperty isInPatientString) {
        this.isInPatientString = isInPatientString;
    }

    public StringProperty getIsOutpatientString() {
        return isOutpatientString;
    }

    public void setIsOutpatientString(StringProperty isOutpatientString) {
        this.isOutpatientString = isOutpatientString;
    }
}
