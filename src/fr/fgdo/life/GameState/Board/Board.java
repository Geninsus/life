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
import fr.fgdo.life.neuralNetwork.exceptions.InputsSizeException;
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
    
    public void update() {
        
        if (generateFood) {
            if (Life.rand.nextFloat() > 0.95) {
                addFood(new Food());
            }
        }
        
        updateMeteorologicalEvents();
        updateCreatures();
        updateFoods();
        
        for (MeteorologicalEvent meteorologicalEvent : meteorologicalEvents) {
            creatures.forEach((creature) -> {
                meteorologicalEvent.checkCreature(creature);
            });
            meteorologicalEvent.update();
        }
        
        creatures.forEach((creature) -> {
            try {
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
        for (Food food : foods) {
            if (creature.intersect(food)) {
                creature.eat(food);
                toRemoveFoods.add(food);
            }
        }
        updateFoods();
    }
    
    public void updateCreaturesViews() {
        for (Creature creature : creatures) {
            for (GameObject otherCreature : creatures) {
                
            }
        }
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

    public void updateFoods() {
        for (Iterator<Food> iterator = toRemoveFoods.iterator(); iterator.hasNext();) {
            Food next = iterator.next();
            foods.remove(next);
            iterator.remove();
        }
    }
    
    public void updateMeteorologicalEvents() {
        for (Iterator<MeteorologicalEvent> iterator = toRemoveMeteorologicalEvents.iterator(); iterator.hasNext();) {
            MeteorologicalEvent next = iterator.next();
            meteorologicalEvents.remove(next);
            iterator.remove();
        }
    }
    
    public void updateCreatures() {
        for (Iterator<Creature> iterator = toRemoveCreatures.iterator(); iterator.hasNext();) {
            Creature next = iterator.next();
            creatures.remove(next);
            iterator.remove();
        }
    }
    
}
