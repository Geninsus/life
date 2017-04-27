/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.fgdo.life.neuralNetwork;

import fr.fgdo.life.Food.Food;
import fr.fgdo.life.Life;
import fr.fgdo.life.neuralNetwork.exceptions.ArraySizeException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author guillaume
 */
public class Neuron implements Serializable{
    private int index;
    private double value;
    private ArrayList<Double> outputWeights;
    
    /**
     *
     * @param numOutputs
     * @param index
     */
    public Neuron(int numOutputs, int index) {
        this.outputWeights = new ArrayList<>();
        this.index = index;
        for (int outputIndex = 0; outputIndex < numOutputs; outputIndex++) {
            this.outputWeights.add(randomizeWeight());
        }
    }
    
    /**
     *
     * @param numOutputs
     * @param index
     * @param parentNeurons
     * @param mutationRate
     * @throws fr.fgdo.life.neuralNetwork.exceptions.ArraySizeException
     */
    public Neuron(int numOutputs, int index, Neuron[] parentNeurons, double mutationRate) throws ArraySizeException {
        this.outputWeights = new ArrayList<>();
        this.index = index;
        for (int outputIndex = 0; outputIndex < numOutputs; outputIndex++) {
            if(parentNeurons.length == 0) {
                throw new ArraySizeException(parentNeurons.length);
            } 
            
            if (Life.rand.nextFloat() > mutationRate) {
                int randIndex = (int)(Math.random() * parentNeurons.length);
            this.outputWeights.add(parentNeurons[randIndex].outputWeights.get(outputIndex));
            } else {
                this.outputWeights.add(randomizeWeight());
            }
        }
    }
    
    /**
     *
     * @param previousLayer
     */
    public void feedForward(Layer previousLayer){
        double sum = 0;
        for (Neuron neuron : previousLayer) {
            sum += neuron.getValue()*neuron.getOutputWeights().get(this.index);
        }
        this.value = this.activationFunction(sum);
    }
    
    /**
     *
     * @param value
     * @return
     */
    public Double activationFunction(Double value) {
        return Math.tanh(value);
    }
    
    private double randomizeWeight() {
        return new Random().nextDouble()*2 -1;
    }

    /**
     * @return the outputWeights
     */
    public ArrayList<Double> getOutputWeights() {
        return outputWeights;
    }

    /**
     * @return the value
     */
    public Double getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(Double value) {
        this.value = value;
    }
}
