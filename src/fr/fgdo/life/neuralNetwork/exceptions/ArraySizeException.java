/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.fgdo.life.neuralNetwork.exceptions;

/**
 *
 * @author guillaume
 */
public class ArraySizeException extends Exception{
    public ArraySizeException(int size) {
        super("Unexpected array size.(" + size + ")");
    }
}
