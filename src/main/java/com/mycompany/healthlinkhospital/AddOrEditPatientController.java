/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.healthlinkhospital;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Account;
import model.HospitalStaff;
import utilities.AlertUtils;

/**
 * FXML Controller class
 *
 * @author pukarsharma
 */
public class AddOrEditPatientController extends BaseController {

    @FXML
    private Button saveEmployeeButton;
    @FXML
    private Button cancelButton;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField emailTextField;
    @FXML
    private TextField passwordField;
    @FXML
    private TextField addressField;
    @FXML
    private TextField phoneField;
    @FXML
    private TextField hourlyRateField;
    @FXML
    private CheckBox isAdminCheckBox;

    public HospitalStaff employee = null;
    public Account account = null;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize(url, rb);
    }

    private void loadAccountDetails() {

        if (employee != null && employee.getUid() != 1001) {
            account = databaseModel.getAccountDetailsForEmployee(employee);
            System.out.print(account.getHourlyRate());
            hourlyRateField.setText(Double.toString(account.getHourlyRate()));
        }
    }

    @FXML
    private void handleSaveEmployeeButton() {

        if (employee == null) {
            String name = nameTextField.getText();
            String email = emailTextField.getText();
            String password = passwordField.getText();
            String address = addressField.getText();
            String phone = phoneField.getText();
            boolean isManager = isAdminCheckBox.isSelected();

            HospitalStaff emp = new HospitalStaff(0, name, email, password, true, address, phone);

            if (!isValidEmail(email)) {
                // Show an error message for invalid email format
                AlertUtils.showErrorAlert("Error", "Please enter a valid email address.");
                return;
            }

            if (!isStrongPassword(password)) {
                // Show an error message for weak password
                AlertUtils.showErrorAlert("Error", "Password must be at least 8 characters long and include letters, numbers, and special characters.");
                return;
            }

            // Insert the employee into the employee table using your database model
            if (databaseModel.insertEmployee(name, email, password, isManager, address, phone)) {

                HospitalStaff recentlyCreatedEmployee = databaseModel.getEmployeeDetails(email);
                insertDefaultAccount(recentlyCreatedEmployee);

                AlertUtils.showConfirmationAlert("Success", "Employee successfully added. Click close to return back to the dashboard or add another employee");
                clearFields();
            }
        } else {

            // get new details
            employee.setName(nameTextField.getText());
            employee.setAddress(addressField.getText());
            employee.setPhone(phoneField.getText());
            
            account.setHourlyRate(Double.parseDouble(hourlyRateField.getText()));

            // edit employee
            if (databaseModel.updateEmployee(employee) && databaseModel.updateAccount(employee, account)) {

                AlertUtils.showConfirmationAlert("Success", "Employee successfully updated. Click close to return back to the dashboard or add another employee");
                closeWindow();
            }
        }
    }

    private void clearFields() {
        nameTextField.clear();
        emailTextField.clear();
        passwordField.clear();
        addressField.clear();
        phoneField.clear();
        hourlyRateField.clear();
        isAdminCheckBox.setSelected(false);
    }

    public void loadFields() {
        loadAccountDetails();
        nameTextField.setText(employee.getName());
        emailTextField.setText(employee.getEmail());
        emailTextField.setDisable(true);
        passwordField.setDisable(true);
        passwordField.setText(employee.getPassword());
        addressField.setText(employee.getAddress());
        phoneField.setText(employee.getPhone());
        isAdminCheckBox.setSelected(employee.isManager());
        if (employee != null && employee.getUid() != 1001) {
            hourlyRateField.setVisible(true);
        } else {
            hourlyRateField.setVisible(false);
            saveEmployeeButton.setVisible(false);
        }
    }

    @FXML
    private void closeWindow() {
        // Return to the dashboard
        // get a handle to the stage
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        // do what you have to do
        stage.close();
    }

    private void insertDefaultAccount(HospitalStaff newEmployee) {
        // insert default account of suer user
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

        // get current date
        String date = df.format(new Date());
        databaseModel.insertAccount(new Account(newEmployee.getUid(), "Common wealth bank AU", date, Double.parseDouble(hourlyRateField.getText())));
    }

    private boolean isValidEmail(String email) {
        // Define a regular expression pattern for a valid email format
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";

        // Compile the pattern
        Pattern pattern = Pattern.compile(emailRegex);

        // Match the email against the pattern
        Matcher matcher = pattern.matcher(email);

        return matcher.matches();
    }

    private boolean isStrongPassword(String password) {
        // Define criteria for a strong password
        int minLength = 8;
        boolean containsLetter = false;
        boolean containsDigit = false;
        boolean containsSpecialChar = false;

        // Check the length of the password
        if (password.length() < minLength) {
            return false;
        }

        // Check if the password contains at least one letter, one digit, and one special character
        for (char c : password.toCharArray()) {
            if (Character.isLetter(c)) {
                containsLetter = true;
            } else if (Character.isDigit(c)) {
                containsDigit = true;
            } else if (isSpecialCharacter(c)) {
                containsSpecialChar = true;
            }

            // If all criteria are met, the password is considered strong
            if (containsLetter && containsDigit && containsSpecialChar) {
                return true;
            }
        }

        return false;
    }

    private boolean isSpecialCharacter(char c) {
        // Define the set of special characters you want to consider
        String specialCharacters = "!@#$%^&*()-_+=<>?";

        // Check if the character is in the set of special characters
        return specialCharacters.indexOf(c) != -1;
    }

}
