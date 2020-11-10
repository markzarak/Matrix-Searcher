/*
 * Home Controller
 * 
 * @author: Mark Zarak, Oct 2020
 */

package ca.sheridancollege.controllers;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import ca.sheridancollege.beans.User;
import ca.sheridancollege.database.DatabaseAccess;
import ca.sheridancollege.squarematrix.SquareMatrixCreator;
import ca.sheridancollege.squarematrix.SquareMatrixSearch;

@Controller
public class HomeController {

	@Autowired
	private DatabaseAccess ds;
	
	@Autowired
	private SquareMatrixSearch squareMatrixSearch;

	// Home Page with user registration
	@GetMapping("/") // localhost:8080
	public String goStartGame(Model model) {
		model.addAttribute("user", new User());

		return "home.html";
	}

	// Create and display 2D matrix
	@GetMapping("/play")
	public String doAddUser(Model model, @ModelAttribute User user) {

		// Create 2D array based on user input
		char[][] squareMatrix = SquareMatrixCreator.doSquareMatrix(user);
		user.setSquareMatrix(squareMatrix);

		// Add squareMatrix array to model for Thymeleaf access
		model.addAttribute("squareMatrix", squareMatrix);

		// Add player info to database
		ds.addPlayer(user);

		return "play.html";
	}

	// Calculate and display score
	@GetMapping("/score")
	public String viewScore(Model model, @RequestParam String wordList) {

		// Split String into separate words using comma delimiter
		List<String> wordToSearch = Arrays.asList(wordList.trim().split("\\s*,\\s*"));
		
		// Begin search for each word
		int totalWordsFound = 0;
		for (String word : wordToSearch) {
			System.out.println("*****************************word: " + word + "*****************************");
			totalWordsFound += squareMatrixSearch.searchForMatch(word);
		}

		// Save score to database
		ds.setScore(totalWordsFound);
		
		// Add players to model for Thymeleaf access
		model.addAttribute("players", ds.getPlayers());

		return "score.html";
	}

}
