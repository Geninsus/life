/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.fgdo.life.neuralNetwork;

import fr.fgdo.life.neuralNetwork.exceptions.ArraySizeException;
import java.util.ArrayList;
import fr.fgdo.life.neuralNetwork.exceptions.InputsSizeException;
import fr.fgdo.life.neuralNetwork.exceptions.TopologySizeException;
import java.util.Arrays;

/**
 *
 * @author guillaume
 */
public class Net {
    private int[] topology;
    private int numLayer;
    private ArrayList<Layer> layers;
    private double mutationRate = 0.05;

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
        this.layers = new ArrayList<>();
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
     * @param topology
     * @throws TopologySizeException
     */
    public Net(Net... parentNets) throws TopologySizeException, ArraySizeException {
        if(parentNets.length == 0) {
            throw new ArraySizeException(parentNets.length);
        }
        for (int parentNetIndex = 1; parentNetIndex < parentNets.length; parentNetIndex++) {
            if(!Arrays.equals(parentNets[0].topology, parentNets[parentNetIndex].topology)) {
                throw new TopologySizeException("Topology don't match.");
            }
        }
        
        this.topology = Arrays.copyOf(parentNets[0].topology, parentNets[0].topology.length);
        this.numLayer = topology.length;
        this.layers = new ArrayList<>();
        for (int layerIndex = 0; layerIndex < topology.length; layerIndex++) {
            this.layers.add(new Layer());
            for (int neuronIndex = 0; neuronIndex<topology[layerIndex]+1; neuronIndex++) {
                
                // Get parents neurons
                Neuron[] parentNeurons = new Neuron[parentNets.length];
                for(int parentNetIndex = 0; parentNetIndex < parentNets.length; parentNetIndex++) {
                    parentNeurons[parentNetIndex] = parentNets[parentNetIndex].layers.get(layerIndex).get(neuronIndex);
                }
                
                if(layerIndex+1 == numLayer){
                    this.layers.get(layerIndex).add(new Neuron(0, neuronIndex, parentNeurons, mutationRate));
                }else{
                    this.layers.get(layerIndex).add(new Neuron(topology[layerIndex+1], neuronIndex, parentNeurons, mutationRate));
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
