/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.fgdo.life.GameObject;

import com.sun.javafx.geom.Area;
import fr.fgdo.life.Creature.Creature;
import fr.fgdo.life.GameState.Board.Board;
import fr.fgdo.life.GameState.Board.BoardView;
import fr.fgdo.math.Point;
import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;
import java.util.Observable;

/**
 *
 * @author fabien
 */
public abstract class GameObject extends Observable implements Serializable{
    protected int radius;
    protected Point center;
    protected Color color;
    protected Board board;
    public boolean toDelete = false;
    
    public int getRadius() {
        return radius;
    }

    public Point getCenter() {
        return center;
    }

    public Color getColor() {
        return color;
    }
    
    public void draw(Graphics g,int screenWidth, int screenHeight, int boardWidth, int boardHeight) {
        int eventRadius = BoardView.getLocalX(getRadius(),screenWidth,boardWidth);
        int xCenterScreen = BoardView.getLocalX(getCenter().x,screenWidth,boardWidth);
        int yCenterScreen = BoardView.getLocalY(getCenter().y,screenHeight,boardHeight);
        g.setColor(this.getColor());
        g.fillOval(xCenterScreen - eventRadius, yCenterScreen - eventRadius,BoardView.getLocalX(getRadius()*2, screenWidth,boardWidth),BoardView.getLocalX(getRadius()*2, screenWidth,boardWidth));
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public boolean intersect(GameObject other) {
        long distanceX = center.x - other.center.x;
        long distanceY = center.y - other.center.y;
        int radiusSum = radius + other.radius;
        return distanceX * distanceX + distanceY * distanceY <= radiusSum * radiusSum;
    }

    public boolean isPointInside(int x,int y) {
        return (x - center.x)*(x - center.x) + (y - center.y)*(y - center.y) < radius*radius;
    }
    
    /**
     * @param color the color to set
     */
    public void setColor(Color color) {
        this.color = color;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public void setCenter(Point center) {
        this.center = center;
    }

    public void setToDelete(boolean toDelete) {
        this.toDelete = toDelete;
    }
    
    
}
