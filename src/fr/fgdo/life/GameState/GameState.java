/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.fgdo.life.GameState;

import fr.fgdo.life.Creature.Creature;
import fr.fgdo.life.GameState.Board.Board;
import fr.fgdo.life.GameState.Board.BoardView;
import fr.fgdo.life.GameState.Board.BoardTabbedView;
import fr.fgdo.life.Life;
import fr.fgdo.life.MenuCreature.MenuCreatureController;
import fr.fgdo.life.MenuCreature.MenuCreatureView;
import fr.fgdo.life.State.State;
import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;
import javax.swing.JButton;
import fr.fgdo.life.neuralNetwork.exceptions.TopologySizeException;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JCheckBox;
import javax.swing.JSlider;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author olivbau
 */
public class GameState extends State implements MouseListener,ChangeListener, ItemListener{

    private Board board;
    private BoardView boardView;
    Random rand = new Random();
    private final BoardTabbedView boardTabbedPane;
    private final JTabbedPane tabbedPane = new JTabbedPane();
    
    public GameState(Life lifeGame) {
        super(lifeGame);
        setLayout(new BorderLayout());
        boardTabbedPane = new BoardTabbedView(this);
        add(boardTabbedPane, BorderLayout.WEST);
    }

    @Override
    public void start() {
        super.start(); //To change body of generated methods, choose Tools | Templates.
        board = new Board(getLifeGame().getGridParams());
        boardView = new BoardView(board);
        board.addObserver(boardView);
        add(boardView, BorderLayout.CENTER);
        board.addObserver(boardTabbedPane);
        board.run();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        
        if (e.getSource().getClass() == JButton.class) {
            JButton button = (JButton)e.getSource();
            switch(button.getName()) {
                case "addCreature":
                        MenuCreatureController menuControl = new MenuCreatureController(board);
                    break;
                case "addFood":
                    System.out.println("Need to implement : Add Food");
                    break;
                case "play":
                    board.run();
                    break;
                case "pause":
                    board.pause();
                    break;
                default:
                    break;
            }
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

    public Board getBoard() {
        return board;
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        JSlider source = (JSlider)e.getSource();
        board.setSpeed(source.getValue());
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        JCheckBox checkbox = (JCheckBox)e.getSource();
        switch (checkbox.getName()) {
            case "showCreaturesNames":
                boardView.showingCreaturesNames(checkbox.isSelected());
                break;
            case "showCreaturesVision":
                boardView.showingCreaturesVisions(checkbox.isSelected());
                break;
            case "showIterations":
                boardView.showingIterations(checkbox.isSelected());
                break;
            default:
                break;
        }
    }

}
