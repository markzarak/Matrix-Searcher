/*
 * Home Controller
 * 
 * @author: Mark Zarak, Nov 2020
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
	
	// Home Page for registered users
	@GetMapping("/home")
	public String goContinueGame(Model model, @ModelAttribute User user) {
		
		user.setName(ds.getPlayer().getName());
		user.setLength(ds.getPlayer().getLength());
		model.addAttribute("user", user);

		return "registered-user-home.html";
	}

	// Create and display 2D matrix
	@GetMapping("/play")
	public String doAddUser(Model model, @ModelAttribute User user) {

		// Create 2D array based on user input
		char[][] squareMatrix = SquareMatrixCreator.doSquareMatrix(user);
		user.setSquareMatrix(squareMatrix);

		// Add squareMatrix array to model for Thymeleaf access
		model.addAttribute("squareMatrix", squareMatrix);

		// Add new player or update current player in database, depending on user choice
		if (ds.getPlayer().isContinuingGame()) {
			ds.updatePlayer(user);
		} else {
			ds.addPlayer(user);
		}
		
		return "play.html";
	}

	// Calculate and display results
	@GetMapping("/score")
	public String viewScore(Model model, @ModelAttribute User user, @RequestParam String wordList) {

		// Split String into separate words using comma delimiter
		List<String> wordToSearch = Arrays.asList(wordList.trim().split("\\s*,\\s*"));
		
		// Begin search for each word
		int totalWordsFound = 0;
		for (String word : wordToSearch) {
			if (word.length()> 0) {
				totalWordsFound += squareMatrixSearch.searchForMatch(word);
			}
			
		}
		
		// Retrieve 2D array from database
		char[][] squareMatrix = SquareMatrixCreator.getSquareMatrixFromString(ds.getPlayer().getLength(),ds.getPlayer().getSquareMatrixString());
		
		// Save score to database 
		ds.setScore(totalWordsFound, squareMatrix.length);
		
		// Add data to User object
		user.setScore(ds.getPlayer().getScore());
		user.setSquareMatrix(squareMatrix);
		user.setWordList(wordList);
		user.setTotalWordsFound(totalWordsFound);
		
		// Add objects to model for Thymeleaf access
		model.addAttribute("user", user);	
		model.addAttribute("players", ds.getTopPlayers());

		return "score.html";
	}
	
	// Continue game
	@GetMapping("/continuegame")
	public String continueGame() {

		ds.setContinuingGame(true);
		
		return "redirect:/home";
	}
	
	// New game
	@GetMapping("/newgame")
	public String newGame() {
		
		ds.setContinuingGame(false);
		
		return "redirect:/";
	}

}
