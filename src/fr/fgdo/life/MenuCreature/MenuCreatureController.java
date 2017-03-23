/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.fgdo.life.MenuCreature;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;

/**
 *
 * @author Guillaume
 */
public class MenuCreatureController implements MouseListener{
    MenuCreatureView menuView;
    public MenuCreatureController(){
        super();
        this.menuView = new MenuCreatureView(this);
    }
    @Override
    public void mouseClicked(MouseEvent me) {
        if(me.getSource().getClass() == JButton.class){
            JButton button = (JButton)me.getSource();
            switch(button.getName()){
                case "quit":
                    System.out.println("quit");
                    this.menuView.setVisible(false);
                    this.menuView.setEnabled(false);
                    break;
                case "create":
                    // Add creature too gameState
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
    
}
