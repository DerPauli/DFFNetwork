package rnn;

import java.util.ArrayList;
import java.util.Collection;

import core.Connection;
import core.HiddenNeuron;
import core.InputNeuron;
import core.Neuron;
import core.OutputNeuron;
import layer.HiddenLayer;
import layer.InputLayer;
import layer.Layer;
import layer.OutputLayer;
/**
 * 
 * @author Paul Merker
 * @author paul_merker@hotmail.com
 * @version 1.0.1
 *
 */
public class NeuralNetwork {

	public static final float LEARNING_CONSTANT = 0.5f;
    
    protected int inputCount;
    protected int outputCount;
    protected int hiddenCount;
    protected int layerCount;
    
    Layer[] layers;
    
    /**
     * NeuralNetwork class can be instanciated with given parameters. The parameters adjust
     * the NN accordingly. <br> Please Note the parameter explanation: <br>
     * <ul>
     * <li>	int inputs: number of input neurons in the input layer </li>
     * <li>	int outputs: number of output neurons in the output layer</li>
     * <li>	int hidden: number of hidden neurons in <b> one </b> hidden layer (repeated for all layers)</li>
     * <li>	int layers: number of all layers in total (input + hidden + output). </li>
     * </ul>
     * If a NN with 3 hidden layers shall be constructed, the param int layers has to equal 5.
     */
    public NeuralNetwork(int inputs, int outputs, int hidden, int layers) {
    		this.inputCount = inputs;
    		this.outputCount = outputs;
    		this.hiddenCount = hidden;
    		this.layerCount = layers;
    		
    		// initialize layers and setting layer properties
    	 	this.layers = new Layer[this.layerCount];
    	 	this.layers[0] = new InputLayer(this.inputCount+1);
    	 	this.layers[this.layerCount-1] = new OutputLayer(this.outputCount);
    	 	
    		for(int i = 1; i < this.layerCount-1; ++i) {
    			this.layers[i] = new HiddenLayer(this.hiddenCount+1);
    		}
    		
    		// Connect input layer to hidden layer
    		InputLayer inp = ((InputLayer) this.layers[0]);
    		HiddenLayer hid = ((HiddenLayer) this.layers[1]);
    		
    		for(int i = 0; i < this.inputCount; ++i) {
    			for(int j = 0; j < this.hiddenCount; ++j) {
    				Connection c = new Connection(inp.getInputs()[i], hid.getHidden()[j]);
    				inp.getInputs()[i].addConnection(c);
    				hid.getHidden()[j].addConnection(c);
    			}
    		}
    		
    		// Connect hidden layers
    		for(int i = 1; i < this.layerCount - 2; ++i) {
    			HiddenLayer tmpHid = ((HiddenLayer) this.layers[i]);
    			HiddenLayer tmpHid2 = ((HiddenLayer) this.layers[i + 1]);
    			
    			for(int j = 0; j < this.hiddenCount; ++j) {
    				for(int k = 0; k < this.hiddenCount; ++k) {
    					Connection c = new Connection(tmpHid.getHidden()[j], tmpHid2.getHidden()[k]);
    					tmpHid.getHidden()[j].addConnection(c);
    					tmpHid2.getHidden()[j].addConnection(c);
    				}
    			}
    		}
    		
    		// Connect last hidden layer to output
    		HiddenLayer hd = ((HiddenLayer) this.layers[this.layerCount-2]);
    		OutputLayer out = ((OutputLayer) this.layers[this.layerCount-1]);
    		
    		for(int i = 0; i < this.hiddenCount; ++i) {
    			for(int j = 0; j < this.outputCount; ++j) {
    				Connection c = new Connection(hd.getHidden()[i], out.getOutputs()[j]);
    				hd.getHidden()[i].addConnection(c);
    				out.getOutputs()[j].addConnection(c);
    			}
    		}
    }
    /**
     * Feeds input through the network and calculates most likely output (max)
     * @param input Array of floats
     * @return output - Maximum output of the NN
     */
    public float feedForward(float[] input) {
        
    		// feed the InputLayer
        for (int i = 0; i < this.inputCount; i++) {
            ((InputLayer) this.layers[0]).getInputs()[i].input(input[i]);  
        }
        
        // Each hidden layer neuron calculates its value (length - 1 cause bias)
        for(Layer layer : this.layers) {
        		if(layer instanceof HiddenLayer) {
        			HiddenNeuron[] hnr = ((HiddenLayer) layer).getHidden();
        			for(int i = 0; i < this.hiddenCount; ++i) {
        				hnr[i].calcOutput();
        			}
        		}
        }

        // output neuron calculates value from all hidden neurons
        for (int i = 0; i < this.outputCount; i++) {
            ((OutputLayer) this.layers[this.layerCount-1]).getOutputs()[i].calcOutput();
        }
        
        return max(((OutputLayer) this.layers[this.layerCount-1]).getOutputs());
    }
    
    /**
     * Trains the network thorugh backpropagation through all layers.
     * The class introduces a <b>LEARNING_CONSTANT</b> with which aggressivness can be adjusted
     * @param inputs Array of floats representing input data set
     * @param expected Expected output for given input set
     * @return
     */
 // complicated backpropagation stuff -> adjust the weights
    public float train(float[] inputs, float expected) {
        // first guess to calculate error
    		float result = feedForward(inputs);
        
        // dOutput calculates with derivative of activation time error
        float deltaOutput = result * (1 - result) * (expected - result);
        
        // BACKPROPOGATION
        // adjust weights from output to hidden (hence the name backpropagation)
        for(OutputNeuron otp : ((OutputLayer) this.layers[this.layerCount-1]).getOutputs()) {
	        Collection connections = otp.getConnections();
	        
	        for (int i = 0; i < connections.size(); ++i) {
	            Connection c = (Connection) ((ArrayList) connections).get(i);
	            // calculate new weight for each PREDECESSOR neuron
	            Neuron neuron = c.getFrom();
	            float nOutput = neuron.getOutput();
	            
	            // formula
	            float deltaWeight = nOutput * deltaOutput;
	            c.adjustWeight(LEARNING_CONSTANT * deltaWeight);
	            // NOTE: deltaWeight can be negative !
	            // LEARNING_CONSTANT describes value of derivative of neural network learning
	        }
	        
	        // adjust hidden weights
	        for(int l = this.layerCount - 1; l >= 1; --l) {
	        		if(this.layers[l] instanceof HiddenLayer) {
	        			HiddenNeuron[] hid = ((HiddenLayer) this.layers[l]).getHidden();
	        			
			        for (int i = 0; i < this.hiddenCount; i++) {
			            connections = hid[i].getConnections();
			            float sum  = 0;
			            
			            for (int j = 0; j < connections.size(); j++) {
			                Connection c = (Connection) ((ArrayList) connections).get(j);
			                if (c.getFrom() == hid[i]) {
			                    sum += c.getWeight() * deltaOutput;
			                }
			            }
			            
			            for (int j = 0; j < connections.size(); j++) {
			            	
			                Connection c = (Connection) ((ArrayList) connections).get(j);
			                if (c.getTo() == hid[i]) {
			                    float op = hid[i].getOutput();
			                    float deltaHidden = op * (1 - op);
			                    deltaHidden *= sum;
			                    Neuron neuron = c.getFrom();
			                    float deltaWeight = neuron.getOutput() * deltaHidden;
			                    c.adjustWeight(LEARNING_CONSTANT * deltaWeight);
			                }
			            } 
			        }
		        }
	        }
        }

        return result;
    }
    
    /**
     * Returns the maximum possible value of all values across output layer neurons.
     * @param output Array of OutputNeurons for evaluation
     * @return maximum - Maximum value of all Neurons of type Float
     */
    private static float max(OutputNeuron[] output) {
    		float record = 0f;
    		
    		for(OutputNeuron onr : output) {
    			if(onr.getOutput() > record)
    				record = onr.getOutput();
    		}
    		return record;
    }
    
}
