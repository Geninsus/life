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
import fr.fgdo.life.GameObject.GameObject;
import fr.fgdo.life.GameState.Board.Events.MeteorologicalEventsTypes;
import fr.fgdo.life.Life;
import fr.fgdo.life.neuralNetwork.exceptions.ArraySizeException;
import fr.fgdo.life.neuralNetwork.exceptions.InputsSizeException;
import fr.fgdo.life.neuralNetwork.exceptions.TopologySizeException;
import fr.fgdo.math.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
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
public class Board extends Observable implements ActionListener,CreatureListener, Serializable{
    
    private boolean generateFood = true;
    private float speed = 1;
    private Timer timerUpdate;
    private long lastUpdate;
    private boolean runningGame = false;
    public static int width;
    public static int height;
    private final String name;
    public int generation = 1;
    
    ArrayList<GameObject> gameObjects;
    ArrayList<Creature> creatures;
    ArrayList<Creature> deadCreatures;

    
    public long iteration = 0;
    
    
    /*Config Generate*/
    public int numberCreatureToGenerate = 10;
    public int interevalCreatureToGenerate = 1000;
    public int nextGenerationCreatures = interevalCreatureToGenerate;
    
    public int numberFoodToGenerate = 20;
    public int interevalFoodToGenerate = 60;
    public int nextGenerationFoods = interevalFoodToGenerate;
    
    public int numberEventToGenerate = 10;
    public int interevalEventToGenerate = 1000;
    public int nextGenerationEvents = interevalEventToGenerate;
    
    public Board(BoardParams params) {
        this.timerUpdate = new Timer(20, this);
        
        Board.width = params.size.x;
        Board.height = params.size.y;
        this.name = params.name;
        timerUpdate.start();
        
        this.gameObjects = new ArrayList<>();
        this.creatures = new ArrayList<>();
        
        
        
        /**FIRE PATH*/
        boolean firePath = true;
        if (firePath) addFirePath();
    }
    
    public void updateView(String arg) {
        setChanged();
        notifyObservers(arg);
        clearChanged();
    }
    
    public void update() throws TopologySizeException, ArraySizeException, InputsSizeException {
        
        boolean autoGen = true;
        if (autoGen && creatures.size() == 0) generateGeneration();
        // Génération food
        generateFood();
        
        // Génération event
        generateEvent();
        
        // Reset Creatures
        resetCreaturesInputs();
        
        // Update des créatures
        updateCreatures();
        
        // Update des créatures
        updateGameObjects();
        
        // On supprime les games objects dépréciés
        removeGameOjects();
        
        //if(creatures.size()>0) reproduce();
        
        iteration++;
    }
    
    public void generateGeneration() throws TopologySizeException, ArraySizeException {
        int nbCreatureToGanerate = 100;
        if (generation == 1) {
            for (int i = 0; i < nbCreatureToGanerate; i++) {
                addCreature();
            }
        }
        else {
            for (int j = 0; j < nbCreatureToGanerate; j++) {
                double totalFitness = 0;
                for (Creature deadCreature : deadCreatures) {
                    totalFitness += deadCreature.fitness;
                }
                double selectA = Life.rand.nextDouble()*totalFitness;
                double selectB = Life.rand.nextDouble()*totalFitness;
                Creature creatureA =null;
                Creature creatureB =null;
                double sumFitness = 0;
                for (int i = 0; i < deadCreatures.size(); i++) {
                    sumFitness+= deadCreatures.get(i).fitness;
                    if (sumFitness>selectA) {
                        creatureA = deadCreatures.get(i);
                        break;
                    }
                }
                for (int i = 0; i < deadCreatures.size(); i++) {
                    sumFitness+= deadCreatures.get(i).fitness;
                    if (sumFitness>selectB) {
                        creatureB = deadCreatures.get(i);
                        break;
                    }
                }
                addCreature(new Creature(creatureA,creatureB));
            }
            
        }
        
        this.deadCreatures = new ArrayList<>();
        generation++;
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
    
    public void resetCreaturesInputs() {       
        for (Creature creature : creatures) {
            creature.setOverCreature(false);
            creature.setVisibleFoods(0, false);
            creature.setVisibleFoods(1, false);
            creature.setVisibleFoods(2, false);
            creature.setVisibleCreatures(0, false);
            creature.setVisibleCreatures(1, false);
            creature.setVisibleCreatures(2, false);
            creature.setVisibleMeteorologicalEvents(1, false);  
        }
    }
    
    public void updateCreatures() throws InputsSizeException {
        for (Creature creature : creatures) {
            for (GameObject gameObject : gameObjects) {
                if(creature == gameObject) continue;
                
                /* S'il y a une intersection */
                if(creature.intersect(gameObject)) {
                    /* CREATURE */
                    if(gameObject instanceof Creature) {
                        creature.setOverCreature(true);
                        
                    /* FOOD */
                    } else if(gameObject instanceof Food) {
                        if (gameObject.toDelete == false ) {
                            creature.eat((Food)gameObject);
                            gameObject.toDelete = true;
                        }

                    /* METEOROLOGICALEVENT */
                    } else if(gameObject instanceof MeteorologicalEvent) {
                        
                    }
                }
                
                /* Si la créature voit quelque chose */
                
                int lineX = creature.getCenter().x + (int) (Math.cos(Math.toRadians(creature.getDirection())) * creature.getDistanceOfView());
                int lineY = creature.getCenter().y - (int) (Math.sin(Math.toRadians(creature.getDirection())) * creature.getDistanceOfView());

                 if(getCircleLineIntersectionPoint(creature.getCenter(), new Point(lineX, lineY), gameObject.getCenter(), gameObject.getRadius()).size() > 0) {
                    
                        /* CREATURE */
                        if(gameObject instanceof Creature) {
                            creature.setVisibleCreatures(1, true);

                        /* FOOD */
                        } else if(gameObject instanceof Food) {
                            creature.setVisibleFoods(1, true);

                        /* METEOROLOGICALEVENT */
                        } else if(gameObject instanceof MeteorologicalEvent) {
                            creature.setVisibleMeteorologicalEvents(1, true);
                        }
                 }
                 
                int lineX_1 = creature.getCenter().x + (int) (Math.cos(Math.toRadians(creature.getDirection() + creature.getFieldOfView())) * creature.getDistanceOfView());
                int lineY_1 = creature.getCenter().y - (int) (Math.sin(Math.toRadians(creature.getDirection() + creature.getFieldOfView())) * creature.getDistanceOfView());

                 if(getCircleLineIntersectionPoint(creature.getCenter(), new Point(lineX_1, lineY_1), gameObject.getCenter(), gameObject.getRadius()).size() > 0) {
                    
                        /* CREATURE */
                        if(gameObject instanceof Creature) {
                            creature.setVisibleCreatures(0, true);

                        /* FOOD */
                        } else if(gameObject instanceof Food) {
                            creature.setVisibleFoods(0, true);

                        /* METEOROLOGICALEVENT */
                        } else if(gameObject instanceof MeteorologicalEvent) {
                            creature.setVisibleMeteorologicalEvents(0, true);

                        }
                 }
                 
                int lineX_2 = creature.getCenter().x + (int) (Math.cos(Math.toRadians(creature.getDirection() - creature.getFieldOfView())) * creature.getDistanceOfView());
                int lineY_2 = creature.getCenter().y - (int) (Math.sin(Math.toRadians(creature.getDirection() - creature.getFieldOfView())) * creature.getDistanceOfView());

                 if(getCircleLineIntersectionPoint(creature.getCenter(), new Point(lineX_2, lineY_2), gameObject.getCenter(), gameObject.getRadius()).size() > 0) {
                    
                        /* CREATURE */
                        if(gameObject instanceof Creature) {
                            creature.setVisibleCreatures(2, true);

                        /* FOOD */
                        } else if(gameObject instanceof Food) {
                            creature.setVisibleFoods(2, true);

                        /* METEOROLOGICALEVENT */
                        } else if(gameObject instanceof MeteorologicalEvent) {
                            creature.setVisibleMeteorologicalEvents(2, true);

                        }
                 }
                 
                 /* Si c'est un évènement */
                 if(gameObject instanceof MeteorologicalEvent) {
                     MeteorologicalEvent meteorologicalEvent = (MeteorologicalEvent) gameObject;
                     meteorologicalEvent.checkCreature(creature);    
                 }                 
            }
            
            creature.update();
        }
    }
    
    public void updateGameObjects() {
        for (GameObject gameObject : gameObjects) {
            if(gameObject instanceof MeteorologicalEvent) {
                 MeteorologicalEvent meteorologicalEvent = (MeteorologicalEvent) gameObject;
                 meteorologicalEvent.update();
            }
        }
    }
         
    public void reproduce() throws TopologySizeException, ArraySizeException {
        if (Life.rand.nextFloat() > 0.999) {
            for (int i = 0; i < 30; i++) {
                
                Creature creature1 = creatures.get((int)(Math.random() * creatures.size()));
                Creature creature2 = creatures.get((int)(Math.random() * creatures.size()));
                
                addCreature(new Creature(creature1, creature2));
            }  
        }
    }
    
    public void removeGameOjects() {
        
        /* Remove Creatures */
        Iterator<Creature> it = creatures.iterator();
        while(it.hasNext()){
            Creature creature = it.next();
            if(creature.toDelete == true) {
                addToDeadCreatures(creature);
                it.remove();
            }
        }
        
        /* Remove other Object */
        Iterator<GameObject> it2 = gameObjects.iterator();
        while(it2.hasNext()){
            if(it2.next().toDelete == true) {
                it2.remove();
            }
        }
    }
    
    public void addToDeadCreatures(Creature creature) {
        for (int i = 0; i < deadCreatures.size(); i++) {
            if (creature.fitness>deadCreatures.get(i).fitness) {
                deadCreatures.add(i, creature);
                return;
            }
        }
        deadCreatures.add(creature);
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
    
    public void addCreature(Creature creature) {
        creature.setBoard(this);
        creatures.add(creature);
        gameObjects.add(creature);
    }

    public void addCreature() throws TopologySizeException {
        Creature newCreature = new Creature();
        addCreature(newCreature);
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
            } catch (TopologySizeException | ArraySizeException | InputsSizeException ex) {
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
    public void creatureIsDead(Creature creature) {
        creature.toDelete = true;

    }

    
    public void skipFrames(int n) throws TopologySizeException, ArraySizeException, InputsSizeException {
        for (int i = 0; i < n; i++) {
            update();
        }
    }
    
    public Creature getCreatureOnPoint(int x,int y) {
        for (GameObject gameObject : gameObjects) {
            if (gameObject instanceof Creature) {
                if(gameObject.isPointInside(x, y)) return (Creature)gameObject;
            }
        }
        return null;
    }
    
    public void addFirePath() {
        MeteorologicalEvent me = new MeteorologicalEvent((this.width>this.height)?this.height/4:this.width/4, new Point(this.width/2, this.height/2), MeteorologicalEventsTypes.FIRE);
        System.out.println(me);
        addEvent(me);
        
    }
}
