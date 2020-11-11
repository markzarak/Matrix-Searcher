package ca.sheridancollege.squarematrix;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.sheridancollege.database.DatabaseAccess;

@Service
public class SquareMatrixSearch {

	@Autowired
	private DatabaseAccess ds;

	// Search squareMatrix for matching strings
	public int searchForMatch(String word) {

		char[][] squareMatrix = SquareMatrixCreator.getSquareMatrixFromString(ds.getPlayer().getLength(), ds.getPlayer().getSquareMatrixString());

		int wordsFound = 0;
		// Loop through all characters in array squareMatrix
		for (int floor = 0; floor < squareMatrix.length; ++floor) {
			for (int room = 0; room < squareMatrix[0].length; ++room) {
				// Search for first character of the word, if matched check for remaining characters
				System.out.println("looking for:" + word + " floor:[" + (floor) + "], room:[" + room + "] has "
						+ squareMatrix[floor][room]);
				if (squareMatrix[floor][room] == word.charAt(0) && wordMatch(squareMatrix, floor, room, 1, word)) {
					wordsFound++;
					System.out.println(word + " successfully found at floor:[" + floor + "], room:[" + room + "]");
				}
			}
		}

		return wordsFound;
	}

	// Returns true if there is a matching String in the 2D array squareMatrix
	public static boolean wordMatch(char[][] squareMatrix, int floor, int room, int indexOfSearch, String word) {
		System.out.println("\nfound first character match for : " + word.charAt(0) + " from " + word);

		boolean wordFound = false;
		indexOfSearch = 1;
		int i = floor;
		int j = room;

		// Search vertical rows down with 2D matrix boundary check
		while (indexOfSearch != word.length() && i + 1 < squareMatrix.length) {
			System.out.println(
					"\n>>Search vertical down");

//				System.out.println(">>looking for:" + word.charAt(indexOfSearch) + " from :[" + (i+1) + "], room:[" + j + "], squareMatrix has:" + squareMatrix[i + 1][j]);
			boolean matchFound = word.charAt(indexOfSearch) == squareMatrix[i + 1][j];
//				System.out.println(">>matchFound:" + matchFound);

			if (matchFound) {
				i++;
				indexOfSearch++;
//					System.out.println(">>MATCH: i(floor):" + i + " indexOfSearch: " + indexOfSearch);
			} else {
//					System.out.println(">>NO MATCH: i(floor):" + i + " indexOfSearch: " + indexOfSearch);
				indexOfSearch = 1;
				i = floor;
//					System.out.println(">>RESET: i(floor):" + i + " indexOfSearch: " + indexOfSearch);
				break;
			}
		}

		// Search vertical rows up with 2D matrix boundary check
		while (indexOfSearch != word.length() && i - 1 >= 0) {
			System.out.println("\n>>Search vertical up");

			// Returns true if the next character of the search string is a match
//				System.out.println(">>looking for:" + word.charAt(indexOfSearch) + " from :[" + (i-1) + "], room:[" + j + "], squareMatrix has:" + squareMatrix[i - 1][j]);
			boolean matchFound = word.charAt(indexOfSearch) == squareMatrix[i - 1][j];
//				System.out.println(">>matchFound:" + matchFound);

			if (matchFound) {
				// Increment search
				i--;
				indexOfSearch++;
//					System.out.println(">>MATCH: i(floor):" + i + " indexOfSearch: " + indexOfSearch);
			} else {
//					System.out.println(">>NO MATCH: i(floor):" + i + " indexOfSearch: " + indexOfSearch);
				// Reset search
				indexOfSearch = 1;
				i = floor;
//					System.out.println(">>RESET: i(floor):" + i + " indexOfSearch: " + indexOfSearch);
				break;
			}
		}

		// Search horizontal right with 2D matrix boundary check
		while (indexOfSearch != word.length() && j + 1 < squareMatrix[i].length) {
			System.out.println("\n>>Search horizontal right");

//				System.out.println(">>looking for:" + word.charAt(indexOfSearch) + " from :[" + i + "], room:[" + (j+1) + "], squareMatrix has:" + squareMatrix[i][j + 1]);
			boolean matchFound = word.charAt(indexOfSearch) == squareMatrix[i][j + 1];
//				System.out.println(">>matchFound:" + matchFound);

			if (matchFound) {
				j++;
				indexOfSearch++;
//					System.out.println(">>MATCH: j(room):" + j + " indexOfSearch: " + indexOfSearch);
			} else {
//					System.out.println(">>NO MATCH: j(room):" + j + " indexOfSearch: " + indexOfSearch);
				indexOfSearch = 1;
				j = room;
//					System.out.println(">>RESET: j(room):" + j + " indexOfSearch: " + indexOfSearch);
				break;
			}
		}

		// Search horizontal left with 2D matrix boundary check
		while (indexOfSearch != word.length() && j - 1 >= 0) {
			System.out.println("\n>>Search horizontal left");

//				System.out.println(">>looking for:" + word.charAt(indexOfSearch) + " from :[" + i + "], room:[" + (j-1) + "], squareMatrix has:" + squareMatrix[i][j - 1]);
			boolean matchFound = word.charAt(indexOfSearch) == squareMatrix[i][j - 1];
//				System.out.println(">>matchFound:" + matchFound);

			if (matchFound) {
				j--;
				indexOfSearch++;
//					System.out.println(">>MATCH: j(room):" + j + " indexOfSearch: " + indexOfSearch);
			} else {
//					System.out.println(">>NO MATCH: j(room):" + j + " indexOfSearch: " + indexOfSearch);
				indexOfSearch = 1;
				j = room;
//					System.out.println(">>RESET: j(room):" + j + " indexOfSearch: " + indexOfSearch);
				break;
			}
		}

		// Search vertically and diagonally left rows down with 2D matrix boundary check

		// Search vertically and diagonally right rows down with 2D matrix boundary
		// check

		// Search horizontally and diagonally left rows down with 2D matrix boundary
		// check

		// Search horizontally and diagonally right rows down with 2D matrix boundary
		// check

		// If all matching characters are found by the end of a String, the word is
		// found
		if (indexOfSearch == word.length()) {
			System.out.println("!!!!!WINNER!!!!!");
			wordFound = true;
		}

		System.out.println("Returning value: " + wordFound);
		return wordFound;
	}

}
