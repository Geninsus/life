/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.fgdo.life.game.views;

import fr.fgdo.life.game.models.Cell;
import fr.fgdo.life.game.models.CellType;
import java.awt.Color;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

/**
 *
 * @author Olivier
 */
public class CellView extends JPanel implements Observer{

    public CellView() {
        super();
        setBorder(new LineBorder(Color.black, 1));
    }

    @Override
    public void update(Observable o, Object arg) {
        Cell cell = (Cell)o;
        if (cell.type == CellType.GRASS) setBackground(new Color(140,255,120));
    }
    
}
