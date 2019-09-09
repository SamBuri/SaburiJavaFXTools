/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package saburijavafxtools;

import Model.Field;
import java.util.Arrays;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;

/**
 *
 * @author CLINICMASTER13
 */
public class CodeGenerator {

    protected List<Field> defaulFields = Arrays.asList(new Field("UserID", "User ID", "String"),
            new Field("UserFullName", "User Full Name", "String"),
            new Field("ClientMachine", "Client Machine", "String"),
            new Field("RecordDateTime", "Record Date Time", "LocalDateTime"),
            new Field("LastUpdateDateTime", "Last Update", "LocalDateTime"));

    public boolean validate(List<Field> fields) throws Exception {
        try {

            FilteredList<Field> primaryKeyList = new FilteredList<>(FXCollections.observableList(fields), e -> true);
            primaryKeyList.setPredicate(FieldPredicates.isPrimaryKey());

            if (fields.isEmpty()) {
                throw new Exception("Must include atleast one item to continue");
            }
            if (primaryKeyList.isEmpty()) {
                throw new Exception("You must enter a primary key");
            }
            if (primaryKeyList.size() > 1) {
                throw new Exception("Composite primary key is not allowed. Please select a primary key");
            }

            FilteredList<Field> displayList = new FilteredList<>(FXCollections.observableList(fields), e -> true);
            displayList.setPredicate(FieldPredicates.isDIsplayKey());
            if (displayList.isEmpty()) {
                throw new Exception("You must enter atleast one display key");
            }

            FilteredList<Field> helperList = new FilteredList<>(FXCollections.observableList(fields), e -> true);
            helperList.setPredicate(FieldPredicates.isHelper());
            if (!helperList.isEmpty()) {
                if (helperList.size() > 1) {
                    throw new Exception("You cannot have more than 1 ID Helpers");
                } else {
                    Field idhelperField = helperList.get(0);
                    if (!(idhelperField.getDataType().equalsIgnoreCase("int") || idhelperField.getDataType().equalsIgnoreCase("integer"))) {
                        throw new Exception("Un suppported data type" + idhelperField + " for ID Helper. Please use int instead");
                    }
                }
            }

            FilteredList<Field> generatorList = new FilteredList<>(FXCollections.observableList(fields), e -> true);
            generatorList.setPredicate(FieldPredicates.isIDGenerator());
            if (!generatorList.isEmpty()) {
                if (generatorList.size() > 1) {
                    throw new Exception("You cannot have more than 1 ID Generators");
                } else {

                    if (helperList.isEmpty()) {
                        throw new Exception("You cannot have ID Generator with ID Helper");
                    }
                }
            }
            return true;
        } catch (Exception e) {
            throw e;
        }
    }

}
