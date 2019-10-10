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
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
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
                + "import static utils.FXUIUtils.*;\n"
                + "import utils.Utilities.FormMode;\n"
                + "import static utils.FXUIUtils.warningOk;\n"
                + "import dbaccess." + objectNameDA + ";\n";

        List<String> imports = new ArrayList();
        this.fields.forEach((t) -> {
            try {
                t.ControllerImports(this.objectName).forEach(i -> addIfNotExists(imports, i));
            } catch (Exception ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            }

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
                if (subField.isReferance()) {
                    tableColumns += subField.getReferencesDA() + " o" + subField.getReferencesDA() + " = new " + subField.getReferencesDA() + "();\n";
                }
                String type = subField.getDataType();
                if (type.equalsIgnoreCase("float") || type.equalsIgnoreCase("double")) {
                    tableColumns += "@FXML private TableColumn<" + custom + "DA, String> " + subField.getColumnName(custom) + ";\n";
                } else {
                    tableColumns += "@FXML private TableColumn<" + custom + "DA, " + subField.getDataTypeWrapper() + "> " + subField.getColumnName(custom) + ";\n";
                }

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
        String numberFormator = "";
        String imageButtonActions = "";
        String editableTable = "";
        String menuLoadCalls = "";
        for (Field field : fields) {

            if (field.isReferance() && !field.isCollection()) {

                if (field.getEnumerated()) {
                    comboLoadings += field.getControlName() + ".setItems(FXCollections.observableArrayList(" + field.getReferences() + ".values()));";
                } else {

                    if (field.getReferences().equalsIgnoreCase("LookupData")) {
                        if (field.getEnumClass().equalsIgnoreCase("CommonEnums")) {
                            lookupDataLoadings += " loadLookupDataCombo(" + field.getControlName() + ", CommonObjectNames." + field.getFieldName().toUpperCase() + ");\n";
                            menuLoadCalls += "selectLookupData(cmiSelect" + field.getFieldName() + ", CommonObjectNames." + field.getFieldName().toUpperCase() + ", \"view\", \"" + field.getFieldName() + "\", 700, 400, " + field.getControlName() + ", false);";
                        } else {
                            lookupDataLoadings += " loadLookupDataCombo(" + field.getControlName() + ", ObjectNames." + field.getFieldName().toUpperCase() + ");\n";
                            menuLoadCalls += "selectLookupData(cmiSelect" + field.getFieldName() + ", ObjectNames." + field.getFieldName().toUpperCase() + ", \"view\", \"" + field.getFieldName() + "\", 700, 400, " + field.getControlName() + ", false);";
                        }
                    } else {
                        comboLoadings += "loadDaCombo(new " + field.getReferences() + "DA().get(), " + field.getControlName() + ");\n";
                        menuLoadCalls += "selectItem(cmiSelect" + field.getFieldName() + ", new " + field.getReferences() + "DA(), \"View\", \"" + field.getFieldName() + "\", 700, 400, " + field.getControlName() + ", true);";

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
            numberFormator += field.NumberFormatter();
        }

        String methodBody = lookupDataLoadings + comboLoadings + "\n"
                + numberValidator + numberFormator + editableTable;

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

        List<Field> conField = fields.stream()
                .filter((p) -> !p.isCollection())
                .filter((p) -> !p.isHelper())
                .collect(Collectors.toList());
        for (int i = 0; i < conField.size(); i++) {
            Field field = conField.get(i);

            makeInitials += field.getVariableName();
            if (field.isReferance() && !field.getEnumerated()) {
                makeInitials += "DA";
            }
            if (i < conField.size() - 1) {
                makeInitials += ",";
            }
        }

        for (int i = 0; i < fields.size(); i++) {
            Field field = this.fields.get(i);
            if (!field.isHelper()) {

                saveVariables += field.initialseSavableVariable();

            }

        }
        String body = saveVariables.concat("\n");
        body += objectNameDA + " " + daVariableName + "= new " + objectNameDA + "(" + makeInitials + ");\n";

        if (!subListFields.isEmpty()) {
            for (Field field : fields) {
                if (field.isCollection()) {
                    body += field.getVariableName() + "DAs.forEach(e->{\n"
                            + "                e.set" + objectName + "((" + objectName + ") " + daVariableName + ".getDBEntity());\n"
                            + "            });\n"
                            + "            " + daVariableName + ".set" + field.getFieldName() + "DAs(" + field.getVariableName() + "DAs);";
                }

            }
        }

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
                updateBody += "addRow(" + field.getControlName() + ", new " + field.getReferencesDA() + "());\n";

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
