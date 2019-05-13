/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package saburijavafxtools;

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
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("SaburiJavaFXTools.fxml"));
                    stpMain.getChildren().add(root);
//                  Scene scene = new Scene(stpMain);
//                    Stage stage = new Stage();
//                    stage.setScene(scene);
                 
//                    stage.initOwner(stpMain.getScene().getWindow());
//                    stage.show();
            
                    btnGenerate.setOnAction(e -> {
                try {
//                    Parent root =FXUIUtils.getUILoader("SaburiJavaFXTools").load();
                    Parent root1 = FXMLLoader.load(getClass().getResource("SaburiJavaFXTools.fxml"));
                    Scene scene1 = new Scene(root);
                    Stage stage1 = new Stage();
                    stage1.setScene(scene1);
                    stage1.initOwner(stpMain.getScene().getWindow());
                    stage1.show();
                    FXUIUtils.message("Clicked");
                } catch (IOException ex) {
                    errorMessage(ex);
                }
            });
            
            btnGenerate.fire();
            
        } catch (Exception e) {
        }
    }
    
}
