/*
 * Home Controller
 * 
 * @author: Mark Zarak, Oct 2020
 */

package ca.sheridancollege.controllers;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import ca.sheridancollege.beans.User;
import ca.sheridancollege.database.DatabaseAccess;

@Controller
public class HomeController {

	// An empty 2D array that will hold the users randomly generated letters for each round
	char[][] squareMatrix;

	// Database access for CRUD operations on user information
	@Autowired
	private DatabaseAccess ds;

	// Home Page with user registration
	@GetMapping("/") // localhost:8080
	public String goStartGame(Model model) {

		// Create new user and add to Model object
		model.addAttribute("user", new User());

		return "home.html";
	}

	// Create and display 2D matrix
	@GetMapping("/play")
	public String doStartGame(Model model, @ModelAttribute User user, @RequestParam int length) {

		// Create 2D array based on user input
		squareMatrix = doSquareMatrix(model, user, length);

		// Add player info to database to track scores
		ds.addPlayer(user.getName(), user.getLength());

		return "play.html";
	}

	// Calculate and display user score
	@GetMapping("/score")
	public String doShowScore(Model model, @ModelAttribute User user, @RequestParam String wordList) {

		// Split String into separate words and track number found during search	
		List<String> wordToSearch = Arrays.asList(wordList.trim().split("\\s*,\\s*"));
		int totalWordsFound = 0;
		
		// Begin search for each word
		for (String word : wordToSearch) {
			System.out.println("*****************************word: " + word + "*****************************");
			totalWordsFound += searchForMatch(word);
		}

		// Save score to database
		int score = totalWordsFound;
		ds.setScore(score);
		System.out.println("Score: " + score);

		return "score.html";
	}

	// Create array filled with random characters
	public char[][] doSquareMatrix(Model model, @ModelAttribute User user, @RequestParam int length) {

		// Create empty array based on user input
		char[][] squareMatrix = new char[length][length];

		// Fill array with random letters of the English alphabet
		for (int floor = 0; floor < squareMatrix.length; ++floor) {
			for (int room = 0; room < squareMatrix[0].length; ++room) {
				// Randomly add letter by incrementing char value
				Random random = new Random();
				char randomLetter = (char) (random.nextInt(26) + 'a');
				squareMatrix[floor][room] = randomLetter;
				System.out.print(squareMatrix[floor][room]);
			}
			System.out.println("");
		}
		System.out.println("");
		// Add squareMatrix array to model for Thymeleaf access
		model.addAttribute("squareMatrix", squareMatrix);

		return squareMatrix;
	}

	// Search squareMatrix for matching strings
	public int searchForMatch(String word) {

		int wordsFound = 0;
		// Loop through all characters in array squareMatrix
		for (int floor = 0; floor < squareMatrix.length; ++floor) {
			for (int room = 0; room < squareMatrix[0].length; ++room) {
				// Search for first character of the word, if matched check for remaining characters
				System.out.println("looking for:" + word + " from :[" + (floor) + "], room:[" + room + "]");
				if (squareMatrix[floor][room] == word.charAt(0) && wordMatch(squareMatrix, floor, room, 1, word)) {
					wordsFound++;
					System.out.println(word + " successfully found at floor:[" + floor + "], room:[" + room + "]");
				}
			}
		}

		return wordsFound;
	}
	
	// Returns true if there is a matching String in the 2D array squareMatrix
	public boolean wordMatch(char[][] squareMatrix, int floor, int room, int indexOfSearch, String word) {
		System.out.println("\nfound first character match for : " + word.charAt(0) + " from " + word);

		boolean wordFound = false;
		indexOfSearch = 1;
		int i = floor;
		int j = room;
		
		// Search vertical rows down with 2D matrix boundary check
		while (indexOfSearch != word.length() && i + 1 < squareMatrix.length) {
			System.out.println("\n>>Search vertical down i+1: " + (i+1) + " squareMatrix.length: " + squareMatrix.length);
			
//			System.out.println(">>looking for:" + word.charAt(indexOfSearch) + " from :[" + (i+1) + "], room:[" + j + "], squareMatrix has:" + squareMatrix[i + 1][j]);
			boolean matchFound = word.charAt(indexOfSearch) == squareMatrix[i + 1][j];
//			System.out.println(">>matchFound:" + matchFound);
			
			if (matchFound) {
				i++;					
				indexOfSearch++;
//				System.out.println(">>MATCH: i(floor):" + i + " indexOfSearch: " + indexOfSearch);
			} else {
//				System.out.println(">>NO MATCH: i(floor):" + i + " indexOfSearch: " + indexOfSearch);
				indexOfSearch = 1;
				i = floor;
//				System.out.println(">>RESET: i(floor):" + i + " indexOfSearch: " + indexOfSearch);
				break;
			}
		}
		
		// Search vertical rows up with 2D matrix boundary check
		while (indexOfSearch != word.length() && i - 1 >= 0) {
			System.out.println("\n>>Search vertical up i-1: " + (i-1));
			
			// Returns true if the next character of the search string is a match
//			System.out.println(">>looking for:" + word.charAt(indexOfSearch) + " from :[" + (i-1) + "], room:[" + j + "], squareMatrix has:" + squareMatrix[i - 1][j]);
			boolean matchFound = word.charAt(indexOfSearch) == squareMatrix[i - 1][j];
//			System.out.println(">>matchFound:" + matchFound);
			
			if (matchFound) {
				// Increment search
				i--;					
				indexOfSearch++;
//				System.out.println(">>MATCH: i(floor):" + i + " indexOfSearch: " + indexOfSearch);
			} else {
//				System.out.println(">>NO MATCH: i(floor):" + i + " indexOfSearch: " + indexOfSearch);
				// Reset search
				indexOfSearch = 1;
				i = floor;
//				System.out.println(">>RESET: i(floor):" + i + " indexOfSearch: " + indexOfSearch);
				break;
			}
		}
		
		// Search horizontal right with 2D matrix boundary check
		while (indexOfSearch != word.length() && j + 1 < squareMatrix[i].length) {
			System.out.println("\n>>Search horizontal right j+1: " + (j+1) + " squareMatrix[i].length: " + squareMatrix[i].length);
			
//			System.out.println(">>looking for:" + word.charAt(indexOfSearch) + " from :[" + i + "], room:[" + (j+1) + "], squareMatrix has:" + squareMatrix[i][j + 1]);
			boolean matchFound = word.charAt(indexOfSearch) == squareMatrix[i][j + 1];
//			System.out.println(">>matchFound:" + matchFound);
			
			if (matchFound) {
				j++;					
				indexOfSearch++;
//				System.out.println(">>MATCH: j(room):" + j + " indexOfSearch: " + indexOfSearch);
			} else {
//				System.out.println(">>NO MATCH: j(room):" + j + " indexOfSearch: " + indexOfSearch);
				indexOfSearch = 1;
				j = room;
//				System.out.println(">>RESET: j(room):" + j + " indexOfSearch: " + indexOfSearch);
				break;
			}
		}
		
		// Search horizontal left with 2D matrix boundary check
		while (indexOfSearch != word.length() && j - 1 >= 0) {
			System.out.println("\n>>Search horizontal left j-1: " + (j-1));
			
//			System.out.println(">>looking for:" + word.charAt(indexOfSearch) + " from :[" + i + "], room:[" + (j-1) + "], squareMatrix has:" + squareMatrix[i][j - 1]);
			boolean matchFound = word.charAt(indexOfSearch) == squareMatrix[i][j - 1];
//			System.out.println(">>matchFound:" + matchFound);
			
			if (matchFound) {
				j--;					
				indexOfSearch++;
//				System.out.println(">>MATCH: j(room):" + j + " indexOfSearch: " + indexOfSearch);
			} else {
//				System.out.println(">>NO MATCH: j(room):" + j + " indexOfSearch: " + indexOfSearch);
				indexOfSearch = 1;
				j = room;
//				System.out.println(">>RESET: j(room):" + j + " indexOfSearch: " + indexOfSearch);
				break;
			}
		}
		
		// Search vertically and diagonally left rows down with 2D matrix boundary check
		
		
		// Search vertically and diagonally right rows down with 2D matrix boundary check
		
		
		// Search horizontally and diagonally left rows down with 2D matrix boundary check
		
		
		// Search horizontally and diagonally right rows down with 2D matrix boundary check
		
		
		// If all matching characters are found by the end of a String, the word is found
		if (indexOfSearch == word.length()) {
			System.out.println("!!!!!WINNER!!!!!");
			wordFound = true;
		}
		
		System.out.println("Returning value: " + wordFound);
		return wordFound;
	}

}

