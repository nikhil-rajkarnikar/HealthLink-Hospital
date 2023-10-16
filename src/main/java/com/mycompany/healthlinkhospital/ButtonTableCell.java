package com.mycompany.healthlinkhospital;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author pukarsharma
 */
import com.mycompany.healthlinkhospital.App;
import com.mycompany.healthlinkhospital.AppointmentDetailsController;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import model.Patient;

public class ButtonTableCell<S> extends TableCell<S, Void> {

    private final Button editButton = new Button("Detail");

    public ButtonTableCell(TableColumn<S, Void> column) {
        // Create a button that is visible in the cell
        editButton.setOnAction(event -> {
            // Handle button click action, e.g., open an edit dialog
            S rowData = getTableView().getItems().get(getIndex());
            // You can access the data associated with this row (S rowData) and perform actions accordingly
            System.out.println("rowData =>> " + rowData);

            if (rowData instanceof Patient) {
                Patient patient = (Patient) rowData;
                openPatientEditDialog(patient);
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

    private void openPatientEditDialog(Patient patient) {
        try {
            // Load the FXML file for the detail view
            FXMLLoader loader = new FXMLLoader(App.class.getResource("AppointmentDetails.fxml"));
            Parent root = loader.load();

            // Pass the patient data to the controller of the detail view
            AppointmentDetailsController detailController = loader.getController();
            detailController.setPatient(patient);

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