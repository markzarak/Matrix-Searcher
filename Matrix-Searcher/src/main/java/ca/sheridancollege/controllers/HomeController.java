/*
 * Home Controller
 * 
 * @author: Mark Zarak, Oct 2020
 */

package ca.sheridancollege.controllers;

import java.util.Random;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import ca.sheridancollege.beans.User;

@Controller
public class HomeController {

	// Home Page with user registration
	@GetMapping("/") // localhost:8080
	public String goStartGame(Model model) {
		
		// Create new user and add to Model object		
		model.addAttribute("user", new User());
		
		return "home.html";
	}
	
	// Create and display 2D matrix 
	@GetMapping("/play")
	public String doStartGame(Model model, @ModelAttribute("user") User user, @RequestParam int length) {
		
		// Create empty 2D array based on user input
		char[][] squareMatrix = new char[length][length];
		
		// Fill squareMatrix array with random letters of the English alphabet
		for (int floor = 0; floor < squareMatrix.length; ++floor) {
			for (int room = 0; room < squareMatrix[0].length; ++room) {
				// Randomly add letter by incrementing char value
				Random random = new Random();
				char randomLetter = (char)(random.nextInt(26) + 'a');
				squareMatrix[floor][room] = randomLetter;
				System.out.print(squareMatrix[floor][room]);
			}
			System.out.println("");
		}
		// Add squareMatrix array to model for Thymeleaf access
		model.addAttribute("squareMatrix", squareMatrix);
		
		System.out.println("User from /play: " + user.toString());
		return "play.html";
	}
		
	// Display user score and high score
	@GetMapping("/score")
	public String doShowScore(Model model, @ModelAttribute("user") User user) {
		
		System.out.println("User from /score: " + user.toString());
		return "score.html";
	}	
	
//	// Page for new game
//	@RequestMapping("/new-game")
//	public String d(Model model) {
//		
//		model.addAttribute("user", new User());
//		return "home.html";
//	}
//	
//	// Page to continue game
//	@RequestMapping("/continue-game")
//	public String e(Model model) {
//		
//		return "registered-user-home.html";
//	}
}
