/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.fgdo.life.Creature;

import com.sun.javafx.geom.Area;
import com.sun.javafx.geom.Ellipse2D;
import fr.fgdo.life.Creature.exceptions.FieldOfViewOutOfRangeException;
import fr.fgdo.life.Food.Food;
import fr.fgdo.life.GameObject.GameObject;
import fr.fgdo.life.GameState.Board.Board;
import fr.fgdo.life.GameState.Board.BoardView;
import fr.fgdo.life.Life;
import fr.fgdo.life.neuralNetwork.Net;
import fr.fgdo.life.neuralNetwork.exceptions.InputsSizeException;
import fr.fgdo.life.neuralNetwork.exceptions.TopologySizeException;
import fr.fgdo.math.Point;
import fr.fgdo.util.RandomNameGenerator;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import javafx.scene.shape.Circle;

/**
 *
 * @author Olivier
 */
public class Creature extends GameObject {
    
    private static final int MIN_FIELD_OF_VIEW = 15;
    private static final int MAX_FIELD_OF_VIEW = 50;
    
    private Net net;
    private double life = Life.rand.nextInt(500)+500;
    private double direction;
    private double fieldOfView = 25;
    private final String name;
    public ArrayList<GameObject> vision;
    
    private boolean[] visibleCreatures = new boolean[3];
    private boolean[] visibleFoods = new boolean[3];
    private boolean[] visibleMeteorologicalEvents = new boolean[3];
    private boolean overCreature;
    private boolean overMeteorologicalEvent;
    
    public Creature(int radius, Color color, Point center, double direction, Net net) {
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
        this.center = new Point(Life.rand.nextInt(Board.width), Life.rand.nextInt(Board.height));
        int topology[] = {3, 1, 2};
        this.net = new Net(topology);
        this.name = RandomNameGenerator.generateName();
        setDirection((double)Life.rand.nextInt(360));
        this.fieldOfView = (double)(MIN_FIELD_OF_VIEW + (int)(Math.random() * ((MAX_FIELD_OF_VIEW - MIN_FIELD_OF_VIEW) + 1)));
    }
    
    public Creature(Board board) throws TopologySizeException {
        this();
        this.board = board;
    }

    public String getName() {
        return name;
    }

    public Color getColor() {
        return color;
    }
    
    public void update() throws InputsSizeException {
        this.removeLife(1);
        System.out.println(visibleFoods[0] + " - " + visibleFoods[1] + " - " + visibleFoods[2]);
        Double netInputs[] = {this.life, Life.rand.nextDouble()*4-2, Life.rand.nextDouble()*4-2};
        Double netOutputs[] = net.feedForward(netInputs);
        Double varDirection = netOutputs[0] * 10;
        Double varSpeed = Math.abs(netOutputs[1] * 10);
        setDirection(direction + varDirection);
        center.x += (long)(Math.cos(Math.toRadians(direction)) * varSpeed);
        center.y += (long)(Math.cos(Math.toRadians(direction)) * varSpeed);
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
    
    public void removeLife(double lifePoint) {
        life -= lifePoint;
        if (life <= 0) {
            board.creatureIsDead(this);
        }
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
        super.draw(g, screenWidth, screenHeight, boardWidth, boardHeight);
        int xCenterScreen = BoardView.getLocalX(getCenter().x,screenWidth,boardWidth);
        int yCenterScreen = BoardView.getLocalY(getCenter().y,screenHeight,boardHeight);
        g.drawLine(xCenterScreen, yCenterScreen, xCenterScreen + (int) (Math.cos(Math.toRadians(getDirection())) * 100), yCenterScreen + (int) (Math.sin(Math.toRadians(getDirection())) * 100));
        g.drawLine(xCenterScreen, yCenterScreen, xCenterScreen + (int) (Math.cos(Math.toRadians(getDirection() + getFieldOfView())) * 100), yCenterScreen + (int) (Math.sin(Math.toRadians(getDirection() + getFieldOfView())) * 100));
        g.drawLine(xCenterScreen, yCenterScreen, xCenterScreen + (int) (Math.cos(Math.toRadians(getDirection() - getFieldOfView())) * 100), yCenterScreen + (int) (Math.sin(Math.toRadians(getDirection() - getFieldOfView())) * 100));
        g.setColor(Color.BLACK);
        g.drawString(getName(), xCenterScreen, yCenterScreen);
    }
 
    public void eat(Food food) {
        life += food.getValue();
    }

    public boolean[] getVisibleCreatures() {
        return visibleCreatures;
    }

    public void setVisibleCreatures(int index, boolean value) {
        this.visibleCreatures[index] = value;
    }

    public boolean[] getVisibleFoods() {
        return visibleFoods;
    }

    public void setVisibleFoods(int index, boolean value) {
        this.visibleFoods[index] = value;
    }

    public boolean[] getVisibleMeteorologicalEvents() {
        return visibleMeteorologicalEvents;
    }

    public void setVisibleMeteorologicalEvents(int index, boolean value) {
        this.visibleMeteorologicalEvents[index] = value;
    }

    public void setOverCreature(boolean overCreature) {
        this.overCreature = overCreature;
    }

    public void setOverMeteorologicalEvent(boolean overMeteorologicalEvent) {
        this.overMeteorologicalEvent = overMeteorologicalEvent;
    }
    
}
