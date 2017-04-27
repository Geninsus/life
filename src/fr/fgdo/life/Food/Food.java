/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.fgdo.life.Food;

import fr.fgdo.life.GameObject.GameObject;
import fr.fgdo.life.GameState.Board.Board;
import fr.fgdo.life.Life;
import fr.fgdo.math.Point;
import java.awt.Color;
import java.io.Serializable;

/**
 *
 * @author Olivier
 */
public class Food extends GameObject implements Serializable{

    private int value = Life.rand.nextInt(30)+20;
    
    public Food() {
        this.radius = 10;
        this.color = Color.gray;
        this.center = new Point(Life.rand.nextInt(Board.width), Life.rand.nextInt(Board.height));
    }

    public int getValue() {
        return value;
    }
    
}
