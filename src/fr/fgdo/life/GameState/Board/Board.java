/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.fgdo.life.GameState.Board;

import fr.fgdo.life.Creature.Creature;
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
    
    Timer timer = new Timer(20, this);
    private final int width;
    private final int height;
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
            creature.update();
        });
    }
    
    public void gameLoop() {
        
    }

    public ArrayList<Creature> getCreatures() {
        return creatures;
    }
    
    public void addCreature(Creature creature) {
        creatures.add(creature);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==timer){
            update();
            updateView();
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
    
    
}
