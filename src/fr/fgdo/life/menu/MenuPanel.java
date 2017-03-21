/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.fgdo.life.menu;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author Olivier
 */
public class MenuPanel extends JPanel{

    public MenuPanel( MenuPanelController menuPanelController ) {
        super();
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(3, 3, 3, 3);
        
        
        JButton newButton = new JButton("New");
        newButton.addActionListener(menuPanelController);
        JButton importButton = new JButton("Import");
        importButton.addActionListener(menuPanelController);
        JButton optionButton = new JButton("Options");
        optionButton.addActionListener(menuPanelController);
        JButton quitButton = new JButton("Quit");
        quitButton.addActionListener(menuPanelController);
        
        add(newButton, gbc);
        add(importButton, gbc);
        add(optionButton, gbc);
        add(quitButton, gbc);
    }
    
}
