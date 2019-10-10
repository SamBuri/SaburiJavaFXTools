/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import helpers.Enums.FXControls;
import helpers.Enums.Saburikeys;
import helpers.Enums.keys;
import helpers.Utilities;
import static helpers.Utilities.addIfNotExists;
import static helpers.Utilities.isNullOrEmpty;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.*;

/**
 *
 * @author ClinicMaster13
 */
public class Field {

    private SimpleStringProperty fieldName = new SimpleStringProperty(this, "fieldName", "");
    private SimpleStringProperty caption = new SimpleStringProperty(this, "caption", "");
    private SimpleStringProperty dataType = new SimpleStringProperty(this, "dataType", "");
    private SimpleStringProperty references = new SimpleStringProperty(this, "references", "");
    private SimpleStringProperty mapping = new SimpleStringProperty(this, "mapping", "");
    private SimpleStringProperty key = new SimpleStringProperty(this, "key", "");
    private SimpleStringProperty saburiKey = new SimpleStringProperty(this, "saburiKey", "");
    private SimpleIntegerProperty size = new SimpleIntegerProperty(this, "size", 100);
    private SimpleBooleanProperty nullable = new SimpleBooleanProperty(this, "nullable", true);
    private SimpleBooleanProperty enumerated = new SimpleBooleanProperty(this, "enumerated", false);
    private SimpleStringProperty subFields = new SimpleStringProperty(this, "subFields", "");
    private SimpleStringProperty enumClass = new SimpleStringProperty(this, "enumClass", "");

    private String variableName;
    private String referencesID;
    private String display;
    private String referencesVariableID;
    private String displayVariableName;
    private final String displayDataType = "String";

    public Field() {
    }

    public Field(String fieldName, String caption, String dataType) {
        this.fieldName = new SimpleStringProperty(fieldName);
        this.caption = new SimpleStringProperty(caption);
        this.dataType = new SimpleStringProperty(dataType);
        this.enumerated = new SimpleBooleanProperty(false);
        this.nullable = new SimpleBooleanProperty(true);
        this.variableName = Utilities.getVariableName(fieldName);
        this.referencesID = this.fieldName.get().concat("ID");
        this.display = this.fieldName.get().concat("Display");
        this.referencesVariableID = Utilities.getVariableName(referencesID);
        this.displayVariableName = Utilities.getVariableName(display);

    }

    public Field(String fieldName, String caption, String dataType, String references) {
        this.fieldName = new SimpleStringProperty(fieldName);
        this.caption = new SimpleStringProperty(caption);
        this.dataType = new SimpleStringProperty(dataType);
        this.references = new SimpleStringProperty(references);
        this.variableName = Utilities.getVariableName(fieldName);
        this.enumerated = new SimpleBooleanProperty(false);
        this.nullable = new SimpleBooleanProperty(true);
        this.referencesID = this.fieldName.get().concat("ID");
        this.display = this.fieldName.get().concat("Display");
        this.referencesVariableID = Utilities.getVariableName(referencesID);
        this.displayVariableName = Utilities.getVariableName(display);

    }

    public Field(String fieldName, String caption, String dataType, String references, String subFields) {
        this.fieldName = new SimpleStringProperty(fieldName);
        this.caption = new SimpleStringProperty(caption);
        this.dataType = new SimpleStringProperty(dataType);
        this.subFields = new SimpleStringProperty(subFields);
        this.references = new SimpleStringProperty(references);
        this.enumerated = new SimpleBooleanProperty(false);
        this.nullable = new SimpleBooleanProperty(true);
        this.variableName = Utilities.getVariableName(fieldName);
        this.referencesID = this.fieldName.get().concat("ID");
        this.display = this.fieldName.get().concat("Display");
        this.referencesVariableID = Utilities.getVariableName(referencesID);
        this.displayVariableName = Utilities.getVariableName(display);

    }

    public Field(String fieldName, String caption, String dataType, String references, String subFields,
            String mapping) {
        this.fieldName = new SimpleStringProperty(fieldName);
        this.caption = new SimpleStringProperty(caption);
        this.dataType = new SimpleStringProperty(dataType);
        this.references = new SimpleStringProperty(references);
        this.subFields = new SimpleStringProperty(subFields);
        this.mapping = new SimpleStringProperty(mapping);
        this.enumerated = new SimpleBooleanProperty(false);
        this.nullable = new SimpleBooleanProperty(true);
        this.variableName = Utilities.getVariableName(fieldName);
        this.referencesID = this.fieldName.get().concat("ID");
        this.display = this.fieldName.get().concat("Display");
        this.referencesVariableID = Utilities.getVariableName(referencesID);
        this.displayVariableName = Utilities.getVariableName(display);

    }

    public Field(String fieldName, String caption, String dataType, String references, String subFields,
            String mapping, String key) {
        this.fieldName = new SimpleStringProperty(fieldName);
        this.caption = new SimpleStringProperty(caption);
        this.dataType = new SimpleStringProperty(dataType);
        this.references = new SimpleStringProperty(references);
        this.subFields = new SimpleStringProperty(subFields);
        this.mapping = new SimpleStringProperty(mapping);
        this.key = new SimpleStringProperty(key);
        this.enumerated = new SimpleBooleanProperty(false);
        this.nullable = new SimpleBooleanProperty(true);
        this.variableName = Utilities.getVariableName(fieldName);
        this.referencesID = this.fieldName.get().concat("ID");
        this.display = this.fieldName.get().concat("Display");
        this.referencesVariableID = Utilities.getVariableName(referencesID);
        this.displayVariableName = Utilities.getVariableName(display);

    }

    public Field(String fieldName, String caption, String dataType, String references, String subFields,
            String mapping, String key, String saburiKey) {
        this.fieldName = new SimpleStringProperty(fieldName);
        this.caption = new SimpleStringProperty(caption);
        this.dataType = new SimpleStringProperty(dataType);
        this.references = new SimpleStringProperty(references);
        this.subFields = new SimpleStringProperty(subFields);
        this.mapping = new SimpleStringProperty(mapping);
        this.key = new SimpleStringProperty(key);
        this.saburiKey = new SimpleStringProperty(saburiKey);
        this.enumerated = new SimpleBooleanProperty(false);
        this.nullable = new SimpleBooleanProperty(true);
        this.variableName = Utilities.getVariableName(fieldName);
        this.referencesID = this.fieldName.get().concat("ID");
        this.display = this.fieldName.get().concat("Display");
        this.referencesVariableID = Utilities.getVariableName(referencesID);
        this.displayVariableName = Utilities.getVariableName(display);

    }

    public Field(String fieldName, String caption, String dataType, String references, String subFields,
            String mapping, String key, String saburiKey, int size) {
        this.fieldName = new SimpleStringProperty(fieldName);
        this.caption = new SimpleStringProperty(caption);
        this.dataType = new SimpleStringProperty(dataType);
        this.references = new SimpleStringProperty(references);
        this.subFields = new SimpleStringProperty(subFields);
        this.mapping = new SimpleStringProperty(mapping);
        this.key = new SimpleStringProperty(key);
        this.saburiKey = new SimpleStringProperty(saburiKey);
        this.size = new SimpleIntegerProperty(size);
        this.enumerated = new SimpleBooleanProperty(false);
        this.nullable = new SimpleBooleanProperty(true);
        this.variableName = Utilities.getVariableName(fieldName);
        this.referencesID = this.fieldName.get().concat("ID");
        this.display = this.fieldName.get().concat("Display");
        this.referencesVariableID = Utilities.getVariableName(referencesID);
        this.displayVariableName = Utilities.getVariableName(display);

    }

    public Field(String fieldName, String caption, String dataType, String references, String subFields,
            String mapping, String key, String saburiKey, int size, boolean emumerated) {
        this.fieldName = new SimpleStringProperty(fieldName);
        this.caption = new SimpleStringProperty(caption);
        this.dataType = new SimpleStringProperty(dataType);
        this.references = new SimpleStringProperty(references);
        this.subFields = new SimpleStringProperty(subFields);
        this.mapping = new SimpleStringProperty(mapping);
        this.key = new SimpleStringProperty(key);
        this.saburiKey = new SimpleStringProperty(saburiKey);
        this.size = new SimpleIntegerProperty(size);
        this.enumerated = new SimpleBooleanProperty(emumerated);
        this.nullable = new SimpleBooleanProperty(true);
        this.variableName = Utilities.getVariableName(fieldName);
        this.referencesID = this.fieldName.get().concat("ID");
        this.display = this.fieldName.get().concat("Display");
        this.referencesVariableID = Utilities.getVariableName(referencesID);
        this.displayVariableName = Utilities.getVariableName(display);

    }

    public Field(String fieldName, String caption, String dataType, String references, String subFields,
            String mapping, String key, String saburiKey, int size, boolean emumerated, boolean nullable) {
        this.fieldName = new SimpleStringProperty(fieldName);
        this.caption = new SimpleStringProperty(caption);
        this.dataType = new SimpleStringProperty(dataType);
        this.references = new SimpleStringProperty(references);
        this.subFields = new SimpleStringProperty(subFields);
        this.mapping = new SimpleStringProperty(mapping);
        this.key = new SimpleStringProperty(key);
        this.saburiKey = new SimpleStringProperty(saburiKey);
        this.size = new SimpleIntegerProperty(size);
        this.enumerated = new SimpleBooleanProperty(emumerated);
        this.nullable = new SimpleBooleanProperty(nullable);
        this.variableName = Utilities.getVariableName(fieldName);
        this.referencesID = this.fieldName.get().concat("ID");
        this.display = this.fieldName.get().concat("Display");
        this.referencesVariableID = Utilities.getVariableName(referencesID);
        this.displayVariableName = Utilities.getVariableName(display);

    }

    public Field(String fieldName, String caption, String dataType, String references, String subFields,
            String mapping, String key, String saburiKey, int size, boolean emumerated, boolean nullable, String enumClass) {
        this.fieldName = new SimpleStringProperty(fieldName);
        this.caption = new SimpleStringProperty(caption);
        this.dataType = new SimpleStringProperty(dataType);
        this.references = new SimpleStringProperty(references);
        this.subFields = new SimpleStringProperty(subFields);
        this.mapping = new SimpleStringProperty(mapping);
        this.key = new SimpleStringProperty(key);
        this.saburiKey = new SimpleStringProperty(saburiKey);
        this.size = new SimpleIntegerProperty(size);
        this.enumerated = new SimpleBooleanProperty(emumerated);
        this.enumClass = new SimpleStringProperty(enumClass);;
        this.nullable = new SimpleBooleanProperty(nullable);
        this.variableName = Utilities.getVariableName(fieldName);
        this.referencesID = this.fieldName.get().concat("ID");
        this.display = this.fieldName.get().concat("Display");
        this.referencesVariableID = Utilities.getVariableName(referencesID);
        this.displayVariableName = Utilities.getVariableName(display);

    }

    public void setFieldName(String fieldName) {
        this.fieldName.set(fieldName);
    }

    public void setCaption(String caption) {
        this.caption.set(caption);
    }

    public void setDataType(String dataType) {
        this.dataType.set(dataType);
    }

    public void setReferences(String references) {
        this.references.set(references);
    }

    public void setMapping(String mapping) {
        this.mapping.set(mapping);
    }

    public void setKey(String key) {
        this.key.set(key);
    }

    public void setSaburiKey(String saburiKey) {
        this.saburiKey.set(saburiKey);
    }

    public void setSubFields(String subFields) {
        this.subFields.set(subFields);
    }

    public void setSize(int size) {
        this.size.set(size);
    }

    public void setNullable(boolean nullable) {
        this.nullable.set(nullable);
    }

    public void setEnumerated(boolean enumerated) {
        this.enumerated.set(enumerated);
    }

    public String getFieldName() {
        return fieldName.get();
    }

    public String getCaption() {
        return caption.get();
    }

    public String getDataType() {
        return dataType.get();
    }

    public String getReferences() {
        return references.get();
    }

    public String getMapping() {
        return mapping.get();
    }

    public String getKey() {
        return key.get();
    }

    public String getSaburiKey() {
        return saburiKey.get();
    }

    public String getSubFields() {
        return subFields.get();
    }

    public int getSize() {
        return size.get();
    }

    public boolean getNullable() {
        return nullable.get();
    }

    public boolean getEnumerated() {
        return enumerated.get();
    }

    public String getEnumClass() {
        return enumClass.get();
    }

    public void setEnumClass(String enumClass) {
        this.enumClass.set(enumClass);
    }

    public SimpleBooleanProperty getNullableProperty() {
        return this.nullable;
    }

    public SimpleBooleanProperty getEnumeratedProperty() {
        return this.enumerated;
    }

    public String getVariableName() {
        return variableName;
    }

    public String getReferencesID() {
        return referencesID;
    }

    public String getReferencesVariableID() {
        return referencesVariableID;
    }

    public String getDisplay() {
        return display;
    }

    public String getDisplayVariableName() {
        return displayVariableName;
    }

    public String getDisplayDataType() {
        return displayDataType;
    }

    public String getColumnName(String custom) {
        if (isReferance()) {
            return "tbc" + custom + getFieldName() + "ID";
        } else {
            return "tbc" + custom + getFieldName();
        }
    }

    public String getColumnName() {
        return "tbc" + getFieldName();
    }

    public boolean hasDisplay() {
        return this.getDataType().equalsIgnoreCase("Double") || this.getDataType().equalsIgnoreCase("float")
                || this.getDataType().equalsIgnoreCase("Date") || this.getDataType().equalsIgnoreCase("DateTime")
                || this.getDataType().equalsIgnoreCase("LocalDate") || this.getDataType().equalsIgnoreCase("LocalDateTime");
    }

    public boolean isCollection() {
        return this.getDataType().equalsIgnoreCase("List") || this.getDataType().equalsIgnoreCase("Set");
    }

    public boolean makeEditableTable() {
        return !isNullOrEmpty(getSubFields());
    }

    @Override
    public String toString() {
        return fieldName.get() + ", " + caption.get() + ", " + dataType.get() + ", " + references.get() + "," + subFields.get() + ", " + mapping.get() + ", " + key.get() + ", " + saburiKey.get() + ", " + size.get() + ", " + enumerated.get() + ", " + nullable.get() + ", " + enumClass.get();
    }

    public boolean isReferance() {
        return !(isNullOrEmpty(this.getReferences()) || this.getReferences().equalsIgnoreCase("None"));
    }

    public boolean isPrimaryKey() {
        return this.getKey().equalsIgnoreCase(keys.Primary.name());
    }

    public boolean isHelper() {
        return this.getSaburiKey().equalsIgnoreCase(Saburikeys.ID_Helper.name());
    }

    public boolean isIDGenerator() {
        return this.getSaburiKey().equalsIgnoreCase(Saburikeys.ID_Generator.name());
    }

    public boolean isDisplayKey() {
        return this.getSaburiKey().equalsIgnoreCase(Saburikeys.Display.name());
    }

    public String getDeclaration(boolean forceReferences, boolean newLine) {
        if (newLine) {

            if (isCollection()) {
                if (getDataType().equalsIgnoreCase("List")) {
                    return "List<" + getReferences() + "> " + getVariableName() + " = new ArrayList<>();\n";
                } else if (getDataType().equalsIgnoreCase("Set")) {
                    return "Set<" + getReferences() + "> " + getVariableName() + " = new HashSet<>();\n";
                } else {
                    return "";
                }
            } else {
                return this.getUsableDataType(forceReferences).concat(" ").concat(variableName).concat(";\n");
            }
        }
        return this.getUsableDataType(forceReferences).concat(" ").concat(variableName);

    }

    public String getReferencesDA() {
        if (!isReferance()) {
            return "";
        }
        return getReferences().concat("DA");
    }

    public String getVariableNameDA() {

        return getVariableName().concat("DA");
    }

    public String getSearchDataType() {
        String type = this.dataType.get();
        if (type.equalsIgnoreCase("String")) {
            return "SearchDataTypes.STRING";
        } else if (type.equalsIgnoreCase("int") || type.equalsIgnoreCase("Integer") || type.equalsIgnoreCase("float") || type.equalsIgnoreCase("double")) {
            return "SearchDataTypes.NUMBER";
        } else if (type.equalsIgnoreCase("Date") || type.equalsIgnoreCase("Time")) {
            return "SearchDataTypes.DATE";
        } else if (type.equalsIgnoreCase("bool") || type.equalsIgnoreCase("Boolean")) {
            return "SearchDataTypes.BOOLEAN";
        } else {
            return "SearchDataTypes.STRING";
        }

    }

    public String getDataTypeWrapper() {
        String type = this.dataType.get();
        if (type.equalsIgnoreCase("String")) {
            return "String";
        } else if (type.equalsIgnoreCase("int")) {
            return "Integer";
        } else if (type.equalsIgnoreCase("float")) {
            return "Float";
        } else if (type.equalsIgnoreCase("double")) {
            return "Double";
        } else if (type.equalsIgnoreCase("Date")) {
            return "Date";
        } else if (type.equalsIgnoreCase("LocalDate")) {
            return "LocalDate";
        } else if (type.equalsIgnoreCase("bool") || type.equalsIgnoreCase("Boolean")) {
            return "Boolean";
        } else {
            return "String";
        }

    }

    public FXControls getControlType() {
        if (isReferance() && !this.isCollection()) {
            return FXControls.ComboBox;
        }

        if (this.getDataType().equalsIgnoreCase("Date") || this.getDataType().equalsIgnoreCase("DateTime")
                || this.getDataType().equalsIgnoreCase("LocalDate") || this.getDataType().equalsIgnoreCase("LocalDateTime")) {
            return FXControls.DatePicker;
        }

        if (this.getDataType().equalsIgnoreCase("bool") || this.getDataType().equalsIgnoreCase("boolean")) {
            return FXControls.CheckBox;
        }

        if (this.getDataType().equalsIgnoreCase("Image")) {
            return FXControls.ImageView;
        }

        if (this.isCollection()) {
            if (makeEditableTable()) {
                return FXControls.TableView;
            } else {
                return FXControls.TextArea;
            }
        }

        if (this.getDataType().equalsIgnoreCase("String")) {
            if (this.getSize() > 100) {
                return FXControls.TextArea;
            }
            return FXControls.TextField;
        } else {
            return FXControls.TextField;
        }
    }

    public String getCall() {

        switch (getControlType()) {
            case ComboBox:
                return " get" + fieldName.get() + "()";
            case CheckBox:
                return " is" + fieldName.get() + "()";
            default:
                return " get" + fieldName.get() + "()";
        }
    }

    public String makeGetter() {
        String type = this.getDataType();
        if (type.equalsIgnoreCase("bool") || type.equalsIgnoreCase("boolean")) {
            return Utilities.makeMethod("public", getUsableDataType(false), "is" + this.getFieldName(), "", "return " + this.variableName + ";");
        } else {
            return Utilities.makeMethod("public", getUsableDataType(true), "get" + this.getFieldName(), "", "return " + this.variableName + ";");

        }
    }

    public String makeSetter() {
        return "public ".concat("void").concat(" ").
                concat("set").concat(getFieldName()).concat("(" + getDeclaration(true, false) + "){\n").concat("this.").
                concat(getVariableName()).concat(" = ").concat(getVariableName()).concat(";\n}");
    }

    public String setCall(String objectName) {
        return Utilities.getVariableName(objectName).concat(".set").concat(getFieldName()).concat("(" + variableName + ");\n");
    }

    public String makeDaGetter(String objectName) {
        String type = this.getDataType();

        if (isCollection()) {
            return this.makeGetter() + ""
                    + "public List<" + getReferencesDA() + "> get" + getFieldName() + "DAs(){\n"
                    + "       return " + getReferencesDA() + ".get" + getReferencesDA() + "s(" + Utilities.getVariableName(objectName) + "." + getCall() + ");\n"
                    + "    }";
        } else if (isReferance() && !isCollection()) {
            if (this.getEnumerated()) {
                return Utilities.makeMethod("public", "Object", "get" + this.getFieldName(), "", "return " + this.variableName.concat(".get();"));
            } else {
                String getRef = Utilities.makeMethod("public", references.get(), "get" + this.getFieldName(), "", "return " + this.variableName + ";");

                String getRefID = Utilities.makeMethod("public", "Object", "get" + this.referencesID, "", "return " + this.referencesVariableID.concat(".get();"));
                String getRefDis = Utilities.makeMethod("public", "String", "get" + this.display, "", "return " + this.displayVariableName.concat(".get();"));

                return getRef + getRefID + getRefDis + referencesDAGetter();
            }
        }
        if (type.equalsIgnoreCase("Object")) {
            return Utilities.makeMethod("public", "Object", "get" + this.getFieldName(), "", "return " + this.variableName.concat(".get();"));
        } else if (type.equalsIgnoreCase("Date") || type.equalsIgnoreCase("DateTime")
                || type.equalsIgnoreCase("LocalDate") || type.equalsIgnoreCase("LocalDateTime")) {
            return Utilities.makeMethod("public", "Object", "get" + this.getFieldName(), "", "return " + this.variableName.concat(".get();"))
                    + Utilities.makeMethod("public", displayDataType, "get" + this.display, "", "return " + this.displayVariableName.concat(".get();"));
        } else if (type.equalsIgnoreCase("float") || type.equalsIgnoreCase("double")) {
            return Utilities.makeMethod("public", getUsableDataType(false), "get" + this.getFieldName(), "", "return " + this.variableName.concat(".get();"))
                    + Utilities.makeMethod("public", displayDataType, "get" + this.display, "", "return " + this.displayVariableName.concat(".get();"));
        } else if (type.equalsIgnoreCase("bool") || type.equalsIgnoreCase("boolean")) {
            return Utilities.makeMethod("public", getUsableDataType(false), "is" + this.getFieldName(), "", "return " + this.variableName.concat(".get();"));
        } else if (type.equalsIgnoreCase("Image")) {
            return makeGetter() + "\n"
                    + Utilities.makeMethod("public ", "ImageView", "getImv" + this.getFieldName(), "", "return " + this.getControlName() + ";");
        } else {
            return Utilities.makeMethod("public", getUsableDataType(false), "get" + this.getFieldName(), "", "return " + this.variableName.concat(".get();"));

        }
    }

    public String makeDaSetter(String objectName) {
        String type = this.getDataType();

        if (isCollection()) {
//            return setCall(objectName)+makeSetter();
            return "public ".concat("void").concat(" ").
                    concat("set").concat(getFieldName()).concat("(" + getDeclaration(true, false) + "){\n").concat(setCall(objectName) + "this.").
                    concat(getVariableName()).concat(" = ").concat(getVariableName()).concat(";\n}") + ""
                    + " public void set" + getFieldName() + "DAs(List<" + getReferences() + "DA> " + Utilities.getVariableName(getReferences()) + "DAs) {\n"
                    + "        this." + Utilities.getVariableName(objectName) + ".set" + getFieldName() + "(" + getReferences() + "DA.get" + getReferences() + "List(" + Utilities.getVariableName(getReferences()) + "DAs));\n"
                    + "\n"
                    + "    }";

        } else if (isReferance() && !(this.getEnumerated() || isCollection())) {
            return Utilities.makeMethod("public", "void", "set" + this.getFieldName(), getDeclaration(true, false),
                    setCall(objectName) + "this." + this.variableName.concat(" = ").concat(this.variableName).concat(";"));

        } else if (type.equalsIgnoreCase("Image")) {
            return Utilities.makeMethod("public", "void", "set" + this.getFieldName(), getDeclaration(true, false),
                    setCall(objectName) + "this." + this.variableName.concat(" = ").concat(this.variableName).concat(";"))
                    + Utilities.makeMethod("public", "void", "setImv" + this.getFieldName(), getControlType() + " " + getControlName(),
                            "this." + this.getControlName().concat(" = ").concat(this.getControlName()).concat(";"));
        } else if (type.equalsIgnoreCase("float") || type.equalsIgnoreCase("double")) {
            return Utilities.makeMethod("public", "void", "set" + this.getFieldName(), getDeclaration(true, false),
                    setCall(objectName) + "this." + this.variableName.concat(".set(" + this.variableName + ");\n"
                    + "this." + displayVariableName.concat(".set") + "(formatNumber(" + variableName + "));"));

        } else {
            return Utilities.makeMethod("public", "void", "set" + this.getFieldName(), getDeclaration(true, false),
                    setCall(objectName) + "this." + this.variableName.concat(".set(" + this.variableName + ");"));
        }
    }

    public String makeProperties() {
        return this.makeGetter() + this.makeSetter();
    }

    public String makeDAProperties(String objectName) {
        return this.makeDaGetter(objectName) + this.makeDaSetter(objectName);
    }

    public String referencesDAGetter() {
        return Utilities.makeMethod("public", getReferencesDA(), "get"+getFieldName()+"DA", "", "return this."+variableName+"!=null? new "+getReferencesDA()+"(this."+variableName+"):null;");
    }

    public String fiedAnnotations(String objectName, String primaryKey) {
        String fieldAnnotation = "";
        if (saburiKey.get().equalsIgnoreCase(Saburikeys.ID_Generator.name())) {
            nullable.set(false);
        }
        String column = "";
        if (key.get().equalsIgnoreCase(keys.Primary.name())) {
            fieldAnnotation += "@Id\n";
            if (enumerated.get()) {
                fieldAnnotation += "@Enumerated\n";

            }
            fieldAnnotation += "@NotNull(message =  \"The field: " + getCaption() + " cannot be null\")\n";
            fieldAnnotation += "@Size(max =  " + getSize() + ", message =  \"The field: " + getCaption() + " size cannot be greater than " + getSize() + "\")\n";
            column += "@Column(length = " + getSize() + ", updatable = false)";

        } else if (isCollection()) {

            if (isNullOrEmpty(mapping.get())) {
                mapping.set("OneToMany");
            }
            fieldAnnotation += "@" + mapping.get() + "(cascade = CascadeType.MERGE)"
                    + "@JoinTable(name = \"" + objectName + getFieldName() + "\",\n"
                    + "            joinColumns = {\n"
                    + "                @JoinColumn(name = \"" + Utilities.getVariableName(primaryKey) + "\", nullable = false)},\n"
                    + "            inverseJoinColumns = {\n"
                    + "                @JoinColumn(name = \"" + Utilities.getVariableName(getReferences()) + "ID\", nullable = false)})";

        } else if (isReferance() && !isCollection()) {
            if (!nullable.get()) {
                fieldAnnotation += "@NotNull(message =  \"The field: " + getCaption() + " cannot be null\")\n";
            }
            if (enumerated.get()) {
                fieldAnnotation += "@Enumerated\n";

            } else {

                if (isNullOrEmpty(mapping.get())) {
                    mapping.set("OneToOne");
                }

                if (isNullOrEmpty(key.get())) {
                    key.set("Foreign");

                }

                column = "@JoinColumn(name = \"" + variableName.concat("ID") + "\"";
                column += ",foreignKey = @ForeignKey(name = \"fk" + fieldName.get().concat("ID") + objectName + "\")";

                if (key.get().equalsIgnoreCase("Unique")) {
                    column += ",unique = true";
                }
                column += ")";
                fieldAnnotation += "@" + mapping.get() + "\n";

            }
        } else if (saburiKey.get().equalsIgnoreCase(Saburikeys.ID_Helper.name())) {
            column = "@Column(updatable = false)";
        } else if (dataType.get().equalsIgnoreCase("String")) {
            fieldAnnotation += "@Size(max =  " + getSize() + ", message =  \"The field: " + getCaption() + " size cannot be greater than " + getSize() + "\")\n";

            column += "@Column(";
            if (!isReferance()) {
                column += "length =  " + getSize();
            }

            if (key.get().equalsIgnoreCase("Unique")) {
                column += ", unique = true";
            }
            column += ")\n";
            if (!nullable.get()) {
                fieldAnnotation += "@NotNull(message =  \"The field: " + getCaption() + " cannot be null\")\n";
            }

        } else if (dataType.get().equalsIgnoreCase("LocalDate") || dataType.get().equalsIgnoreCase("LocalDateTime")) {

            if (!nullable.get()) {
                column += "@Column(name = \"" + variableName + "\",nullable = false)";
                fieldAnnotation += "@NotNull(message =\"The field: " + getCaption() + " cannot be null\")\n";
            }
        } else if (dataType.get().equalsIgnoreCase("Image")) {
            fieldAnnotation += "@Lob\n";

            if (!nullable.get()) {
                fieldAnnotation += "@NotNull(message =  \"The field: " + getCaption() + " cannot be null\")\n";
            }

        }

        fieldAnnotation += column;
        return fieldAnnotation;
    }

    public List entityImports() {
        List list = new ArrayList();
        if (isCollection()) {
            addIfNotExists(list, "import java.util." + getDataType());
            addIfNotExists(list, "import javax.persistence.CascadeType");
            addIfNotExists(list, "import javax.persistence.JoinTable");
            if (getDataType().equalsIgnoreCase("List")) {
                addIfNotExists(list, "import java.util.ArrayList");
            } else if (getDataType().equalsIgnoreCase("Set")) {
                addIfNotExists(list, "import java.util.HashSet");
            }
            if (isNullOrEmpty(mapping.get())) {
                mapping.set("OneToMany");
            }
            addIfNotExists(list, "import javax.persistence." + mapping.get());

        } else if (isReferance()) {
            if (!nullable.get()) {
                addIfNotExists(list, "import javax.validation.constraints.NotNull");
            }
            if (enumerated.get()) {
                if (isNullOrEmpty(getEnumClass())) {
                    addIfNotExists(list, "import utils.CommonEnums." + references.get());
                } else {
                    addIfNotExists(list, "import utils." + getEnumClass() + "." + references.get());
                }
                addIfNotExists(list, "import javax.persistence.Enumerated");

            } else {

                if (isNullOrEmpty(mapping.get())) {
                    mapping.set("OneToOne");
                }

                if (isNullOrEmpty(key.get())) {
                    key.set("Foreign");

                }
                addIfNotExists(list, "import javax.persistence.JoinColumn");
                addIfNotExists(list, "import javax.persistence.ForeignKey");
                addIfNotExists(list, "import javax.persistence." + mapping.get());

            }
        } else if (dataType.get().equalsIgnoreCase("String")) {
            addIfNotExists(list, "import javax.persistence.Column");
            addIfNotExists(list, "import javax.validation.constraints.Size");
            if (key.get().equalsIgnoreCase("Unique")) {
                addIfNotExists(list, "import javax.persistence.Column");
            }

            if (!nullable.get()) {
                addIfNotExists(list, "import javax.validation.constraints.NotNull");
            }

        } else {
            if (!nullable.get()) {
                addIfNotExists(list, "import javax.validation.constraints.NotNull");
            }
            if (dataType.get().equalsIgnoreCase("LocalDate")) {
                addIfNotExists(list, "import java.time.LocalDate");
            } else if (dataType.get().equalsIgnoreCase("LocalDateTime")) {
                addIfNotExists(list, "import java.time.LocalDateTime");
            } else if (dataType.get().equalsIgnoreCase("Image")) {
                addIfNotExists(list, "import javax.persistence.Lob");

            }

        }
        return list;
    }

    public List daImports() {
        List list = new ArrayList();
        if (isCollection()) {
            if (isReferance()) {
                if (enumerated.get()) {
                    if (isNullOrEmpty(getEnumClass())) {
                        addIfNotExists(list, "import utils.CommonEnums." + references.get());
                    } else {
                        addIfNotExists(list, "import utils." + getEnumClass() + "." + references.get());
                    }

                } else {

                    addIfNotExists(list, "import entities." + references.get());
                }
            }
            if (getDataType().equalsIgnoreCase("Set")) {
                addIfNotExists(list, "import java.util.HashSet");
            }
        } else if (isReferance()) {
            if (enumerated.get()) {
                if (isNullOrEmpty(getEnumClass())) {
                    addIfNotExists(list, "import utils.CommonEnums." + references.get());
                } else {
                    addIfNotExists(list, "import utils." + getEnumClass() + "." + references.get());
                }

            } else {

                addIfNotExists(list, "import entities." + references.get());
            }
        } else if (dataType.get().equalsIgnoreCase("LocalDate")) {
            addIfNotExists(list, "import java.time.LocalDate");
            addIfNotExists(list, "import static utils.Utilities.formatDate");
        } else if (dataType.get().equalsIgnoreCase("LocalDateTime")) {
            addIfNotExists(list, "import java.time.LocalDateTime");
            addIfNotExists(list, "import static utils.Utilities.formatDateTime");

        } else if (dataType.get().equalsIgnoreCase("double") || dataType.get().equalsIgnoreCase("float")) {
            addIfNotExists(list, "import static utils.Utilities.formatNumber");

        } else if (dataType.get().equalsIgnoreCase("Image")) {
            addIfNotExists(list, "import javafx.scene.image.ImageView");
            addIfNotExists(list, "import utils.FXUIUtils");

        }

        return list;
    }

    public String getControlName() {
        FXControls controlType = this.getControlType();
        String prefix;
        switch (controlType) {
            case DatePicker:
                prefix = "dtp";
                break;
            case CheckBox:
                prefix = "chk";
                break;
            case ComboBox:
                prefix = "cbo";
                break;
            case Label:
                prefix = "lbl";
                break;
            case TextArea:
                prefix = "txa";
                break;

            case ImageView:
                prefix = "imv";
                break;

            case TableView:
                prefix = "tbl";
                break;

            default:
                prefix = "txt";
                break;
        }

        return prefix.concat(this.getFieldName());
    }

    public String getUsableDataType(boolean forceReferences) {
        if (isCollection()) {
            return getDataType() + "<" + getReferences() + ">";
        } else if (forceReferences && isReferance()) {
            return this.references.get();
        } else if (enumerated.get()) {
            return references.get();
        } else if (this.getDataType().equalsIgnoreCase("bool") || this.getDataType().equalsIgnoreCase("boolean")) {
            return "boolean";
        } else if (this.getDataType().equalsIgnoreCase("Image")) {
            return "byte[]";
        } else {
            return this.getDataType();
        }
    }

//    DB Access specific
    public String daPropertyLine() {
        String type = this.dataType.get();
        if (isCollection()) {
            if (getDataType().equalsIgnoreCase("List")) {
                return " private List<" + getReferences() + "> " + getVariableName() + " = new ArrayList<>();\n";
            } else if (getDataType().equalsIgnoreCase("Set")) {
                return " private Set<" + getReferences() + "> " + getVariableName() + " = new HashSet<>();\n";
            } else {
                return "";
            }
        } else if (isReferance()) {
            if (enumerated.get()) {
                return "private final SimpleObjectProperty " + variableName + " =  new SimpleObjectProperty(this,\"" + variableName + "\");\n";
            } else {
                return "private final SimpleStringProperty " + displayVariableName + " =  new SimpleStringProperty(this,\"" + displayVariableName + "\");\n"
                        + "private final SimpleObjectProperty " + referencesVariableID + " =  new SimpleObjectProperty(this,\"" + referencesVariableID + "\");\n"
                        + "private " + references.get() + " " + variableName + ";\n";
            }
        } else {

            if (type.equalsIgnoreCase("Date") || type.equalsIgnoreCase("DateTime")
                    || type.equalsIgnoreCase("LocalDate") || type.equalsIgnoreCase("LocalDateTime")
                    || type.equalsIgnoreCase("Object")) {
                return "private final SimpleObjectProperty " + variableName + " =  new SimpleObjectProperty(this,\"" + variableName + "\");\n"
                        + "private final SimpleStringProperty " + displayVariableName + " =  new SimpleStringProperty(this,\"" + displayVariableName + "\");\n";
            } else if (type.equalsIgnoreCase("bool") || type.equalsIgnoreCase("boolean")) {
                return "private final SimpleBooleanProperty " + variableName + " =  new SimpleBooleanProperty(this,\"" + variableName + "\");\n";
            } else if (type.equalsIgnoreCase("String")) {
                return "private final SimpleStringProperty " + variableName + " =  new SimpleStringProperty(this,\"" + variableName + "\");\n";
            } else if (type.equalsIgnoreCase("int")) {
                return "private final SimpleIntegerProperty " + variableName + " =  new SimpleIntegerProperty(this,\"" + variableName + "\");\n";
            } else if (type.equalsIgnoreCase("float")) {
                return "private final SimpleFloatProperty " + variableName + " =  new SimpleFloatProperty(this,\"" + variableName + "\");\n"
                        + "private final SimpleStringProperty " + displayVariableName + " =  new SimpleStringProperty(this,\"" + displayVariableName + "\");\n";
            } else if (type.equalsIgnoreCase("double")) {
                return "private final SimpleDoubleProperty " + variableName + " =  new SimpleDoubleProperty(this,\"" + variableName + "\");\n"
                        + "private final SimpleStringProperty " + displayVariableName + " =  new SimpleStringProperty(this,\"" + displayVariableName + "\");\n";

            } else if (type.equalsIgnoreCase("Image")) {
                return "private byte[] " + variableName + ";\n"
                        + "private ImageView " + getControlName() + " = new ImageView();\n";
            } else {
                return "private final SimpleStringProperty " + variableName + " =  new SimpleObjectProperty(this,\"" + variableName + "\");\n";
            }
        }
    }

    public String makeSearchColumn() {

        if (this.isHelper() || this.getControlType().equals(FXControls.ImageView) || isCollection()) {
            return "";
        }

        if (this.isReferance()) {
            if (this.getEnumerated()) {
                return "this.searchColumns.add(new SearchColumn(\"" + this.getVariableName() + "\", \"" + this.getCaption() + "\", this." + this.getVariableName() + ".get(), " + this.getSearchDataType() + ", SearchColumn.SearchType.Equal));\n";
            } else {
                return "this.searchColumns.add(new SearchColumn(\"" + this.getReferencesVariableID() + "\", \"" + this.getCaption() + " ID\", this." + getReferencesVariableID() + ".get(), " + getSearchDataType() + ", SearchColumn.SearchType.Equal,false));\n"
                        + "this.searchColumns.add(new SearchColumn(\"" + displayVariableName + "\", \"" + this.getCaption() + "\", this." + displayVariableName + ".get(), " + getSearchDataType() + "));\n";
            }
        } else {
            return "this.searchColumns.add(new SearchColumn(\"" + this.getVariableName() + "\", \"" + this.getCaption() + "\", this." + getVariableName() + ".get(), " + getSearchDataType() + "));\n";
        }

    }

//    controller Specific
    public String getControlLibary() {
        if (getControlType().equals(FXControls.ImageView)) {
            return "import javafx.scene.image.";
        } else {
            return "import javafx.scene.control.";
        }

    }

    public List<Field> getSubFieldList() throws Exception {
        List<Field> subFieldList = new ArrayList<>();
        if (isCollection()) {
            if (makeEditableTable()) {
                String[] subFieldsArray = getSubFields().split("#");
                for (String st : subFieldsArray) {
                    subFieldList.add(Utilities.getFields(st, ">"));

                }
            }
        }
        return subFieldList;
    }

    public List ControllerImports(String objectName) throws Exception {
        String controlLiberay = getControlLibary();

        List list = new ArrayList();
        addIfNotExists(list, controlLiberay.concat(this.getControlType().name()));
        if (isCollection()) {
            addIfNotExists(list, "import java.util." + getDataType());
            addIfNotExists(list, "import dbaccess." + getReferencesDA());
            addIfNotExists(list, "import javafx.collections.FXCollections");
            if (makeEditableTable()) {
                addIfNotExists(list, "import entities." + objectName);
                addIfNotExists(list, "import javafx.scene.control.MenuItem");
                addIfNotExists(list, "import javafx.scene.control.TableColumn");
                addIfNotExists(list, "import javafx.scene.control.TablePosition");
                addIfNotExists(list, "import javafx.collections.ObservableList");
                addIfNotExists(list, "import utils.EditCell");
                try {
                    this.getSubFieldList().forEach(d -> {
                        if ((d.getDataType().equalsIgnoreCase("float") || d.getDataType().equalsIgnoreCase("double"))) {
                            addIfNotExists(list, "import static utils.Utilities.defortNumberOptional");
                        }
                        if (d.isReferance()) {
                            addIfNotExists(list, "import entities." + d.getReferences());
                            addIfNotExists(list, "import javafx.application.Platform");
                        }
                    });
                } catch (Exception ex) {
                    throw ex;
                }
            }
        } else if (isReferance()) {
            if (enumerated.get()) {
                if (isNullOrEmpty(getEnumClass())) {
                    addIfNotExists(list, "import utils.CommonEnums." + references.get());
                } else {
                    addIfNotExists(list, "import utils." + getEnumClass() + "." + references.get());
                }
                addIfNotExists(list, "import javafx.collections.FXCollections");

            } else {
                addIfNotExists(list, "import javafx.scene.control.MenuItem");
                addIfNotExists(list, "import dbaccess." + getReferencesDA());
                if (references.get().equalsIgnoreCase("LookupData")) {
                    if (getEnumClass().equalsIgnoreCase("CommonEnums")) {
                        addIfNotExists(list, "import utils.CommonObjectNames");
                    } else {
                        addIfNotExists(list, "import utils.ObjectNames");
                    }
                }

            }
        } else if (dataType.get().equalsIgnoreCase("LocalDate")) {
            addIfNotExists(list, "import java.time.LocalDate;");
        } else if (dataType.get().equalsIgnoreCase("LocalDateTime")) {
            addIfNotExists(list, "import java.time.LocalDateTime");
        } else if (dataType.get().equalsIgnoreCase("float") || dataType.get().equalsIgnoreCase("double")) {
            addIfNotExists(list, "import static utils.Utilities.formatNumber");
        } else if (dataType.get().equalsIgnoreCase("Image")) {
            addIfNotExists(list, "import javafx.scene.control.Button");
        }

//        ***********************************************************************************************
        if (getSaburiKey().equalsIgnoreCase(Saburikeys.ID_Generator.name())) {
            addIfNotExists(list, "import static utils.Utilities.isNullOrEmpty");
            if (isReferance()) {
                addIfNotExists(list, "import dbaccess." + getReferencesDA());
            }
        }
        return list;
    }

    public String editTableColumnMethod(Field field) {
        String type = this.getDataType();
        String body = "";
        if (this.isReferance()) {
            body += getColumnName(field.getReferences()) + ".setCellFactory(EditCell." + getDataTypeWrapper() + "TableColumn());";
            body += getColumnName(field.getReferences()) + ".setOnEditCommit(event -> {\n"
                    + "            final " + getDataType() + " value = event.getNewValue() != null ? event.getNewValue()\n"
                    + "                    : event.getOldValue();\n"
                    + getReferences() + " " + variableName + " = o" + getReferencesDA() + ".get" + getReferences() + "(value);\n"
                    + "            if (" + variableName + " == null) {\n"
                    + "                Platform.runLater(()->message(\"No " + getReferences() + " with Id \"+value+\" found\"));\n"
                    + "                return;\n"
                    + "            }"
                    + "            ((" + field.getReferencesDA() + ") event.getTableView().getItems()\n"
                    + "                    .get(event.getTablePosition().getRow()))\n"
                    + "                    .set" + getFieldName() + "(" + variableName + ");\n"
                    + "            " + field.getControlName() + ".refresh();\n"
                    + "            addRow(" + field.getControlName() + ",new " + field.getReferencesDA() + "());\n"
                    + "        });";
        } else if (type.equalsIgnoreCase("float") || type.equalsIgnoreCase("double")) {
            body += getColumnName(field.getReferences()) + ".setCellFactory(EditCell.StringTableColumn());";
            body += getColumnName(field.getReferences()) + ".setOnEditCommit(event -> {\n"
                    + "            final String value = event.getNewValue() != null ? event.getNewValue()\n"
                    + "                    : event.getOldValue();\n"
                    + "            ((" + field.getReferencesDA() + ") event.getTableView().getItems()\n"
                    + "                    .get(event.getTablePosition().getRow()))\n"
                    + "                    .set" + getFieldName() + "(defortNumberOptional(value));\n"
                    + "            " + field.getControlName() + ".refresh();\n"
                    + "            addRow(" + field.getControlName() + ",new " + field.getReferencesDA() + "());\n"
                    + "        });";
        } else {
            body += getColumnName(field.getReferences()) + ".setCellFactory(EditCell." + getDataTypeWrapper() + "TableColumn());";
            body += getColumnName(field.getReferences()) + ".setOnEditCommit(event -> {\n"
                    + "            final " + getDataType() + " value = event.getNewValue() != null ? event.getNewValue()\n"
                    + "                    : event.getOldValue();\n"
                    + "            ((" + field.getReferencesDA() + ") event.getTableView().getItems()\n"
                    + "                    .get(event.getTablePosition().getRow()))\n"
                    + "                    .set" + getFieldName() + "(value);\n"
                    + "            " + field.getControlName() + ".refresh();\n"
                    + "            addRow(" + field.getControlName() + ",new " + field.getReferencesDA() + "());\n"
                    + "        });";
        }

        return Utilities.makeMethod("private", "void", "set" + field.getReferences() + getFieldName(), "", body);

    }

    public String controllerAnnotatedFields() {
        String annotatedField = "";
        if (!isHelper()) {
            annotatedField = "@FXML private " + getControlType().name() + " " + getControlName() + ";\n";
            if (getDataType().equalsIgnoreCase("image")) {
                annotatedField += "@FXML private btnBrowse" + getControlName() + ", btnCapture" + getControlName() + ", btnClear" + getControlName() + "\n";
            }
        }
        return annotatedField;
    }

    public String initialseSavableVariable() {
        if (isHelper()) {
            return "";
        }
        String controlName = this.getControlName();
        if (isCollection()) {

            return "List<" + getReferencesDA() + "> " + variableName + "DAs = " + controlName + ".getItems();\n"
                    + variableName + "DAs.removeIf((p) -> p.getDBEntity() == null);\n";

        }
        if (isReferance()) {
            if (enumerated.get()) {
                return references.get() + " " + this.variableName + " =  (" + references.get() + ")getSelectedValue(" + controlName + ", \"" + caption.get() + "\");\n";
            }
            return getReferencesDA() + " " + this.variableName + "DA =  (" + getReferencesDA() + ") getDAValue(" + controlName + ", \"" + caption.get() + "\");\n";
        }

        if (dataType.get().equalsIgnoreCase("Date") || dataType.get().equalsIgnoreCase("DateTime")
                || dataType.get().equalsIgnoreCase("LocalDate") || dataType.get().equalsIgnoreCase("LocalDateTime")) {
            return "LocalDate " + this.variableName + " = getDate(" + controlName + ", \"" + caption.get() + "\");\n";
        }

        if (dataType.get().equalsIgnoreCase("bool") || dataType.get().equalsIgnoreCase("boolean")) {
            return "boolean " + this.variableName + " = " + controlName + ".isSelected();\n";
        }

        if (dataType.get().equalsIgnoreCase("String")) {
            return "String " + this.variableName + " =  getText(" + controlName + ", \"" + caption.get() + "\");\n";
        }
        if (dataType.get().equalsIgnoreCase("int")) {
            return "int " + this.variableName + " =  getInt(" + controlName + ", \"" + caption.get() + "\");\n";
        }
        if (dataType.get().equalsIgnoreCase("float")) {
            return "float " + this.variableName + " =  getFloat(" + controlName + ", \"" + caption.get() + "\");\n";
        }
        if (dataType.get().equalsIgnoreCase("double")) {
            return "double " + this.variableName + " =  getDouble(" + controlName + ", \"" + caption.get() + "\");\n";
        }

        if (dataType.get().equalsIgnoreCase("Image")) {
            return "byte[] " + this.variableName + " = getBytes(" + controlName + ", \"" + caption.get() + "\");\n";
        } else {
            return "String " + this.variableName + " =  getText(" + controlName + ", \"" + caption.get() + "\");\n";
        }
    }

    public String daInitialiseProperties(String objectVariableName) {
        String propertInitialised = "";
        if (isCollection()) {
            propertInitialised = "";
        } else if (isReferance()) {
            if (getEnumerated()) {
                propertInitialised = "this." + variableName + ".set(" + objectVariableName + "." + getCall() + ");\n";
            } else {
                propertInitialised = "this." + variableName + "= " + objectVariableName + "." + getCall() + ";\n";
                propertInitialised += "if(this." + variableName + "!= null){";
                propertInitialised += "this." + getReferencesVariableID() + ".set(" + variableName + ".getId());\n";
                propertInitialised += "this." + displayVariableName + ".set(" + variableName + ".getDisplayKey());\n}\n";
            }
        } else if (dataType.get().equalsIgnoreCase("Image")) {
            propertInitialised += "this." + variableName + " = " + objectVariableName + "." + getCall() + ";\n"
                    + "this." + getControlName() + " = FXUIUtils.setTableSizeImage(" + getControlName() + ", " + objectVariableName + "." + getCall() + ");";
        } else {
            propertInitialised = "this." + variableName + ".set(" + objectVariableName + "." + getCall() + ");\n";
        }

        if (getDataType().equalsIgnoreCase("float") || getDataType().equalsIgnoreCase("double")) {
            propertInitialised += "this." + displayVariableName + ".set(formatNumber(" + objectVariableName + "." + getCall() + "));\n";

        } else if (dataType.get().equalsIgnoreCase("Date") || dataType.get().equalsIgnoreCase("LocalDate")) {
            propertInitialised += "this." + displayVariableName + ".set(formatDate(" + objectVariableName + "." + getCall() + "));\n";
        } else if (dataType.get().equalsIgnoreCase("DateTime") || dataType.get().equalsIgnoreCase("LocalDateTime")) {
            propertInitialised += "this." + displayVariableName + ".set(formatDateTime(" + objectVariableName + "." + getCall() + "));\n";
        }
        return propertInitialised;
    }

    private String daProperty() {
        String type = this.getDataType();
        if (type.equalsIgnoreCase("Date") || type.equalsIgnoreCase("DateTime")
                || type.equalsIgnoreCase("LocalDate") || type.equalsIgnoreCase("LocalDateTime") || type.equalsIgnoreCase("Object")) {
            return "SimpleObjectProperty ";
        } else if (type.equalsIgnoreCase("bool") || type.equalsIgnoreCase("boolean")) {
            return "SimpleBooleanProperty ";
        } else if (type.equalsIgnoreCase("String")) {
            return "SimpleStringProperty ";
        } else if (type.equalsIgnoreCase("int")) {
            return "SimpleIntegerProperty ";
        } else if (type.equalsIgnoreCase("float")) {
            return "SimpleFloatProperty ";
        } else if (type.equalsIgnoreCase("double")) {
            return "SimpleDoubleProperty ";
        } else {
            return "SimpleStringProperty ";
        }
    }

    public String daProperty(String type) {
        if (type.equalsIgnoreCase("Date") || type.equalsIgnoreCase("DateTime")
                || type.equalsIgnoreCase("LocalDate") || type.equalsIgnoreCase("LocalDateTime") || type.equalsIgnoreCase("Object")) {
            return "SimpleObjectProperty ";
        } else if (type.equalsIgnoreCase("bool") || type.equalsIgnoreCase("boolean")) {
            return "SimpleBooleanProperty ";
        } else if (type.equalsIgnoreCase("String")) {
            return "SimpleStringProperty ";
        } else if (type.equalsIgnoreCase("int")) {
            return "SimpleIntegerProperty ";
        } else if (type.equalsIgnoreCase("float")) {
            return "SimpleFloatProperty ";
        } else if (type.equalsIgnoreCase("double")) {
            return "SimpleDoubleProperty ";
        } else {
            return "SimpleStringProperty ";
        }
    }

    public String clearLine() {
        if (isHelper()) {
            return "";
        }
        switch (getControlType()) {
            case ComboBox:
            case DatePicker:
                return getControlName() + ".setValue(null);\n";
            case ImageView:
                return getControlName() + ".setImage(null);\n";

            case CheckBox:
                return getControlName() + ".setSelected(false);\n";
            case TableView:

                return getControlName() + ".getItems().clear();\n" + "addRow(" + getControlName() + ", new " + getReferencesDA() + "());\n";
            default:
                return getControlName() + ".clear();\n";
        }
    }

    public String setControlText(String value) {
        if (isHelper()) {
            return "";
        }
        switch (getControlType()) {
            case ComboBox:
                return getControlName() + ".setValue(" + value + ");\n";
            case DatePicker:
                return getControlName() + ".setValue((" + getDataType() + ")" + value + ");\n";
            case CheckBox:
                return getControlName() + ".setSelected(" + value + ");\n";
            case ImageView:
                return "setImage(" + getControlName() + ", " + value + ");\n";
            default:
                if (getDataType().equalsIgnoreCase("int") || getDataType().equalsIgnoreCase("Integer")) {
                    return getControlName() + ".setText(String.valueOf(" + value + "));\n";
                } else if (getDataType().equalsIgnoreCase("float") || getDataType().equalsIgnoreCase("double")) {
                    return getControlName() + ".setText(formatNumber(" + value + "));\n";
                } else {
                    return getControlName() + ".setText(" + value + ");\n";
                }
        }
    }

    public String makeUIFXMLEditLine(String id, int rowIndex, int columnIndex) {
        if (isPrimaryKey()) {
            return makePrimaryKeyControl(id, rowIndex, columnIndex);
        }
        if (isHelper()) {
            return "";
        }
        String line = " <Label id=\"" + id + "\" fx:id=\"lbl" + getFieldName() + "\" "
                + "minWidth=\"100\" text=\"" + getCaption() + "\" GridPane.columnIndex=\"" + columnIndex + "\" GridPane.rowIndex=\"" + rowIndex + "\" />\n";

        line += "<" + getControlType() + " fx:id = \"" + getControlName() + "\" id = \"" + id + "\"  GridPane.rowIndex = \"" + rowIndex + "\" "
                + "GridPane.columnIndex = \"" + (columnIndex + 1) + "\" ";

        switch (getControlType()) {
            case DatePicker:
                line += "minWidth=\"185.0\"/>";
                break;
            case CheckBox:
                line += "/>";
                break;
            case ComboBox:
                line += "promptText = \"Select " + getCaption() + "\" minWidth=\"185.0\"";
                if (getEnumerated()) {
                    line += "/>";
                } else {
                    line += ">\n<contextMenu>\n"
                            + "  <ContextMenu fx:id =\"cmuSelect" + getFieldName() + "\" id = \"" + id + "\">\n"
                            + " <items>\n"
                            + "<MenuItem mnemonicParsing=\"false\" fx:id =\"cmiSelect" + getFieldName() + "\" id = \"" + id + "\" text=\"Select " + getFieldName() + "\" />\n"
                            + "   </items>\n"
                            + "</ContextMenu>\n"
                            + " </contextMenu>\n"
                            + "</" + getControlType() + "> ";
                }
                break;
            case ImageView:
                line = " <Label id=\"" + id + "\" fx:id=\"lbl" + getFieldName() + "\" "
                        + "minWidth=\"100\" text=\"" + getCaption() + "\" GridPane.columnIndex=\"" + columnIndex + "\" GridPane.rowIndex=\"" + rowIndex + "\" />\n";
                line += " <HBox alignment=\"center\" spacing=\"4\" GridPane.columnIndex=\"" + (columnIndex + 1) + "\" GridPane.rowIndex=\"" + rowIndex + "\""
                        + " fx:id=\"hbx" + getFieldName() + "\" id=\"" + id + "\">\n"
                        + "               \n"
                        + "               <VBox alignment=\"center\" prefHeight=\"150.0\" prefWidth=\"150.0\" styleClass=\"image-pane\" "
                        + "fx:id=\"vbx" + getFieldName() + "\" id=\"" + id + "\">\n"
                        + "                    <ImageView id=\"" + id + "\" fx:id=\"" + getControlName() + "\" fitHeight=\"100\" fitWidth=\"100.0\" pickOnBounds=\"true\" preserveRatio=\"true\" style=\"-fx-opacity: 45;\" />\n"
                        + "                \n"
                        + "                </VBox>\n"
                        + "                <VBox alignment=\"center\" spacing=\"10\" styleClass=\"image-buttons\">\n"
                        + "                    <Button fx:id=\"btnBrowse" + getFieldName() + "\" id=\"" + id + "\" minWidth=\"60\" text=\"Browse\" /> \n"
                        + "                    <Button fx:id=\"btnCapture" + getFieldName() + "\" id=\"" + id + "\" minWidth=\"60\" text=\"Capture\" />\n"
                        + "                    <Button fx:id=\"btnClear" + getFieldName() + "\" id=\"" + id + "\" minWidth=\"60\" text=\"Clear\" /> \n"
                        + "                </VBox>\n"
                        + "            </HBox>";
                break;
            case Label:
                line += " text = \"" + getCaption() + "\"/>";
                break;
            case TextField:
                line += "minWidth=\"100\" promptText = \"Enter " + getCaption() + "\"/>";
                break;
            case TextArea:
                line += "minWidth=\"100\" promptText = \"Enter " + getCaption() + "\"/>";
                break;
            case TableView:
                return "";

            default:
                line += "minWidth=\"100\" promptText = \"Enter " + getCaption() + "\"/>";
                break;
        }

        return line;
    }

    public String makePrimaryKeyControl(String id, int rowIndex, int columnIndex) {
        if (isHelper()) {
            return "";
        }
        String line = " <Label id=\"" + id + "\" fx:id=\"lbl" + getFieldName() + "\" "
                + "minWidth=\"100\" text=\"" + getCaption() + "\" GridPane.columnIndex=\"" + columnIndex + "\" GridPane.rowIndex=\"" + rowIndex + "\" />\n";

        line += "<" + getControlType() + " fx:id = \"" + getControlName() + "\" id = \"" + id + "\"  GridPane.rowIndex = \"" + rowIndex + "\" "
                + "GridPane.columnIndex = \"" + (columnIndex + 1) + "\" ";

        switch (getControlType()) {
            case DatePicker:
                line += "minWidth=\"185.0\"/>";
                break;
            case CheckBox:
                line += "/>";
                break;
            case ComboBox:
                line += "promptText = \"Select " + getCaption() + "\" minWidth=\"185.0\"";
                line += ">\n<contextMenu>\n"
                        + "  <ContextMenu fx:id =\"cmuSelect" + getFieldName() + "\" id = \"" + id + "\">\n"
                        + " <items>\n"
                        + "<MenuItem mnemonicParsing=\"false\" fx:id =\"cmiLoad\" id = \"" + id + "\" text=\"Load\" />"
                        + "<MenuItem mnemonicParsing=\"false\" fx:id =\"cmiSelect" + getFieldName() + "\" id = \"" + id + "\" text=\"Select " + getFieldName() + "\" />\n"
                        + "   </items>\n"
                        + "</ContextMenu>\n"
                        + " </contextMenu>\n"
                        + "</" + getControlType() + "> ";

                break;
            case ImageView:
                line = " <Label id=\"" + id + "\" fx:id=\"lbl" + getFieldName() + "\" "
                        + "minWidth=\"100\" text=\"" + getCaption() + "\" GridPane.columnIndex=\"" + columnIndex + "\" GridPane.rowIndex=\"" + rowIndex + "\" />\n";
                line += " <HBox alignment=\"center\" spacing=\"10\" GridPane.columnIndex=\"" + (columnIndex + 1) + "\" GridPane.rowIndex=\"" + rowIndex + "\""
                        + " fx:id=\"hbx" + getFieldName() + "\" id=\"" + id + "\">\n"
                        + "               \n"
                        + "               <VBox alignment=\"center\" prefHeight=\"150.0\" prefWidth=\"150.0\" styleClass=\"image-pane\" "
                        + "fx:id=\"vbx" + getFieldName() + "\" id=\"" + id + "\">\n"
                        + "                    <ImageView id=\"" + id + "\" fx:id=\"" + getControlName() + "\" fitHeight=\"150\" fitWidth=\"150.0\" pickOnBounds=\"true\" preserveRatio=\"true\" style=\"-fx-opacity: 45;\" />\n"
                        + "                \n"
                        + "                </VBox>\n"
                        + "                <VBox alignment=\"center\" spacing=\"10\">\n"
                        + "                    <Button fx:id=\"btnBrowse" + getFieldName() + "\" id=\"" + id + "\" minWidth=\"100\" text=\"Browse\" /> \n"
                        + "                    <Button fx:id=\"btnCapture" + getFieldName() + "\" id=\"" + id + "\" minWidth=\"100\" text=\"Capture\" />\n"
                        + "                    <Button fx:id=\"btnClear" + getFieldName() + "\" id=\"" + id + "\" minWidth=\"100\" text=\"Clear\" /> \n"
                        + "                </VBox>\n"
                        + "            </HBox>";
                break;
            case Label:
                line += " minWidth=\"100\"  text = \"" + getCaption() + "\"/>";
                break;
            case TextField:
                line += " minWidth=\"100\" promptText = \"Enter " + getCaption() + "\">";
                line += "\n<contextMenu>\n"
                        + "  <ContextMenu fx:id =\"cmuSelect" + getFieldName() + "\" id = \"" + id + "\">\n"
                        + " <items>\n"
                        + "<MenuItem mnemonicParsing=\"false\" fx:id =\"cmiLoad\" id = \"" + id + "\" text=\"Load\" />"
                        + "   </items>\n"
                        + "</ContextMenu>\n"
                        + " </contextMenu>\n"
                        + "</" + getControlType() + "> ";
                break;
            case TextArea:
                line += " minWidth=\"100\" promptText = \"Enter " + getCaption() + "\">";
                line += "\n<contextMenu>\n"
                        + "  <ContextMenu fx:id =\"cmuSelect" + getFieldName() + "\" id = \"" + id + "\">\n"
                        + " <items>\n"
                        + "<MenuItem mnemonicParsing=\"false\" fx:id =\"cmiLoad\" id = \"" + id + "\" text=\"Load\" />"
                        + "   </items>\n"
                        + "</ContextMenu>\n"
                        + " </contextMenu>\n"
                        + "</" + getControlType() + "> ";
                break;
            case TableView:
                return "";

            default:
                break;
        }

        return line;
    }

    public String NumberValidator() {
        String type = getDataType();
        if (isHelper()) {
            return "";
        }
        if (type.equalsIgnoreCase("int")) {
            return "validateIteger(" + getControlName() + ");\n";
        } else if (type.equalsIgnoreCase("float") || type.equalsIgnoreCase("double")) {
            return "validateNumber(" + getControlName() + ");\n";
        } else {
            return "";
        }
    }

    public String NumberFormatter() {
        String type = getDataType();
        if (isHelper()) {
            return "";
        }
        if (type.equalsIgnoreCase("int")) {
            return "formatInteger(" + getControlName() + ");\n";
        } else if (type.equalsIgnoreCase("float") || type.equalsIgnoreCase("double")) {
            return "formatValue(" + getControlName() + ");\n";
        } else {
            return "";
        }
    }

    public String ImageButtonsActions() {
        String type = getDataType();

        if (type.equalsIgnoreCase("Image")) {
            return " btnBrowse" + getFieldName() + ".setOnAction(e -> browseImage(" + getControlName() + "));\n"
                    + "btnCapture" + getFieldName() + ".setOnAction(e->setCapturedImage(" + getControlName() + "));"
                    + "btnClear" + getFieldName() + ".setOnAction(e->" + getControlName() + ".setImage(null));\n";
        } else {
            return "";
        }
    }

    public String annotedImageButtons() {
        String type = getDataType();

        if (type.equalsIgnoreCase("Image")) {
            return "@FXML private Button btnBrowse" + getFieldName() + ", btnCapture" + getFieldName() + ", btnClear" + getFieldName() + ";\n";

        } else {
            return "";
        }
    }

    public String getTableColumn(String objectName, String editable) {
        String tableColumn = "";

        if (!(isHelper() || isCollection())) {
            if (isReferance() && !getEnumerated()) {
                tableColumn += "<TableColumn id=\"" + objectName + "\" fx:id=\"tbc" + getReferencesID() + "\" text=\"" + getCaption() + " ID\" editable=\"" + editable + "\">\n"
                        + "<cellValueFactory><PropertyValueFactory property=\"" + getReferencesID() + "\" />\n"
                        + "</cellValueFactory>\n"
                        + "</TableColumn>\n";

                tableColumn += "<TableColumn id=\"" + objectName + "\" fx:id=\"tbc" + getDisplay() + "\" text=\"" + getCaption() + "\" editable=\"" + editable + "\">\n"
                        + "<cellValueFactory><PropertyValueFactory property=\"" + getDisplay() + "\" />\n"
                        + "</cellValueFactory>\n"
                        + "</TableColumn>\n";

            } else if (hasDisplay()) {
                tableColumn += "<TableColumn id=\"" + objectName + "\" fx:id=\"tbc" + getFieldName() + "\" text=\"" + getCaption() + "\" editable=\"" + editable + "\">\n"
                        + "<cellValueFactory><PropertyValueFactory property=\"" + getDisplay() + "\" />\n"
                        + "</cellValueFactory>\n"
                        + "</TableColumn>\n";

            } else if (getDataType().equals("Image")) {
                tableColumn += "<TableColumn id=\"" + objectName + "\" fx:id=\"tbc" + getFieldName() + "\" text=\"" + getCaption() + "\" editable=\"" + editable + "\">\n"
                        + "<cellValueFactory><PropertyValueFactory property=\"Imv" + getFieldName() + "\" />\n"
                        + "</cellValueFactory>\n"
                        + "</TableColumn>\n";

            } else {
                tableColumn += "<TableColumn id=\"" + objectName + "\" fx:id=\"tbc" + getFieldName() + "\" text=\"" + getCaption() + "\" editable=\"" + editable + "\">\n"
                        + "<cellValueFactory><PropertyValueFactory property=\"" + getFieldName() + "\" />\n"
                        + "</cellValueFactory>\n"
                        + "</TableColumn>\n";
            }

        }
        return tableColumn;
    }

    public String getTableColumn(String objectName, String custom, String editable) {
        String tableColumn = "";

        if (!(isHelper() || isCollection())) {
            if (isReferance() && !getEnumerated()) {
                tableColumn += "<TableColumn id=\"" + objectName + "\" fx:id=\"tbc" + custom + getReferencesID() + "\" text=\"" + getCaption() + " ID\" editable=\"" + editable + "\">\n"
                        + "<cellValueFactory><PropertyValueFactory property=\"" + getReferencesID() + "\" />\n"
                        + "</cellValueFactory>\n"
                        + "</TableColumn>\n";

                tableColumn += "<TableColumn id=\"" + objectName + "\" fx:id=\"tbc" + custom + getDisplay() + "\" text=\"" + getCaption() + "\" editable=\"" + editable + "\">\n"
                        + "<cellValueFactory><PropertyValueFactory property=\"" + getDisplay() + "\" />\n"
                        + "</cellValueFactory>\n"
                        + "</TableColumn>\n";

            } else if (hasDisplay()) {
                tableColumn += "<TableColumn id=\"" + objectName + "\" fx:id=\"tbc" + custom + getFieldName() + "\" text=\"" + getCaption() + "\" editable=\"" + editable + "\">\n"
                        + "<cellValueFactory><PropertyValueFactory property=\"" + getDisplay() + "\" />\n"
                        + "</cellValueFactory>\n"
                        + "</TableColumn>\n";

            } else if (getDataType().equals("Image")) {
                tableColumn += "<TableColumn id=\"" + objectName + "\" fx:id=\"tbc" + custom + getFieldName() + "\" text=\"" + getCaption() + "\" editable=\"" + editable + "\">\n"
                        + "<cellValueFactory><PropertyValueFactory property=\"Imv" + getFieldName() + "\" />\n"
                        + "</cellValueFactory>\n"
                        + "</TableColumn>\n";

            } else {
                tableColumn += "<TableColumn id=\"" + objectName + "\" fx:id=\"tbc" + custom + getFieldName() + "\" text=\"" + getCaption() + "\" editable=\"" + editable + "\">\n"
                        + "<cellValueFactory><PropertyValueFactory property=\"" + getFieldName() + "\" />\n"
                        + "</cellValueFactory>\n"
                        + "</TableColumn>\n";
            }

        }
        return tableColumn;
    }

    public String makeLoadCollections() {
        if (!makeEditableTable()) {
            return "";
        }

        return " private void load" + getFieldName() + "() {\n"
                + "        try {\n"
                + "            ObservableList<" + getReferencesDA() + "> selectedItems = tbl" + getFieldName() + ".getSelectionModel().getSelectedItems();\n"
                + "            if (selectedItems.isEmpty() || selectedItems.size() > 1) {\n"
                + "                return;\n"
                + "            }\n"
                + "            " + getReferencesDA() + " " + getVariableName() + "DA = (" + getReferencesDA() + ") getSelectedItem(new " + getReferencesDA() + "(), \"View\", \"" + getFieldName() + "\", 400, 450, " + getControlName() + ", false);\n"
                + "\n"
                + "            if (" + getVariableName() + "DA == null) {\n"
                + "                return;\n"
                + "            }\n"
                + "            \n"
                + "             if (" + getControlName() + ".getItems().contains(" + getVariableName() + "DA)) {\n"
                + "                    throw new Exception(\"The record with id: \" + " + getVariableName() + "DA.getId() + \" is already selected\");\n"
                + "            }\n"
                + "\n"
                + "            final TablePosition<" + getReferencesDA() + ", String> focusedCell = " + getControlName() + "\n"
                + "                    .focusModelProperty().get().focusedCellProperty().get();\n"
                + "            " + getControlName() + ".getItems().set(focusedCell.getRow(), " + getVariableName() + "DA);\n"
                + "            " + getControlName() + ".edit(focusedCell.getRow(), focusedCell.getTableColumn());\n"
                + "\n"
                + "        } catch (Exception e) {\n"
                + "            errorMessage(e);\n"
                + "        }\n"
                + "    }\n"
                + "";

    }
}
