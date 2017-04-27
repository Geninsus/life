/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.fgdo.life.GameState.Board.Tabbed;

import fr.fgdo.life.GameState.GameState;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Panel;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.NumberFormat;
import java.util.Observable;
import java.util.Observer;
import javafx.scene.layout.Pane;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;

/**
 *
 * @author Olivier
 */
public class BoardOptionTab extends JPanel implements Observer, MouseListener{

    JButton playButton;
    JButton pauseButton;
    JButton skipFrameButton;
    
    JFormattedTextField numberCreatureToGenerateTextField;
    JFormattedTextField interevalCreatureToGenerateTextField;
    static JFormattedTextField numberFramesToSkipTextField;

    public static int getNumberFramesToSkip() {
        return Integer.parseInt(numberFramesToSkipTextField.getValue().toString());
    }
    
    public BoardOptionTab(GameState gameState) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        JPanel controlPanel = new JPanel();
        NumberFormat numF = NumberFormat.getNumberInstance(); 
        numF.setMaximumIntegerDigits(7);
        
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
        add(controlPanel);
        
        
        
        /*SKIP PANEL*/
        JPanel skipPanel = new JPanel();
        JButton skipFrameButton = new JButton("Skip Frames");
        skipFrameButton.addMouseListener(gameState);
        skipFrameButton.setName("skipFrameButton");
        numberFramesToSkipTextField = new JFormattedTextField(numF);
        numberFramesToSkipTextField.setValue(1000);
        numberFramesToSkipTextField.setPreferredSize(new Dimension(50, 20));
        numberFramesToSkipTextField.setMinimumSize(new Dimension(50, 20));
        skipPanel.add(numberFramesToSkipTextField);
        skipPanel.add(skipFrameButton);
        add(skipPanel);
        
        /*OPTION PANEL*/
        JPanel optionPanel = new JPanel();
        optionPanel.setLayout(new GridBagLayout());
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
        optionPanel.add(showCreaturesNamesCheckBox,gbc);
        
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        JCheckBox showCreaturesVisionCheckBox = new JCheckBox("Show creatures visions");
        showCreaturesVisionCheckBox.setName("showCreaturesVision");
        showCreaturesVisionCheckBox.addItemListener(gameState);
        optionPanel.add(showCreaturesVisionCheckBox,gbc);
        
        
        
        gbc.gridx = 0;
        gbc.gridy = 2;
        JCheckBox showIterationsCheckBox = new JCheckBox("Show iterations");
        showIterationsCheckBox.setName("showIterations");
        showIterationsCheckBox.addItemListener(gameState);
        optionPanel.add(showIterationsCheckBox,gbc);
        
        
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        optionPanel.add(new JLabel("Add"),gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        optionPanel.add(new JLabel("X"),gbc);
        gbc.gridx = 2;
        gbc.gridy = 3;
        optionPanel.add(new JLabel("Creature(s) every"),gbc);
        gbc.gridx = 3;
        gbc.gridy = 3;
        optionPanel.add(new JLabel("Y"),gbc);
        gbc.gridx = 4;
        gbc.gridy = 3;
        optionPanel.add(new JLabel("seconds"),gbc);
        
        
        /*Creature*/
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        optionPanel.add(new JLabel("Add"),gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        numberCreatureToGenerateTextField = new JFormattedTextField(numF);
        numberCreatureToGenerateTextField.setValue(50);
        numberCreatureToGenerateTextField.setPreferredSize(new Dimension(50, 20));
        numberCreatureToGenerateTextField.setMinimumSize(new Dimension(50, 20));
        optionPanel.add(numberCreatureToGenerateTextField,gbc);
        gbc.gridx = 2;
        gbc.gridy = 4;
        optionPanel.add(new JLabel("Creature(s) every"),gbc);
        gbc.gridx = 3;
        gbc.gridy = 4;
        interevalCreatureToGenerateTextField = new JFormattedTextField(numF);
        interevalCreatureToGenerateTextField.setValue(50);
        interevalCreatureToGenerateTextField.setPreferredSize(new Dimension(50, 20));
        interevalCreatureToGenerateTextField.setMinimumSize(new Dimension(50, 20));
        optionPanel.add(interevalCreatureToGenerateTextField,gbc);
        gbc.gridx = 4;
        gbc.gridy = 4;
        optionPanel.add(new JLabel("seconds"),gbc);
        
        add(optionPanel);
        
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

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
    
}
