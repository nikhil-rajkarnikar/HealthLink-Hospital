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
import model.Appointment;
import model.Billing;
import utilities.AlertUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;


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
    @FXML
    private Button saveBillingButton;
    
    Appointment appointment = null;
    Billing billing = new Billing(1, "2023/10/14", "10:30", 20.00, 1001, 1);
    
    
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
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
        
        LocalTime localTime = LocalTime.now();
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        
        System.out.println(patient);
//        if (patient == null) {
//            String patientId = patientFullName.getText();
            String generatedDate = localDate.format(dateFormatter);
            String generatedTime = localTime.format(timeFormatter);
            Double amount = calculateAmount();
            Integer appointmentId = appointment.getAppointmentId();
            Integer patientId = patient.getPatientId();
            
        
            Service selectedService = serviceDropdown.getValue();
            // Insert the patient into the patient table using your database model
            if (databaseModel.insertPatientBilling(generatedDate, generatedTime, amount, appointmentId, patientId)) {

                Billing recentlyCreatedBilling = databaseModel.getBillingPatientDetails(patientId, appointmentId);
//                System.out.println("recentlyCreatedPatient ->" + recentlyCreatedBilling);
                

                AlertUtils.showConfirmationAlert("Success", "Patient billing successfully added");

                AlertUtils.showConfirmationAlert("Success", "Patient billing successfully registered. Click close to return back to the dashboard or register another patient");
//                clearFields();
            }
//        } 
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
    
    @FXML
    private void printPdf() {
        try {
            PDDocument document = new PDDocument();
            PDPage page = new PDPage();
            document.addPage(page);

            PDPageContentStream contentStream = new PDPageContentStream(document, page);
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
            contentStream.beginText();
            contentStream.newLineAtOffset(100, 700);
            contentStream.showText(patient.toString());
            contentStream.showText(appointment.toString());
//            contentStream.showText(billing.toString());
            contentStream.endText();
            contentStream.close();

            document.save("pdf.pdf");
            document.close();

            System.out.println("PDF created successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

