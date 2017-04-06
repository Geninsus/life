/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.fgdo.life.GameObject;

import com.sun.javafx.geom.Area;
import fr.fgdo.life.GameState.Board.Board;
import fr.fgdo.life.GameState.Board.BoardView;
import static fr.fgdo.life.GameState.Board.BoardView.getLocalX;
import static fr.fgdo.life.GameState.Board.BoardView.getLocalY;
import fr.fgdo.math.Point;
import java.awt.Color;
import java.awt.Graphics;
import java.util.EventListener;
import java.util.Observable;

/**
 *
 * @author fabien
 */
public abstract class GameObject extends Observable{
    protected int radius;
    protected Point<Integer> center;
    protected Color color;
    protected Board board;
    protected Area area;
    
    public int getRadius() {
        return radius;
    }

    public Point<Integer> getCenter() {
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

    public Area getArea() {
        return area;
    }
    
    public boolean intersect(GameObject other) {
        int distanceX = center.x - other.center.x;
        int distanceY = center.y - other.center.y;
        int radiusSum = radius + other.radius;
        return distanceX * distanceX + distanceY * distanceY <= radiusSum * radiusSum;
    }
}
