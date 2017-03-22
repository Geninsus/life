/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.fgdo.life.GameState.Board;

import fr.fgdo.life.Creature.Creature;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.HeadlessException;
import java.awt.geom.Ellipse2D;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Olivier
 */
public class BoardView extends JPanel implements Observer{

    private final Board board;
    
    public BoardView(Board board) throws HeadlessException {
        this.board = board;
    }

    @Override
    public void update(Observable o, Object arg) {
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); //To change body of generated methods, choose Tools | Templates.
        for (Creature creature : board.getCreatures()) {
            g.setColor(creature.getColor());
            g.fillOval(creature.getCenter().x, creature.getCenter().y,creature.getRadius(),creature.getRadius());
        }
        
    }
    
    
    
}
