/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.fgdo.life.game.models;

import java.util.Observable;

/**
 *
 * @author Olivier
 */
public class Cell extends Observable{
    public CellType type;

    public Cell(CellType type) {
        this.type = type;
    }
    
    public void updateView() {
        setChanged();
        notifyObservers();
        clearChanged();
    }
}
