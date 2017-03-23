/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.fgdo.life.GameState.Board;

import fr.fgdo.life.Creature.Creature;
import fr.fgdo.math.Point;
import java.awt.Graphics;
import java.awt.HeadlessException;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JPanel;

/**
 *
 * @author Olivier
 */
public class BoardView extends JPanel implements Observer{

    private final Board board;
    private int scale = 1;
    private Point<Integer> center = new Point<Integer>(0,0);
    
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
        g.drawString(Long.toString(board.iteration), 0, 10);
        for (Creature creature : board.getCreatures()) {
            g.setColor(creature.getColor());
            g.fillOval( getLocalX(creature.getCenter().x) - creature.getRadius()/2, getLocalY(creature.getCenter().y) - creature.getRadius()/2,creature.getRadius(),creature.getRadius());
        }
        
    }
    
    public int getLocalX(int x) {
        return (int)(x*(float)getWidth()/board.getWidth());
    }
    
    public int getLocalY(int y) {
        return (int)((y-board.getHeight())*-1*(float)getHeight()/board.getHeight());
    }
    
}
