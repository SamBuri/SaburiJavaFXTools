/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package saburijavafxtools;

import Model.Settings;
import helpers.FXUIUtils;
import static helpers.FXUIUtils.errorMessage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javax.swing.plaf.DesktopPaneUI;

/**
 * FXML Controller class
 *
 * @author ClinicMaster13
 */
public class SceneController implements Initializable {
    
    @FXML
    private StackPane stpMain;
    @FXML
    Button btnGenerate;
    @FXML
    private MenuItem mniSetting;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("SaburiJavaFXTools.fxml"));
                    stpMain.getChildren().add(root);

            
            mniSetting.setOnAction(e->{
             
                try {
                    Parent settingsRoot = FXMLLoader.load(getClass().getResource("Settings.fxml"));
                    Scene scene = new Scene(settingsRoot);
                    Stage stage = new Stage();
                    stage.setScene(scene);
                    stage.show();
                  
                } catch (IOException ex) {
                    errorMessage(ex);
                }
            });
            loadSettings();
            
        } catch (IOException e) {
            errorMessage(e);
        }
    }
    
    private void loadSettings(){
        try {
            Settings settings = new Settings().xmlDecode();
            
            Settings.setAppSettings(settings);
        } catch (IOException ex) {
            errorMessage(ex);
        }
    }
    
}
