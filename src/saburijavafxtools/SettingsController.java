/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package saburijavafxtools;

import Model.Settings;
import helpers.FXUIUtils;
import static helpers.FXUIUtils.errorMessage;
import static helpers.FXUIUtils.getInt;
import static helpers.FXUIUtils.message;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author CLINICMASTER13
 */
public class SettingsController implements Initializable {

    
    @FXML
    private TextField txtBreakLine, txtMiniBreakLine;
    @FXML
    private Button btnSave;
    @FXML
    private Button btnClose;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       loadSettings();
       btnSave.setOnAction(e->this.save());
       btnClose.setOnAction(e->((Node)e.getSource()).getScene().getWindow().hide());
    }    
    
    private void loadSettings(){
        try {
            Settings settings = new Settings().xmlDecode();
            txtBreakLine.setText(String.valueOf(settings.getLineBreak()));
            txtMiniBreakLine.setText(String.valueOf(settings.getMiniLineBreak()));
            Settings.setAppSettings(settings);
        } catch (IOException ex) {
            errorMessage(ex);
        }
    }

    private void save() {
        try {
            int lineBreak = getInt(txtBreakLine, "Break Line");
            int miLineBreak = getInt(txtMiniBreakLine, "Break Line");
            Settings settings = new Settings(lineBreak, miLineBreak);
            settings.xmlEncode();
            message("Operation Successful");
        } catch (Exception e) {
            errorMessage(e);
        }
    }
    
}
