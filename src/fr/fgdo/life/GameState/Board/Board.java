/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.fgdo.life.GameState.Board;

import fr.fgdo.life.Creature.Creature;
import fr.fgdo.life.Creature.CreatureListener;
import fr.fgdo.life.GameState.Board.Events.MeteorologicalEvent;
import fr.fgdo.life.GameState.Board.Events.MeteorologicalEventListener;
import fr.fgdo.life.GameObject.GameObject;
import fr.fgdo.life.neuralNetwork.exceptions.InputsSizeException;
import fr.fgdo.math.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Timer;
import javax.swing.event.EventListenerList;

/**
 *
 * @author Olivier
 */
public class Board extends Observable implements ActionListener,MeteorologicalEventListener,CreatureListener{
    
    private float speed = 1;
    private Timer timerUpdate;
    private long lastUpdate;
    private boolean runningGame = false;
    public static int width;
    public static int height;
    private final String name;
    ArrayList<Creature> creatures;
    ArrayList<MeteorologicalEvent> meteorologicalEvents;
    ArrayList<MeteorologicalEvent> toRemoveMeteorologicalEvents;
    ArrayList<Creature> toRemoveCreatures;
    public long iteration = 0;
    
    
    
    public Board(BoardParams params) {
        this.timerUpdate = new Timer(20, this);
        this.creatures = new ArrayList<>();
        this.meteorologicalEvents = new ArrayList<>();
        this.toRemoveMeteorologicalEvents = new ArrayList<>();
        this.toRemoveCreatures = new ArrayList<>();
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
    
    public void update() {
        
        for (Iterator<MeteorologicalEvent> iterator = toRemoveMeteorologicalEvents.iterator(); iterator.hasNext();) {
            MeteorologicalEvent next = iterator.next();
            meteorologicalEvents.remove(next);
            iterator.remove();
        }
        
        for (Iterator<Creature> iterator = toRemoveCreatures.iterator(); iterator.hasNext();) {
            Creature next = iterator.next();
            creatures.remove(next);
            iterator.remove();
        }
        
        for (MeteorologicalEvent meteorologicalEvent : meteorologicalEvents) {
            creatures.forEach((creature) -> {
                meteorologicalEvent.checkCreature(creature);
            });
            meteorologicalEvent.update();
        }
        
        creatures.forEach((creature) -> {
            try {
                updateCreaturesViews(creature);
                creature.update();
                checkCreature(creature);
            } catch (InputsSizeException ex) {
                Logger.getLogger(Board.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        iteration++;
    }
    
    public void checkCreature(Creature creature) {
        if (creature.getCenter().x + creature.getRadius() > width) creature.getCenter().x = width-creature.getRadius();
        if (creature.getCenter().x - creature.getRadius() < 0) creature.getCenter().x = creature.getRadius();
        if (creature.getCenter().y + creature.getRadius() > height) creature.getCenter().y = height-creature.getRadius();
        if (creature.getCenter().y - creature.getRadius() < 0) creature.getCenter().y = creature.getRadius();
        for (Creature creatureToTest : creatures) {
            if (creatureToTest != creature && creature.intersect(creatureToTest)) {
                System.out.println(creature.getName() + " intersect " + creatureToTest.getName());
            }
        }
    }
    
    public void updateCreaturesViews(Creature creature) {
        for (GameObject otherCreature : creatures) {
            if(otherCreature != creature) {
                
                int lineX = creature.getCenter().x + (int) (Math.cos(Math.toRadians(creature.getDirection())) * 100);
                int lineY = creature.getCenter().y + (int) (Math.sin(Math.toRadians(creature.getDirection())) * 100);
                
                if(getCircleLineIntersectionPoint(creature.getCenter(), new Point(lineX, lineY), otherCreature.getCenter(), otherCreature.getRadius()).size() > 0) {
                    System.out.println(creature.getName() + " voit qqch");
                }
                
            }
        }
    }
    
    public ArrayList<Point> getCircleLineIntersectionPoint(Point pointA, Point pointB, Point center, int radius) {
        int baX = pointB.x - pointA.x;
        int baY = pointB.y - pointA.y;
        int caX = center.x - pointA.x;
        int caY = center.y - pointA.y;

        int a = baX * baX + baY * baY;
        int bBy2 = baX * caX + baY * caY;
        int c = caX * caX + caY * caY - radius * radius;

        double pBy2 = bBy2 / a;
        double q = c / a;

        double disc = pBy2 * pBy2 - q;
        if (disc < 0) {
            return new ArrayList<>();
        }

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
        
        ArrayList<Point> arrayList = new ArrayList<>();
        
        Point p1 = new Point((int)(pointA.x - baX * abScalingFactor1), (int)(pointA.y
                - baY * abScalingFactor1));
        
        if(p1.x >= lowerX && (int)p1.x <= upperX && (int)p1.y >= lowerY && (int)p1.y <= upperY) {
            arrayList.add(p1);
        }
        
        if (disc != 0) { 
        
            Point p2 = new Point((int)(pointA.x - baX * abScalingFactor2), (int)(pointA.y
                    - baY * abScalingFactor2));

            if((int)p2.x >= lowerX && (int)p2.x <= upperX && (int)p2.y >= lowerY && (int)p2.y <= upperY) {
                arrayList.add(p2);
            }
        }
        return arrayList;     
    }
    
    public ArrayList<Creature> getCreatures() {
        return creatures;
    }

    public ArrayList<MeteorologicalEvent> getMeteorologicalEvents() {
        return meteorologicalEvents;
    }
    
    public void addCreature(Creature creature) {
        creatures.add(creature);
    }

    public void addEvent(MeteorologicalEvent meteorologicalEvent) {
        meteorologicalEvents.add(meteorologicalEvent);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(runningGame && e.getSource() == timerUpdate){
            update();
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

    
}
