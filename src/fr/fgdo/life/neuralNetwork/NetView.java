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
public class NetView extends JFrame implements Observer {
    
    private Net net;
    private JPanel globalPanel;
    private JPanel inputPanel;
    private JPanel layersPanel;
    private JPanel outputPanel;
    private Dimension d = new Dimension(20,20);
    
    public NetView(Net net) {
        super("NetView");
        this.setSize(400, 400);
        this.net = net;
        this.globalPanel = new JPanel();
        this.globalPanel.setLayout(new GridLayout(1,3,50,50));
        this.addInputNeuron();
        this.addLayerNeuron();
        this.addOutputNeuron();
        this.add(globalPanel);
        
    }
    
    public void addInputNeuron() {
        
        this.inputPanel = new JPanel();
        this.inputPanel.setLayout(new GridLayout(0,1,50,50));
        
        int inputNumber = this.net.getTopology()[0];
        JButton neuron;
        for (int i = 0; i < inputNumber; i++) {
            neuron = new JButton("");
            neuron.setName(""+i);
            neuron.setSize(this.d);
            this.inputPanel.add(neuron);
        }
        
        this.globalPanel.add(this.inputPanel);
    }
    
    public void addLayerNeuron() {
        
        this.layersPanel = new JPanel();
        JPanel layerPanel;
        int layerNumber = this.net.getTopology().length - 2;
        this.layersPanel.setLayout(new GridLayout(1,layerNumber,50,50));
        
        JButton neuron = new JButton();
        
        for (int i = 0; i < layerNumber ; i++) {
            layerPanel = new JPanel(new GridLayout(this.net.getTopology()[i+1],1,50,50));
            for (int j = 0; j < this.net.getTopology()[i+1]; j++) {
                
                neuron = new JButton();
                neuron.setName(""+i+"/"+j);
                neuron.setSize(this.d);
                layerPanel.add(neuron);
            }
            this.layersPanel.add(layerPanel);
        }
        this.globalPanel.add(layersPanel);
    }
    
    public void addOutputNeuron() {
        
        this.outputPanel = new JPanel();
        this.outputPanel.setLayout(new GridLayout(0,1,50,50));
        
        int inputNumber = this.net.getTopology()[this.net.getTopology().length - 1];
        JButton neuron = new JButton();
        for (int i = 0; i < inputNumber; i++) {
            neuron = new JButton("");
            neuron.setName(""+i);
            neuron.setSize(this.d);
            this.outputPanel.add(neuron);
        }
        
        this.globalPanel.add(this.outputPanel);
    }
    
    public void visible(Boolean b) {
        this.setVisible(b);
    }

    @Override
    public void update(Observable o, Object arg) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
