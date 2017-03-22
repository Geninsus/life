/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.fgdo.life.State;

import fr.fgdo.life.Life;
import javax.swing.JPanel;

/**
 *
 * @author olivbau
 */
public abstract class State extends JPanel{

    private Life lifeGame;
    
    public State(Life lifeGame) {
        this.lifeGame = lifeGame;
    }

    public Life getLifeGame() {
        return lifeGame;
    }
    
    /**
     *
     */
    public void start(){};
}
