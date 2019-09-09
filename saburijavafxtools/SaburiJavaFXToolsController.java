/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package saburijavafxtools;

import helpers.FXUIUtils;
import static helpers.FXUIUtils.browse;
import static helpers.FXUIUtils.errorMessage;
import static helpers.FXUIUtils.message;
import static helpers.FXUIUtils.setTableEditable;
import static helpers.Utilities.isNullOrEmpty;
import static helpers.Utilities.makeDirectory;
import static helpers.Utilities.openFile;
import static helpers.Utilities.writeFile;
import Model.Field;
import helpers.Enums;
import helpers.Enums.MenuTypes;
import helpers.Enums.RelationMappping;
import helpers.Enums.Saburikeys;
import helpers.Enums.keys;
import helpers.Utilities;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;

import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Pair;

/**
 *
 * @author ClinicMaster13
 */
public class SaburiJavaFXToolsController implements Initializable {

    private enum FXControls {
        Label, TextField, ComboBox, DatePicker, CheckBox
    }

    private final ObservableList dataTypes = FXCollections.observableArrayList(
            "String", "List", "Set","Image", "LocalDate", "LocalDateTime", "LocalTime", "boolean", "int", "float", "double"
    );
    
     private final ObservableList enumClasses = FXCollections.observableArrayList("CommonEnums", "Enums");

    private final ObservableList mapppings = FXCollections.observableArrayList(RelationMappping.OneToOne.name(),
            RelationMappping.OneToMany.name(), RelationMappping.ManyToOne.name(), RelationMappping.ManyToMany.name());

    private final ObservableList keyses = FXCollections.observableArrayList(keys.Primary.name(), Enums.keys.Foreign.name(), Enums.keys.Unique.name(), Enums.keys.None.name());

    private final ObservableList saburiKeys = FXCollections.observableArrayList(Saburikeys.ID_Helper.name(),
            Saburikeys.ID_Generator.name(), Saburikeys.Display.name(), Saburikeys.None.name());

    private final ObservableList togenerateFiles = FXCollections.observableArrayList(
            "", "Entity", "DBAcess", "Controller", "View Controller", "FXML", "FXML View", "Menu", "SQL"
    );

    private final ObservableList menuTypes = FXCollections.observableArrayList(Enums.MenuTypes.values());

    @FXML
    TableView<Field> tblSaburiTools;
    @FXML
    private TableColumn<Field, String> tbcFieldName, tbcCaption, tbcDataType, tbcReferences, tbcKey,
            tbcSaburiKey, tbcMapping, tbcSubFields, tbcEnumClass;
    @FXML
    private TableColumn<Field, Integer> tbcSize;

    @FXML
    private TableColumn<Field, Boolean> tbcNullable;

    @FXML
    private TableColumn<Field, Boolean> tbcEnumerated;

    @FXML
    private TextField txtFileName, txtObjectName, txtOutputDirectory, txtParentMenuID;
    @FXML

    private Button btnBrowse, btnOutputDirectory;
    @FXML
    private Button btnGenerate;
    @FXML
    private Button btnClose;
    @FXML
    private Button btnImport, btnSave;
    @FXML
    private ComboBox cboFiles;
    @FXML
    private ComboBox<MenuTypes> cboMenuType;

    @FXML
    private CheckBox chkOpenFile, chkGenerateMenus, chkGenerateViewUI;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            btnBrowse.setOnAction(e -> {
                String fileName = (browse(txtFileName));
                String tokens = fileName.substring(0, fileName.length() - 4);
                txtObjectName.setText(fileName.substring(0, fileName.length() - 4));
                tblSaburiTools.getItems().clear();
            });
            btnImport.setOnAction(e -> {
                this.loadTable();
                String path = txtFileName.getText();
                File file = new File(path);
                String fileName = file.getName();
                String tokens = fileName.substring(0, fileName.length() - 4);
                txtObjectName.setText(fileName.substring(0, fileName.length() - 4));
                btnSave.visibleProperty().bind(Bindings.size(tblSaburiTools.getItems()).greaterThan(0));

            });

            btnSave.setOnAction(e -> {

                try {
                    String path = txtFileName.getText();

                    String contents = "";
                    int index = 0;
                    int size = tblSaburiTools.getItems().size();
                    for (Field field : tblSaburiTools.getItems()) {
                        index++;
                        contents += field.toString();
                        if (index < size) {
                            contents += "\n";
                        }
                    }
                    writeFile(path, contents);
                    message("Saved Successfully");
                } catch (Exception ex) {
                    errorMessage(ex);
                }

            });
            

            btnOutputDirectory.setOnAction(e -> FXUIUtils.browseDirectory(txtOutputDirectory));
            btnGenerate.setOnAction(e -> this.generateCode());
            btnClose.setOnAction(e -> this.close());
            setTableEditable(tblSaburiTools);
            editFieldName();
            editCaption();
            editReferences();
            editSubFields();
            editDataType();
            editMapping();
            editKey();
            editSaburiKey();
            editEnumClass();
            editSize();

            tbcNullable.setCellValueFactory((TableColumn.CellDataFeatures<Field, Boolean> param) -> {
                Field field = param.getValue();
                SimpleBooleanProperty p = field.getNullableProperty();
                p.addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
                    field.setNullable(newValue);
                });
                return p;
            });

            tbcEnumerated.setCellValueFactory((TableColumn.CellDataFeatures<Field, Boolean> param) -> {
                Field field = param.getValue();

                SimpleBooleanProperty p = field.getEnumeratedProperty();
                p.addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
                    field.setEnumerated(newValue);
                });
                return p;
            });

            this.tblSaburiTools.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            cboFiles.setItems(togenerateFiles);
            cboMenuType.setItems(menuTypes);
            cboMenuType.setValue(MenuTypes.SplitButton);
            txtParentMenuID.editableProperty().bindBidirectional(chkGenerateMenus.selectedProperty());

        } catch (Exception e) {
            errorMessage(e);
        }

    }

    private void close() {
        Stage stage = (Stage) btnClose.getScene().getWindow();
        stage.close();
    }

    private void loadTable() {
        try {
            tblSaburiTools.setItems(FXCollections.observableArrayList());
            tblSaburiTools.getItems().addAll(this.readFile(txtFileName.getText(), ","));

            tbcDataType.setCellFactory(ComboBoxTableCell.forTableColumn(dataTypes));
            tbcKey.setCellFactory(ComboBoxTableCell.forTableColumn(keyses));
            tbcSaburiKey.setCellFactory(ComboBoxTableCell.forTableColumn(saburiKeys));
            tbcMapping.setCellFactory(ComboBoxTableCell.forTableColumn(mapppings));
            tbcEnumClass.setCellFactory(ComboBoxTableCell.forTableColumn(enumClasses));

            final Callback<TableColumn<Field, Boolean>, TableCell<Field, Boolean>> cellFactory = CheckBoxTableCell.forTableColumn(tbcNullable);
            tbcNullable.setCellFactory((TableColumn<Field, Boolean> param) -> {
                TableCell<Field, Boolean> cell = cellFactory.call(param);
                cell.setAlignment(Pos.CENTER);
                return cell;
            });
            tbcNullable.setCellFactory(cellFactory);

            final Callback<TableColumn<Field, Boolean>, TableCell<Field, Boolean>> enumeratedCellFactory = CheckBoxTableCell.forTableColumn(tbcEnumerated);
            tbcEnumerated.setCellFactory((TableColumn<Field, Boolean> param) -> {
                TableCell<Field, Boolean> cell = enumeratedCellFactory.call(param);
                cell.setAlignment(Pos.CENTER);
                return cell;
            });
            tbcEnumerated.setCellFactory(enumeratedCellFactory);

        } catch (Exception e) {
            errorMessage(e);
        }

    }

    private ObservableList<Field> readFile(String fileName, String seperator) {

        ObservableList<Field> fields = FXCollections.observableArrayList();
        if (!(fileName.isEmpty())) {
            try {
                BufferedReader reader = new BufferedReader(new FileReader(fileName));
                String line;

                while ((line = reader.readLine()) != null) {
                    Field field = Utilities.getFields(line, seperator);
                    if (field != null) {
                        fields.add(field);
                    }
                }
            } catch (IOException e) {
                errorMessage(e);
            } catch (Exception e) {
                errorMessage(e);
            }
        }

        return fields;

    }

    private void editFieldName() {

        tbcFieldName.setCellFactory(EditCell.forTableColumn());

        tbcFieldName.setOnEditCommit(event -> {
            final String value = event.getNewValue() != null ? event.getNewValue()
                    : event.getOldValue();
            ((Field) event.getTableView().getItems()
                    .get(event.getTablePosition().getRow()))
                    .setFieldName(value);
            tblSaburiTools.refresh();
        });
    }

    private void editCaption() {

        tbcCaption.setCellFactory(EditCell.forTableColumn());

        tbcCaption.setOnEditCommit(event -> {
            final String value = event.getNewValue() != null ? event.getNewValue()
                    : event.getOldValue();
            ((Field) event.getTableView().getItems()
                    .get(event.getTablePosition().getRow()))
                    .setCaption(value);
            tblSaburiTools.refresh();
        });
    }

    private void editReferences() {

        tbcReferences.setCellFactory(EditCell.forTableColumn());

        tbcReferences.setOnEditCommit(event -> {
            final String value = event.getNewValue() != null ? event.getNewValue()
                    : event.getOldValue();
            ((Field) event.getTableView().getItems()
                    .get(event.getTablePosition().getRow()))
                    .setReferences(value);
            tblSaburiTools.refresh();
        });
    }

    private void editSubFields() {
        tbcSubFields.setCellFactory(EditCell.forTableColumn());
        tbcSubFields.setOnEditCommit(event -> {
            final String value = event.getNewValue() != null ? event.getNewValue()
                    : event.getOldValue();
            ((Field) event.getTableView().getItems()
                    .get(event.getTablePosition().getRow()))
                    .setSubFields(value);
            tblSaburiTools.refresh();
        });
    }

    private void editDataType() {

        tbcDataType.setOnEditCommit(event -> {
            final String value = event.getNewValue() != null ? event.getNewValue()
                    : event.getOldValue();
            ((Field) event.getTableView().getItems()
                    .get(event.getTablePosition().getRow()))
                    .setDataType(value);
            tblSaburiTools.refresh();
        });
    }

    private void editMapping() {

        tbcMapping.setOnEditCommit(event -> {
            final String value = event.getNewValue() != null ? event.getNewValue()
                    : event.getOldValue();
            ((Field) event.getTableView().getItems()
                    .get(event.getTablePosition().getRow()))
                    .setMapping(value);
            tblSaburiTools.refresh();
        });
    }

    private void editKey() {

        tbcKey.setOnEditCommit(event -> {
            final String value = event.getNewValue() != null ? event.getNewValue()
                    : event.getOldValue();
            ((Field) event.getTableView().getItems()
                    .get(event.getTablePosition().getRow()))
                    .setKey(value);
            tblSaburiTools.refresh();
        });
    }

    private void editSaburiKey() {

        tbcSaburiKey.setOnEditCommit(event -> {
            final String value = event.getNewValue() != null ? event.getNewValue()
                    : event.getOldValue();
            ((Field) event.getTableView().getItems()
                    .get(event.getTablePosition().getRow()))
                    .setSaburiKey(value);
            tblSaburiTools.refresh();
        });
    }
    
    private void editEnumClass() {

        tbcEnumClass.setOnEditCommit(event -> {
            final String value = event.getNewValue() != null ? event.getNewValue()
                    : event.getOldValue();
            ((Field) event.getTableView().getItems()
                    .get(event.getTablePosition().getRow()))
                    .setEnumClass(value);
            tblSaburiTools.refresh();
        });
    }

    private void editSize() {
        try {
            tbcSize.setCellFactory(EditCell.forIntTableColumn());
            tbcSize.setOnEditCommit(event -> {
                final int value = event.getNewValue() != null ? event.getNewValue()
                        : event.getOldValue();
                ((Field) event.getTableView().getItems()
                        .get(event.getTablePosition().getRow()))
                        .setSize(value);
                tblSaburiTools.refresh();
            });
        } catch (Exception e) {
            errorMessage(e);
        }
    }

//*************************************************************************************
//*************************************************************************************
//Generate code
    private void generateCode() {
        try {
            String objectName = txtObjectName.getText();
            String outPutDirecory = txtOutputDirectory.getText();

            if (isNullOrEmpty(objectName)) {
                message("Must enter Object Name!");
                return;
            }

            if (isNullOrEmpty(outPutDirecory)) {
                message("Must enter Out Put Direcory!");
                return;
            }

            List<Field> fields = tblSaburiTools.getItems();

            CodeGenerator codeGenerator = new CodeGenerator();
            codeGenerator.validate(fields);

            String entityFolder = outPutDirecory + "\\Entities";
            String controllerFolder = outPutDirecory + "\\Controllers";
            String fxmlFolder = outPutDirecory + "\\UIs";

            String dbAccessFolder = outPutDirecory + "\\DBAccess";
            String menusFolder = outPutDirecory + "\\Menus";
            String sqlFolder = outPutDirecory + "\\SQL";

            makeDirectory(entityFolder);
            makeDirectory(dbAccessFolder);
            makeDirectory(controllerFolder);
            makeDirectory(fxmlFolder);
            makeDirectory(dbAccessFolder);
            makeDirectory(menusFolder);
            makeDirectory(sqlFolder);

            String daObjectName = objectName + "DA";
            String entityFileName = entityFolder + "\\" + objectName + ".java";
            String daFileName = dbAccessFolder + "\\" + daObjectName + ".java";
            String fxmlFileName = fxmlFolder + "\\" + objectName + ".fxml";
            String fxmlTBFileName = fxmlFolder + "\\" + objectName + "View.fxml";
            String controllerFileName = controllerFolder + "\\" + objectName + "Controller.java";
            String vwcontrollerFileName = controllerFolder + "\\" + objectName + "ViewController.java";
            String menuFileName = menusFolder + "\\" + objectName + "Menu.txt";
            String sqlFileName = sqlFolder + "\\" + objectName + "SQLFile.txt";

            String entityFileContents = new Entity(objectName, fields).makeClass();
            String daFileContents = new DBAcess(objectName, fields).makeClass();
            String controllerFileContent = new Controller(objectName, fields).makeClass();
            String vwcontrollerFileContent = new ViewController(objectName).makeClass();
            String fxmlFileContent = new UIEdit(objectName, fields).create();
            String fxmlTBFileContent = new UIView(objectName, fields).create();
            String menuFIleContents = Menu.makeMenu(objectName, cboMenuType.getValue(), txtParentMenuID.getText());
            String sqlFileContents = SQLFile.callEditAccessObject(objectName);

            if (Utilities.hasHelper(fields)) {
                sqlFileContents += SQLFile.callEditIDGenerator(objectName);
            }

            List<Pair<String, String>> lstPairs = new ArrayList<>();

            Pair<String, String> fxlPair = new Pair(fxmlFileName, fxmlFileContent);
            Pair<String, String> fxlTBPair = new Pair(fxmlTBFileName, fxmlTBFileContent);
            Pair<String, String> controllerPair = new Pair(controllerFileName, controllerFileContent);
            Pair<String, String> vwcontrollerPair = new Pair(vwcontrollerFileName, vwcontrollerFileContent);
            Pair<String, String> daFilePair = new Pair(daFileName, daFileContents);
            Pair<String, String> entityPair = new Pair(entityFileName, entityFileContents);
            Pair<String, String> menuPair = new Pair(menuFileName, menuFIleContents);
            Pair<String, String> sqlPair = new Pair(sqlFileName, sqlFileContents);

            if (chkGenerateMenus.isSelected()) {
                menuFIleContents += "\n\n\n\n\n";
                menuFIleContents += "\n\n\n\n\n";
                lstPairs.add(new Pair(menuFileName, menuFIleContents));

            }

            if (chkGenerateViewUI.isSelected()) {
                lstPairs.add(fxlTBPair);
                lstPairs.add(vwcontrollerPair);

            }

//          Writing the created files
            Pair<String, String> pairs[];

            String toGenerateFile = "";
            if (cboFiles.getSelectionModel().getSelectedIndex() > -1) {
                toGenerateFile = cboFiles.getValue().toString();
            }

            switch (toGenerateFile) {
                case "Entity":
                    pairs = new Pair[]{entityPair};
                    break;
                case "DBAcess":
                    pairs = new Pair[]{daFilePair};
                    break;
                case "Controller":
                    pairs = new Pair[]{controllerPair};
                    break;

                case "View Controller":
                    pairs = new Pair[]{vwcontrollerPair};
                    break;
                case "FXML":
                    pairs = new Pair[]{fxlPair};
                    break;
                case "FXML View":
                    pairs = new Pair[]{fxlTBPair};
                    break;
                case "Menu":
                    pairs = new Pair[]{menuPair};
                    break;
                case "SQL":
                    pairs = new Pair[]{sqlPair};
                    break;
                default:
                    pairs = new Pair[]{entityPair, daFilePair, controllerPair, fxlPair, menuPair, sqlPair};

                    break;

            }

            lstPairs.addAll(Arrays.asList(pairs));
            lstPairs.forEach(a -> writeFile(a.getKey(), a.getValue()));

            if (!chkOpenFile.isSelected()) {
                message("Operation Successful");
                return;
            }

            lstPairs.forEach(a -> openFile(a.getKey()));

        } catch (Exception e) {
            errorMessage(e);
        }

    }

//    

}
