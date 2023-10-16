/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.healthlinkhospital;

import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import javafx.scene.control.ComboBox;
import model.Patient;
import model.Service;
import utilities.AlertUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;


/**
 *
 * @author user
 */
public class BillingInfoController extends BaseController{
    
    @FXML
    private Label nameTextLabel;
    @FXML
    private Text appointmentDate;
    @FXML
    private Text serviceName;
    @FXML
    private Text serviceCost;
    @FXML
    private Text serviceDuration;
    @FXML
    private Text serviceDepartment;
    @FXML
    private Text doctor;
    @FXML
    private Patient patient;    
    @FXML
    private ComboBox<Service> serviceDropdown;
    @FXML
    private Button cancelButton;
    
    
    public void initialize(URL url, ResourceBundle rb)
    {
        ObservableList<Service> serviceOptions = FXCollections.observableArrayList(Service.values());
        serviceDropdown.setItems(serviceOptions);
        if(patient != null)
        {
            setPatient(patient);
        }
        
    }
    
    public void setPatient(Patient patient)
    {
        this.patient = patient;
        
        nameTextLabel.setText(patient.getName());
        
    }
    
    
    
    @FXML
    private void handleSavePatientBillingButton() {
        String createdDateToString = "";
        LocalDate localDate = LocalDate.now();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("mm-dd-YYYY");
        
        LocalTime localTime = LocalTime.now();
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        

        // Add billing
        if (patient == null) {
//            String patientId = patientFullName.getText();
            String generatedDate = localDate.format(dateFormatter);
            String generatedTime = localTime.format(timeFormatter);
            Double amount = calculateAmount();
            Integer appointmentId = 1;
            Integer patientId =1;
            // Insert the patient into the patient table using your database model
            if (databaseModel.insertPatientBilling(generatedDate, generatedTime, amount, appointmentId, patientId)) {

                Patient recentlyCreatedPatient = databaseModel.getPatientDetails(1);
                System.out.println("recentlyCreatedPatient ->" + recentlyCreatedPatient);

                AlertUtils.showConfirmationAlert("Success", "Patient successfully added");

                AlertUtils.showConfirmationAlert("Success", "Patient billing successfully registered. Click close to return back to the dashboard or register another patient");
//                clearFields();
            }
        } 
        }
    public double calculateAmount()
    {
        return 0.0;
        
    }
    
    @FXML
    private void closeWindow() {
        // Return to the dashboard
        // get a handle to the stage
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        // do what you have to do
        stage.close();
    }
    }

