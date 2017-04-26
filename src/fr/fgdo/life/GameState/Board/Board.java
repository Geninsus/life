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
import fr.fgdo.life.GameState.Board.Events.MeteorologicalEventsTypes;
import fr.fgdo.life.Life;
import fr.fgdo.life.neuralNetwork.exceptions.ArraySizeException;
import fr.fgdo.life.neuralNetwork.exceptions.InputsSizeException;
import fr.fgdo.life.neuralNetwork.exceptions.TopologySizeException;
import fr.fgdo.math.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
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
    ArrayList<GameObject> gameObjects;
    ArrayList<Creature> creatures;
    ArrayList<MeteorologicalEvent> meteorologicalEvents;
    ArrayList<Food> foods;
    ArrayList<MeteorologicalEvent> toRemoveMeteorologicalEvents;
    ArrayList<Creature> toRemoveCreatures;
    ArrayList<Food> toRemoveFoods;
    public long iteration = 0;
    
    
    /*Config Generate*/
    public int numberCreatureToGenerate = 10;
    public int interevalCreatureToGenerate = 1000;
    public int nextGenerationCreatures = interevalCreatureToGenerate;
    
    public int numberFoodToGenerate = 10;
    public int interevalFoodToGenerate = 1000;
    public int nextGenerationFoods = interevalFoodToGenerate;
    
    public int numberEventToGenerate = 10;
    public int interevalEventToGenerate = 1000;
    public int nextGenerationEvents = interevalEventToGenerate;
    
    public Board(BoardParams params) {
        this.timerUpdate = new Timer(20, this);
        this.gameObjects = new ArrayList<>();
        Board.width = params.size.x;
        Board.height = params.size.y;
        this.name = params.name;
        timerUpdate.start();
        
        this.creatures = new ArrayList<>();
        this.meteorologicalEvents = new ArrayList<>();
        this.foods = new ArrayList<>();
        this.toRemoveMeteorologicalEvents = new ArrayList<>();
        this.toRemoveCreatures = new ArrayList<>();
        this.toRemoveFoods = new ArrayList<>();
    }
    
    public void updateView(String arg) {
        setChanged();
        notifyObservers(arg);
        clearChanged();
    }
    
    public void generateFood() {
        if (iteration >= nextGenerationFoods) {
            addFood(numberFoodToGenerate);
            nextGenerationFoods = (int) (iteration+interevalFoodToGenerate);
        }
    }
    
    public void generateEvent() {
        /*To Implement*/
    }
    
    public void reproduce() throws TopologySizeException, ArraySizeException {
        if (Life.rand.nextFloat() > 0.99) {
          for (int i = 0; i < 30; i++) {
                Creature creature1 = creatures.get((int)(Math.random() * creatures.size()));
                Creature creature2 = creatures.get((int)(Math.random() * creatures.size())); 

                addCreature(new Creature(creature1, creature2));
            }  
        }
    }
    
    public void update() throws TopologySizeException, ArraySizeException, InputsSizeException {
                
        // Génération food
        generateFood();
        
        // Génération event
        //generateEvent();
        
        // Update des GameObjects
        updateGameObjects();
        removeGameOjects();
        
        if(creatures.size() > 0) reproduce();
        
        
        
        iteration++;
    }
    
    public void updateGameObjects() throws InputsSizeException {
        
        /* RESET OBJECTS */
        for (int i = 0; i < gameObjects.size(); i++) {
            
            /* CREATURE */
            if(gameObjects.get(i) instanceof Creature) {
                Creature creature = (Creature) gameObjects.get(i);
                creature.setOverCreature(false);
                creature.setVisibleFoods(1, false);
                creature.setVisibleMeteorologicalEvents(1, false);
                
            /* FOOD */
            } else if(gameObjects.get(i) instanceof Food) {
                Food food = (Food) gameObjects.get(i);
                
            /* METEOROLOGICALEVENT */
            } else if(gameObjects.get(i) instanceof MeteorologicalEvent) {
                 MeteorologicalEvent meteorologicalEvent = (MeteorologicalEvent) gameObjects.get(i);
            }
        }
        
        /* DO STUFF WITH OBJECTS */
        for (int i = 0; i < gameObjects.size(); i++) {
            
            /* CREATURE */
            if(gameObjects.get(i) instanceof Creature) {
                Creature creature = (Creature) gameObjects.get(i);
                
                creature.update();
                
                /* ITERATE ALL GAMEOBJECTS */
                for (int j = 0; j < gameObjects.size(); j++) {
                    
                    /* CREATURE */
                    if(gameObjects.get(j) instanceof Creature) {
                        if(i != j) {
                            Creature otherCreature = (Creature) gameObjects.get(j);
                            
                            /* Si une créature rencontre une autre créature */
                            if(creature.intersect(otherCreature)) {
                                creature.setOverCreature(true);
                           }
                            
                           /* Si une créature voit une autre créature */
                           
                           int lineX = creature.getCenter().x + (int) (Math.cos(Math.toRadians(creature.getDirection())) * 100);
                           int lineY = creature.getCenter().y - (int) (Math.sin(Math.toRadians(creature.getDirection())) * 100);

                            if(getCircleLineIntersectionPoint(creature.getCenter(), new Point(lineX, lineY), otherCreature.getCenter(), otherCreature.getRadius()).size() > 0) {
                                creature.setVisibleCreatures(1, true);
                            }
                        }
                        
                    /* FOOD */
                    } else if(gameObjects.get(j) instanceof Food) {
                        Food food = (Food) gameObjects.get(j);
                        
                        /* Miam miam */
                        if (food.toDelete == false && creature.intersect(food)) {
                            creature.eat(food);
                            food.toDelete = true;
                        }

                    /* METEOROLOGICALEVENT */
                    } else if(gameObjects.get(j) instanceof MeteorologicalEvent) {
                         MeteorologicalEvent meteorologicalEvent = (MeteorologicalEvent) gameObjects.get(j);
                         meteorologicalEvent.checkCreature(creature);
                    }        
                }
                
                
            /* FOOD */
            } else if(gameObjects.get(i) instanceof Food) {
                Food food = (Food) gameObjects.get(i);
            
            /* METEOROLOGICALEVENT */
            } else if(gameObjects.get(i) instanceof MeteorologicalEvent) {
                 MeteorologicalEvent meteorologicalEvent = (MeteorologicalEvent) gameObjects.get(i);
                  meteorologicalEvent.update();
            }
        }
    }
    
    public void removeGameOjects() {
        
        Iterator<GameObject> it = gameObjects.iterator();
        while(it.hasNext()){
            GameObject gameObject = it.next();
            if(gameObject.toDelete == true)it.remove();
        }
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
        gameObjects.add(creature);
    }

    
    public void addFood() {
        addFood(new Food());
    }
    
    public void addFood(Food food) {
        gameObjects.add(food);
    }
    
    public void addFood(int n) {
        for (int i = 0; i < n; i++) {
            addFood();
        }
    }
    
    public void addEvent(MeteorologicalEvent meteorologicalEvent) {
        gameObjects.add(meteorologicalEvent);
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
            } catch (InputsSizeException ex) {
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
        creature.toDelete = true;
    }    
}
