
/* Programming Fundamentals 
 * Antonio Brown 
 * Programming Assignment 3 - Machine Learning
 */

import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;

public class NearestNeighbor {

	public static void main(String[] args) throws FileNotFoundException {

		System.out.println("Programming Fundamentals");
		System.out.println("NAME: Antonio Brown");
		System.out.println("PROGRAMMING ASSIGNMENT 3");
		System.out.println(" ");

		Scanner userScan = new Scanner(System.in);

		// Prompts will ask the user to enter file name for it to be then located and
		// imported
		System.out.print("Enter the name of the training file: ");
		File traFile = new File(userScan.next());
		Scanner userTraDta = new Scanner(traFile);
		Scanner userTraLbl = new Scanner(traFile);

		System.out.print("Enter the name of the testing file: ");
		File tstFile = new File(userScan.next());
		Scanner userTstDta = new Scanner(tstFile);
		Scanner userTstLbl = new Scanner(tstFile);

		// Creating Arrays
		double[][] traDta = new double[75][4];
		double[][] tstDta = new double[75][4];
		String[] traLbl = new String[75];
		String[] tstLbl = new String[75];
		// Array will hold predictions
		String[] predictions = new String[75];

		// Takes imported files and write to an array
		traDta = locateDta(userTraDta, traDta);
		tstDta = locateDta(userTstDta, tstDta);
		traLbl = locateLbl(userTraLbl, traLbl);
		tstLbl = locateLbl(userTstLbl, tstLbl);

		// This loop will measure the predications of each class as it cycles through
		// line by line
		int nearestNeighStart = 0;

		for (int a = 0; a < 75; a++) {

			nearestNeighStart = bestFlower(a, tstDta, traDta);

			predictions[a] = traLbl[nearestNeighStart];

		}

		// This method is called to print results
		predictAccuracy(predictions, tstLbl);

		userScan.close();

	}

	// Will create a 2D array from scanner data
	private static double[][] locateDta(Scanner dtaScan, double[][] dtaAll) {

		int douDta = 0;
		String dtaRow;
		String[][] tdData = new String[75][4];

		while (dtaScan.hasNextLine()) {

			dtaRow = dtaScan.nextLine();

			String[] dtaSplit = dtaRow.split(",");

			for (int a = 0; a < 4; a++) {

				tdData[douDta][a] = dtaSplit[a];

				dtaAll[douDta][a] = Double.parseDouble(tdData[douDta][a]);
			}

			douDta++;
		}

		return dtaAll;
	}

	// This method creates string arrays from scanner labels
	private static String[] locateLbl(Scanner lblScan, String[] lblAll) {

		int strLbl = 0;

		while (lblScan.hasNextLine()) {

			String[] seperateString = lblScan.nextLine().split(",");
			lblAll[strLbl] = seperateString[4];
			strLbl++;

		}

		return lblAll;
	}

	// creating distance method to be used to help calculate shortest distance
	private static double calcDist(int tstStart, int traStart, double[][] tra, double[][] tst) {

		double distance = 0.0;

		for (int i = 0; i < 4; i++) {

			distance += Math.pow(tra[traStart][i] - tst[tstStart][i], 2);
		}

		return distance;
	}

	// method that calculates the shortest distance between the training value and
	// the test value
	private static int bestFlower(int tstStart, double[][] tra, double[][] tst) {

		int bestFlo = 0;
		double disTraining = calcDist(tstStart, 0, tra, tst);

		for (int i = 1; i < 75; i++) {

			if (calcDist(tstStart, i, tra, tst) < disTraining) {

				disTraining = calcDist(tstStart, i, tra, tst);
				bestFlo = i;
			}
		}
		return bestFlo;
	}

	// This method takes correct classes and calculates accuracy
	private static void predictAccuracy(String[] realLbl, String[] predLbl) {

		double truePredictions = 0;
		int preAcc = 1;

		System.out.println("\nEX#: TRUE LABEL, PREDICTED LABEL ");

		for (int i = 0; i < 75; i++) {
			System.out.println(preAcc + ": " + realLbl[i] + " " + predLbl[i]);

			// If the predicted value is equal to the true value, then counts it as a
			// correct prediction
			if (predLbl[i].equals(realLbl[i])) {
				truePredictions++;
			}
			preAcc++;
		}

		// This calculates the accuracy between the true values and the predicted
		// values.
		double realAccuracy = (truePredictions / realLbl.length);

		System.out.println("ACCURACY: " + realAccuracy);
	}
}