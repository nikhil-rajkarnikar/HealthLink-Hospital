/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.healthlinkhospital;

import java.io.IOException;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.HospitalStaff;

/**
 *
 * @author pukarsharma
 */
public class LoginController extends BaseController {

    @FXML
    private TextField userNameTxtFld;

    @FXML
    private TextField passwordTxtfld;

    @FXML
    private Label warningOrErrorLbl;

    /**
     *
     * performs login operation
     */
    @FXML
    private void onLoginClicked(ActionEvent event) throws IOException {

        String email = userNameTxtFld.getText();
        String password = passwordTxtfld.getText();

        if (email.isBlank()) {
            warningOrErrorLbl.setText("Please enter your username!");
            return;
        }

        if (password.isBlank()) {
            warningOrErrorLbl.setText("Please ener your password!");
            return;
        }

        // verify and retrieve employee detail
        HospitalStaff employee = databaseModel.login(email, password);

        if (employee != null) {
            warningOrErrorLbl.setText("Login successful..");
            redirectToDashboard(event, employee);
        } else {
            // login failed
            warningOrErrorLbl.setText("Please enter correct username/password");
        }
    }

    private void redirectToDashboard(ActionEvent actionEvent, HospitalStaff employee) throws IOException {
        var pause = new PauseTransition(javafx.util.Duration.seconds(1));
        pause.setOnFinished(event -> {
            try {
                FXMLLoader loader;
                String scenePath;

                HospitalStaff staff = databaseModel.getEmployeeDetails(employee.getEmail());
                if (staff.isManager()) {
                    loader = new FXMLLoader(App.class.getResource("adminDashboard.fxml"));
                    scenePath = "adminDashboard";
                } else {
                    loader = new FXMLLoader(App.class.getResource("dashboard.fxml"));
                    scenePath = "dashboard";
                }

                // closing the current scene
                Node node = (Node) actionEvent.getSource();
                Stage stage = (Stage) node.getScene().getWindow();
                stage.close();

                // loading new scene
                Parent root = loader.load();
                Scene scene = new Scene(root);
                stage.setTitle("HealthLink - Hospital");
                stage.setScene(scene);

                if (scenePath.equals("dashboard")) {
                    DashboardController controller = loader.getController();
                    controller.setEmployee(employee);
                    loader.setController(controller);
                }

                stage.show();

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

        });
        pause.play();
    }
}
