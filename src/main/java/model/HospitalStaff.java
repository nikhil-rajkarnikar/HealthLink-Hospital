/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author pukarsharma
 */
public class HospitalStaff {

    private int userId;
    private String name;
    private String email;
    private String password;
    private boolean isManager;
    private String address;
    private String phone;
    private StringProperty isManagerString;

    public HospitalStaff(int userId, String name, String email, String password, boolean isManager, String address, String phone) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.isManager = isManager;
        this.address = address;
        this.phone = phone;
        this.isManagerString = new SimpleStringProperty(String.valueOf(isManager));
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isManager() {
        return isManager;
    }

    public void setIsManager(boolean isManager) {
        this.isManager = isManager;
    }

    public int getUid() {
        return userId;
    }

    public void setUid(int uid) {
        this.userId = uid;
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

    @Override
    public String toString() {
        return "UserModel values are follow: \n\n" + "uid = " + userId + ", \n\nname = " + name + ", \n\nemail = " + email + ", \n\npassword = " + password + ", \n\nisManager = " + isManager + "\n\n";
    }

    public StringProperty isManagerStringProperty() {
        return isManagerString;
    }
}
