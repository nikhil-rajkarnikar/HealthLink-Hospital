/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.healthlinkhospital;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import model.DatabaseModel;
import static utilities.DatabaseConstants.*;

/**
 *
 * @author pukarsharma
 */
abstract public class BaseController implements Initializable {

    public DatabaseModel databaseModel = null;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        if (databaseModel == null) {

            databaseModel = new DatabaseModel(MYSQL_URL, DB_URL, QRY_CREATE_DB, USER_NAME, PASSWORD);
        }
    }
}
