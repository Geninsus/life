/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.fgdo.life.GameState.Board;

import fr.fgdo.life.Creature.Creature;
import fr.fgdo.math.Point;
import fr.fgdo.math.Vector2;
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
    private boolean showingCreaturesNames = true;
    private boolean showingCreaturesVisions = true;
    private boolean showingIterations = true;
            
    public BoardView(Board board) throws HeadlessException {
        this.board = board;
    }

    @Override
    public void update(Observable o, Object arg) {
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        int XMaxScreen = getXYMaxScreen().x;
        int YMaxScreen = getXYMaxScreen().y;
        
        for (Creature creature : board.getCreatures()) {
            int creatureRadius = getLocalX(creature.getRadius(),XMaxScreen);
            int xCenterScreen = getLocalX(creature.getCenter().x,XMaxScreen) - creature.getRadius()/2;
            int yCenterScreen = getLocalY(creature.getCenter().y,YMaxScreen) - creature.getRadius()/2;
            
            g.setColor(creature.getColor());
            if (showingCreaturesVisions) g.drawLine(xCenterScreen, yCenterScreen, getLocalX(creature.getCenter().x,XMaxScreen) + (int) (Math.cos(Math.toRadians(creature.getDirection())) * 100), getLocalY(creature.getCenter().y,YMaxScreen) + (int) (Math.sin(Math.toRadians(creature.getDirection())) * 100));
            g.fillOval(xCenterScreen-creatureRadius/2, yCenterScreen-creatureRadius/2,creatureRadius,creatureRadius);
            g.setColor(Color.BLACK);
            if (showingCreaturesNames) g.drawString(creature.getName(), xCenterScreen, yCenterScreen);
        }
        if(showingIterations) g.drawString(Long.toString(board.iteration), 0, 10);
        g.drawRect(0, 0, XMaxScreen, YMaxScreen);

    }
    
    public int getLocalX(int x, int maxScreenX) {
        return (int)(x*(float)maxScreenX/board.getWidth());
    }
    
    public int getLocalY(int y,int maxScreenY) {
        return (int)((y-board.getHeight())*-1*(float)maxScreenY/board.getHeight());
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
    
    public Vector2<Integer> getXYMaxScreen() {
        float mapAspectRatio = (float) board.getWidth()/board.getHeight();
        int maxScreenX;
        int maxScreenY;
        if (getWidth()/mapAspectRatio > getHeight()) {
            maxScreenY = getHeight();
            maxScreenX = (int) (maxScreenY*mapAspectRatio);
        }
        else {
            maxScreenX = getWidth();
            maxScreenY = (int) (maxScreenX/mapAspectRatio);

        }
        return new Vector2<>(maxScreenX-1, maxScreenY-1);
    }
}
