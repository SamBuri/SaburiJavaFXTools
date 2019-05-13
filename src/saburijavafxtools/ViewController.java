/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package saburijavafxtools;

import helpers.Utilities;

/**
 *
 * @author CLINICMASTER13
 */
public class ViewController {

    private final String objectName;
    private final String objectNameDA;
    private final String objectNameViewController;

    private final String daVariableName;

    public ViewController(String objectName) {
        this.objectName = objectName;
        this.objectNameDA = objectName.concat("DA");
        this.objectNameViewController = objectName.concat("ViewController");
        this.daVariableName = Utilities.getVariableName(objectNameDA);
    }

    private String imports() {
        return "import dbaccess." + objectNameDA + ";\n"
                + "import java.net.URL;\n"
                + "import java.util.ResourceBundle;\n"
                + "import javafx.collections.FXCollections;\n"
                + "import javafx.fxml.FXML;\n"
                + "import javafx.scene.control.TableView;\n";
    }

    private String proprties() {
        return  "    @FXML\n"
                + "    private TableView<" + objectNameDA + "> tbl" + objectName + ";\n"
                + "    private final " + objectNameDA + " " + daVariableName + " = new " + objectNameDA + "();\n\n";

    }

    private String methods() {

        String initiMethod = "@Override\n"
                + "public void initialize(URL url, ResourceBundle rb) {\n"
                + "  this.tableView =tbl" + objectName + ";\n"
                + "  data = FXCollections.observableArrayList(" + daVariableName + ".get());\n"
                + "  initSearchEvents(\"" + objectName + "EDit\", \"" + objectName + "\");\n"
                + "}";

        return initiMethod;
    }

    public String makeClass() throws Exception {
        JavaClass javaClass = new JavaClass("controllers", objectNameViewController, this.imports(),
                this.proprties(), "", "", methods());
        return javaClass.makeClass("ViewController");
    }

}
