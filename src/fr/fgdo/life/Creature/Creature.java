/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.fgdo.life.Creature;

import fr.fgdo.life.GameState.Board.Board;
import fr.fgdo.life.Life;
import fr.fgdo.life.neuralNetwork.Net;
import fr.fgdo.life.neuralNetwork.exceptions.InputsSizeException;
import fr.fgdo.life.neuralNetwork.exceptions.TopologySizeException;
import fr.fgdo.math.Point;
import java.awt.Color;
import java.util.Random;

/**
 *
 * @author Olivier
 */
public class Creature {
    private Net net;
    private int radius;
    private Color color;
    private Point<Integer> center;
    
    public Creature(int radius, Color color, Point<Integer> center, Net net) {
        this.radius = radius;
        this.color = color;
        this.center = center;
        this.net = net;
    }

    public Creature() throws TopologySizeException {
        this.radius = Life.rand.nextInt(30)+20;
        this.color = new Color(Life.rand.nextFloat(), Life.rand.nextFloat(), Life.rand.nextFloat());
        this.center = new Point<>(Life.rand.nextInt(Board.width), Life.rand.nextInt(Board.height));
        int topology[] = {2, 3, 2};
        this.net = new Net(topology);
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
        
        Double netInputs[] = {Life.rand.nextDouble()*4-2, Life.rand.nextDouble()*4-2};
        Double netOutputs[] = net.feedForward(netInputs);
        Double varX = netOutputs[0] * 10;
        Double varY = netOutputs[1] * 10;
        center.x += varX.intValue();
        center.y += varY.intValue();
    }
        
}
