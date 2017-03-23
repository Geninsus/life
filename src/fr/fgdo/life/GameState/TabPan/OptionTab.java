/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.fgdo.life.GameState.TabPan;

import fr.fgdo.life.GameState.GameState;
import java.awt.BorderLayout;
import java.util.Observable;
import java.util.Observer;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author Olivier
 */
public class OptionTab extends JPanel implements Observer{

    public OptionTab(GameState gameState) {
        setLayout(new BorderLayout());
        JPanel controlPanel = new JPanel();
        
        JButton playButton = new JButton(new ImageIcon("assets/play.png"));
        playButton.setName("play");
        
        JButton pauseButton = new JButton(new ImageIcon("assets/pause.png"));
        pauseButton.setName("pause");
        pauseButton.setEnabled(false);
        
        controlPanel.add(playButton);
        controlPanel.add(pauseButton);
        
        add(controlPanel, BorderLayout.NORTH);
    }

    @Override
    public void update(Observable o, Object arg) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
