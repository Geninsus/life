/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.fgdo.math;

import java.io.Serializable;

/**
 *
 * @author Olivier
 */
public class Vector2 implements Serializable{
    
    public int x;
    public int y;

    public Vector2() {
    }

    public Vector2(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
}
