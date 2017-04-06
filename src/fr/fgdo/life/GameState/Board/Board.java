/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.fgdo.life.GameState.Board;

import fr.fgdo.life.Creature.Creature;
import fr.fgdo.life.GameObject.GameObject;
import fr.fgdo.life.neuralNetwork.exceptions.InputsSizeException;
import fr.fgdo.math.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Timer;

/**
 *
 * @author Olivier
 */
public class Board extends Observable implements ActionListener{
    
    private float speed = 1;
    private Timer timerUpdate;
    private long lastUpdate;
    private boolean runningGame = false;
    public static int width;
    public static int height;
    private final String name;
    ArrayList<Creature> creatures;
    public long iteration = 0;
    
    
    
    public Board(BoardParams params) {
        this.timerUpdate = new Timer(20, this);
        this.creatures = new ArrayList<>();
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
    }
    
    public void updateCreaturesViews(Creature creature) {
        for (GameObject otherCreature : creatures) {
            if(otherCreature != creature) {
                
                long lineX = creature.getCenter().x + (long) (Math.cos(Math.toRadians(creature.getDirection())) * 100);
                long lineY = creature.getCenter().y + (long) (Math.sin(Math.toRadians(creature.getDirection())) * 100);
                
                if(getCircleLineIntersectionPoint(creature.getCenter(), new Point(lineX, lineY), otherCreature.getCenter(), otherCreature.getRadius()).size() > 0) {
                    System.out.println(creature.getName() + " voit qqch");
                }
                
            }
        }
    }
    
    public ArrayList<Point> getCircleLineIntersectionPoint(Point pointA, Point pointB, Point center, int radius) {
        long baX = pointB.x - pointA.x;
        long baY = pointB.y - pointA.y;
        long caX = center.x - pointA.x;
        long caY = center.y - pointA.y;

        long a = baX * baX + baY * baY;
        long bBy2 = baX * caX + baY * caY;
        long c = caX * caX + caY * caY - radius * radius;

        double pBy2 = bBy2 / a;
        double q = c / a;

        double disc = pBy2 * pBy2 - q;
        if (disc < 0) {
            return new ArrayList<>();
        }

        double tmpSqrt = Math.sqrt(disc);
        double abScalingFactor1 = -pBy2 + tmpSqrt;
        double abScalingFactor2 = -pBy2 - tmpSqrt;
        
        long lowerX, upperX, lowerY, upperY;
        
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
        
        Point p1 = new Point((long)(pointA.x - baX * abScalingFactor1),(long) (pointA.y
                - baY * abScalingFactor1));
        
        if(p1.x >= lowerX && (int)p1.x <= upperX && (int)p1.y >= lowerY && (int)p1.y <= upperY) {
            arrayList.add(p1);
        }
        
        if (disc != 0) { 
        
            Point p2 = new Point((long)(pointA.x - baX * abScalingFactor2), (long)(pointA.y
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
    
    public void addCreature(Creature creature) {
        creatures.add(creature);
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

    
}
