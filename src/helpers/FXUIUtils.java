/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helpers;

import static helpers.Utilities.isNullOrEmpty;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.time.LocalDate;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author ClinicMaster13
 */
public class FXUIUtils {
    
      public static String browse(TextField textField) {
        try {

            FileChooser fileChooser = new FileChooser();
//                FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
//                directoryChooser.getExtensionFilters().add(extFilter);
            fileChooser.setTitle("Choose File");
            File file = fileChooser.showOpenDialog(null);
            String path = file.getPath();
            textField.setText(path);
            return file.getName();
        } catch (Exception e) {
            throw  e;
        }
    }
      
          public static void browse(TextField textField, Stage stage) {
        try {

            FileChooser fileChooser = new FileChooser();
//                FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
//                directoryChooser.getExtensionFilters().add(extFilter);
            fileChooser.setTitle("Choose File");
            File file = fileChooser.showOpenDialog(stage);
            String path = file.getPath();
            textField.setText(path);
        } catch (Exception e) {
        }
    }
      
        public static void browseSave(TextField textField) {
        try {

            FileChooser fileChooser = new FileChooser();
//                FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
//                directoryChooser.getExtensionFilters().add(extFilter);
            fileChooser.setTitle("Choose File");
            File file = fileChooser.showSaveDialog(null);
            String path = file.getPath();
            textField.setText(path);
        } catch (Exception e) {
        }
    }
        
          public static void browseDirectory(TextField textField) {
        try {

            DirectoryChooser directoryChooser = new DirectoryChooser();
//                FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
//                directoryChooser.getExtensionFilters().add(extFilter);
            directoryChooser.setTitle("Choose Location");
            File file = directoryChooser.showDialog(null);
            String path = file.getAbsolutePath();
            textField.setText(path);
        } catch (Exception e) {
        }
    }
        
            public static void browseDirectory(TextField textField, Stage stage) {
        try {

            DirectoryChooser directoryChooser = new DirectoryChooser();
//                FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
//                directoryChooser.getExtensionFilters().add(extFilter);
            directoryChooser.setTitle("Choose Location");
            File file = directoryChooser.showDialog(stage);
            String path = file.getAbsolutePath();
            textField.setText(path);
        } catch (Exception e) {
        }
    }
        
        
        public static void errorMessage(Exception e) {
        Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
        alert.setTitle("Error:");
        alert.show();
        e.printStackTrace();

    }

    public static void message(Object message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, message.toString(), ButtonType.OK);
        alert.show();
    }
    
     public static void setTableEditable(TableView table) {
        table.setEditable(true);
        // allows the individual cells to be selected
        table.getSelectionModel().cellSelectionEnabledProperty().set(true);
        // when character or numbers pressed it will start edit in editable
        // fields
        table.setOnKeyPressed(event -> {
            if (event.getCode().isLetterKey() || event.getCode().isDigitKey()) {
                editFocusedCell(table);
            } else if (event.getCode() == KeyCode.RIGHT
                    || event.getCode() == KeyCode.TAB) {
                table.getSelectionModel().selectNext();
                event.consume();
            } else if (event.getCode() == KeyCode.LEFT) {
                // work around due to
                // TableView.getSelectionModel().selectPrevious() due to a bug
                // stopping it from working on
                // the first column in the last row of the table
                selectPrevious(table);
                event.consume();
            } else if (event.getCode() == KeyCode.DELETE) {
                table.getItems().removeAll(table.getSelectionModel().getSelectedItems());
            }
        });
    }

    private static void editFocusedCell(TableView<Class> table) {
        final TablePosition <Class, ?> focusedCell = table
                .focusModelProperty().get().focusedCellProperty().get();
        table.edit(focusedCell.getRow(), focusedCell.getTableColumn());
    }

    @SuppressWarnings("unchecked")
    private  static void selectPrevious(TableView table) {
        if (table.getSelectionModel().isCellSelectionEnabled()) {
            // in cell selection mode, we have to wrap around, going from
            // right-to-left, and then wrapping to the end of the previous line
            TablePosition<Class, ?> pos = table.getFocusModel()
                    .getFocusedCell();
            if (pos.getColumn() - 1 >= 0) {
                // go to previous row
                table.getSelectionModel().select(pos.getRow(),
                        getTableColumn(table,pos.getTableColumn(), -1));
            } else if (pos.getRow() < table.getItems().size()) {
                // wrap to end of previous row
                table.getSelectionModel().select(pos.getRow() - 1,
                        table.getVisibleLeafColumn(
                                table.getVisibleLeafColumns().size() - 1));
            }
        } else {
            int focusIndex = table.getFocusModel().getFocusedIndex();
            if (focusIndex == -1) {
                table.getSelectionModel().select(table.getItems().size() - 1);
            } else if (focusIndex > 0) {
                table.getSelectionModel().select(focusIndex - 1);
            }
        }
    }
    
    private static TableColumn<Class, ?> getTableColumn(TableView table,
            final TableColumn<Class, ?> column, int offset) {
        int columnIndex = table.getVisibleLeafIndex(column);
        int newColumnIndex = columnIndex + offset;
        return table.getVisibleLeafColumn(newColumnIndex);
    }
    
    
    public static String getText(TextField field,String string) throws Exception {
        String enteredString = field.getText().trim();
        if(isNullOrEmpty(enteredString)){
         throw new Exception("You must enter: "+string+"!");
        }
        return enteredString;
    }
    
    public static String getText(ComboBox box,String string) throws Exception {
        String enteredString = box.getValue().toString().trim();
        if(isNullOrEmpty(enteredString)){
         throw new Exception("You must enter: "+string+"!");
        }
        return enteredString;
    }
    
    public static String getText(TextArea field,String string) throws Exception {
        String enteredString = field.getText().trim();
        if(isNullOrEmpty(enteredString)){
         throw new Exception("You must enter: "+string+"!");
        }
        return enteredString;
    }
   
      public static LocalDate getDate(DatePicker picker,String string) throws Exception {
        LocalDate enteredDate = picker.getValue();
        if(enteredDate == null){
         throw new Exception("You must enter: "+string+"!");
        }
        return enteredDate;
    }
      
      public static int getInt(TextField field, String string) throws Exception {
        String enteredString = field.getText().trim();
        if (isNullOrEmpty(enteredString)) {
            field.requestFocus();
            throw new Exception("You must enter: " + string + "!");
        } else if (!Utilities.isInteger(enteredString)) {
            field.requestFocus();
            throw new Exception("Invalid Integer value: " + enteredString + " for " + string + "!");
        } else {
            return Integer.parseInt(enteredString);
        }

    }
     
      public static ImageView getImage(ImageView imageView, byte[] bytes, int width, int height, boolean mAspectRation, boolean mSmoothness){
          InputStream in = new ByteArrayInputStream(bytes);
             Image image = new Image(in,width, height, mAspectRation, mSmoothness);
            imageView.setImage(image);
            return imageView;
      }
      
       public static FXMLLoader getUILoader(String ui) {

        try {

            return new FXMLLoader(FXUIUtils.class.getResource( ui + ".fxml"));

        } catch (Exception e) {

            throw e;
        }
    }
     


}
