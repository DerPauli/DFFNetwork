package app;

import java.io.IOException;

import rnn.NeuralNetwork;
import trainer.TrainerClass;
import util.BufferedReader;

/**
 * 
 * @author Paul Merker
 * @version 1.0.1
 * The Application class provides instanciation of the NN, aswell as the given Trainer class.
 */
public class Application {
	
	
	/**
	 * Main Application class
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		/**
		 * AND NEURAL NETWORK
		 */
		NeuralNetwork nn = new NeuralNetwork(3, 1, 5, 3);	// inp, outp, hiddensize, layers
		TrainerClass trainer = new TrainerClass(nn, 1000000);	// toTrain, cycles per training set
		
		// create training set
		trainer.setTrainingData(new float[] {0, 0, 0}, 0);
		trainer.setTrainingData(new float[] {0, 0, 1}, 0);
		trainer.setTrainingData(new float[] {0, 1, 0}, 0);
		trainer.setTrainingData(new float[] {0, 1, 1}, 0);
		trainer.setTrainingData(new float[] {1, 0, 0}, 0);
		trainer.setTrainingData(new float[] {1, 0, 1}, 0);
		trainer.setTrainingData(new float[] {1, 1, 0}, 0);
		trainer.setTrainingData(new float[] {1, 1, 1}, 1);
		
		System.out.println("Without Training:");
		System.out.println("(0 , 0 , 1): " + nn.feedForward(new float[] {0, 0, 1}));
		System.out.println("(0 , 1 , 0): " + nn.feedForward(new float[] {0, 1, 0}));
		System.out.println("(1 , 0 , 0): " + nn.feedForward(new float[] {1, 0, 0}));
		System.out.println("(1 , 1 , 1): " + nn.feedForward(new float[] {1, 1, 1}));
		
		trainer.train();
		
		System.out.println("With Training:");
		System.out.println("(0 , 0 , 1): " + nn.feedForward(new float[] {0, 0, 1}));
		System.out.println("(0 , 1 , 0): " + nn.feedForward(new float[] {0, 1, 0}));
		System.out.println("(1 , 0 , 0): " + nn.feedForward(new float[] {1, 0, 0}));
		System.out.println("(1 , 1 , 1): " + nn.feedForward(new float[] {1, 1, 1}));
		/**
		 * END AND NEURAL NETWORK
		 */
		System.out.println('\n');
		
		// TODO: distinguish between image of carrot and image of potatoe
		NeuralNetwork imgNN = new NeuralNetwork(100*100, 2, 100*100+10, 3);	// inp, outp, hiddensize, layers
		TrainerClass imgTrainer = new TrainerClass(imgNN, 1000000);	// toTrain, cycles per training set
		BufferedReader imgReader = new BufferedReader();
		
		//try to read the image of a carrot
		System.out.println(imgTrainer.getBasePath() + "/res/img/TestKarotte.png");
		imgReader.readImage(imgTrainer.getBasePath() + "/res/img/TestKarotte.png");
		System.out.println("Without training: " + imgNN.feedForward(imgReader.getPixelArray()));
	}

}
