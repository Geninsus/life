/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.fgdo.life.menu.options;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Olivier
 */
public class OptionsController implements ActionListener{

    private Options model;

    public OptionsController(Options model) {
        this.model = model;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        switch(e.getActionCommand()) {
            case "Fullscreen":
                model.switchFullscreenMode();
                break;
            case "Music":
                model.switchMusic();
                break;
            case "Return":
                break;
            default:
                break;
        }
    }
    
}
