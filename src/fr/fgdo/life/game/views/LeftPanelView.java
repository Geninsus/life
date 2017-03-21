/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.fgdo.life.game.views;

import fr.fgdo.life.game.models.LeftPanelController;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author Olivier
 */
public class LeftPanelView extends JPanel{

    JButton generateBeing;
    
    public LeftPanelView(LeftPanelController controller) {
        generateBeing = new JButton("GENERATE");
        generateBeing.addMouseListener(controller);
        add(generateBeing);
    }

    
    
}
