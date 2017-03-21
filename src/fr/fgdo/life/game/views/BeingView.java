/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.fgdo.life.game.views;

import fr.fgdo.life.game.models.Being;
import java.awt.Color;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JPanel;

/**
 *
 * @author Olivier
 */
public class BeingView extends JPanel {

    public BeingView(Being being) {
        setBackground(being.getColor());
    }

}
