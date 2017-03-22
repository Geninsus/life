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
    private int value;
    private ArrayList<Double> outputWeights;
    public Neuron(int numOutputs, int index) {
        this.outputWeights = new ArrayList<Double>();
        for (int outputIndex = 0; outputIndex < numOutputs; outputIndex++) {
            this.outputWeights.add(randomizeWeight());
        }
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
}
