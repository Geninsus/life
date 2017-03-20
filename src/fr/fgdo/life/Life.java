/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.fgdo.life;

import fr.fgdo.life.menu.MenuPanel;
import fr.fgdo.life.menu.MenuPanelController;
import fr.fgdo.life.menu.options.Options;
import fr.fgdo.life.menu.options.OptionsPanel;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javax.swing.JButton;
import javax.swing.JFrame;

/**
 *
 * @author Olivier
 */
public class Life extends JFrame{

    private static final int DEFAULT_HEIGHT = 600;
    private static final int DEFAULT_WIDTH = 600;
    private MenuPanel menuPanel;
    private Options options; 
    
    public Life() throws HeadlessException {
        super();
        setUpOption();
        setUpFrame();
        setUpMenuPanel();
    }
    
    private void setUpOption() {
        
            File file = new File("config/options.ser");
            if (!file.isFile()) {
                options = new Options();
                options.save();
            }
            else {
                try {
                    FileInputStream fis = new FileInputStream(file);
                    ObjectInputStream ois = new ObjectInputStream(fis);
                    options = (Options)ois.readObject();
                } catch (Exception e) {
                    options = new Options();
                    options.save();
                    
                }
            }
            
        

    }
    
    private void setUpFrame() {
        this.setTitle("Life");
        this.setSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
    
    private void setUpMenuPanel() {
        MenuPanelController menuPanelController = new MenuPanelController(this);
        menuPanel = new MenuPanel(menuPanelController);
        this.add(menuPanel);
    }
    
    public void switchState(GameState gameState) {
        switch (gameState) {
            case OPTIONS:
                this.getContentPane().removeAll();
                this.add(new OptionsPanel());
                revalidate();
                repaint();
                break;
        }
    }
    
}
