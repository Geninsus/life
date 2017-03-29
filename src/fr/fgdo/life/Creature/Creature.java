/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.fgdo.life.Creature;

import fr.fgdo.life.Creature.exceptions.FieldOfViewOutOfRangeException;
import fr.fgdo.life.GameObject.GameObject;
import fr.fgdo.life.GameState.Board.Board;
import fr.fgdo.life.GameState.Board.BoardView;
import fr.fgdo.life.Life;
import fr.fgdo.life.neuralNetwork.Net;
import fr.fgdo.life.neuralNetwork.exceptions.InputsSizeException;
import fr.fgdo.life.neuralNetwork.exceptions.TopologySizeException;
import fr.fgdo.math.Point;
import fr.fgdo.util.RandomNameGenerator;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

/**
 *
 * @author Olivier
 */
public class Creature extends GameObject {
    
    private static final int MIN_FIELD_OF_VIEW = 15;
    private static final int MAX_FIELD_OF_VIEW = 50;
    
    private Net net;
    private double life = 100;
    private double direction;
    private double fieldOfView = 25;
    private final String name;
    public ArrayList<GameObject> vision;
    
    public Creature(int radius, Color color, Point<Integer> center, double direction, Net net) {
        this.radius = radius;
        this.color = color;
        this.center = center;
        this.net = net;
        this.name = RandomNameGenerator.generateName();
        setDirection(direction);
    }

    public Creature() throws TopologySizeException {
        this.radius = Life.rand.nextInt(30)+20;
        this.color = new Color(Life.rand.nextFloat(), Life.rand.nextFloat(), Life.rand.nextFloat());
        this.center = new Point<>(Life.rand.nextInt(Board.width), Life.rand.nextInt(Board.height));
        int topology[] = {3, 1, 2};
        this.net = new Net(topology);
        this.name = RandomNameGenerator.generateName();
        setDirection((double)Life.rand.nextInt(360));
        this.fieldOfView = (double)(MIN_FIELD_OF_VIEW + (int)(Math.random() * ((MAX_FIELD_OF_VIEW - MIN_FIELD_OF_VIEW) + 1)));
    }

    public String getName() {
        return name;
    }

    public Point<Integer> getCenter() {
        return center;
    }

    public Color getColor() {
        return color;
    }
    
    public void update() throws InputsSizeException {
        this.life -= 0.1;
        Double netInputs[] = {this.life, Life.rand.nextDouble()*4-2, Life.rand.nextDouble()*4-2};
        Double netOutputs[] = net.feedForward(netInputs);
        Double varDirection = netOutputs[0] * 10;
        Double varSpeed = Math.abs(netOutputs[1] * 10);
        setDirection(direction + varDirection);
        center.x += (int)(Math.cos(Math.toRadians(direction)) * varSpeed);
        center.y += (int)(Math.cos(Math.toRadians(direction)) * varSpeed);
    }

    /**
     * @return the direction
     */
    public double getDirection() {
        return direction;
    }

    /**
     * @param direction the direction to set
     */
    public void setDirection(double direction) {
        direction = direction % 360;
        if(direction < 0) direction += 360;
        this.direction = direction;
    }
    
    public void removeLife(int lifePoint) {
        life -= lifePoint;
    }

    /**
     * @return the fieldOfView
     */
    public double getFieldOfView() {
        return fieldOfView;
    }

    /**
     * @param fieldOfView the fieldOfView to set
     */
    public void setFieldOfView(double fieldOfView) throws FieldOfViewOutOfRangeException {
        if(fieldOfView < MIN_FIELD_OF_VIEW || fieldOfView > MAX_FIELD_OF_VIEW) {
            throw new FieldOfViewOutOfRangeException("Field of view out of range");
        }
        this.fieldOfView = fieldOfView;
    }

    @Override
    public void draw(Graphics g, int screenWidth, int screenHeight, int boardWidth, int boardHeight) {
        super.draw(g, screenWidth, screenHeight, boardWidth, boardHeight); //To change body of generated methods, choose Tools | Templates.
        int xCenterScreen = BoardView.getLocalX(getCenter().x,screenWidth,boardWidth);
        int yCenterScreen = BoardView.getLocalY(getCenter().y,screenHeight,boardHeight);
        g.drawLine(xCenterScreen, yCenterScreen, xCenterScreen + (int) (Math.cos(Math.toRadians(getDirection())) * 100), yCenterScreen + (int) (Math.sin(Math.toRadians(getDirection())) * 100));
        g.drawLine(xCenterScreen, yCenterScreen, xCenterScreen + (int) (Math.cos(Math.toRadians(getDirection() + getFieldOfView())) * 100), yCenterScreen + (int) (Math.sin(Math.toRadians(getDirection() + getFieldOfView())) * 100));
        g.drawLine(xCenterScreen, yCenterScreen, xCenterScreen + (int) (Math.cos(Math.toRadians(getDirection() - getFieldOfView())) * 100), yCenterScreen + (int) (Math.sin(Math.toRadians(getDirection() - getFieldOfView())) * 100));
        g.setColor(Color.BLACK);
        g.drawString(getName(), xCenterScreen, yCenterScreen);
    }
 
    
    
        
}
