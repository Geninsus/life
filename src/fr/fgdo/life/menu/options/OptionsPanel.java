/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.fgdo.life.menu.options;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;

/**
 *
 * @author Olivier
 */
public class OptionsPanel extends JPanel{

    private JCheckBox fullscreenCheckBox;
    private JCheckBox musicCheckBox;
    
    public OptionsPanel(OptionsController optionController) {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(3, 3, 3, 3);
        
        fullscreenCheckBox = new JCheckBox("Fullscreen");
        fullscreenCheckBox.addActionListener(optionController);
        musicCheckBox = new JCheckBox("Music");
        musicCheckBox.addActionListener(optionController);
        JButton returnButton = new JButton("Return");
        returnButton.addActionListener(optionController);
        add(fullscreenCheckBox, gbc);
        add(musicCheckBox, gbc);
        add(returnButton, gbc);
    }

}
