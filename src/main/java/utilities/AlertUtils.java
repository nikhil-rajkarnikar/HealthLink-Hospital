/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utilities;

/**
 *
 * @author pukarsharma
 */
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class AlertUtils {

    public static void showErrorAlert(String title, String message) {
        showAlert(title, message, AlertType.ERROR);
    }

    public static void showInfoAlert(String title, String message) {
        showAlert(title, message, AlertType.INFORMATION);
    }

    public static void showWarningAlert(String title, String message) {
        showAlert(title, message, AlertType.WARNING);
    }

    public static void showConfirmationAlert(String title, String message) {
        showAlert(title, message, AlertType.CONFIRMATION);
    }

    private static void showAlert(String title, String message, AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        alert.showAndWait();
    }
}
