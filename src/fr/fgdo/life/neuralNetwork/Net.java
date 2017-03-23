/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.fgdo.life.neuralNetwork;

import java.util.ArrayList;
import fr.fgdo.life.neuralNetwork.exceptions.InputsSizeException;
import fr.fgdo.life.neuralNetwork.exceptions.TopologySizeException;

/**
 *
 * @author guillaume
 */
public class Net {
    private int[] topology;
    private int numLayer;
    private ArrayList<Layer> layers;
    private double fitness;

    /**
     *
     * @param topology
     * @throws TopologySizeException
     */
    public Net(int[] topology) throws TopologySizeException {
        if(topology.length < 2){
           throw new TopologySizeException("Net must contain at least 2 layers.");
        }
        this.topology = topology;
        this.numLayer = topology.length;
        this.layers = new ArrayList<Layer>();
        for (int layerIndex = 0; layerIndex < topology.length; layerIndex++) {
            this.layers.add(new Layer());
            for (int neuronIndex = 0; neuronIndex<topology[layerIndex]+1; neuronIndex++) {
                if(layerIndex+1 == numLayer){
                    this.layers.get(layerIndex).add(new Neuron(0, neuronIndex));
                }else{
                    this.layers.get(layerIndex).add(new Neuron(topology[layerIndex+1], neuronIndex));
                }
            }
            this.layers.get(layerIndex).get(topology[layerIndex]).setValue(1.0);
        }
        
    }
    
    /**
     *
     * @param inputs
     * @return
     * @throws InputsSizeException
     */
    public Double[] feedForward(Double[] inputs) throws InputsSizeException{
        if(inputs.length != layers.get(0).size()-1){
            throw new InputsSizeException(inputs.length,layers.get(0).size()-1);
        }
        for (int inputIndex = 0; inputIndex < topology[0]; inputIndex++) {
            layers.get(0).get(inputIndex).setValue(inputs[inputIndex]);
        }
        //normalize
        Double[] outputs = new Double[topology[numLayer-1]];
        Layer previousLayer;
        for (int layerIndex = 1; layerIndex < layers.size(); layerIndex++)  {
            previousLayer = layers.get(layerIndex-1);
            for (int neuronIndex = 0; neuronIndex < topology[layerIndex]; neuronIndex++) {
                layers.get(layerIndex).get(neuronIndex).feedForward(previousLayer);
                if(layerIndex == layers.size()-1){
                    outputs[neuronIndex] = layers.get(layerIndex).get(neuronIndex).getValue();
                }
            }
        }
        return outputs;
    }
    
    /*public double normalizeInputs() {
        return ;
    }
    
    public double normalizeOutputs() {
        return ;
    }*/
}
