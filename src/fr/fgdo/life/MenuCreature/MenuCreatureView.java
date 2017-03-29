/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.fgdo.life.MenuCreature;

import java.awt.BorderLayout;
import java.awt.Color;
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
    
    private JPanel pan;
    private MenuCreatureController menuController;
    private static final int DEFAULT_HEIGHT = 600;
    private static final int DEFAULT_WIDTH = 600;
    
    public MenuCreatureView(MenuCreatureController menuController)  {
        super();
        this.menuController = menuController;
        this.SetupFrame();
        this.SetupPanel();
        this.setupAll();
    }
    
    private void SetupFrame() {
        this.setTitle("Menu Creature");
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setLayout(new BorderLayout());
    }
    
    private void SetupPanel(){
        this.pan = new JPanel();
        this.add(pan);
    }
    
    private void setupAll(){
        JRadioButton neural = new JRadioButton("Neural");
        this.pan.add(neural);
        
        
        JRadioButton neural2 = new JRadioButton("Neural2");
        this.pan.add(neural2);
        
        /* Cancel Button */
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setName("cancel");
        cancelButton.addMouseListener(this.menuController);
        this.pan.add(cancelButton);
        
        /* Create Button */
        JButton createButton = new JButton("Create");
        createButton.setName("create");
        createButton.addMouseListener(this.menuController);
        createButton.addActionListener(menuController);
        this.pan.add(createButton);
    }

    @Override
    public void update(java.util.Observable o, Object o1) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
