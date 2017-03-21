/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.fgdo.life.newGame;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 *
 * @author Olivier
 */
public class NewGameController extends MouseAdapter{

    private NewGamePanel view;
    private NewGame model;

    public NewGameController(NewGame model) {
        this.model = model;
    }

    public void setView(NewGamePanel view) {
        this.view = view;
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        model.setMapName(view.getMapName());
        model.setMapWidth(view.getMapWidth());
        model.setMapHeight(view.getMapHeight());
    }
    
    
    
}
