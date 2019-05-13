/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package saburijavafxtools;

import Model.Field;
import helpers.Utilities;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author CLINICMASTER13
 */
public class UIView extends CodeGenerator {

    private final String objectName;
    private final String objectNameController;
    private final List<Field> fields;

    public UIView(String objectName, List<Field> fields) {
        this.objectName = objectName;
        this.fields = fields;
        this.objectNameController = objectName.concat("ViewController");
    }

    private List<Field> getAllField() {
        List<Field> list = new ArrayList<>();
        list.addAll(fields);
        list.addAll(defaulFields);
        return list;
    }

    private String imports() {
        String imports = "<?import javafx.scene.layout.AnchorPane?>\n"
                + "<?import javafx.scene.layout.HBox?>\n"
                + "<?import javafx.scene.layout.GridPane?>\n"
                + "<?import javafx.scene.layout.VBox?>\n"
                + "<?import javafx.geometry.Insets?>\n"
                + "<?import javafx.scene.control.ContextMenu?>\n"
                + "<?import javafx.scene.control.MenuItem?>\n"
                + "<?import javafx.scene.control.TableColumn?>\n"
                + "<?import javafx.scene.control.TableView?>\n"
                + "<?import javafx.scene.control.TextField?>\n"
                + "<?import javafx.scene.control.ComboBox?>\n"
                + "<?import javafx.scene.control.cell.PropertyValueFactory?>\n"
                + "<?import javafx.scene.control.Label?>\n"
                + "<?import javafx.scene.control.Button?>\n";

        return imports;
    }

    private String makeBody() {
        String tableColumn = "";
        tableColumn = getAllField().stream().map((field) -> field.getTableColumn(objectName, "false")).reduce(tableColumn, String::concat);

        String contextMenu = "<contextMenu>\n"
                + "            <ContextMenu fx:id =\"cmu" + objectName + "\" id = \"" + objectName + "\">\n"
                + "              <items>\n"
                + "                <MenuItem mnemonicParsing=\"false\" fx:id =\"cmiUpdate\" id = \"update\" text=\"Edit\" />\n"
                + "                <MenuItem mnemonicParsing=\"false\" fx:id =\"cmiDelete\" id = \"Delete\" text=\"Delete\" />\n"
                + "                 <MenuItem mnemonicParsing=\"false\" fx:id =\"cmiSelectAll\" id = \"" + objectName + "\" text=\"Select All\" />\n"
                + "              </items>\n"
                + "            </ContextMenu>\n"
                + "         </contextMenu>\n";

        return " <HBox spacing= \"10\">\n"
                + "            <padding>\n"
                + "                <Insets bottom=\"10.0\" left=\"10.0\" right=\"10.0\" top=\"10.0\" />\n"
                + "            </padding>\n"
                + "            <Label fx:id=\"lblSearch\" minWidth =\"100\" id=\"" + objectName + "\" GridPane.columnIndex =\"0\" GridPane.rowIndex =\"0\" text=\"Search\" />\n"
                + "            <ComboBox fx:id=\"cboSearchColumn\" minWidth =\"100\" id=\"" + objectName + "\" GridPane.columnIndex =\"0\" GridPane.rowIndex =\"0\" promptText =\"Select Column\" />\n"
                + "            <ComboBox fx:id=\"cboSearchType\" minWidth =\"100\" id=\"" + objectName + "\" GridPane.columnIndex =\"0\" GridPane.rowIndex =\"0\" promptText =\"Select\" />\n"
                + "            <TextField fx:id=\"txtSearch\" minWidth =\"100\" id=\"" + objectName + "\" GridPane.columnIndex =\"1\" GridPane.rowIndex =\"0\" promptText=\"Search\" />\n"
                + " <TextField id=\"" + objectName + "\" fx:id=\"txtSecondValue\" minWidth=\"100\" promptText=\"Second Value\" visible =\"false\" GridPane.columnIndex=\"1\" GridPane.rowIndex=\"0\" />\n"
                + "        </HBox>\n"
                + Utilities.makeTable(objectName, tableColumn, contextMenu);

    }

    public String create() {
        return new FXMLFile(imports(), makeBody()).create(objectName, objectNameController, "", false);

    }

}
