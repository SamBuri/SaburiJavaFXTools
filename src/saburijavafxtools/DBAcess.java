/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package saburijavafxtools;

import Model.Field;
import helpers.Utilities;
import static helpers.Utilities.addIfNotExists;
import static helpers.Utilities.makeMethod;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;

/**
 *
 * @author CLINICMASTER13
 */
public class DBAcess extends CodeGenerator {

    private String objectName;
    private List<Field> fields;
//    private final List<Field> allFields;
    private String objectNameDA;
    private final String objectVariableName;
    private final String primaryKeyVariableName;
    private final String objectNameDAVariableName;
    private final String superClass = "DBAccess";
    private final FilteredList<Field> collectionList;

    public DBAcess(String objectName, List<Field> fields) {
        this.objectName = objectName;
        this.fields = fields;
//        this.allFields = fields;
//        this.allFields.addAll(defaulFields);
        this.objectNameDA = objectName.concat("DA");
        this.objectVariableName = Utilities.getVariableName(objectName);
        this.primaryKeyVariableName = Utilities.getPrimaryKey(fields).getVariableName();
        this.objectNameDAVariableName = Utilities.getVariableName(objectNameDA);
        collectionList = new FilteredList<>(FXCollections.observableList(fields), e -> true);
        collectionList.setPredicate((p) -> p.isCollection());
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
        this.objectNameDA = objectName.concat("DA");
    }

    public List<Field> getFields() {
        return fields;
    }

    public void setFields(List<Field> fields) {
        this.fields = fields;
    }

    public String getObjectNameDA() {
        return objectNameDA;
    }

    public String makeImports() {
        String imp = "import entities." + objectName + ";"
                + "import javafx.beans.property.*;\n"
                + "import java.util.ArrayList;\n"
                + "import java.util.List;\n"
                + "import javafx.util.Pair;\n"
                + "import helpers.SearchColumn;\n"
                + "import helpers.SearchColumn.SearchDataTypes;\n";
        List<String> imports = new ArrayList();
        fields.forEach((t) -> {
            t.daImports().forEach(i -> addIfNotExists(imports, i));

        });
        for (String impo : imports) {
            imp += impo + ";\n";
        }
        return imp;
    }

    public String makeProperties() {
        String properties = "private " + this.objectName.concat(" ").concat(objectVariableName).concat(";\n");
        for (Field field : fields) {
            properties += field.daPropertyLine();
        }
        return properties;
    }

    private String callNextIDHelper() throws Exception {
        Field idHelperObject = Utilities.getIDHelper(fields);
        Field idGeneratorObject = Utilities.getIDGenerator(fields);
        if (idGeneratorObject != null && idHelperObject == null) {
            throw new Exception("The ID generator does not have a helper column");
        }
        if (idGeneratorObject == null && idHelperObject == null) {
            return "";
        }
        if (idGeneratorObject == null) {
            return "getNext" + idHelperObject.getFieldName() + "()";
        } else {

            String idGeneratorVariableName = idGeneratorObject.getVariableName();
            if (idGeneratorObject.isReferance()) {
                idGeneratorVariableName = idGeneratorVariableName.concat("DA");
            }

            return " getNext" + idHelperObject.getFieldName() + "(" + idGeneratorVariableName + ")";
        }

    }

    private String makeConstructor() {

        String initialisers = "initialseProprties();\ncreateSearchColumns();";

        String noArg = makeMethod("public", "", objectNameDA, "", "createSearchColumns();");

        String puConstructor = makeMethod("public", "", objectNameDA, "String persistenceUnit", "super(persistenceUnit);");

        String readLineSimple = objectName.concat(" ").concat(objectVariableName);
        String readInitialSimple = "this." + objectVariableName + " = " + objectVariableName.concat(";\n").concat(initialisers);
        String readConstructorSimple = makeMethod("public", "", objectNameDA, readLineSimple, readInitialSimple);

        String readLine = "String persistenceUnit, " + objectName.concat(" ").concat(objectVariableName);
        String readInitial = "super(persistenceUnit);\n".concat("this." + objectVariableName + " = " + objectVariableName).concat(";\n").concat(initialisers);
        String readConstructor = makeMethod("public", "", objectNameDA, readLine, readInitial);

        String construtorLine = "";

        String makeInitials = "";
        for (int i = 0; i < fields.size(); i++) {
            Field field = this.fields.get(i);
            if (field.isCollection()) {
                construtorLine += field.getDeclaration(false, false);
                makeInitials += field.getVariableName();
            } else if (field.isReferance() && !field.getEnumerated()) {
                construtorLine += field.getReferencesDA() + " " + Utilities.getVariableName(field.getFieldName() + "DA ");
                makeInitials += "(" + field.getReferences() + ")" + Utilities.getVariableName(field.getFieldName() + "DA ") + ".getDBEntity()";
            } else {
                if (field.isHelper()) {
                    try {
                        makeInitials += callNextIDHelper();
                    } catch (Exception ex) {
                        Logger.getLogger(DBAcess.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    {
                        construtorLine += field.getDeclaration(false, false);
                        makeInitials += field.getVariableName();
                    }
                }

            }

            if (i < fields.size() - 1) {
                if (!field.isHelper()) {
                    construtorLine += ",";
                }
                makeInitials += ",";
            }

        }

        String construtorInitials = "this." + objectVariableName + " = new " + objectName + "(";

        String simpleCreate = construtorInitials += makeInitials + ");".concat(initialisers);
        String createConstructorSimple = makeMethod("public", "", objectNameDA, construtorLine, simpleCreate);

        String createLine = "String persistenceUnit, " + construtorLine;
        String createInitial = "super(persistenceUnit);\n".concat(simpleCreate);
        String createConstructor = makeMethod("public", "", objectNameDA, createLine, createInitial);

        return noArg + puConstructor + readConstructorSimple + readConstructor + createConstructorSimple + createConstructor;
    }

    private String makeSearchColumn() {
        String searchColumnBody = "";
        for (Field field : fields) {
            searchColumnBody += field.makeSearchColumn();
        }
        searchColumnBody += "this.searchColumns.addAll(this.getDefaultSearchColumns());";

        return Utilities.makeMethod("private", "void", "createSearchColumns", "", searchColumnBody);
    }

    private String initialiseProperties() {
        String constructorBody = "this.dBEntity = " + objectVariableName.concat(";\n");
        for (Field field : fields) {
            constructorBody += field.daInitialiseProperties(objectVariableName);
        }
        constructorBody += "initCommonProprties();";
        return Utilities.makeMethod("private", "void", "initialseProprties", "", constructorBody);
    }

    public String makeDAProperties() {
        String properties = "";
        properties = fields.stream().map((field) -> field.makeDAProperties(objectName)).reduce(properties, String::concat);

        return properties;
    }

//    public String makeSetters() {
//        String setters = "";
//        for (Field field : fields) {
//            setters += field.makeDaSetter(objectName);
//        }
//        return setters;
//    }
    private String getNextIDHelper() throws Exception {
        Field idHelperObject = Utilities.getIDHelper(fields);
        Field idGeneratorObject = Utilities.getIDGenerator(fields);

        if (idGeneratorObject != null && idHelperObject == null) {
            throw new Exception("The ID generator does not have a helper column");
        }
        if (idGeneratorObject == null && idHelperObject == null) {
            return "";
        }
        if (idGeneratorObject == null) {
            return "public final int getNext" + idHelperObject.getFieldName() + "() {\n"
                    + "        return this.getMax(\"" + idHelperObject.getVariableName() + "\") + 1;\n"
                    + "    }\n";
        } else {

            String idGeneratorVariableName = idGeneratorObject.getVariableName();
            String idHelperVariableName = idHelperObject.getVariableName();
            if (idGeneratorObject.isReferance() && !idGeneratorObject.getEnumerated()) {
                return "public final int getNext" + idHelperObject.getFieldName() + "(" + idGeneratorObject.getReferencesDA() + " " + idGeneratorVariableName + "DA) {\n"
                        + "        return this.getMax(\"" + idHelperVariableName + "\",\"" + idGeneratorVariableName + "\", " + idGeneratorVariableName + "DA.getDBEntity())+1;\n"
                        + "    }\n";
            } else {
                return "public final int getNext" + idHelperObject.getFieldName() + "(Object " + idGeneratorVariableName + ") {\n"
                        + "        return this.getMax( \"" + idHelperVariableName + "\",\"" + idGeneratorVariableName + "\", " + idGeneratorVariableName + ")+1;\n"
                        + "    }\n";
            }

        }

    }

    private String getNextIDColumn() throws Exception {
        Field idHelperObject = Utilities.getIDHelper(fields);
        Field idGeneratorObject = Utilities.getIDGenerator(fields);
        Field primaryKeyObject = Utilities.getPrimaryKey(fields);
        if (idGeneratorObject != null && idHelperObject == null) {
            throw new Exception("The ID generator does not have a helper column");
        }
        if (idGeneratorObject == null && idHelperObject == null) {
            return "";
        }
        if (idGeneratorObject == null) {
            String idHelperVariableName = idHelperObject.getVariableName();
            return "public String getNext" + primaryKeyObject.getFieldName() + "(int " + idHelperVariableName + " ) {\n"
                    + "        return new IDGeneratorDA().getToAppendString(" + objectName + ".class.getSimpleName(), " + idHelperVariableName + ");\n"
                    + "    }\n";
        } else {

            String idGeneratorVariableName = idGeneratorObject.getVariableName();
            String idHelperVariableName = idHelperObject.getVariableName();
            String references = idGeneratorObject.getReferences();
            if (idGeneratorObject.isReferance() && idGeneratorObject.getEnumerated()) {
                return "public String getNext" + primaryKeyObject.getFieldName() + "(int " + idHelperVariableName + ", " + references + " " + idGeneratorVariableName + ") {\n"
                        + "  return new IDGeneratorDA().getToAppendString(" + objectName + ".class.getSimpleName(), (" + idGeneratorVariableName + ".ordinal()+1), " + idHelperVariableName + ");\n"
                        + "    }\n";
            } else {
                return "public String getNext" + primaryKeyObject.getFieldName() + "(int " + idHelperVariableName + ", " + idGeneratorObject.getDataType() + " " + idGeneratorVariableName + ") {\n"
                        + "          return new IDGeneratorDA().getToAppendString(" + objectName + ".class.getSimpleName()," + idGeneratorVariableName + ", " + idHelperVariableName + ");"
                        + "    }\n";
            }

        }

    }

    public String getObjectFromDB() {
        List<String> collectionVariableNames = new ArrayList<>();
        collectionList.forEach(f -> collectionVariableNames.add(f.getVariableName()));

        String body;
        if (collectionVariableNames.isEmpty()) {

            body = "return (" + objectName + ") super.find(" + objectName + ".class, " + primaryKeyVariableName + ");";
        } else if (collectionVariableNames.size() == 1) {

            body = "return (" + objectName + ") super.findJoin(" + objectName + ".class, " + primaryKeyVariableName + ",\"" + collectionVariableNames.get(0) + "\");";
        } else {
            String joinString = "List<String> associatedEntities = new ArrayList();";

            for (String associatedEntity : collectionVariableNames) {
                joinString += "associatedEntities.add(\"" + associatedEntity + "\");";
            }
            body = joinString;
            body += "return (" + objectName + ") super.findJoin(" + objectName + ".class, " + primaryKeyVariableName + ", associatedEntities);";
        }
        return makeMethod("public", objectName, "get" + objectName, "String " + primaryKeyVariableName, body);
    }

    private String makeMethods() {
        try {

            String equals = "         @Override\n"
                    + "    public boolean equals(Object o) {\n"
                    + "        if (this == o) {\n"
                    + "            return true;\n"
                    + "        }\n"
                    + "        if (!(o instanceof " + objectNameDA + ")) {\n"
                    + "            return false;\n"
                    + "        }\n"
                    + "        \n"
                    + "        " + objectNameDA + " " + objectNameDAVariableName + " = (" + objectNameDA + ") o;\n"
                    + "        \n"
                    + "        if(" + objectNameDAVariableName + ".getDBEntity()== null||this.getDBEntity() == null){\n"
                    + "            return false;\n"
                    + "        }\n"
                    + "        return this.getId().equals(" + objectNameDAVariableName + ".getId());"
                    + "    }";

            String hashCode = " @Override\n"
                    + "            public int hashCode\n"
                    + "            \n"
                    + "                () {\n"
                    + "        return " + objectVariableName + ".getId().hashCode();\n"
                    + "            }";

            String dagetSearchColumns = " @Override\n"
                    + "    public List<SearchColumn> getSearchColumns() {\n"
                    + "       return this.searchColumns;\n"
                    + "    }\n";

            String getID = " @Override\n"
                    + "    public Object getId() {\n"
                    + "        return this." + objectVariableName + ".getId();\n"
                    + "    }";

            String getDisplayKey = "@Override\n"
                    + "    public String getDisplayKey() {\n"
                    + "        return this." + objectVariableName + ".getDisplayKey();\n"
                    + "    }";

            String daGetValue = "public List<" + objectNameDA + "> get(String columName, Object value) {\n"
                    + "        List<" + objectName + "> data= super.find(" + objectName + ".class, columName, value);\n"
                    + "        List <" + objectNameDA + "> list = new ArrayList<>();\n"
                    + "        data.forEach(da ->list.add(new " + objectNameDA + "(da)));\n"
                    + "        return list;\n"
                    + "    }";

            String daGetPairValues = "public List<Pair<String, Object>> keyValuePairs(String columName, Object value){\n"
                    + "         List<Pair<String, Object>> pairs = new ArrayList<>();\n"
                    + "          this.get(columName,value).forEach((t) ->  pairs.add(t.keyValuePair()));\n"
                    + "         return pairs;\n"
                    + "    }";

            String daGetPairs = "public List<Pair<String, Object>> keyValuePairs(){\n"
                    + "         List<Pair<String, Object>> pairs = new ArrayList<>();\n"
                    + "         this.get().forEach((t) ->  pairs.add(t.keyValuePair()));\n"
                    + "         return pairs;\n"
                    + "    }";

            String mConvert = " public static List<" + objectName + "> get" + objectName + "List(List<" + objectNameDA + "> " + Utilities.getVariableName(objectNameDA) + "s){\n"
                    + "       List<" + objectName + "> " + objectVariableName + "s = new ArrayList<>();\n"
                    + "       " + Utilities.getVariableName(objectNameDA) + "s.forEach(a->" + objectVariableName + "s.add(a." + objectVariableName + "));\n"
                    + "       return " + objectVariableName + "s;\n"
                    + "    }";

            String dConvert = " public static List<" + objectNameDA + "> get" + objectNameDA + "s(List<" + objectName + "> " + objectVariableName + "s) {\n"
                    + "        List<" + objectNameDA + "> list = new ArrayList<>();\n"
                    + "        " + objectVariableName + "s.forEach((" + objectVariableName + ") -> {\n"
                    + "            list.add(new " + objectNameDA + "(" + objectVariableName + "));\n"
                    + "        });\n"
                    + "        return list;\n"
                    + "    }";

            String daSave = "public boolean save() throws Exception{\n"
                    + " return super.persist(this." + objectVariableName + ");\n"
                    + "\n"
                    + "}\n"
                    + "";
            String daUpdate = " public boolean update() {\n"
                    + "return super.merge(this." + objectVariableName + ");\n"
                    + "\n"
                    + "}\n"
                    + "";

            String daDelete = " public boolean delete() {\n"
                    + "return super.remove(this." + objectVariableName + ");\n"
                    + "\n"
                    + "}\n"
                    + "";

            String dagetAllObject = "public List<" + objectName + "> get" + objectName + "s() {\n"
                    + "return super.find(" + objectName + ".class);\n"
                    + "}";

            String getObjectName = getObjectFromDB();

            String dagetAll = "  @Override\npublic List<" + superClass + "> get() {\n"
                    + "List<" + superClass + "> list = new ArrayList<>();\n"
                    + "List<" + objectName + "> datas = super.find(" + objectName + ".class);\n"
                    + "datas.forEach((data) -> {\n"
                    + "list.add(new " + objectNameDA + "(data));\n"
                    + "});\n"
                    + "return list;\n"
                    + "}\n"
                    + "";

            String daget = "public " + objectNameDA + " get(String " + primaryKeyVariableName + ") throws Exception {\n"
                    + "       " + objectName + " o" + objectName + " = get" + objectName + "(" + primaryKeyVariableName + ");\n"
                    + "       if(o" + objectName + " == null) throw new Exception(\"No Record with id: \"+" + primaryKeyVariableName + ");\n"
                    + "        return new " + objectNameDA + "(o" + objectName + ");\n"
                    + "    }\n";

            String getByColName = "public List<" + objectName + "> get" + objectName + "s(String columName, Object value) {\n"
                    + "        return  super.find(" + objectName + ".class, columName, value);}\n";

            String daMax = " public int getMax(String columnName) {\n"
                    + "return super.getMax(" + objectName + ".class, columnName);\n"
                    + "}\n";

            String daMax1 = "public int getMax(String columnName, String comparatorColumn, Object comparatorVaue) {\n"
                    + "        return super.getMax(" + objectName + ".class, columnName, comparatorColumn, comparatorVaue);\n"
                    + "    }\n";

            String todaList = "public List<" + objectNameDA + "> toDaList(List<" + objectName + "> " + objectVariableName + "s) {\n"
                    + "        List<" + objectNameDA + "> " + objectNameDAVariableName + "s = new ArrayList<>();\n"
                    + "        " + objectVariableName + "s.forEach(s -> " + objectNameDAVariableName + "s.add(new " + objectNameDA + "(s)));\n"
                    + "        return " + objectNameDAVariableName + "s;\n"
                    + "    }";

            String toDBAccesList = "public List<DBAccess> toDBAccessList(List<" + objectName + "> " + objectVariableName + "s) {\n"
                    + "        List<DBAccess> dbAccesses = new ArrayList<>();\n"
                    + "        " + objectVariableName + "s.forEach(s -> dbAccesses.add(new " + objectNameDA + "(s)));\n"
                    + "        return dbAccesses;\n"
                    + "    }";

            String daNextIDHelper = getNextIDHelper();
            String daNextPrimaryKey = getNextIDColumn();
            return equals + hashCode + initialiseProperties() + makeSearchColumn() + dagetSearchColumns + getID + getDisplayKey + dConvert + mConvert + daSave + daUpdate + daDelete + getObjectName + dagetAllObject + dagetAll
                    + daget + daGetValue + daGetPairs + daGetPairValues + todaList + toDBAccesList + daMax + daMax1 + daNextIDHelper + daNextPrimaryKey + getByColName + "\n";
        } catch (Exception ex) {
            Logger.getLogger(DBAcess.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }

    public String makeClass() throws Exception {
        validate(fields);
        JavaClass javaClass = new JavaClass("dbaccess", objectNameDA, this.makeImports(),
                this.makeProperties(), this.makeConstructor(), this.makeDAProperties(), makeMethods());
        return javaClass.makeClass("DBAccess");
    }

}
