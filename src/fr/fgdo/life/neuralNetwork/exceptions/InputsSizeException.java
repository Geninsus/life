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
public class InputsSizeException extends Exception{
    public InputsSizeException(int size, int secondSize) {
        super("Unexpected inputs size.(" + size +"!=" + secondSize + ")");
    }
}
