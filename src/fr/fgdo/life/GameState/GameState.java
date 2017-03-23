/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.fgdo.life.GameState;

import fr.fgdo.life.Creature.Creature;
import fr.fgdo.life.GameState.Board.Board;
import fr.fgdo.life.GameState.Board.BoardView;
import fr.fgdo.life.Life;
import fr.fgdo.life.State.State;
import fr.fgdo.math.Point;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JLabel;

/**
 *
 * @author olivbau
 */
public class GameState extends State implements MouseListener{

    private Board board;
    private BoardView boardView;
    Random rand = new Random();
    
    public GameState(Life lifeGame) {
        super(lifeGame);
        setLayout(new BorderLayout());
        JButton button = new JButton("Add Creature");
        button.addMouseListener(this);
        add(button, BorderLayout.WEST);
    }

    @Override
    public void start() {
        super.start(); //To change body of generated methods, choose Tools | Templates.
        board = new Board(getLifeGame().getGridParams());
        boardView = new BoardView(board);
        board.addObserver(boardView);
        add(boardView, BorderLayout.CENTER);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        board.addCreature(new Creature( rand.nextInt(30)+30, new Color(rand.nextFloat(),rand.nextFloat(),rand.nextFloat()), new Point<Integer>(10,10),board));
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
