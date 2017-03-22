/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.fgdo.life.GameState.Board;

/**
 *
 * @author Olivier
 */
public class Board {

    private final int width;
    private final int height;
    private final String name;
    
    public Board(BoardParams params) {
        this.width = params.size.x;
        this.height = params.size.y;
        this.name = params.name;
    }
    
    
}
