/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.fgdo.life.game.views;

import fr.fgdo.life.game.models.Grid;
import java.awt.GridLayout;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author Olivier
 */
public class GridView extends JPanel implements Observer{
    
    CellView[][] boardViews;
    
    public GridView() {
        super();
        
    }

    @Override
    public void update(Observable o, Object arg) {
        Grid grid = (Grid)o;
        int rows = grid.getWidth();
        int cols = grid.getHeight();
        if (boardViews == null) {
            boardViews = new CellView[rows][cols];
            setLayout(new GridLayout(rows, cols));
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    boardViews[i][j] = new CellView();
                    grid.getBoard()[i][j].addObserver(boardViews[i][j]);
                    grid.getBoard()[i][j].updateView();
                    add(boardViews[i][j]);
                }
            }
        }
        
        
    }
    
}
