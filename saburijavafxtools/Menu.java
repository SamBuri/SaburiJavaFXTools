/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package saburijavafxtools;

import helpers.Enums;
import static helpers.Utilities.isNullOrEmpty;

/**
 *
 * @author CLINICMASTER13
 */
public class Menu {

    public static String makeMenu(String objectName, Enums.MenuTypes menuType, String parentMenu) {
        String parentMenuHolder;
        String fxmlMenu;
        String menu = "";
        if (isNullOrEmpty(parentMenu)) {
            parentMenuHolder = objectName;
        } else {
            if (!parentMenu.endsWith(objectName)) {
                parentMenuHolder = parentMenu.concat(objectName);
            } else {
                parentMenuHolder = parentMenu;
            }

        }

        String addMenu;
        String updateMenu;
        String viewMenu;
        String menuName;
        if (menuType.equals(Enums.MenuTypes.Menu)) {
            menuName = "MenuItem";
            parentMenuHolder = "mnu" + parentMenuHolder;
            addMenu = parentMenuHolder + "Add";
            updateMenu = parentMenuHolder + "Update";
            viewMenu = parentMenuHolder + "View";
            fxmlMenu = "<items>\n"
                    + "<Menu fx:id=\"" + parentMenuHolder + "\" mnemonicParsing=\"false\" text=\"" + objectName + "\" id = \"" + objectName + "\">\n"
                    + "<MenuItem fx:id=\"" + addMenu + "\" id = \"Create\" mnemonicParsing=\"false\" text=\"Add\" />\n"
                    + "<MenuItem fx:id=\"" + updateMenu + "\" id = \"Update\" mnemonicParsing=\"false\" text=\"Update\" />\n"
                    + "<MenuItem fx:id=\"" + viewMenu + "\" id = \"Read\" mnemonicParsing=\"false\" text=\"View\" />\n"
                    + "</Menu>\n"
                    + "</items>";
        } else {
            menuName = "MenuItem";
            parentMenuHolder = "spm" + parentMenuHolder;
            addMenu = parentMenuHolder + "Add";
            updateMenu = parentMenuHolder + "Update";
            viewMenu = parentMenuHolder + "View";

            fxmlMenu = "<SplitMenuButton fx:id=\"" + parentMenuHolder + "\" id =\"" + objectName + "\"  mnemonicParsing=\"false\" text=\"" + objectName + "\">\n"
                    + "<items>\n"
                    + "<MenuItem fx:id=\"" + addMenu + "\" id =\"Create\" mnemonicParsing=\"false\" text=\"Save\" />\n"
                    + "<MenuItem fx:id=\"" + updateMenu + "\" id =\"Update\" mnemonicParsing=\"false\" text=\"Update\" />\n"
                    + "<MenuItem fx:id=\"" + viewMenu + "\" id =\"Read\" mnemonicParsing=\"false\" text=\"View\" />\n"
                    + "</items>\n"
                    + "</SplitMenuButton>";
        }
        String searchMenu = "new SearchItem(new " + objectName + "DA(), \"" + objectName + "\", \"" + objectName + "\", false),\n" +
"            new SearchItem(new " + objectName + "DA(), Revision, \"" + objectName + "\", \"" + objectName + "\", false)";

        String menuFields = "@FXML private " + menuName + " " + addMenu + ", " + updateMenu + ", " + viewMenu + ";\n";

        menu += fxmlMenu;
        menu+="\n\n\\***********************Add to SearchObjects***************************************************/";
        menu+=searchMenu;
        menu += "\n\n****************************add SceneController.java*************************************************************\n";
        menu += menuFields;
        menu += "editMenuItemClick(" + addMenu + ", \"" + objectName + "\", \"" + objectName + "\", FormMode.Save);\n";
        menu += "editMenuItemClick(" + updateMenu + ", \"" + objectName + "\", \"" + objectName + "\", FormMode.Update);\n";
        menu += "viewMenuItemClick(" + viewMenu + ", new " + objectName + "DA(), \"" + objectName + "\", \"" + objectName + "\",false, true);\n";
        return menu;
    }

}
