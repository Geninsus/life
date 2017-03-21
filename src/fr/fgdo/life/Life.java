/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.fgdo.life;

import fr.fgdo.life.menu.MenuPanel;
import fr.fgdo.life.menu.MenuPanelController;
import fr.fgdo.life.menu.options.Options;
import fr.fgdo.life.menu.options.OptionsController;
import fr.fgdo.life.menu.options.OptionsPanel;
import fr.fgdo.life.newGame.NewGame;
import fr.fgdo.life.newGame.NewGameController;
import fr.fgdo.life.newGame.NewGamePanel;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import javax.swing.JFrame;

/**
 *
 * @author Olivier
 */
public class Life extends JFrame{

    private static final int DEFAULT_HEIGHT = 600;
    private static final int DEFAULT_WIDTH = 600;
    private MenuPanel menuPanel;
    private NewGamePanel newGamePanel;
    private OptionsPanel optionsPanel;
    private Options options;
    
    public Life() throws HeadlessException {
        super();
        
        setUpOption();
        setUpFrame();
        setUpMenuPanel();
        setUpOptionsPanel();
        setUpNewGamePanel();
        switchState(GameState.MENU);
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
                } catch (IOException | ClassNotFoundException e) {
                    options = new Options();
                    options.save();
                    
                }
            }
            options.setLifeGame(this);

    }
    
    private void setUpFrame() {
        this.setTitle("Life");
        this.setSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        if (options.isFullscreenMode()) setFullscreen();
        else removeFullscreen();
        this.setVisible(true);
    }
    
    private void setUpMenuPanel() {
        MenuPanelController menuPanelController = new MenuPanelController(this);
        menuPanel = new MenuPanel(menuPanelController);
    }
    
    private void setUpOptionsPanel() {
        optionsPanel = new OptionsPanel(options, new OptionsController(options,this));
    }
    
    private void setUpNewGamePanel() {
        NewGame newGame = new NewGame();
        NewGameController newGameController = new NewGameController(newGame);
        newGamePanel = new NewGamePanel(newGameController);
        newGameController.setView(newGamePanel);
    }
    
    public final void switchState(GameState gameState) {
        this.getContentPane().removeAll();
        switch (gameState) {
            case MENU:
                this.add(menuPanel);
                break;
            case NEW_GAME:
                this.add(newGamePanel);
                break;
            case OPTIONS:
                this.add(optionsPanel);
                break;
        }
        revalidate();
        repaint();
    }
    
    public void setFullscreen() {
        setExtendedState(JFrame.MAXIMIZED_BOTH); 
        setVisible(true);
    }
    
    public void removeFullscreen() {
        setExtendedState(JFrame.NORMAL); 
        setVisible(true);
    }
}
