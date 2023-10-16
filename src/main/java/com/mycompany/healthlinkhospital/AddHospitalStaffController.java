/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.healthlinkhospital;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.HospitalStaff;
import utilities.AlertUtils;

/**
 * FXML Controller class
 *
 * @author nikhilrajkarnikar
 */
public class AddHospitalStaffController extends BaseController {

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
    private CheckBox isAdminCheckBox;

    public HospitalStaff staff = null;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize(url, rb);
    }

    @FXML
    private void handleSaveEmployeeButton() {

        if (staff == null) {
            String name = nameTextField.getText();
            String email = emailTextField.getText();
            String password = passwordField.getText();
            String address = addressField.getText();
            String phone = phoneField.getText();
            boolean isManager = isAdminCheckBox.isSelected();

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

                AlertUtils.showConfirmationAlert("Success", "Employee successfully added");
                clearFields();
            }
        }
    }

    private void clearFields() {
        nameTextField.clear();
        emailTextField.clear();
        passwordField.clear();
        addressField.clear();
        phoneField.clear();
        isAdminCheckBox.setSelected(false);
    }

    public void loadFields() {
//        loadAccountDetails();
        nameTextField.setText(staff.getName());
        emailTextField.setText(staff.getEmail());
        emailTextField.setDisable(true);
        passwordField.setDisable(true);
        passwordField.setText(staff.getPassword());
        addressField.setText(staff.getAddress());
        phoneField.setText(staff.getPhone());
        isAdminCheckBox.setSelected(staff.isManager());
    }

    @FXML
    private void closeWindow() {
        // Return to the dashboard
        // get a handle to the stage
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        // do what you have to do
        stage.close();
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
