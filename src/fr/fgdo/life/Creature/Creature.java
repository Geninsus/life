/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.fgdo.life.Creature;

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
    
    public Creature(int radius, Color color, Point<Integer> center) {
        this.radius = radius;
        this.color = color;
        this.center = center;
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
        if (rand.nextBoolean()) 
            center.x++;
        else 
            center.y++;
    }
}
