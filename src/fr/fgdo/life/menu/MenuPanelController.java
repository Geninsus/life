/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.fgdo.life.menu;

import fr.fgdo.life.GameState;
import fr.fgdo.life.Life;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Olivier
 */
public final class MenuPanelController implements ActionListener{

    private final Life lifeGame;
    public MenuPanelController(Life lifeGame) {
        this.lifeGame = lifeGame;
    }

    
    
    
    /**
     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "New":
                break;
            case "Import":
                break;
            case "Options":
                lifeGame.switchState(GameState.OPTIONS);
                break;
            case "Quit":
                System.exit(0);
                break;
        }
    }

    
    
}
