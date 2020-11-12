/*
 * A class that searches a 2D array for strings in all directions: horizontally, 
 * vertically, and diagonally. It returns the number of strings found as an integer.
 * 
 * @author: Mark Zarak, Nov 2020
 */

package ca.sheridancollege.squarematrix;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.sheridancollege.database.DatabaseAccess;

@Service
public class SquareMatrixSearch {

	@Autowired
	private DatabaseAccess ds;

	// Search 2D array squareMatrix for matching strings in any direction
	public int searchForMatch(String word) {

		// Convert search string to upper case to match 2D array
		word = word.toUpperCase();

		// Retrieve player's 2D array from database
		char[][] squareMatrix = SquareMatrixCreator.getSquareMatrixFromString(ds.getPlayer().getLength(), ds.getPlayer().getSquareMatrixString());

		// Begin search through array for first character of the string
		int wordsFound = 0;
		for (int floor = 0; floor < squareMatrix.length; ++floor) {
			for (int room = 0; room < squareMatrix[0].length; ++room) {
				// If a match is found, check for remaining characters from all directions and return number of strings found
				if (squareMatrix[floor][room] == word.charAt(0) && wordMatch(squareMatrix, floor, room, word) > 0) {
					wordsFound += wordMatch(squareMatrix, floor, room, word);
				}
			}
		}

		return wordsFound;
	}

	// Returns number of matching Strings in the 2D array squareMatrix
	public static int wordMatch(char[][] squareMatrix, int floor, int room, String word) {

		int wordsFound = 0;
		int indexOfSearch = 1; // Searches from second character in string
		int i = floor;
		int j = room;

		// Search vertical rows down with 2D matrix boundary check
		while (indexOfSearch != word.length() && i + 1 < squareMatrix.length) {

			boolean matchFound = word.charAt(indexOfSearch) == squareMatrix[i + 1][j];
			// Increment character search if match found, otherwise reset values and break
			if (matchFound) {
				i++;
				indexOfSearch++;
			} else {
				i = floor;
				indexOfSearch = 1;
				break;
			}
		}

		// Resets values and increments wordsFound if string was found and contains more than one character
		if (indexOfSearch == word.length() && word.length() > 1) {
			i = floor;
			indexOfSearch = 1;
			wordsFound++;
		}

		// Search vertical rows up with 2D matrix boundary check
		while (indexOfSearch != word.length() && i - 1 >= 0) {

			// Returns true if the next character of the search string is a match
			boolean matchFound = word.charAt(indexOfSearch) == squareMatrix[i - 1][j];
			// Increment character search if match found, otherwise reset values and break
			if (matchFound) {
				i--;
				indexOfSearch++;
			} else {
				indexOfSearch = 1;
				i = floor;
				break;
			}
		}

		// Resets values and increments wordsFound if string was found and contains more than one character
		if (indexOfSearch == word.length() && word.length() > 1) {
			i = floor;
			indexOfSearch = 1;
			wordsFound++;
		}

		// Search horizontal right with 2D matrix boundary check
		while (indexOfSearch != word.length() && j + 1 < squareMatrix[i].length) {

			boolean matchFound = word.charAt(indexOfSearch) == squareMatrix[i][j + 1];
			// Increment character search if match found, otherwise reset values and break
			if (matchFound) {
				j++;
				indexOfSearch++;
			} else {
				indexOfSearch = 1;
				j = room;
				break;
			}
		}

		// Resets values and increments wordsFound if string was found and contains more than one character
		if (indexOfSearch == word.length() && word.length() > 1) {
			j = room;
			indexOfSearch = 1;
			wordsFound++;
		}

		// Search horizontal left with 2D matrix boundary check
		while (indexOfSearch != word.length() && j - 1 >= 0) {

			boolean matchFound = word.charAt(indexOfSearch) == squareMatrix[i][j - 1];
			// Increment character search if match found, otherwise reset values and break
			if (matchFound) {
				j--;
				indexOfSearch++;
			} else {
				indexOfSearch = 1;
				j = room;
				break;
			}
		}

		// Search vertically down and diagonally left rows with 2D matrix boundary check
		while (indexOfSearch != word.length() && i + 1 < squareMatrix.length && j - 1 >= 0) {

			boolean matchFound = word.charAt(indexOfSearch) == squareMatrix[i + 1][j - 1];
			// Increment character search if match found, otherwise reset values and break
			if (matchFound) {
				i++;
				j--;
				indexOfSearch++;
			} else {
				i = floor;
				j = room;
				indexOfSearch = 1;
				break;
			}
		}

		// Resets values and increments wordsFound if string was found and contains more than one character
		if (indexOfSearch == word.length() && word.length() > 1) {
			i = floor;
			j = room;
			indexOfSearch = 1;
			wordsFound++;
		}

		// Search vertically down and diagonally right rows with 2D matrix boundary check
		while (indexOfSearch != word.length() && i + 1 < squareMatrix.length && j + 1 < squareMatrix[i].length) {

			boolean matchFound = word.charAt(indexOfSearch) == squareMatrix[i + 1][j + 1];
			// Increment character search if match found, otherwise reset values and break
			if (matchFound) {
				i++;
				j++;
				indexOfSearch++;
			} else {
				i = floor;
				j = room;
				indexOfSearch = 1;
				break;
			}
		}

		// Resets values and increments wordsFound if string was found and contains more than one character
		if (indexOfSearch == word.length() && word.length() > 1) {
			i = floor;
			j = room;
			indexOfSearch = 1;
			wordsFound++;
		}

		// Search vertically up and diagonally left rows with 2D matrix boundary check
		while (indexOfSearch != word.length() && i - 1 >= 0 && j - 1 >= 0) {

			boolean matchFound = word.charAt(indexOfSearch) == squareMatrix[i - 1][j - 1];
			// Increment character search if match found, otherwise reset values and break
			if (matchFound) {
				i--;
				j--;
				indexOfSearch++;
			} else {
				i = floor;
				j = room;
				indexOfSearch = 1;
				break;
			}
		}

		// Resets values and increments wordsFound if string was found and contains more than one character
		if (indexOfSearch == word.length() && word.length() > 1) {
			i = floor;
			j = room;
			indexOfSearch = 1;
			wordsFound++;
		}

		// Search vertically up and diagonally right rows with 2D matrix boundary check
		while (indexOfSearch != word.length() && i - 1 >= 0 && j + 1 < squareMatrix[i].length) {

			boolean matchFound = word.charAt(indexOfSearch) == squareMatrix[i - 1][j + 1];
			// Increment character search if match found, otherwise reset values and break
			if (matchFound) {
				i--;
				j++;
				indexOfSearch++;
			} else {
				i = floor;
				j = room;
				indexOfSearch = 1;
				break;
			}
		}

		// Increments wordsFound if a string of any size was found
		if (indexOfSearch == word.length()) {
			wordsFound++;
		}

		return wordsFound;
	}
}
