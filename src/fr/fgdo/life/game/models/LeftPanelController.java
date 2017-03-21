/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.fgdo.life.game.models;

import fr.fgdo.math.Point;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 *
 * @author Olivier
 */
public class LeftPanelController extends MouseAdapter{
    
    Game game;

    public LeftPanelController(Game game) {
        this.game = game;
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e); //To change body of generated methods, choose Tools | Templates.
        Being being = new Being(new Color((int)(Math.random()*256),(int)(Math.random()*256),(int)(Math.random()*256)));
        game.addBeing(being, new Point(0, 0));
    }
    
}
