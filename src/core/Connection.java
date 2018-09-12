package core;

public class Connection {

	private Neuron from;
    private Neuron to;
    private float weight;

    public Connection(Neuron a_, Neuron b_) {
        this.from = a_;
        this.to = b_;
        this.weight = (float) Math.random() * 2 - 1;
    }

    public Neuron getFrom() {
        return this.from;
    }
    
    public Neuron getTo() {
        return this.to;
    }  
    
    public float getWeight() {
        return this.weight;
    }

    public void adjustWeight(float deltaWeight) {
        this.weight += deltaWeight;
    }
    
}
