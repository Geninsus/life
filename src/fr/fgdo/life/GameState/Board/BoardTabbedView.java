/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.fgdo.life.GameState.Board;

import fr.fgdo.life.GameState.GameState;
import java.awt.BorderLayout;
import java.util.Observable;
import java.util.Observer;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 *
 * @author Olivier
 */
public class BoardTabbedView extends JTabbedPane implements Observer{

    JPanel addTab;
    JPanel optionsTab;
    
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
        
        addTab.add(addCreatureButton);
        addTab.add(addFoodButton);
        this.addTab("Add", addTab);
        
        
        
        /*Option Tab*/
        optionsTab = new JPanel();
        optionsTab.setLayout(new BorderLayout());
        JPanel controlPanel = new JPanel();
        playButton = new JButton(new ImageIcon("assets/play.png"));
        playButton.setName("play");
        playButton.addMouseListener(gameState);
        
        pauseButton = new JButton(new ImageIcon("assets/pause.png"));
        pauseButton.setName("pause");
        pauseButton.addMouseListener(gameState);
        
        controlPanel.add(playButton);
        controlPanel.add(pauseButton);
        
        optionsTab.add(controlPanel, BorderLayout.NORTH);
        this.addTab("Options", optionsTab);
    }

    @Override
    public void update(Observable o, Object argO) {
        String arg = (String)argO;
        switch (arg) {
            case "TabbedView:run":
                playButton.setEnabled(false);
                pauseButton.setEnabled(true);
                break;
            case "TabbedView:pause":
                playButton.setEnabled(true);
                pauseButton.setEnabled(false);
                break;
        }
    }
    
}
