/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 *
 * @author CLINICMASTER13
 */
public class Settings {
    private static Settings AppSettings;
private  final String fileName ="./settings.xml";
    private int lineBreak;
    private int miniLineBreak;

    public Settings() {
    }

    public Settings(int lineBreak, int miniLineBreak) {
        this.lineBreak = lineBreak;
        this.miniLineBreak = miniLineBreak;
    }

    
    public static Settings getAppSettings() {
        return AppSettings;
    }

    public static void setAppSettings(Settings aAppSettings) {
        AppSettings = aAppSettings;
    }
    
    
    public int getLineBreak() {
        return lineBreak;
    }

    
    public void setLineBreak(int lineBreak) {
        this.lineBreak = lineBreak;
    }

    public int getMiniLineBreak() {
        return miniLineBreak;
    }

    public void setMiniLineBreak(int miniLineBreak) {
        this.miniLineBreak = miniLineBreak;
    }
    

     public void xmlEncode() throws FileNotFoundException, IOException{
        try (FileOutputStream fileOutputStream = new FileOutputStream(fileName); XMLEncoder encoder = new XMLEncoder(fileOutputStream)) {
            encoder.writeObject(this);
        }
    }
    
    public Settings xmlDecode() throws FileNotFoundException, IOException{
        try (FileInputStream fileInputStream = new FileInputStream(fileName); 
                XMLDecoder decoder = new XMLDecoder(fileInputStream)) {
            return (Settings) decoder.readObject();
        }
    }
    
    
    
    
}
