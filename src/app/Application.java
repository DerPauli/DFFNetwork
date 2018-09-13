package app;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.lang.Math;

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
	 * @throws ExecutionException 
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws IOException, InterruptedException, ExecutionException {
		/**
		 * AND NEURAL NETWORK
		 */
		long startTime = System.nanoTime();
		NeuralNetwork nn = new NeuralNetwork(3, 1, 7, 5);	// (inp neurons, outp neurons, hidden neuron, hidden layers)
		TrainerClass trainer = new TrainerClass(nn, 1000000);	// (toTrain, cycles per training set)
		
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
		
		final ExecutorService executorService = Executors.newFixedThreadPool(16);
		final Future ret = executorService.submit(trainer);
		
		ret.get();
		
		//trainer.train();
		
		System.out.println("With Training:");
		System.out.println("(0 , 0 , 1): " + nn.feedForward(new float[] {0, 0, 1}));
		System.out.println("(0 , 1 , 0): " + nn.feedForward(new float[] {0, 1, 0}));
		System.out.println("(1 , 0 , 0): " + nn.feedForward(new float[] {1, 0, 0}));
		System.out.println("(1 , 1 , 1): " + nn.feedForward(new float[] {1, 1, 1}));
		/**
		 * END AND NEURAL NETWORK
		 */
		System.out.println('\n');
		
//		// TODO: distinguish between image of carrot and image of potato
//		NeuralNetwork imgNN = new NeuralNetwork(100*100, 2, 100*100+10, 3);	// inp, outp, hiddensize, layers
//		TrainerClass imgTrainer = new TrainerClass(imgNN, 1000000);	// toTrain, cycles per training set
//		BufferedReader imgReader = new BufferedReader();
//		
//		//try to read the image of a carrot
//		System.out.println(imgTrainer.getBasePath() + "/res/img/TestKarotte.png");
//		imgReader.readImage(imgTrainer.getBasePath() + "/res/img/TestKarotte.png");
//		System.out.println("Without training: " + imgNN.feedForward(imgReader.getPixelArray()));
		
		
		// AND Gate:
		// with trainer:   				  	15.066944229 seconds execution time.
		// with java standard threadpool: 	15.284811500000002 seconds execution time.
		
		
		long endTime   = System.nanoTime();
		long totalTime = endTime - startTime;
		System.out.println(totalTime * Math.pow(10, -9) + " seconds execution time.");
	}

}
