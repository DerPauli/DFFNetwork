package layer;

import core.HiddenNeuron;

/**
 * 
 * @author Paul Merker
 * @version 1.0.1
 * Hidden Layer extends Layer class and provides implementations for all layers in between first and last layer.
 *
 */
public class HiddenLayer extends Layer {
	
	private HiddenNeuron[] hidden;
	
	public HiddenLayer(int neuronCount) {
		super(neuronCount);
		this.hidden = new HiddenNeuron[neuronCount];
		this.initialize();
	}
	
	private void initialize() {
		for (int i = 0; i < this.hidden.length-1; ++i) {
            this.hidden[i] = new HiddenNeuron();
        }
		this.hidden[this.hidden.length-1] = new HiddenNeuron(1);
	}
	
	public HiddenNeuron[] getHidden() {
		return this.hidden;
	}

}
