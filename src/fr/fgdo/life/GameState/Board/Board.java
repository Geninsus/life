/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.fgdo.life.GameState.Board;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Timer;

/**
 *
 * @author Olivier
 */
public class Board extends Observable implements ActionListener{

    private final int width;
    private final int height;
    private final String name;
    Timer timer=new Timer(10, this);
    
    public Board(BoardParams params) {
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
    
    public void gameLoop() {
        
    }
    

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==timer){
            System.err.println("Update");
        }
    }
    
}
