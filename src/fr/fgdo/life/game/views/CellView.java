/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.fgdo.life.game.views;

import fr.fgdo.life.game.models.Cell;
import fr.fgdo.life.game.models.CellType;
import java.awt.BorderLayout;
import java.awt.Color;
import java.util.Observable;
import java.util.Observer;
import javafx.scene.layout.Border;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

/**
 *
 * @author Olivier
 */
public class CellView extends JPanel implements Observer{

    JLabel numberBeings;
    BeingView beingView;
    
    public CellView() {
        super();
        setLayout(new BorderLayout());
        setBorder(new LineBorder(Color.black, 1));
        numberBeings = new JLabel("");
        add(numberBeings,BorderLayout.NORTH);
    }

    @Override
    public void update(Observable o, Object arg) {
        Cell cell = (Cell)o;
        if (cell.type == CellType.GRASS) setBackground(new Color(140,255,120));
        numberBeings.setText(Integer.toString(cell.getNumBeings()));
        try {
            remove(beingView);
        } catch (Exception e) {
        }
        if (cell.getFirstBeing() != null) {
            beingView = new BeingView(cell.getFirstBeing());
            add(beingView,BorderLayout.CENTER);
        }
    }
    
}
