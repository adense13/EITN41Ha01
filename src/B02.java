
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class B02 {
	
	/*
	 * u = där finns 2^u bins
	 * k = nbr of collisions needed
	 * c = nbr of coins before finished
	 */
	public double run(int u, int k, int c){
		int nbrBins = (int) Math.pow(2, u);
//		System.out.println("2^u: "+nbrBins);
		
		int[] bins = new int[nbrBins];
		Random rand = new Random();
		
		double generatedCoins = 0;
		double ballsThrown = 0;
		
		while(generatedCoins < c){
			int currentIndex = rand.nextInt(nbrBins);
			ballsThrown++;
			bins[currentIndex] = bins[currentIndex] + 1;
			if(bins[currentIndex] == k){
				generatedCoins++;
			}
//			System.out.println("Current index: "+currentIndex);
//			System.out.println("Coins generated: "+generatedCoins);
//			System.out.println("Balls thrown: "+ballsThrown);
//			System.out.println("---------------------------");
		}
		return ballsThrown;
	}
	 
	private double calcDeviation(ArrayList<Double> array, double mean){
		double sum = 0;
		for (double element : array) {
		    sum = sum + Math.pow(( element ) - mean, 2);
		}
		return Math.sqrt(sum/array.size());
	}
	
	private void startSimulation(int runsBeforeEval, int coinTarget, double desiredWidth, int u, int k){
		double sum = 0;
		ArrayList<Double> list = new ArrayList();
		double runsSoFar = 0;
		double currentWidth = 0;
		double currentMean = 0;
		do{
			for(int i=0; i<runsBeforeEval; i++){
				double res = run(u, k, coinTarget);
				list.add( res );
				sum = sum + res;
				runsSoFar++;
				
			}
			currentMean = sum/list.size();
			//System.out.println("CurrentMean: "+currentMean);
			double lambda = 3.66;
			double stdDeviation = calcDeviation(list, currentMean);
			//System.out.println("StandardDeviation: "+stdDeviation);
			currentWidth = 2 * lambda * (stdDeviation/Math.sqrt(runsSoFar));
			//System.out.println("CurrentWidth: "+currentWidth);
		}while (currentWidth > desiredWidth);
		
		System.out.println("Coins: "+coinTarget+", Mean: "+currentMean+", Width: "+currentWidth + " Runs: "+runsSoFar);
	}
	
	public static void main(String[] args){
		B02 test = new B02();
		
		long startTime = System.currentTimeMillis();
		
		int desiredWidth = 7868;
		//runsBeforeEval, coinTarget, desiredWidth, u, k
		//test.startSimulation(2, 10000, desiredWidth, 20, 7);
		test.startSimulation(20, 100, desiredWidth, 19, 7);
		System.out.println("Time elapsed: "+(System.currentTimeMillis()-startTime));
		
		
//		test.startSimulation(100, 100, desiredWidth);
//		System.out.println("Time elaped: "+(System.currentTimeMillis()-startTime));
//		test.startSimulation(1000, 10000, desiredWidth);
//		System.out.println("Time elapsed: "+(System.currentTimeMillis()-startTime));
	}

}
