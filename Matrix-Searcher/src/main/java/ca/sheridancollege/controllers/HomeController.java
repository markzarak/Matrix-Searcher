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

	char[][] squareMatrix;

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

		// Create 2D matrix based on user input
		squareMatrix = doSquareMatrix(model, user, length);

		// Add player info to database to track scores
		ds.addPlayer(user.getName(), user.getLength());

		return "play.html";
	}

	// Calculate and display user score
	@GetMapping("/score")
	public String doShowScore(Model model, @ModelAttribute User user, @RequestParam String wordList) {

		int totalWordsFound = 0;
		List<String> wordToSearch = Arrays.asList(wordList.trim().split("\\s*,\\s*"));

		// Search each word entered by user
		for (String word : wordToSearch) {
			totalWordsFound += searchForMatch(word);
		}

		// Calculate score and add to database
		int score = totalWordsFound;
		ds.setScore(score);
		System.out.println("Score: " + score);

		return "score.html";
	}

	// Create 2D matrix filled with random characters
	public char[][] doSquareMatrix(Model model, @ModelAttribute User user, @RequestParam int length) {

		// Create empty 2D array based on user input
		char[][] squareMatrix = new char[length][length];

		// Fill 2D array with random letters of the English alphabet
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

	// Search 2D matrix for matching strings
	public int searchForMatch(String word) {

		int wordsFound = 0;
		// Loop through characters in squareMatrix
		for (int floor = 0; floor < squareMatrix.length; ++floor) {
			for (int room = 0; room < squareMatrix[0].length; ++room) {
				// Initiate search on first character, if matched check the remaining characters
				if (squareMatrix[floor][room] == word.charAt(0) && wordMatch(squareMatrix, floor, room, 1, word)) {
					wordsFound++;
					System.out.println(word + " has been found, score +" + wordsFound + "\n");
				}
			}
		}

		return wordsFound;
	}
	
	// Returns true if there is a matching String in the 2D matrix
	public boolean wordMatch(char[][] squareMatrix, int floor, int room, int index, String word) {
		
		System.out.println("\nfound: " + word.charAt(0) + " at index 0");
		System.out.println("looking for remaining letters: " + word);

		boolean found = false;
		index = 1;
		int i = floor;
		int j = room;
		// Search vertical down with 2D matrix boundary check
		while (index != word.length() && !(i < 0 || i >= squareMatrix.length || j < 0 || j >= squareMatrix[i].length)) {
			System.out.println("\n>Search vertical down");
			
			System.out.println(">looking for:" + word.charAt(index) + " in floor:" + floor + ", room:" + room);
			System.out.println(">squareMatrix has:" + squareMatrix[i + 1][j]);
			boolean matchFound = word.charAt(index) == squareMatrix[i + 1][j];
			System.out.println(">matchFound:" + matchFound);
			
			if (matchFound) {
				System.out.println(">>floor: " + i + " #Index: " + index + " added letter: " + word.charAt(index));
				i++;					
				index++;
			} else {
				index = 1;
				i = floor;
				j = room;
				break;
			}
		}
		
		// Search vertical up with 2D matrix boundary check
		while (index != word.length() && !(i < 0 || i >= squareMatrix.length || j < 0 || j >= squareMatrix[i].length)) {
			System.out.println("\n>Search vertical up");
			
			System.out.println(">looking for:" + word.charAt(index) + " in floor:" + floor + ", room:" + room);
			System.out.println(">squareMatrix has:" + squareMatrix[i - 1][j]);
			boolean matchFound = word.charAt(index) == squareMatrix[i - 1][j];
			System.out.println(">matchFound:" + matchFound);
			
			if (matchFound) {
				System.out.println(">>floor: " + i + " #Index: " + index + " added letter: " + word.charAt(index));
				i--;					
				index++;
			} else {
				index = 1;
				i = floor;
				j = room;
				break;
			}
		}
		
		// Search horizontal right with 2D matrix boundary check
		while (index != word.length() && !(i < 0 || i >= squareMatrix.length || j < 0 || j >= squareMatrix[i].length)) {
			System.out.println("\n>Search  horizontal right");
			
			System.out.println(">looking for:" + word.charAt(index) + " in floor:" + floor + ", room:" + room);
			System.out.println(">squareMatrix has:" + squareMatrix[i][j + 1]);
			boolean matchFound = word.charAt(index) == squareMatrix[i][j + 1];
			System.out.println(">matchFound:" + matchFound);
			
			if (matchFound) {
				System.out.println(">>floor: " + i + " #Index: " + index + " added letter: " + word.charAt(index));
				j++;					
				index++;
			} else {
				index = 1;
				i = floor;
				j = room;
				break;
			}
		}
		
		// If all matching characters are found by the end of a String, the word is found
		if (index == word.length()) {
			System.out.println(">>WINNER...exiting loop<<");
			found = true;
		}

		// Search horizontal left with 2D matrix boundary check
		while (index != word.length() && !(i < 0 || i >= squareMatrix.length || j < 0 || j >= squareMatrix[i].length)) {
			System.out.println("\n>Search  horizontal left");
			
			System.out.println(">looking for:" + word.charAt(index) + " in floor:" + floor + ", room:" + room);
			System.out.println(">squareMatrix has:" + squareMatrix[i][j - 1]);
			boolean matchFound = word.charAt(index) == squareMatrix[i][j - 1];
			System.out.println(">matchFound:" + matchFound);
			
			if (matchFound) {
				System.out.println(">>floor: " + i + " #Index: " + index + " added letter: " + word.charAt(index));
				j--;					
				index++;
			} else {
				index = 1;
				i = floor;
				j = room;
				break;
			}
		}
		
		// If all matching characters are found by the end of a String, the word is found
		if (index == word.length()) {
			System.out.println(">>WINNER...exiting loop<<");
			found = true;
		}
		
		System.out.println("Returning value: " + found);
		return found;
	}

}

