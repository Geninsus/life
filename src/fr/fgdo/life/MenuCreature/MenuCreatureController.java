/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.fgdo.life.MenuCreature;

import fr.fgdo.life.Creature.Creature;
import fr.fgdo.life.GameState.Board.Board;
import fr.fgdo.life.neuralNetwork.exceptions.TopologySizeException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;

/**
 *
 * @author Guillaume
 */
public class MenuCreatureController implements MouseListener, ActionListener{
    MenuCreatureView menuView;
    Board board;
    public MenuCreatureController(Board board){
        super();
        this.menuView = new MenuCreatureView(this);
        this.board = board;
    }
    
    public void close(){
        this.menuView.setVisible(false);
        this.menuView.setEnabled(false);
    }
    @Override
    public void mouseClicked(MouseEvent me) {
        if(me.getSource().getClass() == JButton.class){
            JButton button = (JButton)me.getSource();
            switch(button.getName()){
                case "cancel":
                    this.close();
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent me) {
         //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseReleased(MouseEvent me) {
         //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseEntered(MouseEvent me) {
         //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent me) {
         //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
         if(ae.getSource().getClass() == JButton.class){
            JButton button = (JButton)ae.getSource();
            switch(button.getName()){
                case "create":
            {
                try {
                    this.board.addCreature(new Creature());
                } catch (TopologySizeException ex) {
                    Logger.getLogger(MenuCreatureController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
                    this.close();
                default:
                    break;
            }
        }
    }
    
}
