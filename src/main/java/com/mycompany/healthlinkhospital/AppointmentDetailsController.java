/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.healthlinkhospital;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;
import model.Patient;
import utilities.AlertUtils;

/**
 *
 * @author pukarsharma
 */
public class AppointmentDetailsController extends BaseController {

    @FXML
    private TableView<Appointment> appointmentTable;

    @FXML
    private TextArea patientDetailTextArea;

    private Patient patient = null;

    // Create TableColumn objects
    TableColumn<Appointment, String> appointmentDateColumn = new TableColumn<>("Appointment Date");
    TableColumn<Appointment, String> appointmentTimeColumn = new TableColumn<>("Appointment Time");
    TableColumn<Appointment, Void> editColumn = new TableColumn<>("Edit");
    TableColumn<Appointment, Void> deleteColumn = new TableColumn<>("Delete");
    TableColumn<Appointment, Void> billingColumn = new TableColumn<>("Billing");

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize(url, rb);
    }

    @FXML
    private void addAppointmentButtonTapped() {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("appointment.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));

            AppointmentController appointmentController = loader.getController();
            appointmentController.patientId = patient.getPatientId();

            // Show the week's hours entry form
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setPatient(Patient p) {
        patient = p;
        setupTextField();
        setupTableView();
    }

    public void setupTextField() {
        patientDetailTextArea.setText(patient.toString());
    }

    public final boolean isTimeInFuture(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate inputDate = LocalDate.parse(dateString, formatter);
        LocalDate currentDate = LocalDate.now();

        // Compare the input date with the current date
        return inputDate.isAfter(currentDate);
    }

    private void setupTableView() {
        // Create TableColumn objects
        appointmentDateColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentDate"));
        appointmentTimeColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentTime"));

        editColumn.setCellFactory(column -> {
            return new TableCell<Appointment, Void>() {
                private final Button editButton = new Button("Edit");

                {
                    editButton.setOnAction(event -> {
                        Appointment appointment = getTableView().getItems().get(getIndex());
                        try {
                            // Load the FXML file for the detail view
                            FXMLLoader loader = new FXMLLoader(App.class.getResource("appointment.fxml"));
                            Parent root = loader.load();

                            AppointmentController attendanceController = loader.getController();
                            attendanceController.patientId = appointment.getPatientId();
                            attendanceController.appointment = appointment;
                            attendanceController.loadAppointment();

                            // Create a new scene for the detail view
                            Scene detailScene = new Scene(root);

                            // Create a new stage (window) for the detail view
                            Stage detailStage = new Stage();
                            detailStage.setTitle("Appointment Details");
                            detailStage.setScene(detailScene);

                            // Show the detail view
                            detailStage.show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                }

                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);

                    if (empty) {
                        setGraphic(null);
                    } else {
                        Appointment appointment = getTableView().getItems().get(getIndex());
                        if (isTimeInFuture(appointment.getAppointmentDate())) {
                            setGraphic(editButton);
                        } else {
                            setGraphic(null);
                        }
                    }
                }
            };
        });

        deleteColumn.setCellFactory(column -> {
            return new TableCell<Appointment, Void>() {
                private final Button deleteButton = new Button("Delete");
                {
                    deleteButton.setOnAction(event -> {
                        Appointment appointment = getTableView().getItems().get(getIndex());
                        if (databaseModel.deleteAppointment(appointment)) {
                            AlertUtils.showConfirmationAlert("Success", "Appointment deleted");
                            
                            addItemsInTable();
                        } else {
                            AlertUtils.showErrorAlert("Error", "Error deleting the appointment, please try again");
                        }
                    });
                }

                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);

                    if (empty) {
                        setGraphic(null);
                    } else {
                        Appointment appointment = getTableView().getItems().get(getIndex());
                        if (isTimeInFuture(appointment.getAppointmentDate())) {
                            setGraphic(deleteButton);
                        } else {
                            setGraphic(null);
                        }
                    }
                }
            };
        });
        
        billingColumn.setCellFactory(column -> {
            return new TableCell<Appointment, Void>() {
                private final Button billingButton = new Button("Billing");
                {
                    billingButton.setOnAction(event -> {
                        Appointment appointment = getTableView().getItems().get(getIndex());
                         try {
                            // Load the FXML file for the detail view
                            FXMLLoader loader = new FXMLLoader(App.class.getResource("patientBilling.fxml"));
                            Parent root = loader.load();

                            // Pass the patient data to the controller of the detail view
                            BillingInfoController billingDetailController = loader.getController();
                            billingDetailController.appointment = appointment;
                            billingDetailController.setPatient(patient);

                            // Create a new scene for the detail view
                            Scene detailScene = new Scene(root);

                            // Create a new stage (window) for the detail view
                            Stage billingStage = new Stage();
                            billingStage.setTitle("Billing Details");
                            billingStage.setScene(detailScene);

                            // Show the detail view
                            billingStage.show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                }
                
                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);

                    if (empty) {
                        setGraphic(null);
                    } else {
                        Appointment appointment = getTableView().getItems().get(getIndex());                       
                            setGraphic(billingButton);                       

                    }
                }

                  
            };
        });

        appointmentTable.getColumns().addAll(appointmentDateColumn, appointmentTimeColumn, editColumn, deleteColumn, billingColumn);

        addItemsInTable();

        appointmentTable.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {

                Appointment selectedPatient = appointmentTable.getSelectionModel().getSelectedItem();
                if (selectedPatient != null) {
                    // navigateToEditPatient(selectedPatient);
                }
            }
        });
    }

    private void addItemsInTable() {
        // Create an ObservableList from the list of employees
        ObservableList<Appointment> appointments = FXCollections.observableArrayList(databaseModel.getAllAppointments());
        System.out.println("Patients->" + databaseModel.getAllAppointments());
        // Set the data to the TableView
        appointmentTable.setItems(appointments);
    }

    private void closeWindow() {
        // Return to the dashboard
        // get a handle to the stage
        Stage stage = (Stage) appointmentTable.getScene().getWindow();
        // do what you have to do
        stage.close();
    }

    private void navigateToEditPatient(Patient patient) {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("addPatient.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            Stage loginStage = new Stage();
            loginStage.setTitle("Edit employee details");

            AddOrEditPatientController editController = loader.getController();
            editController.patient = patient;

            loginStage.setScene(scene);
            loginStage.show();
//            editController.loadFields();

        } catch (IOException e) {
            System.out.println("com.mycompany.healthlinkhospital.CheckEmployeeDetailController.navigateToEditEmployee() failed ==> " + e);
        }
    }
}
