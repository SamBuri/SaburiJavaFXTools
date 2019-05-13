/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package saburijavafxtools;

import Model.Field;
import helpers.Utilities;
import static helpers.Utilities.addIfNotExists;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;

/**
 *
 * @author CLINICMASTER13
 */
public class Controller {

    private final String objectName;
    private final List<Field> fields;
    private final String objectNameDA;
    private final String objectNameController;
    private final String primaryKeyVariableName;
    private final String daGlobalVariable;
    private final String daVariableName;
    private final Field primaryKey;
    FilteredList<Field> subListFields;

    public Controller(String objectName, List<Field> fields) {
        this.objectName = objectName;
        this.fields = fields;
        this.objectNameDA = objectName.concat("DA");
        this.objectNameController = objectName.concat("Controller");
        this.primaryKeyVariableName = Utilities.getPrimaryKey(fields).getVariableName();
        this.daGlobalVariable = "o".concat(objectNameDA);
        this.daVariableName = Utilities.getVariableName(objectNameDA);
        this.primaryKey = Utilities.getPrimaryKey(fields);
        subListFields = new FilteredList<>(FXCollections.observableList(fields), e -> true);
        subListFields.setPredicate(FieldPredicates.hasSubFields());
    }

    private String imports() {
        String imp = "import java.net.URL;\n"
                + "import java.util.ResourceBundle;\n"
                + "import javafx.fxml.FXML;\n"
                + "import static helpers.FXUIUtils.*;\n"
                + "import helpers.Utilities.FormMode;\n"
                + "import static helpers.FXUIUtils.warningOk;\n"
                + "import dbaccess." + objectNameDA + ";\n";

        List<String> imports = new ArrayList();
        this.fields.forEach((t) -> {
            t.ControllerImports().forEach(i -> addIfNotExists(imports, i));

        });
        for (String impo : imports) {
            imp += impo + ";\n";
        }
        return imp;

    }

    private String annotedControllers() throws Exception {

        String properties = "";
        properties += " private final " + objectNameDA + " " + daGlobalVariable + " = new " + objectNameDA + "();\n";

        for (Field field : fields) {
            if (!field.isHelper()) {
                if (field.isCollection()) {
                    properties += "@FXML private " + field.getControlType().name() + "<" + field.getReferences() + "DA> " + field.getControlName() + ";\n";
                    if (field.makeEditableTable()) {
                        properties += "@FXML private MenuItem cmiSelect" + field.getFieldName() + ";\n";
                    }
                } else {
                    properties += "@FXML private " + field.getControlType().name() + " " + field.getControlName() + ";\n";
                    properties += field.annotedImageButtons();
                    if (field.isReferance() && !field.getEnumerated()) {
                        properties += "@FXML private MenuItem cmiSelect" + field.getFieldName() + ";\n";
                    }
                }
            }
        }

        String tableColumns = "";
        for (Field field : subListFields) {

            String custom = field.getReferences();
            List<Field> subFields = field.getSubFieldList();

            for (Field subField : subFields) {
                tableColumns += "@FXML private TableColumn<" + custom + "DA, " + subField.getDataTypeWrapper() + "> " + subField.getColumnName(custom) + ";\n";

            }

        }
        return properties + tableColumns;
    }

    public String setControlIDInInitialiser() {
        Field idGeneratorObject = Utilities.getIDGenerator(fields);
        Field IDHelperObject = Utilities.getIDHelper(fields);
        if (IDHelperObject == null) {
            return "";
        }
        if (idGeneratorObject == null) {
            return "this.setNext" + primaryKey.getFieldName() + "();\n";
        } else {

            if (idGeneratorObject.isReferance()) {
                return "cbo" + idGeneratorObject.getFieldName() + ".setOnAction(e -> this.setNext" + primaryKey.getFieldName() + "());";
            } else {
                return idGeneratorObject.getControlName() + ".focusedProperty().addListener((observable, oldValue, newValue) -> {\n"
                        + "                if(oldValue){\n"
                        + "                    this.setNext" + primaryKey.getFieldName() + "();\n"
                        + "                }\n"
                        + "            });\n";
            }

        }
    }

    private String initMethod() throws Exception {
        String initProperties = "this.primaryKeyControl = " + primaryKey.getControlName() + ";\n"
                + "          this.dbAccess = " + daGlobalVariable + ";\n"
                + "          this.restrainColumnConstraint = false;\n"
                + "this.minSize = 360;\n";
        String lookupDataLoadings = "";
        String comboLoadings = "";
        String numberValidator = "";
        String imageButtonActions = "";
        String editableTable = "";
        String menuLoadCalls = "";
        for (Field field : fields) {

            if (field.isReferance() && !field.isCollection()) {

                if (field.getEnumerated()) {
                    comboLoadings += field.getControlName() + ".setItems(FXCollections.observableArrayList(" + field.getReferences() + ".values()));";
                } else {
                    menuLoadCalls += " cmiSelect" + field.getFieldName() + ".setOnAction(e->{\n"
                            + "                try {\n"
                            + "                    getSelectedItem(new " + field.getReferencesDA() + "(),\"View\", \"" + field.getCaption() + "\", 400, 450, " + field.getControlName() + ",false);\n"
                            + "                } catch (IOException ex) {\n"
                            + "                    errorMessage(ex);\n"
                            + "                }\n"
                            + "            });";
                    if (field.getReferences().equalsIgnoreCase("LookupData")) {

                        lookupDataLoadings += " loadLookupDataCombo(" + field.getControlName() + ", ObjectNames." + field.getFieldName().toUpperCase() + ");\n";
                    } else {
                        comboLoadings += "loadDaCombo(new " + field.getReferences() + "DA().get(), " + field.getControlName() + ");\n";

                    }

                }

            } else {
                if (field.makeEditableTable()) {
                    editableTable += field.getControlName() + ".setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);";
                    editableTable += "setTableEditable(" + field.getControlName() + ");";
                    editableTable += "addRow(" + field.getControlName() + ", new " + field.getReferencesDA() + "());";
                    editableTable += "cmiSelect" + field.getFieldName() + ".setOnAction(e->load" + field.getFieldName() + "());";

                }

            }
            imageButtonActions += field.ImageButtonsActions();
            numberValidator += field.NumberValidator();
        }

        String methodBody = lookupDataLoadings + comboLoadings + "\n"
                + numberValidator + editableTable;

        String editColumnMethodCall = "";
        for (Field field : subListFields) {
            List<Field> subFields = field.getSubFieldList();

            for (Field subField : subFields) {
                editColumnMethodCall += "set" + field.getReferences() + subField.getFieldName() + "();\n";
            }
        }

        methodBody += initProperties + imageButtonActions + setControlIDInInitialiser() + editColumnMethodCall + menuLoadCalls;
        return " @Override\n" + Utilities.makeTryMethod("public", "void", "initialize", "URL url, ResourceBundle rb", methodBody);
    }

    private String save() {
        String makeInitials = "";
        String saveVariables = "";
        for (int i = 0; i < fields.size(); i++) {
            Field field = this.fields.get(i);
            if (!field.isHelper()) {
                if (field.isCollection()) {
                    makeInitials += field.getReferencesDA() + ".get" + field.getReferences() + "List(" + field.getVariableName() + ")";
                } else {

                    makeInitials += field.getVariableName();
                    if (field.isReferance() && !field.getEnumerated()) {
                        makeInitials += "DA";
                    }

                }
                saveVariables += field.initialseSavableVariable();

            }

            if (i < fields.size() - 1) {
                if (!field.isHelper()) {
                    makeInitials += ",";
                }
            }
        }
        String body = saveVariables.concat("\n");
        body += objectNameDA + " " + daVariableName + "= new " + objectNameDA + "(" + makeInitials + ");\n";
        body += "String buttonText =btnSave.getText();\n"
                + "if (buttonText.equalsIgnoreCase(FormMode.Save.name())) {\n"
                + "                " + daVariableName + ".save();\n"
                + "                message(\"Saved Successfully\");\n"
                + "                clear();\n"
                + "            } else if (buttonText.equalsIgnoreCase(FormMode.Update.name())) {\n"
                + "                " + daVariableName + ".update();\n"
                + "                message(\"Updated Successfully\");\n"
                + "            }\n";

        return Utilities.makeTryMethod("@Override\nprotected", "void", "save", "", body);
    }

    private String loadData() {
        String updateBody = "";
        for (Field field : fields) {
            if (field.isCollection()) {
                updateBody += field.getControlName() + ".setItems(FXCollections.observableArrayList(" + daVariableName + ".get" + field.getFieldName() + "DAs()));";
            } else if (field.isReferance() && !field.getEnumerated()) {
                updateBody += field.setControlText(daVariableName.concat(".get" + field.getFieldName() + "DA()"));
            } else {
                updateBody += field.setControlText(daVariableName.concat(".").concat(field.getCall()));
            }
        }
        String updateMethod = "@Override\npublic void loadData() {\n"
                + "        try {\n"
                + primaryKey.initialseSavableVariable() + "\n"
                + objectNameDA + " " + daVariableName + " = " + daGlobalVariable + " .get(" + primaryKeyVariableName + ");\n"
                + updateBody + "\n"
                + "        } catch (Exception e) {\n"
                + "            errorMessage(e);\n"
                + "        }\n"
                + "\n"
                + "    }\n"
                + "";

        return updateMethod;

    }

    private String clear() {
        String clear = "";
        for (Field field : fields) {
            clear += field.clearLine();
        }
        clear += setControlIDInInitialiser();
        return Utilities.makeMethod("private", "void", "clear", "", clear);
    }

    private String getSetIDControl() throws Exception {

        String body;
        Field idHelperObject = Utilities.getIDHelper(fields);
        Field idGeneratorObject = Utilities.getIDGenerator(fields);
        if (idGeneratorObject != null && idHelperObject == null) {
            throw new Exception("The ID generator does not have a helper column");
        }
        if (idGeneratorObject == null && idHelperObject == null) {
            return "";
        }
        if (idGeneratorObject == null) {
            body = primaryKey.getControlName() + ".setText(" + daGlobalVariable + ".getNext" + primaryKey.getFieldName() + ""
                    + "(" + daGlobalVariable + ".getNext" + idHelperObject.getFieldName() + "()));\n";
        } else {

            String idGeneratorVariableName = idGeneratorObject.getVariableName();
            String references = idGeneratorObject.getReferences();
            if (idGeneratorObject.isReferance()) {

                if (idGeneratorObject.getEnumerated()) {
                    body = references + " " + idGeneratorVariableName + " = (" + references + ") "
                            + "cbo" + idGeneratorObject.getFieldName() + ".getValue();\n"
                            + "        if (" + idGeneratorVariableName + " == null) {\n"
                            + "            return;\n"
                            + "        }\n"
                            + primaryKey.getControlName() + ".setText(" + daGlobalVariable + ".getNext" + primaryKey.getFieldName() + ""
                            + "(" + daGlobalVariable + ".getNext" + idHelperObject.getFieldName() + "(" + idGeneratorVariableName + "), " + idGeneratorVariableName + "));\n";

                } else {
                    body = "String " + idGeneratorVariableName + " = "
                            + "getTextValue(cbo" + idGeneratorObject.getFieldName() + ");\n"
                            + "        if (isNullOrEmpty(" + idGeneratorVariableName + ")) {\n"
                            + "            return;\n"
                            + "        }\n"
                            + idGeneratorObject.initialseSavableVariable() + ""
                            + primaryKey.getControlName() + ".setText(" + daGlobalVariable + ".getNext" + primaryKey.getFieldName() + ""
                            + "(" + daGlobalVariable + ".getNext" + idHelperObject.getFieldName() + "(" + idGeneratorVariableName + "DA), " + idGeneratorVariableName + "));\n";
                }

            } else {
                body = "String " + idGeneratorVariableName + " = "
                        + idGeneratorObject.getControlName() + ".getText();\n"
                        + "        if (isNullOrEmpty(" + idGeneratorVariableName + ")) {\n"
                        + "            return;\n"
                        + "        }\n"
                        + primaryKey.getControlName() + ".setText(" + daGlobalVariable + ".getNext" + primaryKey.getFieldName() + ""
                        + "(" + daGlobalVariable + ".getNext" + idHelperObject.getFieldName() + "(" + idGeneratorVariableName + "), " + idGeneratorVariableName + "));\n";

            }

        }

        String genCondition = "if(btnSave.getText ().equalsIgnoreCase(FormMode.Save.name())){\n";
        String bBody = genCondition + body.concat("}");
        String tryBody = "";
        String methodName = "setNext".concat(primaryKey.getFieldName());
        if (!bBody.equals("")) {
            tryBody = "try{" + bBody + "}catch(Exception e){errorMessage(e);}";
            bBody = tryBody;

        }

        return Utilities.makeMethod("private", "void", methodName, "", bBody);

    }

    private String primaryKeyClear() {
        if (primaryKey.isReferance()) {
            return primaryKey.getControlName().concat(".setValue(null);");
        }
        return primaryKey.getControlName().concat(".clear();");
    }

    private String methods() throws Exception {

        String cDelete = "@Override\nprotected void delete(){\n"
                + "        try {\n"
                + Utilities.getPrimaryKey(fields).initialseSavableVariable()
                + objectNameDA + " " + daVariableName + " = " + daGlobalVariable + " .get(" + primaryKeyVariableName + ");\n"
                + " if(!warningOk(\"Confirm Delete\", \"You are about to delete a record with ID: \"+" + primaryKeyVariableName + "+\" Are you sure you want to continue?\", \"Remember this action cannot be un done\"))return;\n"
                + "            if(" + daVariableName + ".delete()){\n"
                + "                message(\"Deleted Successfully\");\n"
                + "                this.clear();\n"
                + "            }\n"
                + "        } catch (Exception e) {\n"
                + "            errorMessage(e);\n"
                + "        }\n"
                + "        }\n";

        String editColumn = "";
        for (Field field : subListFields) {
            List<Field> subFields = field.getSubFieldList();

            for (Field subField : subFields) {
                editColumn += subField.editTableColumnMethod(field);
            }
        }

        String loadMethods = "";
        loadMethods = subListFields.stream().map((field) -> field.makeLoadCollections()).reduce(loadMethods, String::concat);
        return initMethod() + save() + cDelete + loadData() + getSetIDControl() + editColumn + loadMethods + clear();

    }

    public String makeClass() throws Exception {
        JavaClass javaClass = new JavaClass("controllers", objectNameController, this.imports(),
                this.annotedControllers(), "", "", methods());
        return javaClass.makeClass("EditController");
    }

}
