/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.fgdo.life.MainMenuState;

import fr.fgdo.life.Life;
import fr.fgdo.life.State.State;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;

/**
 *
 * @author olivbau
 */
public class MainMenuState extends State implements MouseListener{

    public MainMenuState(Life lifeGame) {
        super(lifeGame);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(3, 3, 3, 3);
        
        
        JButton newButton = new JButton("New Game");
        JButton importButton = new JButton("Import");
        JButton optionButton = new JButton("Options");
        JButton quitButton = new JButton("Quit");
        
        newButton.addMouseListener(this);
        importButton.addMouseListener(this);
        optionButton.addMouseListener(this);
        quitButton.addMouseListener(this);
        
        add(newButton, gbc);
        add(importButton, gbc);
        add(optionButton, gbc);
        add(quitButton, gbc);
        
    }

    @Override
    public void mouseClicked(MouseEvent me) {
        JButton button = (JButton)me.getSource();
        switch (button.getText()) {
            case "New Game":
                this.getLifeGame().enterState(1);
                break;
            default:
                break;
        }
        
    }

    @Override
    public void mousePressed(MouseEvent me) {
    }

    @Override
    public void mouseReleased(MouseEvent me) {
    }

    @Override
    public void mouseEntered(MouseEvent me) {
    }

    @Override
    public void mouseExited(MouseEvent me) {
    }
    
}
