/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.fgdo.life.menu.options;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Olivier
 */
public class Options implements Serializable{
    private boolean fullscreenMode;
    private boolean music;

    public Options() {
        fullscreenMode = false;
        music = true;
    }

    public boolean isFullscreenMode() {
        return fullscreenMode;
    }

    public boolean isMusic() {
        return music;
    }
    
    public void switchFullscreenMode() {
        fullscreenMode = !fullscreenMode;
        save();
    }

    public void switchMusic() {
        music = !music;
        save();
    }
    
    public void save() {
        FileOutputStream fos;
        try {
            fos = new FileOutputStream("config/options.ser");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(this);
            System.out.println("Saved With Succes");
        } catch (IOException ex) {
            Logger.getLogger(Options.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("Error Saving");
        }
    }
    
}
