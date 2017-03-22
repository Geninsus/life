/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.fgdo.life.Creature;

import fr.fgdo.math.Point;
import java.awt.Color;

/**
 *
 * @author Olivier
 */
public class Creature {
    int radius;
    Color color;
    Point<Integer> center;

    public Creature(int radius, Color color, Point<Integer> center) {
        this.radius = radius;
        this.color = color;
        this.center = center;
    }

    public Point<Integer> getCenter() {
        return center;
    }
    
    public void update() {
        center.x++;
    }
}
