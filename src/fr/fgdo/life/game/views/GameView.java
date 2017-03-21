/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.fgdo.life.game.views;

import fr.fgdo.life.game.models.Game;
import fr.fgdo.life.game.models.LeftPanelController;
import java.awt.BorderLayout;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author Olivier
 */
public class GameView extends JPanel implements Observer{
    
    private GridView gridView;
    private LeftPanelView leftPanelView;
    
    public GameView(Game game) {
        super();
        setLayout(new BorderLayout(3, 3));
        leftPanelView = new LeftPanelView(new LeftPanelController(game));
        add(leftPanelView, BorderLayout.WEST);
    }

    @Override
    public void update(Observable o, Object arg) {
        Game game = (Game)o;
        System.out.println(game);
        if (gridView == null) {
            gridView = new GridView();
            game.getGrid().addObserver(gridView);
            game.getGrid().updateView();
        }
        add(gridView,BorderLayout.CENTER);
    }
    
}
