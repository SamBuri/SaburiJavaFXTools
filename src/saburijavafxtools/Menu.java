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

    public static String makeMenu(String objectName, String objectCaption, Enums.MenuTypes menuType, String parentMenu) {
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
        String addText = "New";
        String editText = "Edit";
        String viewText = "View";
        if (menuType.equals(Enums.MenuTypes.Menu)) {
            menuName = "MenuItem";
            parentMenuHolder = "mnu" + parentMenuHolder;
            addMenu = parentMenuHolder + addText;
            updateMenu = parentMenuHolder + editText;
            viewMenu = parentMenuHolder + viewText;
            fxmlMenu = "<items>\n"
                    + "<Menu fx:id=\"" + parentMenuHolder + "\" mnemonicParsing=\"false\" text=\"" + objectCaption + "\" id = \"" + objectName + "\">\n"
                    + "<MenuItem fx:id=\"" + addMenu + "\" id = \"Create\" mnemonicParsing=\"false\" text=\"" + addText + "\" />\n"
                    + "<MenuItem fx:id=\"" + updateMenu + "\" id = \"Update\" mnemonicParsing=\"false\" text=\"" + editText + "\" />\n"
                    + "<MenuItem fx:id=\"" + viewMenu + "\" id = \"Read\" mnemonicParsing=\"false\" text=\"" + viewText + "\" />\n"
                    + "</Menu>\n"
                    + "</items>";
        } else {
            menuName = "MenuItem";
            parentMenuHolder = "spm" + parentMenuHolder;
            addMenu = parentMenuHolder + addText;
            updateMenu = parentMenuHolder + editText;
            viewMenu = parentMenuHolder + viewText;

            fxmlMenu = "<SplitMenuButton fx:id=\"" + parentMenuHolder + "\" id =\"" + objectName + "\"  mnemonicParsing=\"false\" text=\"" + objectCaption + "\">\n"
                    + "<items>\n"
                    + "<MenuItem fx:id=\"" + addMenu + "\" id =\"Create\" mnemonicParsing=\"false\" text=\"" + addText + "\" />\n"
                    + "<MenuItem fx:id=\"" + updateMenu + "\" id =\"Update\" mnemonicParsing=\"false\" text=\"" + editText + "\" />\n"
                    + "<MenuItem fx:id=\"" + viewMenu + "\" id =\"Read\" mnemonicParsing=\"false\" text=\"" + viewText + "\" />\n"
                    + "</items>\n"
                    + "</SplitMenuButton>";
        }
        String searchMenu = "new SearchItem(new " + objectName + "DA(), \"" + objectName + "\", \"" + objectCaption + "s\", false),\n"
                + "            new SearchItem(new " + objectName + "DA(), Revision, \"" + objectName + "\", \"" + objectCaption + "s\", false)";

        String menuFields = "@FXML private " + menuName + " " + addMenu + ", " + updateMenu + ", " + viewMenu + ";\n";

        menu += fxmlMenu;
        menu += "\n\n\\***********************Add to SearchObjects***************************************************/";
        menu += searchMenu;
        menu += "\n\n****************************add SceneController.java*************************************************************\n";
        menu += menuFields;
        menu += "editMenuItemClick(" + addMenu + ", \"" + objectName + "\", \"" + objectCaption + "\", FormMode.Save);\n";
        menu += "editMenuItemClick(" + updateMenu + ", \"" + objectName + "\", \"" + objectCaption + "\", FormMode.Update);\n";
        menu += "viewMenuItemClick(" + viewMenu + ", new " + objectName + "DA(), \"" + objectName + "\", \"" + objectCaption + "s\",false, true);\n";
        return menu;
    }

}
