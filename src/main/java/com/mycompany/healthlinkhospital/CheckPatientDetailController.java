/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.healthlinkhospital;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Patient;

/**
 * FXML Controller class
 *
 * @author pukarsharma
 */
public class CheckPatientDetailController extends BaseController {

    @FXML
    private TextField searchField;

    @FXML
    private Button searchBtn;

    private TextArea employeeDetailSearchArea;

    @FXML
    private TableView<Patient> patientTable;

    // Create TableColumn objects
    TableColumn<Patient, String> nameColumn = new TableColumn<>("Name");
    TableColumn<Patient, String> emailColumn = new TableColumn<>("Email");
    TableColumn<Patient, String> addressColumn = new TableColumn<>("Address");
    TableColumn<Patient, String> phoneColumn = new TableColumn<>("Phone");
    TableColumn<Patient, String> createdDateColumn = new TableColumn<>("Created Date");
    TableColumn<Patient, String> imagingRequiredColumn = new TableColumn<>("Imaging Required");
    TableColumn<Patient, String> outPatientColumn = new TableColumn<>("Out Patient");
    TableColumn<Patient, String> inPatientColumn = new TableColumn<>("In Patient");
    TableColumn<Patient, Void> detailColumn = new TableColumn<>("Detail");
    TableColumn<Patient, Void> billingColumn = new TableColumn<>("Billing");

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize(url, rb);
        setupTableView();
    }

    private void setupTableView() {
        // Create TableColumn objects
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        createdDateColumn.setCellValueFactory(new PropertyValueFactory<>("createdDate"));
        imagingRequiredColumn.setCellValueFactory(new PropertyValueFactory<>("doesRequireImaging"));
        outPatientColumn.setCellValueFactory(new PropertyValueFactory<>("isOutPatient"));
        inPatientColumn.setCellValueFactory(new PropertyValueFactory<>("isInPatient"));

        // Set the cell factory for the column with the button
        detailColumn.setCellFactory(col -> new ButtonTableCell<>(detailColumn));
        detailColumn.setMinWidth(100);
        
        billingColumn.setCellFactory(col -> new ButtonBillingCell<>(billingColumn));
        billingColumn.setMinWidth(100);

        patientTable.getColumns().addAll(nameColumn, emailColumn, addressColumn, phoneColumn, createdDateColumn, imagingRequiredColumn, outPatientColumn, inPatientColumn, detailColumn, billingColumn);

        // Create an ObservableList from the list of employees
        ObservableList<Patient> patientData = FXCollections.observableArrayList(databaseModel.getAllPatients());
        // Set the data to the TableView
        patientTable.setItems(patientData);

        patientTable.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {

                Patient selectedPatient = patientTable.getSelectionModel().getSelectedItem();
                if (selectedPatient != null) {
                    navigateToEditPatient(selectedPatient);
                }
            }
        });
    }

    private void closeWindow() {
        // Return to the dashboard
        // get a handle to the stage
        Stage stage = (Stage) searchField.getScene().getWindow();
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
            editController.loadFields();

        } catch (IOException e) {
            System.out.println("com.mycompany.healthlinkhospital.CheckEmployeeDetailController.navigateToEditEmployee() failed ==> " + e);
        }
    }

//    public void refreshPatientList() {
//        // Make sure that databaseModel is properly initialized and not null
//
//        patientTable.setItems((ObservableList<Patient>) databaseModel.getAllPatients());
//
//    }

    @FXML
    private void handleSearchPatientButton() throws SQLException {
        String employeeEmail = searchField.getText();

        patientTable.getItems().stream()
                .filter(item -> (item.getEmail().equals(employeeEmail)))
                .findAny()
                .ifPresent(item -> {
                    System.out.println("Search item =" + item);

                    patientTable.getSelectionModel().select(item);
                    patientTable.scrollTo(item);
                });
    }

}
