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
    
    private Timer timer = new Timer(20, this);
    private boolean runningGame = false;
    public static int width;
    public static int height;
    private final String name;
    ArrayList<Creature> creatures;
    
    public Board(BoardParams params) {
        this.creatures = new ArrayList<>();
        this.width = params.size.x;
        this.height = params.size.y;
        this.name = params.name;
        timer.start();
    }
    
    public void updateView() {
        setChanged();
        notifyObservers();
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
            updateView();
        }
    }

    public void run() {
        runningGame = true;
    }
    
    public void pause() {
        runningGame =false;
    }
    
    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
    
    
}
