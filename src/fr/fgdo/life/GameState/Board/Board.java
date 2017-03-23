/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.fgdo.life.GameState.Board;

import fr.fgdo.life.Creature.Creature;
import fr.fgdo.life.neuralNetwork.exceptions.InputsSizeException;
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
                creature.update();
                checkCreature(creature);
            } catch (InputsSizeException ex) {
                Logger.getLogger(Board.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        iteration++;
    }
    
    public void checkCreature(Creature creature) {
        if (creature.getCenter().x > width) creature.getCenter().x = width-1;
        if (creature.getCenter().x < 0) creature.getCenter().x = 0;
        if (creature.getCenter().y > height) creature.getCenter().y = height;
        if (creature.getCenter().y < 0) creature.getCenter().y = 0;
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
            System.out.println(timerUpdate.getDelay());
        }
        
    }

    
}
