/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package neuralNetwork;

import java.util.ArrayList;
import neuralNetwork.exceptions.TopologySizeException;

/**
 *
 * @author guillaume
 */
public class Net {
    private int[] topology;
    private int numLayer;
    private ArrayList<ArrayList<Neuron>> layers;
    private double fitness;
    public Net(int[] topology) throws TopologySizeException {
        if(topology.length < 2){
           throw new TopologySizeException("Net must contain at least 2 layers.");
        }
        this.topology = topology;
        this.numLayer = topology.length;
        this.layers = new ArrayList<ArrayList<Neuron>>();
        for (int layerIndex : topology) {
            this.layers.add(new ArrayList<Neuron>());
            for (int neuronIndex = 0; neuronIndex<topology[layerIndex]; neuronIndex++) {
                //this.layers.get(layerIndex).add(new Neuron());
                
            }
        }
    }
}
