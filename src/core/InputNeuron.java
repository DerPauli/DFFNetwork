package core;

public class InputNeuron extends Neuron {
	
	// constructor chain to declare non bias neuron
	public InputNeuron() {
		super();
    }
    
	// bias input neuron
    public InputNeuron(int i) {
        super(i);
    }

    // give input to input layer neuron
    public void input(float d) {
        output = d;
    }
}
