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
import java.text.NumberFormat;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Olivier
 */
public class NewGamePanel extends JPanel{

    private JFormattedTextField mapHeightTextField;
    private JFormattedTextField mapWidthTextField;
    private JTextField mapNameTextField;
    
    public NewGamePanel(NewGameController controller) {
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
        add(new JLabel("Map name : "),gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        mapNameTextField = new JTextField("New Map");
        mapNameTextField.setPreferredSize(new Dimension(150, 20));
        mapNameTextField.setMinimumSize(new Dimension(150, 20));
        add(mapNameTextField,gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        add(new JLabel("Map Width : "),gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        mapWidthTextField = new JFormattedTextField(numF);
        mapWidthTextField.setValue(100);
        mapWidthTextField.setPreferredSize(new Dimension(50, 20));
        mapWidthTextField.setMinimumSize(new Dimension(50, 20));
        add(mapWidthTextField,gbc);
        
        
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        add(new JLabel("Map Height : "),gbc);
        
        
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        mapHeightTextField = new JFormattedTextField(numF);
        mapHeightTextField.setValue(100);
        mapHeightTextField.setPreferredSize(new Dimension(50, 20));
        mapHeightTextField.setMinimumSize(new Dimension(50, 20));
        add(mapHeightTextField,gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        JButton confirmButton = new JButton("Confirm");
        confirmButton.addMouseListener(controller);
        add(confirmButton,gbc);
    }
    
    public int getMapWidth() {
        int width;
        try {
            width  = Integer.valueOf(mapWidthTextField.getText());
        } catch (NumberFormatException e) {
            width = 1;
        }
        return width;
    }
    
    public int getMapHeight() {
        int height;
        try {
            height  = Integer.valueOf(mapHeightTextField.getText());
        } catch (NumberFormatException e) {
            height = 1;
        }
        return height;
    }
    
    public String getMapName() {
        String name;
        name = mapNameTextField.getText();
        if (name.isEmpty()) name = "NewMap";
        return name;
    }
}
