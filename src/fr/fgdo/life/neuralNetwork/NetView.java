/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.fgdo.life.neuralNetwork;

import java.awt.Color;
import java.awt.Component;
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
    private Dimension d = new Dimension(5,5);
    
    
    public NetView(Net net) {
        this.setSize(100,100);
        this.net = net;
        this.globalPanel = new JPanel();
        this.globalPanel.setLayout(new GridLayout(1,3,20,20));
        this.addInputNeuron();
        this.addLayerNeuron();
        this.addOutputNeuron();
        this.add(globalPanel);
    }
    
    public void update(){
        if(this.net instanceof Net){
            Color color;
            JButton button;
            Double value;
            /* Update Input Button Value */
            Component[] components = this.inputPanel.getComponents();
            System.err.println(components.length);
            Layer layer = this.net.getLayers().get(0);
            int j = 0;
            for (Neuron neuron : layer) {
                value = Math.floor(neuron.getValue() * 100) / 100;
                button = (JButton) components[j];
                color = new Color((int)(value+1)*127,(int)(value + 1)*56 + 142,252);
                button.setBackground(color);
                button.setText(Double.toString(neuron.getValue()));
                j++;
            }
            /* Update Layer Button Value */
            components = this.layersPanel.getComponents();
            Component[] componentsIn;
            
            int hiddenLayerNumber = this.net.getTopology().length - 2;
            System.err.println(hiddenLayerNumber);
            for (int i = 0; i < hiddenLayerNumber ; i++) {
                j=0;
                JPanel panel = (JPanel) components[i];
                componentsIn = panel.getComponents();
                Layer hiddenLayer = this.net.getLayers().get(i+1);
                for (Neuron neuron : hiddenLayer) {
                    value = Math.floor(neuron.getValue() * 100) / 100;
                    button = (JButton) componentsIn[j];
                    color = new Color((int)(value+1)*127,(int)(value+1)*56 + 142,252);
                    button.setBackground(color);
                    button.setText(Double.toString(value));
                    j++;
                }
            }
            /* Update Output Button Value */
            components = this.outputPanel.getComponents();
            System.err.println(components.length);
            layer = this.net.getLayers().get(this.net.getLayers().size() -1);
            j = 0;
            for (Neuron neuron : layer) {
                value = Math.floor(neuron.getValue() * 100) / 100;
                button = (JButton) components[j];
                color = new Color((int)(value+1)*127,(int)(value+1)*56 + 142,252);
                button.setBackground(color);
                button.setText(Double.toString(value));
                j++;
            }
        }
        
    }
    
    private void addInputNeuron() {
        
        this.inputPanel = new JPanel();
        
        Layer layerInput =  this.net.getLayers().get(0);
        this.inputPanel.setLayout(new GridLayout(0,1,10,10));
        
        
        JButton neuronView;
        for(Neuron neuron : layerInput) {
            neuronView = new JButton(Double.toString(neuron.getValue()));
            neuronView.setName("");
            neuronView.setSize(d);
            this.inputPanel.add(neuronView);
        }
        
        this.globalPanel.add(this.inputPanel);
    }
    
    private void addLayerNeuron() {
        
        this.layersPanel = new JPanel();
        JPanel layerPanel;
        
        int hiddenLayerNumber = this.net.getTopology().length - 2;
        this.layersPanel.setLayout(new GridLayout(1,hiddenLayerNumber,10,10));
        
        JButton neuronView = new JButton();
        
        for (int i = 0; i < hiddenLayerNumber ; i++) {
            Layer hiddenLayer = this.net.getLayers().get(i+1);
            layerPanel = new JPanel(new GridLayout(hiddenLayer.size(),1,25,25));
            for (Neuron neuron : hiddenLayer) {
                neuronView = new JButton(Double.toString(neuron.getValue()));
                neuronView.setName("");
                neuronView.setSize(d);
                layerPanel.add(neuronView);
            }
            this.layersPanel.add(layerPanel);
        }
        this.globalPanel.add(layersPanel);
    }
    
    private void addOutputNeuron() {
        
        this.outputPanel = new JPanel();
        this.outputPanel.setLayout(new GridLayout(0,1,10,10));
        
        Layer outputLayer = this.net.getLayers().get(this.net.getLayers().size() -1);
       
        JButton neuronView = new JButton();
        for (Neuron neuron : outputLayer) {
            neuronView = new JButton(Double.toString(neuron.getValue()));
            neuronView.setName("");
            neuronView.setSize(d);
            this.outputPanel.add(neuronView);
        }
        
        this.globalPanel.add(this.outputPanel);
    }

    public void setNet(Net net){
        this.net = net;
    }
    
    @Override
    public void update(Observable o, Object arg) {
    }
    
    @Override
    public void update(Graphics g){
        this.update();
    }
}
