/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.fgdo.life.MenuCreature;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.Observer;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

/**
 *
 * @author Guillaume
 */
public class MenuCreatureView extends JFrame implements Observer{
    
    private JPanel globalPan;
    private JPanel neuronPan;
    private MenuCreatureController menuController;
    private static final int DEFAULT_HEIGHT = 600;
    private static final int DEFAULT_WIDTH = 600;
    
    public MenuCreatureView(MenuCreatureController menuController)  {
        super();
        this.menuController = menuController;
        this.SetupPanel();
        this.SetupFrame();
        this.setupButton();
        this.SetupNeuron();
    }
    
    private void SetupPanel() {
        this.globalPan = new JPanel(new BorderLayout());
        this.add(globalPan);
    }
    
    private void SetupFrame() {
        this.setTitle("Menu Creature");
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setLocationRelativeTo(null);
        setVisible(true);
        setLayout(new BorderLayout());
    }

    
    private void setupButton(){
        
        JPanel buttonPanel = new JPanel(new GridLayout(1,2,10,50));
        
        /* Cancel Button */
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setName("cancel");
        cancelButton.addMouseListener(this.menuController);
        buttonPanel.add(cancelButton);
        
        /* Create Button */
        JButton createButton = new JButton("Create");
        createButton.setName("create");
        createButton.addMouseListener(this.menuController);
        createButton.addActionListener(this.menuController);
        buttonPanel.add(createButton);
        
        /* Add to global Panel */
        this.globalPan.add(buttonPanel,BorderLayout.SOUTH);
    }

    
    private void SetupNeuron() {
        
        this.neuronPan = new JPanel(new GridLayout(0,3));
        
        JButton neuronButton = new JButton("Neuron");
        neuronButton.setName("neuronbutton");
        neuronButton.addActionListener(this.menuController);
        this.neuronPan.add(neuronButton);
        this.globalPan.add(neuronPan,BorderLayout.CENTER);
           
    }
    
    public void addNeuron() {
        System.err.println("addNeuron");
        this.neuronPan.add(new JButton("neuron"));
        this.globalPan.updateUI();
        
    }
    @Override
    public void update(java.util.Observable o, Object o1) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
