/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.fgdo.life.newGame;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.text.Format;
import java.text.NumberFormat;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;

/**
 *
 * @author Olivier
 */
public class NewGamePanel extends JPanel{

    public NewGamePanel() {
        super();
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(3, 3, 3, 3);
        
        NumberFormat numF = NumberFormat.getNumberInstance(); 
        numF.setMaximumIntegerDigits(4);
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        add(new JLabel("Map Width : "),gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        JFormattedTextField mapWidthTextField = new JFormattedTextField(numF);
        mapWidthTextField.setPreferredSize(new Dimension(50, 20));
        mapWidthTextField.setMinimumSize(new Dimension(50, 20));
        add(mapWidthTextField,gbc);
        
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        add(new JLabel("Map Height : "),gbc);
        
        
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        JFormattedTextField mapHeightTextField = new JFormattedTextField(numF);
        mapHeightTextField.setPreferredSize(new Dimension(50, 20));
        mapHeightTextField.setMinimumSize(new Dimension(50, 20));
        add(mapHeightTextField,gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        JButton confirmButton = new JButton("Confirm");
        add(confirmButton,gbc);
    }
    
}
