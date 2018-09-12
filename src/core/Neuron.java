package core;

import java.util.ArrayList;
import java.util.Collection;

/**
 * 
 * @author Paul Merker
 * @version 1.0.1
 * The Neuron class provides implementation of Neuron in a NN.
 *
 */
public class Neuron {
	protected float output;
    protected Collection connections; 
    protected boolean bias = false;
    
    /**
     * Constructor for non biased, normal neuron. Instanciates neuron and sets fields accordingly
     */
    public Neuron() {
        this.output = 0;
        this.connections = new ArrayList();  
        this.bias = false;
    }

    /**
     * Constructor for biased neuron.
     * @param bias Bias value for output from bias neuron
     */
    public Neuron(int bias) {
        this.output = bias;
        this.connections = new ArrayList();
        this.bias = true;
    }
    
    /**
     * Compiles the output for given Neuron of NeuronType and sets its output field accordingly
     */
    public void calcOutput() {
    		// if neuron is no bias itself, calculate the output
        if (!this.bias) {
            float sum = 0f;
            float bias = 0f;
            
            for (int i = 0; i < this.connections.size(); ++i) {
            	
            		// calculate each ouptut to this neuron from each neuron pointing
            		// to this using input * weight
                Connection c = (Connection) ((ArrayList) this.connections).get(i);
                Neuron from = c.getFrom();
                Neuron to = c.getTo();
                
                if (to == this) {
                    
                		// if incoming output is from bias neuron, then
                		// calculate the bias to add upon the activation
                    if (from.bias) {
                        bias = from.getOutput() * c.getWeight();
                    } else {
                        sum += from.getOutput() * c.getWeight();
                    }
                }
            }
            // Output is result of sigmoid function
            this.output = f(bias+sum);
        }
    }
    
    /**
     * Add a connection between two Neurons to this neuron's connection list (Collection).
     * @param c Connection type connection
     * @see Connection
     */
    public void addConnection(Connection c) {
        this.connections.add(c);
    }

    /**
     * 
     * @return Returns the output value of the neuron
     */
    public float getOutput() {
        return this.output;
    }

    /**
     * Activation function for given neuron (Sigmoid function). Can be adjusted in source code only.
     * @param x The given input for activation
     * @return Returns output of Sigmoid function with given input x
     */
    public static float f(float x) {
        return 1.0f / (1.0f + (float) Math.exp(-x));
    }
    
    /**
     * 
     * @return Returns Collection of Connections the neuron holds
     */
    public Collection getConnections() {
        return this.connections;
    }
}
