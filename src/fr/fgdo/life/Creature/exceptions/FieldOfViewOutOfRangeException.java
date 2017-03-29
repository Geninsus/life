/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.fgdo.life.Creature.exceptions;

/**
 *
 * @author guillaume
 */
public class FieldOfViewOutOfRangeException extends Exception{
    public FieldOfViewOutOfRangeException(String message) {
        super(message);
    }
}
