/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.fgdo.life.GameState.Board.Tabbed;

import fr.fgdo.life.Creature.Creature;
import fr.fgdo.life.GameState.GameState;
import fr.fgdo.life.neuralNetwork.exceptions.TopologySizeException;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;

/**
 *
 * @author Olivier
 */
public class BoardSavedCreaturesTab extends JPanel implements MouseListener{
    
    ArrayList<Creature> savedCreatures;
    JList<String> listSavedCreatures;
    
    GameState gameState;
    
    public BoardSavedCreaturesTab(GameState gameState) {
        this.gameState = gameState;
        
        JButton addCreaturesButton = new JButton("Add Creature(s)");
        addCreaturesButton.setName("addCreatureButton");
        addCreaturesButton.addMouseListener(this);
        add(addCreaturesButton);
        
        JButton refreshButton = new JButton("Refresh");
        refreshButton.setName("refreshButton");
        refreshButton.addMouseListener(this);
        add(refreshButton); 
        
        refresh();
        
    }

    public void refresh() {
        
        savedCreatures = new ArrayList<>();
        File folder = new File("savedCreatures");
        for (File file : folder.listFiles()) {
            savedCreatures.add(Creature.open(file.getPath()));
        }
        
        String[] listNameSavedCreatures = new String[savedCreatures.size()];
        for (int i = 0; i < savedCreatures.size(); i++) {
            listNameSavedCreatures[i] = savedCreatures.get(i).getName();
        }
        if (listSavedCreatures != null) remove(listSavedCreatures);
        listSavedCreatures = new JList<>(listNameSavedCreatures);
        add(listSavedCreatures);
        repaint();
        revalidate();
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        JButton button = (JButton)e.getSource();
        
        switch(button.getName()) {
            case "refreshButton":
                refresh();
                break;
            case "addCreatureButton":
                for (int indice : listSavedCreatures.getSelectedIndices()) {
                    Creature creature = savedCreatures.get(indice).clone();
                    if (creature != null )gameState.getBoard().addCreature(creature);
                }
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
