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
    private Timer timer = new Timer((int) (20.0/speed), this);
    private boolean runningGame = false;
    public static int width;
    public static int height;
    private final String name;
    ArrayList<Creature> creatures;
    public long iteration = 0;
    
    
    
    public Board(BoardParams params) {
        this.creatures = new ArrayList<>();
        this.width = params.size.x;
        this.height = params.size.y;
        this.name = params.name;
        timer.start();
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
        if(runningGame && e.getSource()==timer){
            update();
            updateView("View:update");
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
            timer.setDelay((int) (20.0/speed));
            System.out.println(timer.getDelay());
        }
        
    }

    
}
