/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.fgdo.life.GameState.Board.Tabbed;

import fr.fgdo.life.GameState.GameState;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Observable;
import java.util.Observer;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTabbedPane;

/**
 *
 * @author Olivier
 */
public class BoardTabbedView extends JTabbedPane implements Observer{

    JPanel addTab;
    public BoardOptionTab optionsTab;
    JPanel eventsTab;
    
    JButton playButton;
    JButton pauseButton;
    
    public BoardTabbedView( GameState gameState) {
        
        /*Add TAB*/
        addTab = new JPanel();
        JButton addCreatureButton = new JButton("Add Creature");
        addCreatureButton.setName("addCreature");
        addCreatureButton.addMouseListener(gameState);
        
        JButton addFoodButton = new JButton("Add Food");
        addFoodButton.setName("addFood");
        addFoodButton.addMouseListener(gameState);
        
        JButton implementCreatureButton = new JButton("Implement Creature");
        implementCreatureButton.setName("implementCreature");
        implementCreatureButton.addMouseListener(gameState);
        
        addTab.add(implementCreatureButton);
        addTab.add(addCreatureButton);
        addTab.add(addFoodButton);
        this.addTab("Add", addTab);
        
        
        
        /*Option Tab*/
        /*NORTH*/
        optionsTab = new BoardOptionTab(gameState);
        this.addTab("Options", optionsTab);
        
        /* Events TAB */
        eventsTab = new JPanel();
        
        
        JButton addFireButton = new JButton("Add Fire");
        addFireButton.setName("addFire");
        addFireButton.addMouseListener(gameState);
        eventsTab.add(addFireButton);
        this.addTab("Events",eventsTab);
    }

    @Override
    public void update(Observable o, Object argO) {
        String arg = (String)argO;
        switch (arg) {
        }
    }
    
}
