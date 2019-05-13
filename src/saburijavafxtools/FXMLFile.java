/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package saburijavafxtools;

/**
 *
 * @author CLINICMASTER13
 */
public class FXMLFile {
   
    private final String imports;
    private final String body;

    public FXMLFile(String imports, String body) {
        this.imports = imports;
        this.body = body;
    }
    
      public String create(){
      return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n".concat(imports).concat("\n").concat(body);
              
    }
    
     public String create(String objectName, String controller, String listControls, boolean makeSaveButton){
      return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n".concat(imports)
              +"<VBox id=\"" + objectName + "\"  spacing=\"10\"  "
              + "xmlns=\"http://javafx.com/javafx/10.0.1\" xmlns:fx=\"http://javafx.com/fxml/1\" fx:controller=\"controllers." + controller + "\">\n"
                + "<children>\n"
              .concat("\n").concat(body).concat(fxmbodyBottom(makeSaveButton, objectName, listControls));
              
    }
     
     
      private String fxmbodyBottom(boolean makeSaveButton, String objectName, String listControls) {
        String bodyBottom = "";

        if (makeSaveButton) {
            bodyBottom += "</GridPane>\n".concat(listControls);
        }

        
        if (makeSaveButton) {
              bodyBottom += "<AnchorPane>\n";
            bodyBottom += "<VBox spacing=\"2\" AnchorPane.bottomAnchor=\"27.0\" AnchorPane.leftAnchor=\"7.0\">\n"
                    + "<Button id=\"" + objectName + "\" fx:id=\"btnSearch\"  prefWidth=\"80.0\" text=\"Search\" visible=\"false\" />\n"
                    + "<Button id=\"" + objectName + "\" fx:id=\"btnSave\"  prefHeight=\"25.0\" prefWidth=\"80.0\" text=\"Save\" />\n"
                    + "</VBox>\n"
                    + "<VBox spacing=\"2\" AnchorPane.bottomAnchor=\"27.0\" AnchorPane.rightAnchor=\"7.0\">\n"
                    + "<Button id=\"" + objectName + "\" fx:id=\"btnDelete\"  prefWidth=\"80.0\" text=\"Delete\" visible=\"false\" />\n"
                    + "<Button id=\"" + objectName + "\" fx:id=\"btnClose\"  prefHeight=\"25.0\" prefWidth=\"80.0\" text=\"Close\" />\n"
                    + "</VBox>\n"
                    + "</AnchorPane>"
                    + "</children>\n"
                    + "\n"
                    + "</VBox>\n";

        } else {
            bodyBottom += "</children>\n"
                    + "\n"
                    + "</VBox>\n";
        }
        return bodyBottom;
    }
    
  
    
    
    
}
