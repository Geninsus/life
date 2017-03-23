/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.fgdo.life;

import fr.fgdo.life.GameState.GameState;
import fr.fgdo.life.GameState.Board.BoardParams;
import fr.fgdo.life.State.State;
import fr.fgdo.life.MainMenuState.MainMenuState;
import fr.fgdo.life.NewGameState.NewGameState;
import java.awt.HeadlessException;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JFrame;

/**
 *
 * @author Olivier
 */
public class Life extends JFrame{

    private static final int DEFAULT_HEIGHT = 600;
    private static final int DEFAULT_WIDTH = 600;
    private ArrayList<State> gameStates = new ArrayList<>();
    private int currentStateId = -1;
    private BoardParams boardParams;
    
    public static final Random rand = new Random();
    
    public Life() throws HeadlessException {
        super();
        setupFrame();
        setupStates();
        enterState(0);
        setVisible(true);
    }
    
    public static void main(String[] args) {
        Life lifeGame = new Life();
    }
    
    private void setupFrame() {
        setTitle("Life");
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
    
    private void setupStates() {
        gameStates.add(new MainMenuState(this));
        gameStates.add(new NewGameState(this));
        gameStates.add(new GameState(this));
    }
    
    public void enterState(int id) {
        if (currentStateId > -1) remove(gameStates.get(currentStateId));
        add(gameStates.get(id));
        gameStates.get(id).start();
        currentStateId = id;
        revalidate();
        repaint();
    }

    public BoardParams getGridParams() {
        return boardParams;
    }

    public void setGridParams(BoardParams gridParams) {
        this.boardParams = gridParams;
    }
    
}
