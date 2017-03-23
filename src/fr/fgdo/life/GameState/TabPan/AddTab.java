/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.fgdo.life.GameState.TabPan;

import fr.fgdo.life.GameState.GameState;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author Olivier
 */
public class AddTab extends JPanel{

    public AddTab(GameState gameState) {
        
        JButton addCreatureButton = new JButton("Add Creature");
        addCreatureButton.setName("addCreature");
        addCreatureButton.addMouseListener(gameState);
        
        JButton addFoodButton = new JButton("Add Food");
        addFoodButton.setName("addFood");
        addFoodButton.addMouseListener(gameState);
        
        add(addCreatureButton);
        add(addFoodButton);
    }
    
}
