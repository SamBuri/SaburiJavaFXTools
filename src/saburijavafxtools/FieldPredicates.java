/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package saburijavafxtools;

import Model.Field;
import java.util.function.Predicate;

/**
 *
 * @author CLINICMASTER13
 */
public class FieldPredicates {
  
    public static Predicate<Field> isHelper(){
     return (p) -> p.isHelper();

    }
    
    public static Predicate<Field> isPrimaryKey(){
     return (p) -> p.isPrimaryKey();

    }
    
    public static Predicate<Field> isIDGenerator(){
     return (p) -> p.isIDGenerator();

    }
    
    public static Predicate<Field> isDIsplayKey(){
     return (p) -> p.isDisplayKey();

    }
    
    public static Predicate<Field> hasSubFields(){
     return (p) -> p.makeEditableTable();

    }
    
    
}
