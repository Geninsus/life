/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.fgdo.life.GameState;

import fr.fgdo.life.Life;
import fr.fgdo.life.State.State;
import javax.swing.JButton;

/**
 *
 * @author olivbau
 */
public class GameState extends State{

    public GameState(Life lifeGame) {
        super(lifeGame);
        add(new JButton("GAME"));
    }
    
}
