/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.fgdo.life.game.models;

import java.util.ArrayList;
import java.util.Observable;

/**
 *
 * @author Olivier
 */
public class Cell extends Observable{
    public CellType type;
    private ArrayList<Being> beingsList = new ArrayList<>();
    
    public Cell(CellType type) {
        this.type = type;
    }
    
    public void updateView() {
        setChanged();
        notifyObservers();
        clearChanged();
    }
    
    public void addBeing(Being being) {
        beingsList.add(being);
        updateView();
    }
    
    public int getNumBeings() {
        return beingsList.size();
    }
    
    public Being getFirstBeing() {
        try {
            return beingsList.get(beingsList.size()-1);
        } catch (Exception e) {
        }
        return null;
    }
}
