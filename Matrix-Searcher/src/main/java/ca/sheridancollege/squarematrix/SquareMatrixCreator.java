/*
 * A class that creates a 2D array filled with random characters and returns
 * it as a nested array or String. 
 * 
 * @author: Mark Zarak, Nov 2020
 */

package ca.sheridancollege.squarematrix;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;

import ca.sheridancollege.beans.User;
import ca.sheridancollege.database.DatabaseAccess;

public class SquareMatrixCreator {

	@Autowired
	private DatabaseAccess ds;

	// A method that returns a new 2D array filled with random characters
	public static char[][] doSquareMatrix(User user) {

		// Create empty array based on user input
		char[][] squareMatrix = new char[user.getLength()][user.getLength()];

		// Fill array with random letters of the English alphabet
		for (int i = 0; i < squareMatrix.length; ++i) {
			for (int j = 0; j < squareMatrix[0].length; ++j) {
				// Randomly add letter by incrementing char value
				Random random = new Random();
				char randomLetter = (char) (random.nextInt(26) + 'a');
				squareMatrix[i][j] = Character.toUpperCase(randomLetter);
			}
		}

		return squareMatrix;
	}

	// A method that returns the String form of a 2D array
	public static String doSquareMatrixString(User user) {

		String squareMatrixString="";
		for (int i = 0; i < user.getSquareMatrix().length; i++) {
			for (int j = 0; j < user.getSquareMatrix()[i].length; j++) {
				squareMatrixString += user.getSquareMatrix()[i][j];
			}
		}
		
		return squareMatrixString;
	}

	// A method that returns a 2D array from String form
	public static char[][] getSquareMatrixFromString(int length, String squareMatrixString) {

		char[] characterArray = squareMatrixString.toCharArray();

		char[][] squareMatrix = new char[length][length];

		// Fill 2D array
		int count = 0;
		for (int i = 0; i < squareMatrix.length; ++i) {
			for (int j = 0; j < squareMatrix[i].length; ++j) {
				squareMatrix[i][j] = characterArray[count];
				count++;
			}
		}

		return squareMatrix;
	}

}
