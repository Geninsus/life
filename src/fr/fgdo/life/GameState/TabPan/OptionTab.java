/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.fgdo.life.GameState.TabPan;

import fr.fgdo.life.GameState.GameState;
import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Observable;
import java.util.Observer;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author Olivier
 */
public class OptionTab extends JPanel implements Observer, MouseListener{

    private JButton playButton;
    private JButton pauseButton;
    
    public OptionTab(GameState gameState) {
        setLayout(new BorderLayout());
        JPanel controlPanel = new JPanel();
        
        playButton = new JButton(new ImageIcon("assets/play.png"));
        playButton.setName("play");
        playButton.addMouseListener(gameState);
        playButton.addMouseListener(this);
        
        pauseButton = new JButton(new ImageIcon("assets/pause.png"));
        pauseButton.setName("pause");
        pauseButton.addMouseListener(gameState);
        pauseButton.addMouseListener(this);
        pauseButton.setEnabled(false);
        
        controlPanel.add(playButton);
        controlPanel.add(pauseButton);
        
        add(controlPanel, BorderLayout.NORTH);
    }

    @Override
    public void update(Observable o, Object arg) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        JButton button = (JButton)e.getSource();
        switch (button.getName()) {
            case "play":
                playButton.setEnabled(false);
                pauseButton.setEnabled(true);
                break;
            case "pause":
                playButton.setEnabled(true);
                pauseButton.setEnabled(false);
                break;
            default:
                break;
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
    
}
