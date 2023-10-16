/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.healthlinkhospital;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;
import model.Appointment;
import utilities.AlertUtils;
import model.Doctor;

/**
 * AppointmentController class
 *
 * @author pukarsharma
 */
public class AppointmentController extends BaseController {

    @FXML
    private ComboBox<Doctor> checkinTypeCombobox;

    @FXML
    private ComboBox<String> timeComboBox;

    @FXML
    private DatePicker appointmentDateField;

    @FXML
    private Button saveButton;

    protected ObservableList<Doctor> doctor = FXCollections.observableArrayList(
            Doctor.DrAsthana,
            Doctor.DrMunna,
            Doctor.DrSuman
    );

    public int patientId = 0;
    public Appointment appointment = null;
   
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize(url, rb);
        checkinTypeCombobox.setItems(doctor);

        // load dates for the last week
        loadDefaultDatesForDoctors();

        // set the fefault combobox selected 
        checkinTypeCombobox.setValue(Doctor.DrAsthana);
        timeComboBox.getItems().addAll(Doctor.DrAsthana.getAvailableTimes());

        // load appointment times
        checkinTypeCombobox.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            System.out.println(newValue);
            timeComboBox.getItems().clear();
            timeComboBox.getItems().addAll(newValue.getAvailableTimes());
        });
    }

    @FXML
    private void closeWindow() {
        // Return to the dashboard
        // get a handle to the stage
        Stage stage = (Stage) saveButton.getScene().getWindow();
        // do what you have to do
        stage.close();
    }

    private void loadDefaultDatesForDoctors() {
        Doctor.DrAsthana.addAvailableTimesIn30MinuteIntervals("09:00", "17:00");
        Doctor.DrMunna.addAvailableTimesIn30MinuteIntervals("14:00", "20:00");
        Doctor.DrSuman.addAvailableTimesIn30MinuteIntervals("11:00", "14:00");
    }

    public final LocalDate getLocalDate(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(dateString, formatter);
        return localDate;
    }

    public void loadAppointment() {
        if (appointment != null) {
            try {
                appointmentDateField.setValue(getLocalDate(appointment.getAppointmentDate()));
                timeComboBox.setValue(appointment.getAppointmentTime());
            } catch (NullPointerException e) {
            }
        }
    }

    @FXML
    private void handleSaveButton() {

        Boolean isSaved = false;

        // Get the selected CheckinType from the ComboBox
        Doctor selectedDoctor = checkinTypeCombobox.getSelectionModel().getSelectedItem();
        String time = "";
        String date = "";

        // handle edge cases
        if (appointmentDateField.getValue() != null) {
            date = appointmentDateField.getValue().toString();
        }

        if (timeComboBox.getSelectionModel().getSelectedItem() != null) {
            time = timeComboBox.getSelectionModel().getSelectedItem();
        }

        if (date.isEmpty() || time.isEmpty()) {
            AlertUtils.showErrorAlert("Error", "Please enter appointment date/time");
            return;
        }

        // Start insertion process
        Appointment _appointment;

        if (appointment == null) { // insert
           
            _appointment = new Appointment(0, date, time, patientId, 0, selectedDoctor.getId(), 30);
            
            if (databaseModel.insertAppointment(_appointment)) {
                isSaved = true;
            }
        } else { // update
            
            _appointment = new Appointment(appointment.getAppointmentId(), date, time, 0, 0, 0, 0);
            if (databaseModel.updateAppointment(_appointment)) {
                isSaved = true;
            }
        }
        if (isSaved) {
            AlertUtils.showConfirmationAlert("Success", "Appointment saved successfully");
            closeWindow();
        } else {
            AlertUtils.showErrorAlert("Error", "Error saving the appointment, please try again");
        }
    }
}
