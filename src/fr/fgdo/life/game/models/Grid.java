/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.fgdo.life.game.models;

import fr.fgdo.math.Point;
import java.util.ArrayList;
import java.util.Observable;

/**
 *
 * @author Olivier
 */
public class Grid extends Observable {
    private int rows;
    private int cols;
    private ArrayList<Being> beings;
    private Cell board[][];
    public Grid() {
    }

    public Grid(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        System.out.println(rows);
        board = new Cell[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
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
        return rows;
    }

    public int getHeight() {
        return cols;
    }
    
    public void addBeing(Being being, Point coord) {
        board[coord.y][coord.x].addBeing(being);
    }
    
}
