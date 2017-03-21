/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.fgdo.life.game.models;

import fr.fgdo.life.newGame.NewGame;
import fr.fgdo.math.Point;
import java.io.Serializable;
import java.util.Observable;
import java.util.Observer;

/**
 *
 * @author Olivier
 */
public class Game extends Observable implements Serializable,Observer{
    private String name;
    private int cols;
    private int rows;
    private Grid grid;

    public Game(NewGame newGame) {
        name = newGame.getMapName();
        cols = newGame.getMapWidth();
        rows = newGame.getMapHeight();
        grid = new  Grid(rows,cols);
    }
    
    public Game() {
        name = "New Game";
        cols = 10;
        rows = 10;
        grid = new  Grid(cols,rows);
    }
    
    public void updateView() {
        setChanged();
        notifyObservers();
        clearChanged();
    }

    public Grid getGrid() {
        return grid;
    }

    @Override
    public void update(Observable o, Object arg) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void addBeing(Being being, Point coord) {
        grid.addBeing(being, coord);
    }
}
