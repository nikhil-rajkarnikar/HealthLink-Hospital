/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.healthlinkhospital;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Account;
import model.Patient;
import utilities.AlertUtils;

/**
 * FXML Controller class
 *
 * @author pukarsharma
 */
public class AddOrEditPatientController extends BaseController {

    @FXML
    private TextField nameTextField;
    @FXML
    private TextField emailTextField;
    @FXML
    private TextField addressField;
    @FXML
    private TextField phoneField;
    @FXML
    private DatePicker createdDateField;
    @FXML
    private CheckBox isImagingRequired;
    @FXML
    private CheckBox isOutPatient;
    @FXML
    private CheckBox isInPatient;
    @FXML
    private Button savePatientButton;
    @FXML
    private Button cancelButton;

    public Patient patient = null;
    public Account account = null;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize(url, rb);
    }

    // TODO
//    private void loadAccountDetails() {
//
//        if (patient != null && patient.getPatientId() != 1001) {
//            account = databaseModel.getAccountDetailsForEmployee(employee);
//            System.out.print(account.getHourlyRate());
////            hourlyRateField.setText(Double.toString(account.getHourlyRate()));
//        }
//    }
    @FXML
    private void handleSavePatientButton() {
        String createdDateToString = "";

        // Add patient
        if (patient == null) {
            String name = nameTextField.getText();
            String email = emailTextField.getText();
            String address = addressField.getText();
            String phone = phoneField.getText();
            LocalDate createdDate = createdDateField.getValue();
            boolean imagingRequired = isImagingRequired.isSelected();
            boolean outPatient = isOutPatient.isSelected();
            boolean inPatient = isInPatient.isSelected();

            createdDateToString = createdDate.toString();

            // Insert the patient into the patient table using your database model
            if (databaseModel.insertPatient(name, email, address, phone, createdDateToString, imagingRequired, outPatient, inPatient)) {

                Patient recentlyCreatedPatient = databaseModel.getPatientDetails(1);
                System.out.println("recentlyCreatedPatient ->" + recentlyCreatedPatient);

                AlertUtils.showConfirmationAlert("Success", "Patient successfully added");

//                AlertUtils.showConfirmationAlert("Success", "Patient successfully registered. Click close to return back to the dashboard or register another patient");
                clearFields();
            }
            // Edit/Update Patient
        } else {

            // get new details
            patient.setName(nameTextField.getText());
            patient.setEmail(emailTextField.getText());
            patient.setAddress(addressField.getText());
            patient.setPhone(phoneField.getText());
            patient.setCreatedDate(createdDateToString);
            patient.setDoesRequireImaging(isImagingRequired.isSelected());
            patient.setIsOutpatient(isOutPatient.isSelected());
            patient.setIsInPatient(isInPatient.isSelected());

            // edit patient
            if (databaseModel.updatePatient(patient)) {

                AlertUtils.showConfirmationAlert("Success", "Patient successfully updated. Click close to return back to the dashboard or add another patient");
                closeWindow();
            }
        }
    }

    private void clearFields() {
        nameTextField.clear();
        emailTextField.clear();
        addressField.clear();
        phoneField.clear();
        createdDateField.setValue(null);
        isImagingRequired.setSelected(false);
        isOutPatient.setSelected(false);
        isInPatient.setSelected(false);
    }

//    public void loadFields() {
//        loadAccountDetails();
//        nameTextField.setText(employee.getName());
//        emailTextField.setText(employee.getEmail());
//        emailTextField.setDisable(true);
//        passwordField.setDisable(true);
//        passwordField.setText(employee.getPassword());
//        addressField.setText(employee.getAddress());
//        phoneField.setText(employee.getPhone());
//        isAdminCheckBox.setSelected(employee.isManager());
//        if (employee != null && employee.getUid() != 1001) {
//            hourlyRateField.setVisible(true);
//        } else {
//            hourlyRateField.setVisible(false);
//            saveEmployeeButton.setVisible(false);
//        }
//    }
    @FXML
    private void closeWindow() {
        // Return to the dashboard
        // get a handle to the stage
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        // do what you have to do
        stage.close();
    }
}
