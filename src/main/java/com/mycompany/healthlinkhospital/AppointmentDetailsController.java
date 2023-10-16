/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.healthlinkhospital;


import com.mycompany.healthlinkhospital.TableCells.DeleteAppoinmentButtonTableCell;
import com.mycompany.healthlinkhospital.TableCells.EditAppoinmentTableCell;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;
import model.Patient;

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
    TableColumn<Appointment, String> appointmentDateColumn  = new TableColumn<>("Appointment Date");
    TableColumn<Appointment, String> appointmentTimeColumn = new TableColumn<>("Appointment Time");
    TableColumn<Appointment, String> drNameColumn = new TableColumn<>("Scheduled with");
    TableColumn<Appointment, Void> editColumn = new TableColumn<>("Edit");
    TableColumn<Appointment, Void> deleteColumn = new TableColumn<>("Delete");

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize(url, rb);
    }
    
    public void setPatient(Patient p) {
        patient = p;
        setupTextField();
        setupTableView();
    }

    public void setupTextField() {
        patientDetailTextArea.setText(patient.toString());
    }
    private void setupTableView() {
        // Create TableColumn objects
        appointmentDateColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentDate"));
        appointmentTimeColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentTime"));
        

        // Set the cell factory for the column with the button
        deleteColumn.setCellFactory(col -> new DeleteAppoinmentButtonTableCell<>(deleteColumn));
        deleteColumn.setMinWidth(100);
        
        editColumn.setCellFactory(col -> new EditAppoinmentTableCell<>(editColumn));
        editColumn.setMinWidth(100);

        appointmentTable.getColumns().addAll(appointmentDateColumn, appointmentTimeColumn, drNameColumn);
        
        // time is less that time.nowm show otherwise hide
        appointmentTable.getColumns().add(editColumn);
        appointmentTable.getColumns().add(deleteColumn);

        // Create an ObservableList from the list of employees
        ObservableList<Appointment> appointments = FXCollections.observableArrayList(databaseModel.getAllAppointments());
        System.out.println("Patients->" + databaseModel.getAllAppointments());
        // Set the data to the TableView
        appointmentTable.setItems(appointments);

        appointmentTable.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {

                Appointment selectedPatient = appointmentTable.getSelectionModel().getSelectedItem();
                if (selectedPatient != null) {
                    // navigateToEditPatient(selectedPatient);
                }
            }
        });
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
