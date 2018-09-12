package trainer;

import java.io.File;
import java.util.HashMap;
import rnn.NeuralNetwork;

/**
 * 
 * @author Paul Merker
 * @version 1.0.1
 * The TrainerClass provides means for the user to train the NN automatically.
 * After instantiation with given NN the TrainerClass.setTrainingData() method has to be called.
 * After setting the TrainingData with parameters, TrainerClass.train() can be accessed.
 */
public class TrainerClass {
	
	private NeuralNetwork nn;
	private int times;
	private HashMap<float[], Float> data;
	private String basePath = new File("").getAbsolutePath();
	
	/**
	 * Constructor of TrainerClass. Expects two inputs and sets fields accordingly.
	 * @param toTrain NeuralNetwork type to train
	 * @param times Numbers of iterations per given training set
	 */
	public TrainerClass(NeuralNetwork toTrain, int times) {
		this.nn = toTrain;
		this.times = times;
		this.data = new HashMap<float[], Float>();
	}
	
	/**
	 * The train method trains the NN with numbers of iterations given in Constructor.
	 * @see TrainerClass#TrainerClass(NeuralNetwork toTrain, int times)
	 */
	public void train() {
		// train through all given sets
		int i = 0;
		while(i < this.times) {
			for(float[] input : this.data.keySet()) {
				this.nn.train(input, this.data.get(input));
			}
			++i;
		}
		System.out.println("Done with training the NeuralNetwork");
	}
	
	/**
	 * 
	 * @param input Array of Floats with input training data set
	 * @param output Expected output to given input
	 */
	public void setTrainingData(float[] input, float output) {
		this.data.put(input, output);
	}
	
	public String getBasePath() {
		return this.basePath;
	}
}
