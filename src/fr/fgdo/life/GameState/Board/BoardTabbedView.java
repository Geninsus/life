/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.fgdo.life.GameState.Board;

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
        /*NORTH*/
        optionsTab = new JPanel();
        optionsTab.setLayout(new BorderLayout());
        JPanel controlPanel = new JPanel();
        
        JSlider speedSlider = new JSlider(1, 3);
        speedSlider.setMajorTickSpacing(1);
        speedSlider.setPaintTicks(true);
        speedSlider.setPaintLabels(true);
        speedSlider.setValue(1);
        speedSlider.addChangeListener(gameState);
        playButton = new JButton(new ImageIcon("assets/play.png"));
        playButton.setName("play");
        playButton.addMouseListener(gameState);
        
        pauseButton = new JButton(new ImageIcon("assets/pause.png"));
        pauseButton.setName("pause");
        pauseButton.addMouseListener(gameState);
        
        controlPanel.add(speedSlider);
        controlPanel.add(playButton);
        controlPanel.add(pauseButton);
        
        optionsTab.add(controlPanel, BorderLayout.NORTH);
        
        
        /*CENTER*/
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(3, 3, 3, 3);
        
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        JCheckBox showCreaturesNamesCheckBox = new JCheckBox("Show creatures names");
        showCreaturesNamesCheckBox.setName("showCreaturesNames");
        showCreaturesNamesCheckBox.addItemListener(gameState);
        centerPanel.add(showCreaturesNamesCheckBox,gbc);
        
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        JCheckBox showCreaturesVisionCheckBox = new JCheckBox("Show creatures visions");
        showCreaturesVisionCheckBox.setName("showCreaturesVision");
        showCreaturesVisionCheckBox.addItemListener(gameState);
        centerPanel.add(showCreaturesVisionCheckBox,gbc);
        
        
        
        gbc.gridx = 0;
        gbc.gridy = 2;
        JCheckBox showIterationsCheckBox = new JCheckBox("Show iterations");
        showIterationsCheckBox.setName("showIterations");
        showIterationsCheckBox.addItemListener(gameState);
        centerPanel.add(showIterationsCheckBox,gbc);
        
        
        optionsTab.add(centerPanel,BorderLayout.CENTER);
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
