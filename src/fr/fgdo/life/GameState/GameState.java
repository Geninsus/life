/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.fgdo.life.GameState;

import fr.fgdo.life.GameState.Board.Board;
import fr.fgdo.life.GameState.Board.BoardView;
import fr.fgdo.life.Life;
import fr.fgdo.life.State.State;
import javax.swing.JButton;

/**
 *
 * @author olivbau
 */
public class GameState extends State{

    private Board board;
    private BoardView boardView;
    
    public GameState(Life lifeGame) {
        super(lifeGame);
        
    }

    @Override
    public void start() {
        super.start(); //To change body of generated methods, choose Tools | Templates.
        board = new Board(getLifeGame().getGridParams());
        boardView = new BoardView();
        add(boardView);
    }

    
}
