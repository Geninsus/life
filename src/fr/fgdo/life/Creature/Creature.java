/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.fgdo.life.Creature;

import fr.fgdo.life.GameState.Board.Board;
import fr.fgdo.math.Point;
import java.awt.Color;
import java.util.Random;

/**
 *
 * @author Olivier
 */
public class Creature {
    int radius;
    Color color;
    Point<Integer> center;
    Random rand = new Random();
    Board board;
    
    public Creature(int radius, Color color, Point<Integer> center, Board board) {
        this.radius = radius;
        this.color = color;
        this.center = center;
        this.board = board;
    }

    public Point<Integer> getCenter() {
        return center;
    }

    public Color getColor() {
        return color;
    }

    public int getRadius() {
        return radius;
    }
    
    public void update() {
        if (rand.nextBoolean()) {
            if(board.getWidth()>center.x) {
                center.x++;
            }
        }
        else {
            center.y++;
        }
            
    }
}
