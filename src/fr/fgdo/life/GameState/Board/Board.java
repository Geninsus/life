/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.fgdo.life.GameState.Board;

import fr.fgdo.life.Creature.Creature;
import fr.fgdo.life.Creature.CreatureListener;
import fr.fgdo.life.Food.Food;
import fr.fgdo.life.GameState.Board.Events.MeteorologicalEvent;
import fr.fgdo.life.GameState.Board.Events.MeteorologicalEventListener;
import fr.fgdo.life.GameObject.GameObject;
import fr.fgdo.life.Life;
import fr.fgdo.life.neuralNetwork.exceptions.ArraySizeException;
import fr.fgdo.life.neuralNetwork.exceptions.InputsSizeException;
import fr.fgdo.life.neuralNetwork.exceptions.TopologySizeException;
import fr.fgdo.math.Point;
import java.awt.Color;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Timer;

/**
 *
 * @author Olivier
 */
public class Board extends Observable implements ActionListener,MeteorologicalEventListener,CreatureListener{
    
    private boolean generateFood = true;
    private float speed = 1;
    private Timer timerUpdate;
    private long lastUpdate;
    private boolean runningGame = false;
    public static int width;
    public static int height;
    private final String name;
    ArrayList<Creature> creatures;
    ArrayList<MeteorologicalEvent> meteorologicalEvents;
    ArrayList<Food> foods;
    ArrayList<MeteorologicalEvent> toRemoveMeteorologicalEvents;
    ArrayList<Creature> toRemoveCreatures;
    ArrayList<Food> toRemoveFoods;
    public long iteration = 0;
    
    
    
    public Board(BoardParams params) {
        this.timerUpdate = new Timer(20, this);
        this.creatures = new ArrayList<>();
        this.meteorologicalEvents = new ArrayList<>();
        this.foods = new ArrayList<>();
        this.toRemoveMeteorologicalEvents = new ArrayList<>();
        this.toRemoveCreatures = new ArrayList<>();
        this.toRemoveFoods = new ArrayList<>();
        Board.width = params.size.x;
        Board.height = params.size.y;
        this.name = params.name;
        timerUpdate.start();
    }
    
    public void updateView(String arg) {
        setChanged();
        notifyObservers(arg);
        clearChanged();
    }
    
    public void generateFood() {
        if (generateFood) {
            if (Life.rand.nextFloat() > 0.95) {
                addFood(new Food());
            }
        }
    }
    
    public void reproduce() throws TopologySizeException, ArraySizeException {
        
        if (Life.rand.nextFloat() > 0.99) {

            Creature creature1 = creatures.get((int)(Math.random() * creatures.size()));
            Creature creature2 = creatures.get((int)(Math.random() * creatures.size())); 

            addCreature(new Creature(creature1, creature2));
        
        }
    }
    
    public void generateEvent() {
        /*To Implement*/
    }
    
    public void updateMeteorologicalEvents() {
        for (MeteorologicalEvent meteorologicalEvent : meteorologicalEvents) {
            creatures.forEach((creature) -> {
                meteorologicalEvent.checkCreature(creature);
            });
            meteorologicalEvent.update();
        }
    }
    
    public void updateCreatures() {
        creatures.forEach((creature) -> {
            try {
                updateCreaturesIntersects(creature);
                updateCreaturesViews(creature);
                creature.update();
                checkBoundsCreature(creature);
            } catch (InputsSizeException ex) {
                Logger.getLogger(Board.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
    
    public void update() throws TopologySizeException, ArraySizeException {
        generateFood();
        
        updateMeteorologicalEventsArray();
        updateCreaturesArray();
        updateFoodsArray();
        
        updateMeteorologicalEvents();
        updateCreatures();
        
        if(creatures.size() > 0) reproduce();
        
        iteration++;
    }
    
    public void checkBoundsCreature(Creature creature) {
        if (creature.getCenter().x + creature.getRadius() > width) creature.getCenter().x = width-creature.getRadius();
        if (creature.getCenter().x - creature.getRadius() < 0) creature.getCenter().x = creature.getRadius();
        if (creature.getCenter().y + creature.getRadius() > height) creature.getCenter().y = height-creature.getRadius();
        if (creature.getCenter().y - creature.getRadius() < 0) creature.getCenter().y = creature.getRadius();
        for (Creature creatureToTest : creatures) {
            if (creatureToTest != creature && creature.intersect(creatureToTest)) {
                //System.out.println(creature.getName() + " intersect " + creatureToTest.getName());
            }
        }
        for (Food food : foods) {
            if (creature.intersect(food)) {
                creature.eat(food);
                toRemoveFoods.add(food);
            }
        }
        updateFoodsArray();
    }
    
    public void updateCreaturesIntersects(Creature creature) {
        for (Creature creatureToTest : creatures) {
            if (creature != creatureToTest && creature.intersect(creatureToTest)) {
                creature.setOverCreature(true);
                return;
            }
        }
        creature.setOverCreature(false);
    }
    
    public void updateMeteorologicalEventsIntersects(Creature creature) {
        for (MeteorologicalEvent meteorologicalEvent : meteorologicalEvents) {
            if (creature.intersect(meteorologicalEvent)) {
                creature.setOverMeteorologicalEvent(true);
                return;
            }
        }
        creature.setOverMeteorologicalEvent(false);
    }
    
    public void updateCreaturesViews(Creature creature) {
        
        
        ArrayList<GameObject> gameObjects = new ArrayList<>();
        //gameObjects.addAll(creatures);
        gameObjects.addAll(foods);
        //gameObjects.addAll(meteorologicalEvents);
        
        
        /* RESET capteurs */
        creature.setVisibleFoods(1, false);
        
        
        for (GameObject otherGameObject : gameObjects) {
            if(otherGameObject != creature) {
                                        
                int lineX = creature.getCenter().x + (int) (Math.cos(Math.toRadians(creature.getDirection())) * 100);
                int lineY = creature.getCenter().y - (int) (Math.sin(Math.toRadians(creature.getDirection())) * 100);
                                
                if(getCircleLineIntersectionPoint(creature.getCenter(), new Point(lineX, lineY), otherGameObject.getCenter(), otherGameObject.getRadius()).size() > 0) {
                    creature.setVisibleFoods(1, true);
                } else {
                }
                
            }
        }
    }
    
    public ArrayList<Point> getCircleLineIntersectionPoint(Point pointA,Point pointB, Point center, double radius) {
        double baX = pointB.x - pointA.x;
        double baY = pointB.y - pointA.y;
        double caX = center.x - pointA.x;
        double caY = center.y - pointA.y;

        double a = baX * baX + baY * baY;
        double bBy2 = baX * caX + baY * caY;
        double c = caX * caX + caY * caY - radius * radius;

        double pBy2 = bBy2 / a;
        double q = c / a;

        double disc = pBy2 * pBy2 - q;
        if (disc < 0) {
            return new ArrayList<>();
        }
        // if disc == 0 ... dealt with later
        double tmpSqrt = Math.sqrt(disc);
        double abScalingFactor1 = -pBy2 + tmpSqrt;
        double abScalingFactor2 = -pBy2 - tmpSqrt;
        
        int lowerX, upperX, lowerY, upperY;
        
        if(pointA.x < pointB.x) {
            lowerX = pointA.x;
            upperX = pointB.x;
        } else {
           lowerX = pointB.x;
           upperX = pointA.x; 
        }
        
        if(pointA.y < pointB.y) {
            lowerY = pointA.y;
            upperY = pointB.y;
        } else {
           lowerY = pointB.y;
           upperY = pointA.y; 
        }
        
        ArrayList toto = new ArrayList<>();
        
        Point p1 = new Point((int)(pointA.x - baX * abScalingFactor1),(int)(pointA.y - baY * abScalingFactor1));
        if (disc == 0) { // abScalingFactor1 == abScalingFactor2
            if(p1.x >= lowerX && p1.x < upperX && p1.y >= lowerY && p1.y < upperY) {
                toto.add(p1);
            }
            toto.add(p1);
            return toto;
        }
        Point p2 = new Point((int)(pointA.x - baX * abScalingFactor2),(int)(pointA.y - baY * abScalingFactor2));
            if(p2.x >= lowerX && p2.x < upperX && p2.y >= lowerY && p2.y < upperY) {
                toto.add(p2);
            }
            return toto;
    }
    
    public ArrayList<Creature> getCreatures() {
        return creatures;
    }

    public ArrayList<MeteorologicalEvent> getMeteorologicalEvents() {
        return meteorologicalEvents;
    }

    public ArrayList<Food> getFoods() {
        return foods;
    }
    
    public void addCreature(Creature creature) {
        creatures.add(creature);
    }

    public void addFood(Food food) {
        foods.add(food);
    }
    
    public void addEvent(MeteorologicalEvent meteorologicalEvent) {
        meteorologicalEvents.add(meteorologicalEvent);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(runningGame && e.getSource() == timerUpdate){
            try {
                update();
            } catch (TopologySizeException ex) {
                Logger.getLogger(Board.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ArraySizeException ex) {
                Logger.getLogger(Board.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (System.nanoTime()- lastUpdate > 20000000) {
                updateView("View:update");
                lastUpdate = System.nanoTime();
            }
        }
        
    }

    public void run() {
        runningGame = true;
        updateView("TabbedView:run");
    }
    
    public void pause() {
        runningGame =false;
        updateView("TabbedView:pause");
    }
    
    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setSpeed(float speed) {
        if (this.speed != speed) {
            this.speed = speed;
            timerUpdate.setDelay((int) (20.0/speed));
        }
        
    }

    @Override
    public void meteorologicalEventOver(MeteorologicalEvent me) {
        toRemoveMeteorologicalEvents.add(me);
    }

    @Override
    public void creatureIsDead(Creature creature) {
        toRemoveCreatures.add(creature);
    }

    public void updateFoodsArray() {
        for (Iterator<Food> iterator = toRemoveFoods.iterator(); iterator.hasNext();) {
            Food next = iterator.next();
            foods.remove(next);
            iterator.remove();
        }
    }
    
    public void updateMeteorologicalEventsArray() {
        for (Iterator<MeteorologicalEvent> iterator = toRemoveMeteorologicalEvents.iterator(); iterator.hasNext();) {
            MeteorologicalEvent next = iterator.next();
            meteorologicalEvents.remove(next);
            iterator.remove();
        }
    }
    
    public void updateCreaturesArray() {
        for (Iterator<Creature> iterator = toRemoveCreatures.iterator(); iterator.hasNext();) {
            Creature next = iterator.next();
            creatures.remove(next);
            iterator.remove();
        }
    }
    
}
