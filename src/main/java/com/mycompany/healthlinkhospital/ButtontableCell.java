/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.healthlinkhospital;

/**
 *
 * @author pukarsharma
 */
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class ButtontableCell<S> extends TableCell<S, Void> {
    private final Button editButton = new Button("Edit");

    public ButtontableCell(TableColumn<S, Void> column) {
        // Create a button that is visible in the cell
        editButton.setOnAction(event -> {
            // Handle button click action, e.g., open an edit dialog
            S rowData = getTableView().getItems().get(getIndex());
            // You can access the data associated with this row (S rowData) and perform actions accordingly
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
}
