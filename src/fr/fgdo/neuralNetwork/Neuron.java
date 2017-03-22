/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package neuralNetwork;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author guillaume
 */
public class Neuron {
    private int index;
    private double value;
    private ArrayList<Double> outputWeights;
    
    /**
     *
     * @param numOutputs
     * @param index
     */
    public Neuron(int numOutputs, int index) {
        this.outputWeights = new ArrayList<Double>();
        this.index = index;
        for (int outputIndex = 0; outputIndex < numOutputs; outputIndex++) {
            this.outputWeights.add(randomizeWeight());
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
