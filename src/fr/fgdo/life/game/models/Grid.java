/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.fgdo.life.game.models;

import java.util.ArrayList;
import java.util.Observable;

/**
 *
 * @author Olivier
 */
public class Grid extends Observable {
    private int width;
    private int height;
    private ArrayList<Being> beings;
    private Cell board[][];
    public Grid() {
    }

    public Grid(int width, int height) {
        this.width = width;
        this.height = height;
        board = new Cell[height][height];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                board[i][j] = new Cell(CellType.GRASS);
            }
        }
    }

    public Cell[][] getBoard() {
        return board;
    }
    
    public void updateView() {
        setChanged();
        notifyObservers();
        clearChanged();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
    
}
