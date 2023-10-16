/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.healthlinkhospital.TableCells;

import com.mycompany.healthlinkhospital.App;
import com.mycompany.healthlinkhospital.AppointmentController;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import model.Appointment;
import model.DatabaseModel;
import model.HospitalStaff;
import model.Patient;

/**
 *
 * @author pukarsharma
 */

public class EditAppoinmentTableCell<S> extends TableCell<S, Void> {
    private final Button editButton = new Button("Edit");
    private Appointment appointment = null;

    public EditAppoinmentTableCell(TableColumn<S, Void> column) {
        // Create a button that is visible in the cell
        editButton.setOnAction(event -> {
            // Handle button click action, e.g., open an edit dialog
            S rowData = getTableView().getItems().get(getIndex());
            // You can access the data associated with this row (S rowData) and perform actions accordingly
            System.out.println("rowData =>> " + rowData);
            if (rowData instanceof Appointment) {
                Appointment _appointment = (Appointment) rowData;
                //handleEditAppointment(patient);
                appointment = _appointment;
                openAppointmentEditDialog();
            }
        });

        HBox pane = new HBox(editButton);
        pane.setSpacing(5);
        setGraphic(pane);
    }

    @Override
    protected void updateItem(Void item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
            setGraphic(null);
        }
    }
    
    private void openAppointmentEditDialog() {
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
    }
}