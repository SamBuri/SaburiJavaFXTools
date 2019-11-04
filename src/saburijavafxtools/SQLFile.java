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
public class SQLFile {
    
    public static String callEditIDGenerator(String objectName){
      return "call uspEditIDGenerator('" + objectName + "', '0', 2, '', 0, '');\n";
    }
    
    public static String callEditAccessObject(String objectName, String objectCaption){
     return "call uspEditAccessObject ('"+objectName+"', '"+objectCaption+"', '0');\n";
    }
    
}
