/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.fgdo.life.game.models;

import fr.fgdo.life.newGame.NewGame;
import java.io.Serializable;
import java.util.Observable;

/**
 *
 * @author Olivier
 */
public class Game extends Observable implements Serializable{
    private String name;
    private int width;
    private int height;
    private Grid grid;

    public Game(NewGame newGame) {
        name = newGame.getMapName();
        width = newGame.getMapWidth();
        height = newGame.getMapHeight();
        grid = new  Grid(width,height);
    }
    
    public Game() {
        name = "New Game";
        width = 10;
        height = 10;
        grid = new  Grid(width,height);
    }
    
    public void updateView() {
        setChanged();
        notifyObservers();
        clearChanged();
    }

    public Grid getGrid() {
        return grid;
    }
    
}
