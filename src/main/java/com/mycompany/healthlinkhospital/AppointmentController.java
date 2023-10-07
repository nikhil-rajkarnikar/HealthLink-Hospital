/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.healthlinkhospital;

import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Appointment;
import model.HospitalStaff;
import utilities.AlertUtils;
import model.CheckinType;

/**
 * AppointmentController class
 *
 * @author pukarsharma
 */
public class AppointmentController extends BaseController {

    @FXML
    private ComboBox<CheckinType> checkinTypeCombobox;

    @FXML
    private Label rangeLabel;
    @FXML
    private Label totalHoursLabel;
    @FXML
    private Label day1Label;
    @FXML
    private Label day2Label;
    @FXML
    private Label day3Label;
    @FXML
    private Label day4Label;
    @FXML
    private Label day5Label;
    @FXML
    private Label day6Label;
    @FXML
    private Label day7Label;
    @FXML
    private Label timesheetLockedLabel;
    @FXML
    private TextField day1Textfield;
    @FXML
    private TextField day2Textfield;
    @FXML
    private TextField day3Textfield;
    @FXML
    private TextField day4Textfield;
    @FXML
    private TextField day5Textfield;

    @FXML
    private Button saveButton;

    protected ObservableList<CheckinType> checkinType = FXCollections.observableArrayList(
            CheckinType.MANUAL,
            CheckinType.WEBBASED,
            CheckinType.BIOMETRIC
    );
    HospitalStaff employee = null;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize(url, rb);
        checkinTypeCombobox.setItems(checkinType);

        // set the fefault combobox selected 
        checkinTypeCombobox.setValue(CheckinType.MANUAL);

        // load dates for the last week
        loadDates();
    }

    void handleTimesheetLocked() {

        boolean isTimeSheetLocked = !databaseModel.getTimesheetDatesForEmployeeLastWeek(employee.getUid()).isEmpty();

        day1Textfield.setDisable(isTimeSheetLocked);
        day2Textfield.setDisable(isTimeSheetLocked);
        day3Textfield.setDisable(isTimeSheetLocked);
        day4Textfield.setDisable(isTimeSheetLocked);
        day5Textfield.setDisable(isTimeSheetLocked);
        timesheetLockedLabel.setVisible(isTimeSheetLocked);
        saveButton.setDisable(isTimeSheetLocked);
    }

    @FXML
    private void closeWindow() {
        // Return to the dashboard
        // get a handle to the stage
        Stage stage = (Stage) saveButton.getScene().getWindow();
        // do what you have to do
        stage.close();
    }

    private void loadDates() {
        // Get the current date
        LocalDate currentDate = LocalDate.now();

        // Calculate the start and end dates of the last week
        LocalDate endOfLastWeek = currentDate.with(TemporalAdjusters.previous(DayOfWeek.SUNDAY));
        LocalDate startOfLastWeek = endOfLastWeek.minusDays(6);

        // Create a list to store the dates of the last week as strings
        List<String> lastWeekDatesAsString = new ArrayList<>();

        // Define a date format
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // Loop through the days of the last week and add them as strings to the list
        LocalDate date = startOfLastWeek;
        while (!date.isAfter(endOfLastWeek)) {
            lastWeekDatesAsString.add(date.format(dateFormatter));
            date = date.plusDays(1);
        }

        // Print the dates of the last week as strings
        for (String dateString : lastWeekDatesAsString) {
            System.out.println(dateString);
        }

        day1Label.setText(lastWeekDatesAsString.get(0));
        day2Label.setText(lastWeekDatesAsString.get(1));
        day3Label.setText(lastWeekDatesAsString.get(2));
        day4Label.setText(lastWeekDatesAsString.get(3));
        day5Label.setText(lastWeekDatesAsString.get(4));
        day6Label.setText(lastWeekDatesAsString.get(5));
        day7Label.setText(lastWeekDatesAsString.get(6));

        rangeLabel.setText(lastWeekDatesAsString.get(0) + " to " + lastWeekDatesAsString.get(6));
    }

    @FXML
    private void calculateHours() {
        // used from fxml on textfield delegates of key pressed
        try {
            double hours = Double.parseDouble(day1Textfield.getText()) + Double.parseDouble(day2Textfield.getText()) + Double.parseDouble(day3Textfield.getText()) + Double.parseDouble(day4Textfield.getText()) + Double.parseDouble(day5Textfield.getText());
            totalHoursLabel.setText("Total hours worked: " + hours + " hours");
            saveButton.setDisable(false);
        } catch (NumberFormatException e) {
            // Handle invalid input
            totalHoursLabel.setText("Invalid number/s entered");
            saveButton.setDisable(true);
        }
    }

    @FXML
    private void handleSaveButton() {

        String date = "";
        Double hoursWorked = 0.0;
        Boolean isSaved = false;

        // Get the selected CheckinType from the ComboBox
        CheckinType selectedCheckinType = checkinTypeCombobox.getSelectionModel().getSelectedItem();

        for (int day = 1; day < 6; day++) {

            switch (day) {
                case 1:
                    date = day1Label.getText();
                    hoursWorked = Double.valueOf(day1Textfield.getText());
                    break;
                case 2:
                    date = day2Label.getText();
                    hoursWorked = Double.valueOf(day2Textfield.getText());
                    break;
                case 3:
                    date = day3Label.getText();
                    hoursWorked = Double.valueOf(day3Textfield.getText());
                    break;
                case 4:
                    date = day4Label.getText();
                    hoursWorked = Double.valueOf(day4Textfield.getText());
                    break;
                case 5:
                    date = day5Label.getText();
                    hoursWorked = Double.valueOf(day5Textfield.getText());
                    break;
            }
//            Appointment attn = new Appointment(date, hoursWorked, selectedCheckinType.getId(), employee.getUid());
//            // Insert the hours worked for the current day into the database using your database model
//            if (databaseModel.insertAttendance(attn)) {
//                isSaved = true;
//            }
        }
        if (isSaved) {
            AlertUtils.showConfirmationAlert("Success", "Attendance saved successfully");
            closeWindow();
        } else {
            AlertUtils.showErrorAlert("Error", "Error saving the attendance, please try again");
        }
    }
}
