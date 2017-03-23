/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.fgdo.life.Creature;

import fr.fgdo.life.neuralNetwork.Net;
import fr.fgdo.life.neuralNetwork.exceptions.InputsSizeException;
import fr.fgdo.math.Point;
import java.awt.Color;
import java.util.Random;

/**
 *
 * @author Olivier
 */
public class Creature {
    private Net net;
    int radius;
    Color color;
    Point<Integer> center;
    Random rand = new Random();
    
    public Creature(int radius, Color color, Point<Integer> center, Net net) {
        this.radius = radius;
        this.color = color;
        this.center = center;
        this.net = net;
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
    
    public void update() throws InputsSizeException {
        
        Double netInputs[] = {rand.nextDouble(), rand.nextDouble()};
        Double netOutputs[] = net.feedForward(netInputs);
        Double varX = netOutputs[0] * 5;
        Double varY = netOutputs[1] * 5;
        center.x += Math.abs(varX.intValue());
        center.y += Math.abs(varY.intValue());
    }
        
}
