/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.fgdo.life.Creature;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Olivier
 */
public class CreaturePanel extends JDialog implements Observer{

    JLabel name = new JLabel();
    JLabel positionX = new JLabel();
    JLabel positionY = new JLabel();
    JLabel radius = new JLabel();
    JLabel fieldOfView = new JLabel();
    JLabel direction = new JLabel();
    
    public CreaturePanel(Creature creature, Component c) {
        
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(3, 3, 3, 3);
        gbc.gridwidth = 1;
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Name : "),gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 0;
        add(name,gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("PositionX : "),gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(positionX,gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("PositionY : "),gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 2;
        add(positionY,gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(new JLabel("Radius : "),gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 3;
        add(radius,gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 4;
        add(new JLabel("fieldOfView : "),gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 4;
        add(fieldOfView,gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 5;
        add(new JLabel("Direction : "),gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 5;
        add(direction,gbc);
        
        creature.addObserver(this);
        setTitle(creature.getName());
        setSize(300,400);
        setLocationRelativeTo(c);
        setVisible(true);
        
    }
    

    @Override
    public void update(Observable o, Object arg) {
        Creature creature = (Creature)o;
        
        name.setText(creature.getName());
        positionX.setText(Integer.toString(creature.getCenter().x));
        positionY.setText(Integer.toString(creature.getCenter().y));
        radius.setText(Integer.toString(creature.getRadius()));
        fieldOfView.setText(Integer.toString((int)creature.getFieldOfView()));
        direction.setText(Integer.toString((int)creature.getDirection()));
    }
    
}
