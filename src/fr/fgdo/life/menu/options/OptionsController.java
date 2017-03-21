/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.fgdo.life.menu.options;

import fr.fgdo.life.GameState;
import fr.fgdo.life.Life;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Olivier
 */
public class OptionsController implements ActionListener{

    private Options model;
    private Life lifeGame;
    public OptionsController(Options model, Life lifeGame) {
        this.model = model;
        this.lifeGame = lifeGame;
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
                lifeGame.switchState(GameState.MENU);
                break;
            default:
                break;
        }
    }
    
}
