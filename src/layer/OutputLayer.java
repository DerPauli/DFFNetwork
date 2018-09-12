package layer;

import core.OutputNeuron;

/**
 * 
 * @author Paul Merker
 * @version 1.0.1
 * Output Layer extends Layer class and provides implementations for last Layer in NN.
 *
 */
public class OutputLayer extends Layer {
	
	private OutputNeuron[] output;
	
	public OutputLayer(int neuronCount) {
		super(neuronCount);
		this.output = new OutputNeuron[neuronCount];
		this.initialize();
	}
	
	private void initialize() {
		for (int i = 0; i < this.output.length; ++i) {
            this.output[i] = new OutputNeuron();
        }
	}
	
	public OutputNeuron[] getOutputs() {
		return this.output;
	}

}
