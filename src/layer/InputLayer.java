package layer;

import core.InputNeuron;
/**
 * 
 * @author Paul Merker
 * @version 1.0.1
 * Input Layer extends Layer class and provides implementations for first Layer in NN.
 *
 */
public class InputLayer extends Layer {
	
	private InputNeuron[] input;
	
	public InputLayer(int neuronCount) {
		super(neuronCount);
		this.input = new InputNeuron[neuronCount];
		this.initialize();
	}
	
	private void initialize() {
		for (int i = 0; i < this.input.length-1; ++i) {
            this.input[i] = new InputNeuron();
        }
		this.input[this.input.length-1] = new InputNeuron(1);
	}

	public InputNeuron[] getInputs() {
		return this.input;
	}
}
