package com.mycompany.healthlinkhospital;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Account;
import model.CheckinType;
import model.HospitalStaff;
import model.Patient;
import utilities.PayrollSystem;

public class DashboardController extends BaseController {

    @FXML
    private Button addPatientButton;
    @FXML
    private Button checkEmployeeDetailButton;
    @FXML
    private Button logoutButton;
    @FXML
    private Button attendanceButton;
    @FXML
    private TextArea personalDetailTextArea;
    @FXML
    private TextArea accountsTextArea;

    @FXML
    private TextArea taxTextArea;

    protected ObservableList checkinType = FXCollections.observableArrayList(new ArrayList<>(EnumSet.allOf(CheckinType.class)));

    public HospitalStaff employee = null;

    public Patient patient = null;

    List<PayrollSystem.TaxBracket> taxBrackets = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize(url, rb);
        addTaxBrackets();
    }

    @FXML
    private void refreshButtonClicked() {
        if (employee != null) {
            setEmployee(databaseModel.getEmployeeDetails(employee.getEmail()));
        }
    }

    @FXML
    private void logout() {
        // Close the current dashboard window
        Stage stage = (Stage) logoutButton.getScene().getWindow();
        stage.close();

        try {
            employee = null;
            FXMLLoader loader = new FXMLLoader(App.class.getResource("login.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root, 480, 480);
            Stage loginStage = new Stage();
            loginStage.setScene(scene);
            loginStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleAddPatientButton() {
        // Load and display the register view (assuming "register.fxml" is the register page)

        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("addPatient.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage registerStage = new Stage();
            registerStage.setScene(scene);
            registerStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleSearchEmployeeButton() {

        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("checkEmployeeDetail.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage registerStage = new Stage();
            registerStage.setScene(scene);
            registerStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleAddTimesheetButton() {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("attendance.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Week's Hours Entry");

            AppointmentController attendanceController = loader.getController();
            attendanceController.staff = employee;

            // Show the week's hours entry form
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void navigateToEditEmployee() {
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

    public void setEmployee(HospitalStaff employee) {
        this.employee = employee;
        displayEmployeeDetails();
        loadAccountDetails();
    }

    private void displayEmployeeDetails() {
        if (employee != null) {
            StringBuilder details = new StringBuilder();
            details.append("Name: ").append(employee.getName()).append("\n");
            details.append("Email: ").append(employee.getEmail()).append("\n");
            details.append("Is Manager: ").append(employee.isManager()).append("\n");
            details.append("Address: ").append(employee.getAddress()).append("\n");
            details.append("Phone number: ").append(employee.getPhone()).append("\n");

            addPatientButton.setVisible(employee.isManager());
            checkEmployeeDetailButton.setVisible(employee.isManager());
            personalDetailTextArea.setText(details.toString());
        }
    }

    private void loadAccountDetails() {
        if (employee != null) {
            if (employee.getUid() != 1001) {
                Account account = databaseModel.getAccountDetailsForEmployee(employee);
                if (account != null) {
                    double taxableIncome = account.getHourlyRate() * 52;
                    double incomeTax = PayrollSystem.calculateIncomeTax(taxableIncome, taxBrackets);
                    System.out.println("Income Tax: $" + incomeTax);

                    StringBuilder taxDetails = new StringBuilder();
                    taxDetails.append("Your per hour work rate is: ").append(account.getHourlyRate()).append("\n");
                    taxDetails.append("Your taxable income per year is calculated as:  ").append(taxableIncome).append("\n");
                    taxDetails.append("Your predicted (before claims) income tax for current fiscal year is: $").append(incomeTax).append("\n");
                    taxTextArea.setText(taxDetails.toString());
                }
            } else {
                StringBuilder taxDetails = new StringBuilder();
                taxDetails.append("The super user doesn't have any tax information logged. ");
                taxTextArea.setText(taxDetails.toString());
            }
        }
    }

    private void addTaxBrackets() {
        // Add tax brackets with their minimum and maximum incomes and rates
        taxBrackets.add(new PayrollSystem.TaxBracket(0, 10000, 0.10)); // 10% tax on income between 0 and 10,000
        taxBrackets.add(new PayrollSystem.TaxBracket(10001, 20000, 0.15)); // 15% tax on income between 10,001 and 20,000
        taxBrackets.add(new PayrollSystem.TaxBracket(20001, 30000, 0.20)); // 20% tax on income between 20,001 and 30,000
    }
}
