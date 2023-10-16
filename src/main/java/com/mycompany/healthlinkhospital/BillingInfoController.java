/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.healthlinkhospital;

import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;
import java.net.URL;
import model.Patient;


/**
 *
 * @author user
 */
public class BillingInfoController extends BaseController{
    
    @FXML
    private Text patientFullName;
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
    
    private Patient patient;
    
    
    public void initialize(URL url, ResourceBundle rb)
    {
        if(patient != null)
        {
            setPatient(patient);
        }
        
    }
    
    public void setPatient(Patient patient)
    {
        this.patient = patient;
        
        patientFullName.setText(patient.getName());
        
    }
    
}
