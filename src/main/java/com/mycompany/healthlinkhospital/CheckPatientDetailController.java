/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.healthlinkhospital;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
import model.HospitalStaff;
import model.Patient;
import utilities.AlertUtils;

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

    @FXML
    private TextArea employeeDetailSearchArea;

    @FXML
    private TableView<HospitalStaff> employeeTable;

    // Create TableColumn objects
    TableColumn<HospitalStaff, String> nameColumn = new TableColumn<>("Name");
    TableColumn<HospitalStaff, String> emailColumn = new TableColumn<>("Email");
    TableColumn<HospitalStaff, String> addressColumn = new TableColumn<>("Address");
    TableColumn<HospitalStaff, String> phoneColumn = new TableColumn<>("Phone");
    TableColumn<HospitalStaff, String> isAdminColumn = new TableColumn<>("Is Administrator");

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

        isAdminColumn.setCellValueFactory(new PropertyValueFactory<>("isManagerString"));

        employeeTable.getColumns().addAll(nameColumn, emailColumn, addressColumn, phoneColumn, isAdminColumn);

        // Create an ObservableList from the list of employees
        ObservableList<HospitalStaff> employeeData = FXCollections.observableArrayList(databaseModel.getAllEmployees());

        // Set the data to the TableView
        employeeTable.setItems(employeeData);

        employeeTable.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {

                HospitalStaff selectedEmployee = employeeTable.getSelectionModel().getSelectedItem();
                if (selectedEmployee != null) {
//                    navigateToEditEmployee(selectedEmployee);
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

    private void navigateToEditEmployee(Patient patient) {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("addEmployee.fxml"));
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

    @FXML
    private void handleSearchEmployeeButton() throws SQLException {

        String employeeEmail = searchField.getText();

        employeeTable.getItems().stream()
                .filter(item -> (item.getEmail().equals(employeeEmail)))
                .findAny()
                .ifPresent(item -> {
                    employeeTable.getSelectionModel().select(item);
                    employeeTable.scrollTo(item);
                });

    }

    private void displayEmployeeDetails(HospitalStaff employee) {
        if (employee != null) {
            StringBuilder details = new StringBuilder();
            details.append("Name: ").append(employee.getName()).append("\n");
            details.append("Email: ").append(employee.getEmail()).append("\n");
            details.append("Is Manager: ").append(employee.isManager()).append("\n");
            employeeDetailSearchArea.setText(details.toString());
        }
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
}
