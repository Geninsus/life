/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.fgdo.life.GameState.Board;

import fr.fgdo.life.Creature.Creature;
import fr.fgdo.life.Food.Food;
import fr.fgdo.life.GameObject.GameObject;
import fr.fgdo.life.GameState.Board.Events.MeteorologicalEvent;
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
    private Point center = new Point(0,0);
    public static boolean showingCreaturesNames = true;
    public static boolean showingCreaturesVisions = true;
    public static boolean showingIterations = true;
            
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
        
        for (MeteorologicalEvent meteorologicalEvent : board.getMeteorologicalEvents()) {
            meteorologicalEvent.draw(g,XMaxScreen,YMaxScreen,board.getWidth(),board.getHeight());
        }
        for (Food food : board.getFoods()) {
            food.draw(g,XMaxScreen,YMaxScreen,board.getWidth(),board.getHeight());
        }
        for (Creature creature : board.getCreatures()) {
            creature.draw(g,XMaxScreen,YMaxScreen,board.getWidth(),board.getHeight());
        }
        g.setColor(Color.BLACK);
        if(showingIterations) g.drawString(Long.toString(board.iteration), 0, 10);
        try {
            if (getMousePosition().x <= XMaxScreen && getMousePosition().y <= YMaxScreen) {
                g.drawString(Integer.toString(getBackX(getMousePosition().x, XMaxScreen, board.getWidth()))+ "," + getBackY(getMousePosition().y, YMaxScreen, board.getHeight()), getMousePosition().x, getMousePosition().y);
            }
        } catch (Exception e) {
        }
        g.drawRect(0, 0, getLocalX(board.getWidth(), XMaxScreen, board.getWidth()), getLocalY(0, YMaxScreen, board.getHeight()));
        
    }
    
    public static int getBackX(int localX, int maxScreenX, int width) {
        return (int)(localX*(float)width/maxScreenX);
    }
    
    public static int getBackY(int localY, int maxScreenY, int height) {
        return (height+((localY+maxScreenY)*height)/(-1*maxScreenY))*-1;
    }
    
    public static int getLocalX(int x, int maxScreenX, int width) {
        return (int)(x*(float)maxScreenX/width);
    }
    
    public static int getLocalY(int y,int maxScreenY, int height) {
        return (int)((y-height)*-1*(float)maxScreenY/height);
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
