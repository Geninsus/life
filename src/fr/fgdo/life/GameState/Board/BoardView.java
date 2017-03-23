/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.fgdo.life.GameState.Board;

import fr.fgdo.life.Creature.Creature;
import fr.fgdo.math.Point;
import java.awt.Color;
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
    private boolean showingCreaturesNames = false;
    private boolean showingCreaturesVisions = false;
    private boolean showingIterations = false;
            
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
            if (showingCreaturesVisions) g.drawLine(getLocalX(creature.getCenter().x), getLocalY(creature.getCenter().y), getLocalX(creature.getCenter().x) + (int) (Math.cos(Math.toRadians(creature.getDirection())) * 100), getLocalY(creature.getCenter().y) + (int) (Math.sin(Math.toRadians(creature.getDirection())) * 100));
            int screenX = getLocalX(creature.getCenter().x) - creature.getRadius()/2;
            int screenY = getLocalY(creature.getCenter().y) - creature.getRadius()/2;
            g.fillOval( screenX, screenY,creature.getRadius(),creature.getRadius());
            g.setColor(Color.BLACK);
            if (showingCreaturesNames) g.drawString(creature.getName(), screenX, screenY);
        }
        if(showingIterations) g.drawString(Long.toString(board.iteration), 0, 10);
    }
    
    public int getLocalX(int x) {
        return (int)(x*(float)getWidth()/board.getWidth());
    }
    
    public int getLocalY(int y) {
        return (int)((y-board.getHeight())*-1*(float)getHeight()/board.getHeight());
    }

    public void showingCreaturesNames(boolean showingCreaturesNames) {
        this.showingCreaturesNames = showingCreaturesNames;
    }

    public void showingCreaturesVisions(boolean showingCreaturesVisions) {
        this.showingCreaturesVisions = showingCreaturesVisions;
    }

    public void showingIterations(boolean showingIterations) {
        this.showingIterations = showingIterations;
    }
    
}
