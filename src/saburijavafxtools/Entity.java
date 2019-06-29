/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package saburijavafxtools;

import Model.Field;
import helpers.Enums;
import helpers.Utilities;
import static helpers.Utilities.addIfNotExists;
import static helpers.Utilities.isNullOrEmpty;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author CLINICMASTER13
 */
public class Entity extends CodeGenerator {

    String objectName;
    List<Field> fields;
    String objectNameDA;
    String objectNameController;
    String objectNameViewController;
    String objectNameEdit;
    String objectNameView;
    String primaryKeyVariableName;
    String objectVariableName;

    public Entity(String objectName, List<Field> fields) {
        this.objectName = objectName;
        this.fields = fields;
        this.objectNameDA = objectName.concat("DA");
        this.objectNameController = objectName.concat("Controller");
        this.objectNameViewController = objectName.concat("ViewController");
        this.objectNameEdit = objectName.concat("Edit");
        this.objectNameView = objectName.concat("View");
        this.primaryKeyVariableName = Utilities.getPrimaryKey(fields).getVariableName();
        this.objectVariableName = Utilities.getVariableName(objectName);
    }

    public String makeEntityImports() {
        String imp = "import javax.persistence.Entity;\n"
                + "import javax.persistence.Id;\n"
                + "import org.hibernate.envers.Audited;\n"
                + "import org.hibernate.envers.RelationTargetAuditMode;\n";
        List<String> imports = new ArrayList();
        this.fields.forEach((t) -> {
            t.entityImports().forEach(i -> addIfNotExists(imports, i));

        });
        for (String impo : imports) {
            imp += impo + ";\n";
        }
        return imp;
    }

    public String makeAnnotedFields() {
        String annotedFields = "";
        for (Field field : this.fields) {
            annotedFields += field.fiedAnnotations(objectName, primaryKeyVariableName);
            annotedFields += "private " + field.getDeclaration(true, true);
        }
        return annotedFields;
    }

    public String makeConstructor() {

        String construtorLine = "";
        String construtorInitials = "";
        for (int i = 0; i < fields.size(); i++) {
            Field field = this.fields.get(i);
            construtorLine += field.getDeclaration(true, false);
            if (i < fields.size() - 1) {
                construtorLine += ",";
            }
            construtorInitials += "this." + field.getVariableName() + " = " + field.getVariableName() + ";\n";

        }

        return Utilities.makeMethod("public", "", objectName, construtorLine, construtorInitials);
    }

    public String makeProperties() {
        String properties = "";
        for (Field field : fields) {
            properties += field.makeProperties();
        }
        return properties;
    }

//    public String makeSetters() {
//        String setters = "";
//        for (Field field : fields) {
//            setters += field.makeSetter();
//        }
//        return setters;
//    }
    public String overriddenID() {

        String primaryKey = "";
        for (Field field : fields) {
            if (field.getKey().equalsIgnoreCase(Enums.keys.Primary.name())) {
                primaryKey = field.getVariableName();
            }
        }

        if (primaryKey.equalsIgnoreCase("id")) {
            return "";
        }
        String method = " @Override\npublic Object getId(){\n";
        if (!isNullOrEmpty(primaryKey)) {
            method += "return this." + primaryKey + ";\n";
        } else {
            method += " throw new UnsupportedOperationException(\"Not supported yet.\");\n";
        }
        method += "}\n";
        return method;
    }

    public String overriddenDisplayKey() {
        String displayKey = "";
        List<String> displayKeys = new ArrayList();
        for (Field field : fields) {

            if (field.getSaburiKey().equalsIgnoreCase(Enums.Saburikeys.Display.name())) {
                displayKeys.add(field.getVariableName());

            }
        }
        for (int i = 0; i < displayKeys.size(); i++) {
            if (i == 0) {
                displayKey += displayKeys.get(i);
            } else {
                displayKey += "+\" \"+this." + displayKeys.get(i);
            }
        }
        if (displayKey.equalsIgnoreCase("displayKey")) {
            return "";
        }
        String method = " @Override\npublic String getDisplayKey(){\n";
        if (!isNullOrEmpty(displayKey)) {
            method += "return this." + displayKey + ";\n";
        } else {
            method += " throw new UnsupportedOperationException(\"Not supported yet.\");\n";
        }
        method += "}\n";
        return method;
    }

    public String otherMethods() {
        String objectEquals = " @Override\n"
                + "    public boolean equals(Object o) {\n"
                + "        if (this == o) {\n"
                + "            return true;\n"
                + "        }\n"
                + "        if (!(o instanceof " + this.objectName + ")) {\n"
                + "            return false;\n"
                + "        }\n"
                + "\n"
                + "        " + this.objectName + " " + objectVariableName + " = (" + objectName + ") o;\n"
                + "\n"
                + "        return this.getId().equals(" + objectVariableName + ".getId());\n"
                + "    }\n";

        String objectHashCode = "    @Override\n"
                + "    public int hashCode() {\n"
                + "        return this." + primaryKeyVariableName + ".hashCode();\n"
                + "\n"
                + "    }";

        return objectEquals + objectHashCode;

    }

    public String makeClass() throws Exception {
        validate(fields);
        String constructor = new JavaClass(objectName).makeNoArgConstructor().concat("\n") + this.makeConstructor();
        String methods = otherMethods() + this.overriddenID() + this.overriddenDisplayKey();
        JavaClass javaClass = new JavaClass("entities", objectName, this.makeEntityImports(),
                this.makeAnnotedFields(), constructor, this.makeProperties(), methods);
        return javaClass.makeClass("DBEntity", "@Entity\n@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)");
    }

}
