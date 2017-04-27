/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.fgdo.life.neuralNetwork;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.util.Observable;
import java.util.Observer;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLayer;
import javax.swing.JPanel;

/**
 *
 * @author Guillaume
 */
public class NetView extends JPanel implements Observer {
    
    private Net net;
    private JPanel globalPanel;
    private JPanel inputPanel;
    private JPanel layersPanel;
    private JPanel outputPanel;
    private Dimension d = new Dimension(20,20);
    
    public NetView(Net net) {
        this.net = net;
        this.globalPanel = new JPanel();
        this.globalPanel.setLayout(new GridLayout(1,3,30,30));
        this.addInputNeuron();
        this.addLayerNeuron();
        this.addOutputNeuron();
        this.add(globalPanel);
    }
    
    private void addInputNeuron() {
        
        this.inputPanel = new JPanel();
        
        Layer layerInput =  this.net.getLayers().get(0);
        this.inputPanel.setLayout(new GridLayout(0,1,20,20));
        
        
        JButton neuronView;
        for(Neuron neuron : layerInput) {
            neuronView = new JButton(Double.toString(neuron.getValue()));
            neuronView.setName("");
            neuronView.setSize(10, 10);
            this.inputPanel.add(neuronView);
        }
        
        this.globalPanel.add(this.inputPanel);
    }
    
    private void addLayerNeuron() {
        
        this.layersPanel = new JPanel();
        JPanel layerPanel;
        
        int hiddenLayerNumber = this.net.getTopology().length - 2;
        this.layersPanel.setLayout(new GridLayout(1,hiddenLayerNumber,20,20));
        
        JButton neuronView = new JButton();
        
        for (int i = 0; i < hiddenLayerNumber ; i++) {
            Layer hiddenLayer = this.net.getLayers().get(i+1);
            layerPanel = new JPanel(new GridLayout(hiddenLayer.size(),1,50,50));
            for (Neuron neuron : hiddenLayer) {
                neuronView = new JButton(Double.toString(neuron.getValue()));
                neuronView.setName("");
                neuronView.setSize(10,10);
                layerPanel.add(neuronView);
            }
            this.layersPanel.add(layerPanel);
        }
        this.globalPanel.add(layersPanel);
    }
    
    private void addOutputNeuron() {
        
        this.outputPanel = new JPanel();
        this.outputPanel.setLayout(new GridLayout(0,1,20,20));
        
        Layer outputLayer = this.net.getLayers().get(this.net.getLayers().size() -1);
       
        JButton neuronView = new JButton();
        for (Neuron neuron : outputLayer) {
            neuronView = new JButton(Double.toString(neuron.getValue()));
            neuronView.setName("");
            neuronView.setSize(10,10);
            this.outputPanel.add(neuronView);
        }
        
        this.globalPanel.add(this.outputPanel);
    }
    
    public void refresh(){
        this.globalPanel = new JPanel();
        this.globalPanel.setLayout(new GridLayout(1,3,50,50));
        this.addInputNeuron();
        this.addLayerNeuron();
        this.addOutputNeuron();
        this.add(globalPanel);
    }

   
    @Override
    public void update(Observable o, Object arg) {
    }
}
