/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.fgdo.life.game.models;

import fr.fgdo.life.game.views.BeingView;
import java.awt.Color;
import java.util.Observable;

/**
 *
 * @author Olivier
 */
public class Being {
    private Color color;

    public Being(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }
    
}
