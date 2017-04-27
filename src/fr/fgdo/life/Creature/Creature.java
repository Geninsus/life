/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.fgdo.life.Creature;

import fr.fgdo.life.Creature.exceptions.FieldOfViewOutOfRangeException;
import fr.fgdo.life.Food.Food;
import fr.fgdo.life.GameObject.GameObject;
import fr.fgdo.life.GameState.Board.Board;
import static fr.fgdo.life.GameState.Board.Board.height;
import static fr.fgdo.life.GameState.Board.Board.width;
import fr.fgdo.life.GameState.Board.BoardView;
import fr.fgdo.life.Life;
import fr.fgdo.life.neuralNetwork.Net;
import fr.fgdo.life.neuralNetwork.exceptions.ArraySizeException;
import fr.fgdo.life.neuralNetwork.exceptions.InputsSizeException;
import fr.fgdo.life.neuralNetwork.exceptions.TopologySizeException;
import fr.fgdo.math.Point;
import fr.fgdo.util.RandomNameGenerator;
import java.awt.Color;
import java.awt.Graphics;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Olivier
 */
public final class Creature extends GameObject implements Serializable {
    
    /*CONST*/
    private static final int MIN_FIELD_OF_VIEW = 15;
    private static final int MAX_FIELD_OF_VIEW = 50;
    private static final int MAX_LIFE = 1000;
    private static final float MUTATION_RATE = (float)0.3;
    
    /*Characteristics*/
    private String name;
    private double life = MAX_LIFE;
    private Net net;
    private double direction;
    private double fieldOfView = 25;
    private double distanceOfView = 200;
    
    /*View*/
    public ArrayList<GameObject> vision;
    
    private boolean[] visibleCreatures = new boolean[3];
    private boolean[] visibleFoods = new boolean[3];
    private boolean[] visibleMeteorologicalEvents = new boolean[3];
    
    /*Position*/
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
        //this.radius = Life.rand.nextInt(30)+20;
        this.radius = 15;
        this.color = new Color(Life.rand.nextFloat(), Life.rand.nextFloat(), Life.rand.nextFloat());
        this.center = new Point(Life.rand.nextInt(Board.width), Life.rand.nextInt(Board.height));
        int topology[] = {1, 1, 2};
        this.name = RandomNameGenerator.generateName();
        this.net = new Net(topology,this.name);
        setDirection((double)Life.rand.nextInt(360));
        //setDirection((double)Life.rand.nextInt(360));
        this.direction = 80;
        this.fieldOfView = 30;
        //this.fieldOfView = (double)(MIN_FIELD_OF_VIEW + (int)(Math.random() * ((MAX_FIELD_OF_VIEW - MIN_FIELD_OF_VIEW) + 1)));
    }
    
    public Creature(Creature... creatures) throws TopologySizeException, ArraySizeException {
        
        if (creatures.length == 0) {
            throw new ArraySizeException(creatures.length);
        }
        this.radius = creatures[Life.rand.nextInt(creatures.length)].getRadius();
        this.color = creatures[Life.rand.nextInt(creatures.length)].getColor();
        this.center = new Point(Life.rand.nextInt(Board.width), Life.rand.nextInt(Board.height));
        this.direction = 0;
        this.name = RandomNameGenerator.generateName();
        Net[] parentsNets = new Net[creatures.length];
        for (int i = 0; i < creatures.length; i++) {
            parentsNets[i]=creatures[i].net;
        }
        this.net = new Net(this.name,parentsNets);
        this.board = creatures[0].board;
        this.fieldOfView = creatures[Life.rand.nextInt(creatures.length)].getFieldOfView();
        this.life = MAX_LIFE;
        mutate();
    }
    

    public String getName() {
        return name;
    }

    public void mutate() {
        //if (Life.rand.nextFloat() < MUTATION_RATE) this.life = MAX_LIFE;
        if (Life.rand.nextFloat() < MUTATION_RATE) this.radius = Life.rand.nextInt(10)+10;
    }
    
    public void update() throws InputsSizeException {
        this.color = new Color((255-(int)(life/MAX_LIFE*255))%255, 255, 0);
        this.removeLife(1);
        double food = (visibleFoods[1])? 1 : -1;
        //double meteorological = (visibleMeteorologicalEvents[1])? 1 : -1;
        Double netInputs[] = {food};
        Double netOutputs[] = net.feedForward(netInputs);
        Double varDirection = netOutputs[0] * 10;
        Double varSpeed = Math.abs(netOutputs[1] * 10);
        this.updatePosition(varDirection, varSpeed);
        setChanged();
        notifyObservers();
        clearChanged();
    }
    
    private void updatePosition(double varDirection, double varSpeed) {
        setDirection(direction + varDirection);
        center.x += (long)(Math.cos(Math.toRadians(direction)) * varSpeed);
        center.y -= (long)(Math.sin(Math.toRadians(direction)) * varSpeed);
        
        if (getCenter().x + getRadius() > width) getCenter().x = width-getRadius();
        if (getCenter().x - getRadius() < 0) getCenter().x = getRadius();
        if (getCenter().y + getRadius() > height) getCenter().y = height-getRadius();
        if (getCenter().y - getRadius() < 0) getCenter().y = getRadius();
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
    
    public void showNet(){
        this.net.show();
    }

    @Override
    public void draw(Graphics g, int screenWidth, int screenHeight, int boardWidth, int boardHeight) {
        super.draw(g, screenWidth, screenHeight, boardWidth, boardHeight);
        int xCenterScreen = BoardView.getLocalX(getCenter().x,screenWidth,boardWidth);
        int yCenterScreen = BoardView.getLocalY(getCenter().y,screenHeight,boardHeight);
        if (BoardView.showingCreaturesVisions) g.drawLine(xCenterScreen, yCenterScreen, BoardView.getLocalX(getCenter().x + (int) (Math.cos(-1*Math.toRadians(getDirection())) * distanceOfView),screenHeight,boardHeight), BoardView.getLocalY(getCenter().y + (int) (Math.sin(-1*Math.toRadians(getDirection())) * distanceOfView),screenHeight,boardHeight));
        //g.drawLine(xCenterScreen, yCenterScreen, xCenterScreen + (int) (Math.cos(Math.toRadians(getDirection())) * 100), yCenterScreen + (int) (Math.sin(Math.toRadians(getDirection())) * 100));
        //g.drawLine(xCenterScreen, yCenterScreen, xCenterScreen + (int) (Math.cos(Math.toRadians(getDirection() + getFieldOfView())) * 100), yCenterScreen + (int) (Math.sin(Math.toRadians(getDirection() + getFieldOfView())) * 100));
        //g.drawLine(xCenterScreen, yCenterScreen, xCenterScreen + (int) (Math.cos(Math.toRadians(getDirection() - getFieldOfView())) * 100), yCenterScreen + (int) (Math.sin(Math.toRadians(getDirection() - getFieldOfView())) * 100));
        g.setColor(Color.BLACK);
        if (BoardView.showingCreaturesNames) g.drawString(getName(), xCenterScreen, yCenterScreen);
    }
 
    public void eat(Food food) {
        life += food.getValue();
        if(life > MAX_LIFE) life = MAX_LIFE;
    }

    @Override
    public String toString() {
        return getName()+"\n"+getCenter().x+" , "+getCenter().y+"\n"+getRadius();
    }

    
    public void save() {
        try{
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(
            new FileOutputStream("savedCreatures/"+getName()+".ser"));
            objectOutputStream.writeObject(this);
            System.out.println("Saved !");
        }
        catch(IOException ioException){
            System.err.println(ioException);
        }
    }
    
    public static Creature open(String name) {
        Creature creature = null;
        try{
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(name));
            creature = (Creature) objectInputStream.readObject();
        }
        catch(IOException ioException){
            System.err.println(ioException);
        }
        catch(ClassNotFoundException classNotFoundException){
            System.err.println(classNotFoundException);
        }
        return creature;
    }

    public Creature clone() {
        try {
            Creature creature = new Creature();
            creature.setName(name);
            creature.setNet(net);
            creature.setLife(life);
            creature.setDistanceOfView(distanceOfView);
            creature.setRadius(radius);
            creature.setColor(color);
            return creature;
        } catch (TopologySizeException ex) {
            Logger.getLogger(Creature.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void setLife(double life) {
        this.life = life;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNet(Net net) {
        this.net = net;
    }

    public void setDistanceOfView(double distanceOfView) {
        this.distanceOfView = distanceOfView;
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
    
    public double getLife() {
        return life;
    }

    public Net getNet() {
        return net;
    }

    public double getDistanceOfView() {
        return distanceOfView;
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
    
    
    public boolean isOverCreature() {
        return overCreature;
    }

    public boolean isOverMeteorologicalEvent() {
        return overMeteorologicalEvent;
    }

    
}
